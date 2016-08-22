package com.coherentlogic.coherent.datafeed.spark.integration.receivers

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ImportResource
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.ApplicationContext

import org.springframework.messaging.MessageChannel

import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants
import com.coherentlogic.coherent.datafeed.domain.MarketPrice

import org.springframework.context.annotation.Profile
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicBoolean

import java.beans.PropertyChangeEvent

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException

import net.coherentlogic.coherent.datafeed.examples.ExampleRICS
import com.coherentlogic.coherent.datafeed.domain.StatusResponse
import com.coherentlogic.coherent.datafeed.services.ServiceName
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent
import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder
import com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID
import com.coherentlogic.coherent.datafeed.domain.SessionBean

import org.springframework.integration.support.MessageBuilder

import collection.JavaConversions._

/**
 *
 */
//@Profile(Array[String]("prod"))
@SpringBootApplication
/* Not sure why we need to exclude the DataSourceAutoConfiguration in this example as this is not a problem in other
 * modules but if we don't have this then we end up with the following exception:
 *
 * Error creating bean with name 'spring.datasource.CONFIGURATION_PROPERTIES': Initialization of bean failed; nested
 * exception is javax.validation.ValidationException: Unable to create a Configuration, because no Bean Validation
 * provider could be found. Add a provider like Hibernate Validator (RI) to your classpath.
 *
 * and also
 *
 * Unable to create a Configuration, because no Bean Validation provider could be found. Add a provider like Hibernate
 * Validator (RI) to your classpath.
 */
@EnableAutoConfiguration//(exclude=Array(classOf[DataSourceAutoConfiguration]))
@ComponentScan(basePackages=Array[String]{"com.coherentlogic.coherent.datafeed"})
@ImportResource(
    Array[String](
        "classpath*:spring/jmx-beans.xml",
        "classpath*:spring/hornetq-beans.xml",
        "classpath*:spring/marketprice-jms-workflow-beans.xml"
    )
)
class ElektronMarketPriceReceiver extends Receiver[String]((StorageLevel.MEMORY_ONLY))
  with CommandLineRunner with MarketPriceConstants {

  var log = LoggerFactory.getLogger(classOf[ElektronMarketPriceReceiver]);

  @Autowired
  var applicationContext : ApplicationContext = null

  @Autowired
  @Qualifier("addMarketPriceToQueueChannel")
  var addMarketPriceToQueueChannel : MessageChannel = null

  var elektronQueryBuilder : ElektronQueryBuilder = null

  var latch = new AtomicBoolean (true)

  @Override
  def onStart () {

    log.info("onStart: method begins.")

    elektronQueryBuilder = applicationContext.getBean(classOf[ElektronQueryBuilder])

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

    val messageConsumer = applicationContext.getBean("marketPriceConsumer").asInstanceOf[MessageConsumer]

    val nextUpdateCtr = new AtomicLong (0);

    val updateThread = new Thread {
      override def run {
        while (latch.get) {
          val next = getNextUpdate (messageConsumer, 5000L);
          System.out.println("receive.updateThread["+ nextUpdateCtr.incrementAndGet() +"]: " + next);
        }
      }
    }

    updateThread.start()

    log.info("onStart: method ends.")
  }

  @Override
  def onStop () {

    log.info("onStop: method begins.")

    latch.set(false)

    log.info("onStop: method ends.")
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

  private def queryMarketPriceService () {

    var statusUpdateCtr : AtomicLong = new AtomicLong (0)

    ExampleRICS.rics.foreach {
      ric =>
      /* We *MUST* acquire the MarketPrice instance from the Spring container because we are using AOP and if we
       * simply create the class directly by calling the ctor, none of the property change events will fire when
       * an update is applied and the same applies to the StatusResponse.
       */
      var marketPrice : MarketPrice = elektronQueryBuilder.newMarketPrice(ric)

      marketPrice.addAggregatePropertyChangeListener(
        new AggregatePropertyChangeListener[MarketPrice] () {

          def onAggregatePropertyChangeEvent(event : AggregatePropertyChangeEvent[MarketPrice]) {

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

            println("marketPrice.aggregateUpdate ends.")
          }
        }
      }

      elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketPrice);
    }
  }

  @throws(classOf[Exception])
  override def run(args:String*) {

    log.info("run: method begins; args: " + args)

    onStart()

    queryMarketPriceService ()

    log.info("run: method ends.")
  }
}
