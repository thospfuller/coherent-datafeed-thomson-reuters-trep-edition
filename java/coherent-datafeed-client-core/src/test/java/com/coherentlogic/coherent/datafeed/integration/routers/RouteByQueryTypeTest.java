package com.coherentlogic.coherent.datafeed.integration.routers;

/**
 * Unit test for the {@link RouteByQueryType} class.
 *
 * @deprecated After refactoring this project we no longer need this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RouteByQueryTypeTest {

//    private final MessageChannel marketPriceChannel =
//        mock (MessageChannel.class);
//    private final MessageChannel timeSeriesChannel =
//        mock (MessageChannel.class);
//    private final MessageChannel ts1DefChannel =
//        mock (MessageChannel.class);
//    private final MessageChannel errorChannel =
//        mock (MessageChannel.class);
//
//    private TimeSeriesEntries cachedTimeSeriesHolder = null;
//    private TS1DefEntry ts1DefEntry = null;
//    private CachedMarketPrice cachedMarketPrice = null;
//
//    private OMMItemEvent itemEvent = null;
//
//    private Map<Class<? extends Serializable>, MessageChannel>
//        messageChannelMap = null;
//
//    private Cache<Handle, CachedObject<? extends Serializable>>
//        cache = null;
//
//    private Handle handle = null;
//
//    private RouteByQueryType routeByQueryType = null;
//
//    @Before
//    public void setUp() throws Exception {
//
//        handle = mock(Handle.class);
//
//        itemEvent = mock(OMMItemEvent.class);
//
//        messageChannelMap =
//            new HashMap<Class<? extends Serializable>, MessageChannel> ();
//
//        messageChannelMap.put(CachedMarketPrice.class, marketPriceChannel);
//        messageChannelMap.put(TimeSeriesEntries.class, timeSeriesChannel);
//        messageChannelMap.put(TS1DefEntry.class, ts1DefChannel);
//
//        when(itemEvent.getHandle()).thenReturn(handle);
//
//        EmbeddedCacheManager cacheManager = new DefaultCacheManager();
//
//        cache = cacheManager.getCache(RouteByQueryTypeTest.class.getName());
//
//        routeByQueryType = new RouteByQueryType(
//            messageChannelMap,
//            errorChannel,
//            cache
//        );
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        handle = null;
//        itemEvent = null;
//        messageChannelMap = null;
//        cache = null;
//        routeByQueryType = null;
//        cachedTimeSeriesHolder = null;
//        ts1DefEntry = null;
//        cachedMarketPrice = null;
//    }
//
//    @Test
//    public void testDetermineTimeSeriesChannel() {
//
//        cachedTimeSeriesHolder = new TimeSeriesEntries (handle, 0);
//
//        cache.put(handle, cachedTimeSeriesHolder);
//
//        Message<OMMItemEvent> message =
//            new GenericMessage<OMMItemEvent> (itemEvent);
//
//        when(itemEvent.getHandle()).thenReturn(handle);
//
//        Collection<MessageChannel> messageChannelCollection =
//            routeByQueryType.determineTargetChannels(message);
//
//        List<MessageChannel> messageChannels =
//            new ArrayList<MessageChannel> (messageChannelCollection);
//
//        assertEquals(1, messageChannels.size());
//
//        assertEquals(timeSeriesChannel, messageChannels.get(0));
//    }
//
//    @Test
//    public void testDetermineTS1DefChannel() {
//
//        ts1DefEntry = new TS1DefEntry(handle);
//
//        cache.put(handle, ts1DefEntry);
//
//        Message<OMMItemEvent> message =
//            new GenericMessage<OMMItemEvent> (itemEvent);
//
//        when(itemEvent.getHandle()).thenReturn(handle);
//
//        Collection<MessageChannel> messageChannelCollection =
//            routeByQueryType.determineTargetChannels(message);
//
//        List<MessageChannel> messageChannels =
//            new ArrayList<MessageChannel> (messageChannelCollection);
//
//        assertEquals(1, messageChannels.size());
//
//        assertEquals(ts1DefChannel, messageChannels.get(0));
//    }
//
//    @Test
//    public void testDetermineErrorChannel() {
//
//        // We expect the error channel in this setup because we're setting the
//        // cached time series handle to something which won't match up when the
//        // message is passed to the determineTargetChannels method.
//
//        Handle someOtherHandle = mock(Handle.class);
//
//        cachedTimeSeriesHolder = new TimeSeriesEntries (handle, 0);
//
//        cache.put(someOtherHandle, cachedTimeSeriesHolder);
//
//        Message<OMMItemEvent> message =
//            new GenericMessage<OMMItemEvent> (itemEvent);
//
//        when(itemEvent.getHandle()).thenReturn(handle);
//
//        Collection<MessageChannel> messageChannelCollection =
//            routeByQueryType.determineTargetChannels(message);
//
//        List<MessageChannel> messageChannels =
//            new ArrayList<MessageChannel> (messageChannelCollection);
//
//        assertEquals(1, messageChannels.size());
//
//        assertEquals(errorChannel, messageChannels.get(0));
//    }
}
