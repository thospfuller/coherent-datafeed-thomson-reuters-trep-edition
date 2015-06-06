import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID
import com.coherentlogic.coherent.datafeed.builders.ServiceName
import com.coherentlogic.coherent.datafeed.client.Client

import com.reuters.ts1.TS1Constants
import com.reuters.rfa.common.Handle

def client = new Client ()

client.start()

def dacsId = System.getenv(DACS_ID)

def loginHandle = client.login(dacsId)

client.waitForInitialisationToComplete()

final def timeSeriesService =
    client.getTimeSeriesService();

timeSeriesService.queryTimeSeriesFor(
    loginHandle,
    ServiceName.IDN_RDF,
    "TRI.N",
    TS1Constants.DAILY_PERIOD
);

def timeSeries =
    timeSeriesService.getNextUpdateAsTimeSeries();

def TABS = "\t\t\t";

print ("\tDATE");

for (String nextHeader : timeSeries.getHeaders())
    print(TABS + nextHeader);

for (def sample : timeSeries.getSamples()) {

print("\n" + sample.getDate());

for (def nextPoint : sample.getPoints())
    print (TABS + nextPoint);
}