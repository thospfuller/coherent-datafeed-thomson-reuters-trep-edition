import java.util.Date;
import java.util.Date;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.config.impl.ConfigurationImpl;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.HornetQServers;
import org.hornetq.core.remoting.impl.invm.InVMAcceptorFactory;
import org.hornetq.core.remoting.impl.invm.InVMConnectorFactory;
import org.hornetq.jms.server.JMSServerManager;
import org.hornetq.jms.server.config.ConnectionFactoryConfiguration;
import org.hornetq.jms.server.config.JMSConfiguration;
import org.hornetq.jms.server.config.QueueConfiguration;
import org.hornetq.jms.server.config.impl.ConnectionFactoryConfigurationImpl;
import org.hornetq.jms.server.config.impl.JMSConfigurationImpl;
import org.hornetq.jms.server.config.impl.QueueConfigurationImpl;
import org.hornetq.jms.server.impl.JMSServerManagerImpl;
import org.jnp.server.Main;
import org.jnp.server.NamingBeanImpl;

/**
 * This example demonstrates how to run a HornetQ embedded with JMS
 * 
 * @author <a href="clebert.suconic@jboss.com">Clebert Suconic</a>
 * @author <a href="mailto:jmesnil@redhat.com">Jeff Mesnil</a>
 */
@GrabResolver(name='JBoss releases', root='https://repository.jboss.org/nexus/content/repositories/releases/')
@Grab(group='org.hornetq', module='hornetq-core', version='2.0.0.GA')
@Grab(group='org.hornetq', module='hornetq-jms', version='2.0.0.GA')
@Grab(group='org.hornetq', module='hornetq-logging', version='2.0.0.GA')
@Grab(group='org.hornetq', module='hornetq-transports', version='2.0.0.GA')
@Grab(group='org.jboss.netty', module='netty', version='3.2.0.Final')
@Grab(group='org.jboss.javaee', module='jboss-jms-api', version='1.1.0.GA')
@Grab(group='org.jboss.logging', module='jboss-logging', version='3.1.2.GA')
@GrabResolver(name='Maven Central', root='http://repo1.maven.org/')
@Grab(group='jboss', module='jnpserver', version='4.2.2.GA')
@GrabConfig(systemClassLoader=true)
public class EmbeddedExample
{

   public static void main(final String[] args)
   {
      try
      {

         // Step 1. Create HornetQ core configuration, and set the properties accordingly
         Configuration configuration = new ConfigurationImpl();
         configuration.setPersistenceEnabled(false);
         configuration.setSecurityEnabled(false);
         configuration
             .getAcceptorConfigurations()
                 .add(new TransportConfiguration(InVMAcceptorFactory.class.getName()));

         // Step 2. Create HornetQ core server
         HornetQServer hornetqServer = HornetQServers.newHornetQServer(configuration);

         // Step 3. Create and start the JNDI server
         System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
         NamingBeanImpl naming = new NamingBeanImpl();
         naming.start();
         Main jndiServer = new Main();
         jndiServer.setNamingInfo(naming);
         jndiServer.setPort(1099);
         jndiServer.setBindAddress("localhost");
         jndiServer.setRmiPort(1098);
         jndiServer.setRmiBindAddress("localhost");
         jndiServer.start();

         // Step 4. Create the JMS configuration
         JMSConfiguration jmsConfig = new JMSConfigurationImpl();

         // Step 5. Configure context used to bind the JMS resources to JNDI
         Hashtable<String, String> env = new Hashtable<String, String>();
         env.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
         env.put("java.naming.provider.url", "jnp://localhost:1099");
         env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
         Context context = new InitialContext(env);
         jmsConfig.setContext(context);

         // Step 6. Configure the JMS ConnectionFactory
         TransportConfiguration connectorConfig = new TransportConfiguration(InVMConnectorFactory.class.getName());
         ConnectionFactoryConfiguration cfConfig = new ConnectionFactoryConfigurationImpl("cf", connectorConfig, "/cf");
         jmsConfig.getConnectionFactoryConfigurations().add(cfConfig);

         // Step 7. Configure the JMS Queue
         QueueConfiguration queueConfig = new QueueConfigurationImpl("queue1", null, false, "/queue/queue1");
         jmsConfig.getQueueConfigurations().add(queueConfig);

         // Step 8. Start the JMS Server using the HornetQ core server and the JMS configuration
         JMSServerManager jmsServer = new JMSServerManagerImpl(hornetqServer, jmsConfig);
         jmsServer.start();
         System.out.println("Started Embedded JMS Server");

         // Step 9. Lookup JMS resources defined in the configuration
         ConnectionFactory cf = (ConnectionFactory)context.lookup("/cf");
         Queue queue = (Queue)context.lookup("/queue/queue1");

         // Step 10. Send and receive a message using JMS API
         Connection connection = null;
         try
         {
            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("Hello sent at " + new Date());
            System.out.println("Sending message: " + message.getText());
            producer.send(message);
            MessageConsumer messageConsumer = session.createConsumer(queue);
            connection.start();
            TextMessage messageReceived = (TextMessage)messageConsumer.receive(5000);
            System.out.println("Received message:" + messageReceived.getText());
         }
         finally
         {
            if (connection != null)
            {
               connection.close();
            }

            // Step 11. Stop the JMS server
            jmsServer.stop();
            System.out.println("Stopped the JMS Server");

            // Step 12. Stop the JNDI server
            naming.stop();
            jndiServer.stop();
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}

EmbeddedExample.main(null)