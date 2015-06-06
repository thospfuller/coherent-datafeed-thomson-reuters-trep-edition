import com.coherentlogic.coherent.datafeed.client.Client
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID
import com.coherentlogic.coherent.datafeed.builders.ServiceName

import com.reuters.rfa.common.Handle

import org.slf4j.LoggerFactory

def log = LoggerFactory.getLogger(this.class.name);

Client client = new Client ()

client.start()

def dacsId = System.getenv(DACS_ID)

def loginHandle = client.login(dacsId)

client.waitForInitialisationToComplete()

def marketPriceService = client.getMarketPriceService ()

def itemHandles = marketPriceService.query(
    ServiceName.dIDN_RDF,
    loginHandle,
    "GOOG.O",
    "MSFT.O",
    "ODFL.OQ",
    "LKQ.OQ",
    "MDVN.OQ",
    "BFb.N")

long ctr = 0;

while (true) {
    def nextMarketPriceUpdate =
        marketPriceService.getNextUpdateAsJSON();
    log.info ("nextMarketPriceUpdate[" + (ctr++) + "]: " +
        nextMarketPriceUpdate);
}