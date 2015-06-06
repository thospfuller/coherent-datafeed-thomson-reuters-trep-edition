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
import org.hornetq.core.server.impl.HornetQServerImpl;
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
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.jms.HornetQJMSClient;
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
@Grab(group='org.jboss.netty', module='netty', version='3.1.5.GA') //
@Grab(group='org.jboss.javaee', module='jboss-jms-api', version='1.1.0.GA') //
// mvn install:install-file -Dfile=./jboss-logging-spi.jar -DgroupId=org.jboss.logging -DartifactId=jboss-logging -Dversion=2.1.0.GA -Dpackaging=jar
// grape install org.jboss.logging jboss-logging 2.1.0.GA
@Grab(group='org.jboss.logging', module='jboss-logging', version='2.1.0.GA') //
@GrabResolver(name='Maven Central', root='http://repo1.maven.org/')
@Grab(group='jboss', module='jnpserver', version='4.2.2.GA')
@GrabConfig(systemClassLoader=true)
public class EmbeddedExample
{

   public static void main(final String[] args) {
      try {

          // Step 1. Create the Configuration, and set the properties accordingly
          Configuration configuration = new ConfigurationImpl();
          configuration.setPersistenceEnabled(false);
          configuration.setSecurityEnabled(false);
          configuration.setClustered(false);
          configuration.setClusterUser("cdatafeed");
          configuration.setClusterPassword("not-used");
          configuration
              .getAcceptorConfigurations()
              .add(
                  new TransportConfiguration(
                      InVMAcceptorFactory.class.getName()
                  )
              );

          // Step 2. Create and start the server
          // Note that HornetQServers is a 2.2.1 class.
          // HornetQServer server = HornetQServers.newHornetQServer(configuration);
          
          HornetQServer server = new HornetQServerImpl (configuration);
          
          server.start();

          // Step 3. As we are not using a JNDI environment we instantiate the objects directly
          ClientSessionFactory sf = HornetQClient.createClientSessionFactory(new TransportConfiguration(InVMConnectorFactory.class.getName()));

          // Step 4. Create a core queue
          ClientSession coreSession = sf.createSession(false, false, false);

          /* See 7.6 here http://docs.jboss.org/hornetq/2.2.5.Final/user-manual/en/html/using-jms.html#using-jms.server.configuration
           *
           */
          
          TransportConfiguration transportConfiguration =
              new TransportConfiguration(InVMConnectorFactory.class.getName());                

          ConnectionFactory cf = HornetQJMSClient.createConnectionFactory(transportConfiguration);
          
          Queue orderQueue = HornetQJMSClient.createQueue("OrderQueue");
          Connection connection = cf.createConnection();

      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}

EmbeddedExample.main(null)