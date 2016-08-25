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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
object ElektronMarketPriceReceiverApp extends scala.App {

  val log = LoggerFactory.getLogger("ElektronMarketPriceReceiverApp")

  val config = new SparkConf()
    .setAppName("tr-elektron-market-price-streaming-integration")
    .setMaster("local[*]") // Setting this to 1 does not print data whereas * works.

  val streamingContext = new StreamingContext(config, Seconds(5))

  val stream: InputDStream[MarketPrice] = streamingContext.receiverStream(new ElektronMarketPriceReceiver ())

  stream.print()

  // Required by the saveAsTextFiles operation.
  System.setProperty("hadoop.home.dir", "C:/development/software/winutils/")

  stream.saveAsTextFiles("C:/Temp/spark-dump/spark-market-price-dump", "txt")

  streamingContext.start()

  streamingContext.awaitTerminationOrTimeout(Long.MaxValue)
}

class ElektronMarketPriceReceiver extends Receiver[MarketPrice](StorageLevel.MEMORY_ONLY) {

  var log = LoggerFactory.getLogger(classOf[ElektronMarketPriceReceiver])

  @Override
  def onStart () {

    var applicationContext : ApplicationContext = new ClassPathXmlApplicationContext(
      "classpath*:spring/application-context.xml",
      "classpath*:spring/jmx-beans.xml",
      "classpath*:spring/hornetq-beans.xml",
      "classpath*:spring/marketprice-jms-workflow-beans.xml"
    )

    val messageConsumer : MessageConsumer = applicationContext.getBean("marketPriceConsumer").asInstanceOf[MessageConsumer]

    var elektronQueryBuilder : ElektronQueryBuilder = applicationContext.getBean(classOf[ElektronQueryBuilder])

    val dacsId : String = System.getenv(DACS_ID)

    val sessionBean : SessionBean = elektronQueryBuilder.newSessionBean(dacsId)

    val statusUpdateCtr : AtomicLong = new AtomicLong (0)

    sessionBean.getStatusResponse().addAggregatePropertyChangeListener(
      new AggregatePropertyChangeListener[StatusResponse] () {

         def onAggregatePropertyChangeEvent(event : AggregatePropertyChangeEvent[StatusResponse]) {

           println("statusResponse.aggregateUpdate ["+ statusUpdateCtr.incrementAndGet() + "] begins.")

            var propertyChangeEventMap : java.util.Map[String, PropertyChangeEvent]
              = event.getPropertyChangeEventMap()

            for ((key : String, value : PropertyChangeEvent) <- propertyChangeEventMap) {
              println("- key: " + key + ", value: " + value)
            }

            println("statusResponse.aggregateUpdate ends.")
         }
      }
    )

    elektronQueryBuilder.login(sessionBean)

    queryMarketPriceService (applicationContext)

    val nextUpdateCtr = new AtomicLong (0);

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

    var addMarketPriceToQueueChannel : MessageChannel = applicationContext.getBean("addMarketPriceToQueueChannel").asInstanceOf[MessageChannel]

    ExampleRICS.rics.foreach {
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

            println("marketPrice.statusResponse.aggregateUpdate ["+ statusUpdateCtr.incrementAndGet()
              + "] begins for " + marketPrice.getRic)

            var propertyChangeEventMap : java.util.Map[String, PropertyChangeEvent]
              = event.getPropertyChangeEventMap()

            for ((key : String, value : PropertyChangeEvent) <- propertyChangeEventMap) {
              println("- key: " + key + ", value: " + value)
            }
          }
        }
      }

      elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketPrice);
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
