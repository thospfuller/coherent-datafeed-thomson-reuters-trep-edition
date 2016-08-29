package com.coherentlogic.coherent.datafeed.spark.integration.receivers

import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder
import org.springframework.context.annotation.ImportResource
import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException
import org.apache.spark.SparkConf
import com.coherentlogic.coherent.datafeed.domain.MarketPrice
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants
import org.springframework.boot.CommandLineRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import com.coherentlogic.coherent.datafeed.domain.StatusResponse
import net.coherentlogic.coherent.datafeed.examples.ExampleRICS
import org.springframework.messaging.MessageChannel
import org.apache.spark.streaming.dstream.InputDStream
import org.springframework.context.ApplicationContext
import org.springframework.boot.builder.SpringApplicationBuilder
import com.coherentlogic.coherent.datafeed.domain.SessionBean
import org.apache.spark.streaming.StreamingContext
import org.springframework.beans.factory.annotation.Qualifier
import javax.jms.MessageConsumer
import javax.jms.ObjectMessage
import javax.jms.JMSException
import javax.jms.Message
import org.apache.spark.streaming.receiver.Receiver
import java.util.concurrent.atomic.AtomicLong
import org.apache.spark.streaming.Seconds
import org.slf4j.LoggerFactory
import com.coherentlogic.coherent.datafeed.services.ServiceName
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener
import org.apache.spark.storage.StorageLevel
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent
import java.beans.PropertyChangeEvent
import org.springframework.integration.support.MessageBuilder

import com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID

import collection.JavaConversions._
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * A Spark receiver that is used to query Elektron for market price updates and routes this information to a Spark
 * cluster.
 *
 * Submit the receiver via the command line as follows:
 *
 * C:\development\software\spark-2.0.0-bin-hadoop2.7>
 *
 * .\bin\spark-submit.cmd --jars
 *     file://C:/development/projects/release-18.Aug.2016/cdtrep/java/coherent-datafeed-spark-int/target/coherent-datafeed-spark-int-1.0.8-RELEASE.jar
 *     --master local[*]
 *     file://C:/development/projects/release-18.Aug.2016/cdtrep/java/coherent-datafeed-assembly/target/coherent-datafeed-assembly-1.0.8-RELEASE.jar
 *     --class com.coherentlogic.coherent.datafeed.spark.integration.receivers.ElektronMarketPriceReceiver
 *
 * @see <a href="http://nishutayaltech.blogspot.com/2015/04/how-to-run-apache-spark-on-windows7-in.html">How to run
 * Apache Spark on Windows7 in standalone mode</a>
 * 
 * @see <a href="https://databricks.com/blog/2016/07/14/a-tale-of-three-apache-spark-apis-rdds-dataframes-and-datasets.html">A Tale of Three Apache Spark APIs: RDDs, DataFrames, and Datasets: When to use them and why</a>
 * @see <a href="https://people.apache.org/~pwendell/spark-nightly/spark-master-docs/latest/sql-programming-guide.html#creating-dataframes">Spark SQL, DataFrames and Datasets Guide</a>
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
object ElektronMarketPriceReceiver extends scala.App {

  //
  // Below is an example of how the receiver can be used.
  //

  val log = LoggerFactory.getLogger("ElektronMarketPriceReceiverApp")

  val config = new SparkConf()
    .setAppName("tr-elektron-market-price-streaming-integration")
    .setMaster("local[*]")

  val streamingContext = new StreamingContext(config, Seconds(5))

  val dacsId : String = System.getenv(DACS_ID)

  val stream: InputDStream[MarketPrice] = streamingContext.receiverStream(
    new ElektronMarketPriceReceiver (
      dacsId,
      ServiceName.dELEKTRON_DD,
      ExampleRICS.rics
    )
  )

  stream.print()

// Required by the saveAsTextFiles operation.
//
//  System.setProperty("hadoop.home.dir", "C:/development/software/winutils/")
//
//  stream.saveAsTextFiles("C:/Temp/spark-dump/spark-market-price-dump", "txt")

  streamingContext.start()

  streamingContext.awaitTerminationOrTimeout(Long.MaxValue)
}

