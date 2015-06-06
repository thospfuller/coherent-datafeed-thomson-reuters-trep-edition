@GrabResolver(name='JBoss releases', root='https://repository.jboss.org/nexus/content/repositories/releases/')
@Grab(group='org.hornetq', module='hornetq-core', version='2.0.0.GA')
@Grab(group='org.hornetq', module='hornetq-jms', version='2.0.0.GA')
@Grab(group='org.hornetq', module='hornetq-logging', version='2.0.0.GA')
@Grab(group='org.hornetq', module='hornetq-transports', version='2.0.0.GA')
@Grab(group='org.jboss.netty', module='netty', version='3.1.5.GA') //
@Grab(group='org.jboss.javaee', module='jboss-jms-api', version='1.1.0.GA') //
// mvn install:install-file -Dfile=./jboss-logging-spi.jar -DgroupId=org.jboss.logging -DartifactId=jboss-logging -Dversion=2.1.0.GA -Dpackaging=jar
// grape install org.jboss.logging jboss-logging 2.1.0.GA
@Grab(group='org.jboss.logging', module='jboss-logging', version='2.1.0.GA') //
@GrabResolver(name='Maven Central', root='http://repo1.maven.org/')
@Grab(group='jboss', module='jnpserver', version='4.2.2.GA')
@GrabConfig(systemClassLoader=true)

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.core.remoting.impl.invm.InVMConnectorFactory;
import org.hornetq.core.remoting.impl.invm.InVMAcceptorFactory;

import org.hornetq.core.config.Configuration
import org.hornetq.core.config.impl.ConfigurationImpl
import org.hornetq.core.server.cluster.QueueConfiguration
import org.hornetq.core.server.HornetQServer
import org.hornetq.core.server.impl.HornetQServerImpl

import org.hornetq.jms.server.JMSServerManager
import org.hornetq.jms.server.impl.JMSServerManagerImpl
import org.hornetq.jms.server.config.JMSConfiguration;
import org.hornetq.jms.server.config.impl.JMSConfigurationImpl
import org.hornetq.jms.server.config.QueueConfiguration
import org.hornetq.jms.server.config.impl.QueueConfigurationImpl

def QUEUE_NAME = "exampleQueue", JMS_QUEUE_NAME = "/queue/${QUEUE_NAME}"

HornetQServer server = null
Connection connection = null

try {

Configuration configuration = new ConfigurationImpl ();
        
configuration.setPersistenceEnabled(false)
configuration.setSecurityEnabled(false)
configuration.setClustered(false)
configuration.setClusterUser("blah")
configuration.setClusterPassword("not-used")
configuration.setJMXManagementEnabled(false)
configuration
    .getAcceptorConfigurations()
        .add(
            new TransportConfiguration(
                InVMAcceptorFactory.class.getName())
    );

server = new HornetQServerImpl (configuration);

JMSConfiguration jmsConfiguration = new JMSConfigurationImpl ()

def queueConfigurations = jmsConfiguration.getQueueConfigurations()

queueConfigurations << new QueueConfigurationImpl (QUEUE_NAME, null, false, JMS_QUEUE_NAME)

JMSServerManager jmsServerManager = new JMSServerManagerImpl(server, jmsConfiguration);

// We're not using JNDI.
jmsServerManager.setContext(null);
jmsServerManager.start();

// -----

// Step 1. Directly instantiate the JMS Queue object.
Queue queue = HornetQJMSClient.createQueue("${QUEUE_NAME}");

// Step 2. Instantiate the TransportConfiguration object which contains the knowledge of what transport to use,
// The server port etc.
TransportConfiguration transportConfiguration = new TransportConfiguration(InVMConnectorFactory.class.getName());

// Step 3 Directly instantiate the JMS ConnectionFactory object using that TransportConfiguration
ConnectionFactory cf = HornetQJMSClient.createConnectionFactory(transportConfiguration);

// Step 4.Create a JMS Connection
connection = cf.createConnection();

// Step 5. Create a JMS Session
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

// Step 6. Create a JMS Message Producer
MessageProducer producer = session.createProducer(queue);

// Step 7. Create a Text Message
TextMessage message = session.createTextMessage("This is a text message");

System.out.println("Sent message: " + message.getText());

// Step 8. Send the Message
producer.send(message);

// Step 9. Create a JMS Message Consumer
MessageConsumer messageConsumer = session.createConsumer(queue);

// Step 10. Start the Connection
connection.start();

// Step 11. Receive the message
TextMessage messageReceived = (TextMessage)messageConsumer.receive(5000);

System.out.println("Received message: " + messageReceived.getText());

connection.close();

} finally {
    if (connection != null) {
        connection.close ()
    }
    if (server != null) {
        server.stop ()
    }
}