@GrabResolver(name='Maven Central', root='http://repo1.maven.org/')
@Grab(group='redis.clients', module='jedis', version='2.1.0')
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

class DefaultPubSubImpl extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        println "channel: $channel, message: $message.todaysHigh"
    }
 
    @Override
    public void onPMessage(String pattern, String channel, String message) {}
 
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {}
 
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {}
 
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {}
 
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {}
}

def subscriber = new DefaultPubSubImpl ()

// 192.168.236.190
Jedis jedis = new Jedis("REDISSVR", 6379, 0)
jedis.connect();

jedis.subscribe (subscriber, "updates")