class ElektronMarketPriceReceiver(dacsId : String, serviceName : ServiceName, rics : Array[String])
    extends Receiver[MarketPrice](StorageLevel.MEMORY_ONLY) {

  var log = LoggerFactory.getLogger(classOf[ElektronMarketPriceReceiver])

  @Override
  def onStart () {

    var applicationContext : ApplicationContext = new ClassPathXmlApplicationContext(
      "classpath*:spring/application-context.xml",
      "classpath*:spring/jmx-beans.xml",
      "classpath*:spring/hornetq-beans.xml",
      "classpath*:spring/marketprice-jms-workflow-beans.xml"
    )

    val messageConsumer : MessageConsumer =
      applicationContext.getBean("marketPriceConsumer").asInstanceOf[MessageConsumer]

    var elektronQueryBuilder : ElektronQueryBuilder = applicationContext.getBean(classOf[ElektronQueryBuilder])

    val sessionBean : SessionBean = elektronQueryBuilder.newSessionBean(dacsId)

    val statusUpdateCtr : AtomicLong = new AtomicLong (0)

    sessionBean.getStatusResponse().addAggregatePropertyChangeListener(
      new AggregatePropertyChangeListener[StatusResponse] () {

         def onAggregatePropertyChangeEvent(event : AggregatePropertyChangeEvent[StatusResponse]) {

           log.info("statusResponse.aggregateUpdate ["+ statusUpdateCtr.incrementAndGet() + "] begins.")

            var propertyChangeEventMap : java.util.Map[String, PropertyChangeEvent]
              = event.getPropertyChangeEventMap()

            for ((key : String, value : PropertyChangeEvent) <- propertyChangeEventMap) {
              log.info("- key: " + key + ", value: " + value)
            }

            log.info("statusResponse.aggregateUpdate ends.")
         }
      }
    )

    elektronQueryBuilder.login(sessionBean)

    queryMarketPriceService (applicationContext)

    val updateThread = new Thread {
      override def run {
        while (!isStopped()) {

          val next = getNextUpdate (messageConsumer, 5000L);

          if (next != null) {
            store(next)
          }
        }
      }
    }

    updateThread.start()
  }

  @Override
  def onStop () {
  }

  private def queryMarketPriceService (applicationContext : ApplicationContext) {

    var elektronQueryBuilder : ElektronQueryBuilder = applicationContext.getBean(classOf[ElektronQueryBuilder])

    var statusUpdateCtr : AtomicLong = new AtomicLong (0)

    var addMarketPriceToQueueChannel : MessageChannel =
      applicationContext.getBean("addMarketPriceToQueueChannel").asInstanceOf[MessageChannel]

    rics.foreach {
      ric =>
      /* We *MUST* acquire the MarketPrice instance from the Spring container because we are using AOP and if we
       * simply create the class directly by calling the ctor, none of the property change events will fire when
       * an update is applied and the same applies to the StatusResponse.
       */
      var marketPrice : MarketPrice = elektronQueryBuilder.newMarketPrice(ric)

      marketPrice.addAggregatePropertyChangeListener(
        new AggregatePropertyChangeListener[MarketPrice] () {

          override def onAggregatePropertyChangeEvent(event : AggregatePropertyChangeEvent[MarketPrice]) {

            var marketPrice : MarketPrice = event.getSource ()

            var clone : MarketPrice = marketPrice.clone().asInstanceOf[MarketPrice]

            addMarketPriceToQueueChannel.send(
                MessageBuilder.withPayload(clone).build()
            )
          }
        }
      )

      var statusResponse : StatusResponse = marketPrice.getStatusResponse()

      statusResponse.addAggregatePropertyChangeListener {
        new AggregatePropertyChangeListener[StatusResponse] () {

          override def onAggregatePropertyChangeEvent (
            event : AggregatePropertyChangeEvent[StatusResponse]) {

            log.info("marketPrice.statusResponse.aggregateUpdate ["+ statusUpdateCtr.incrementAndGet()
              + "] begins for " + marketPrice.getRic)

            var propertyChangeEventMap : java.util.Map[String, PropertyChangeEvent]
              = event.getPropertyChangeEventMap()

            for ((key : String, value : PropertyChangeEvent) <- propertyChangeEventMap) {
              log.info("- key: " + key + ", value: " + value)
            }
          }
        }
      }

      elektronQueryBuilder.query(serviceName, marketPrice);
    }
  }

  private def getNextUpdate (messageConsumer : MessageConsumer, timeout : Long) : MarketPrice = {

    log.info("getNextUpdate: method begins; timeout: " + timeout)

    var nextMessage : Message = null

    try {
        nextMessage = messageConsumer.receive(timeout)
    } catch {
      case jmsException : JMSException => throw new UpdateFailedException (
        "The next update was not received due to an exception being thrown while waiting to receive the next message.",
        jmsException)
    }

    var result : MarketPrice = null

    if (nextMessage != null) {

      var nextObjectMessage : ObjectMessage = nextMessage.asInstanceOf[ObjectMessage]

        try {
            result = nextObjectMessage.getObject().asInstanceOf[MarketPrice]
            nextMessage.acknowledge();
        } catch {
            case jmsException : JMSException => throw new UpdateFailedException (
              "The next object could not be converted to a market price.", jmsException)
        }
    }

    log.info("getNextUpdate: method ends; result: " + result)

    return result;
  }
}
