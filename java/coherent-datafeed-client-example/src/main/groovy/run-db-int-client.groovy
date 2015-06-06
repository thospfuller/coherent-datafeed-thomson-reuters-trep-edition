import com.coherentlogic.coherent.datafeed.db.integration.AdvancedClient
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID

System.setProperty("java.util.logging.config.file", "C:/Temp")
System.setProperty("rpackagePath", "C:/development")

def client = new Client ()

client.start ()

def authenticationService = client.getAuthenticationService ()
def marketPriceService = client.getMarketPriceService ()

String dacsId = System.getenv(DACS_ID)

authenticationService.login(dacsId)

marketPriceService.query ("GOOG.O", "MSFT.O", "ODFL.OQ", "LKQ.OQ", "MDVN.OQ")

while (true) {
    println(marketPriceService.getNextUpdate ())
}