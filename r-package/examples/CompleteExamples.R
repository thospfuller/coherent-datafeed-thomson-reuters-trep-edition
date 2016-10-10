library(cdatafeedtrep)
cdatafeedtrep::Initialize()
dacsId <- Sys.getenv("DACS_ID")
cdatafeedtrep::Login(dacsId)
result <- GetTimeSeriesDataFor(symbol = "MSFT.O", period="monthly")
tempDF <- data.frame(DATE=unlist(result$DATE),HIGH=unlist(result$HIGH))
tempDF$DATE <- as.POSIXct(as.numeric(as.character(tempDF$DATE)),origin="1970-01-01",tz="GMT")
tempDF$HIGH <- as.numeric(as.character(tempDF$HIGH))
plot(tempDF, type="l")

tempDF <- data.frame(DATE=unlist(result$DATE), LOW=unlist(result$LOW))
tempDF$DATE <- as.POSIXct(as.numeric(as.character(tempDF$DATE)),origin="1970-01-01",tz="GMT")
tempDF$LOW <-as.numeric(as.character(tempDF$LOW))

result <- GetTimeSeriesDataFor(symbol = "TWTR.N", period="monthly", timeout = "120000")
tempDF <- data.frame(DATE=unlist(result$DATE),OPEN=unlist(result$OPEN))
tempDF <- tempDF[order(tempDF$DATE),]
tempDF$DATE <- as.POSIXct(as.numeric(substr(x = as.character(tempDF$DATE), start=1, stop=10)), origin="1970-01-01", tz="GMT")
tempDF$OPEN <- as.numeric(as.character(tempDF$OPEN))
plot(tempDF, type="l")

plot(tempDF, type="l")

cdatafeedtrep::QueryMarketPrice(symbols = c("MSFT.O", "ORCL.O", "GOOG.O", "ILMN.O", "TRI.N"))

while (TRUE) {
    nextUpdate <- cdatafeedtrep::GetNextMarketPriceUpdate()
    print(nextUpdate)
}

cdatafeedtrep::QueryMarketMaker(symbols = c("MSFT.O", "ORCL.O", "GOOG.O", "ILMN.O", "TRI.N"))

while (TRUE) {
    nextUpdate <- cdatafeedtrep::GetNextMarketMakerUpdateAsJavaObject()
    print(nextUpdate)
}

cdatafeedtrep::QueryMarketByPrice(symbols = c("MSFT.O", "ORCL.O", "GOOG.O", "ILMN.O", "TRI.N", "DBSM.SI"))

while (TRUE) {
    nextUpdate <- cdatafeedtrep::GetNextMarketByPriceUpdateAsJavaObject()
    print(nextUpdate)
}

cdatafeedtrep::QueryMarketByOrder(symbols = c("MSFT.O", "ORCL.O", "GOOG.O", "ILMN.O", "TRI.N", "DBSM.SI"))

while (TRUE) {
    nextUpdate <- cdatafeedtrep::GetNextMarketByOrderUpdateAsJavaObject()
    print(nextUpdate)
}

rics <- c(
    "LCOc1",
    "GOOG.O",
    "MSFT.O",
    "ODFL.OQ",
    "LKQ.OQ",
    "MDVN.OQ",
    "BFb.N",
    "KO.N",
    ".TRXFLDAFPUM11",
    "SWM.N",
    "ERICb.F",
    "ERICb.DE",
    "ERICb.D",
    "ERICb.BE",
    "ERICa.ST",
    "ERICa.F",
    "ERICa.DE",
    "ERICa.BE",
    "ERIC.PH",
    "ERIC.P",
    "ERIC.OQ",
    "ERIC.MW",
    "ERIC.DF",
    "ERIC.C",
    "ERIC.A",
    "DRICqf.BO",
    "BRIC.S",
    "BRIC.MI",
    "BRIC.AS",
    "ARIC.F",
    ".VBRICUTR",
    "ERIC.O",
    "RIC.A",
    "ARIC.BO",
    "BRIC.L",
    "DRIC.BO",
    "AAT.N",
    "ABX.N",
    "ACAS.O",
    "ACC.N",
    "ADGE.A",
    "AEL.N",
    "AEO.N",
    "AEP.N",
    "AFA.N",
    "AFG.N",
    "AFW.N",
    "AGM.N",
    "AGMa.N",
    "AGNC.O",
    "AGNCP.O",
    "AIG.N",
    "ALN.A",
    "AM.N",
    "AMID.N",
    "AMNB.O",
    "AMOV.O",
    "AMRB.O",
    "AMS.A",
    "AMSC.O",
    "AMSWA.O",
    "AMT.N",
    "AMWD.O",
    "AMX.N",
    "ANAT.O",
    "APEI.O",
    "ARC.N",
    "ARII.O",
    "ANAT.O",
    "APEI.O",
    "ARC.N",
    "ARII.O",
    "ARL.N",
    "ASEI.O",
    "ATAX.O",
    "AVD.N",
    ".DAXBRIC",
    ".DAXBRICGB",
    ".DAXBRICGBN",
    ".DAXBRICGBP",
    ".TRXFLDAFPU",
    ".TRXFLDAFPUMAT",
    "ACIDq.L",
    "ALU.TI",
    "A8H.F",
    "A5J.F",
    "ARNR.L",
    "BZR.BE",
    "ABCA.L",
    "CXS.F",
    "CAGR.MI",
    "ACCI.L",
    "ATHCO.CY",
    "QA9.F",
    "ECT.AS",
    "ACTG.CY",
    "AA4.F",
    "AA4.MI",
    "ADML.L",
    "ALLD.F"
)

Query(symbols = rics)

while (TRUE) {
    nextUpdate <- cdatafeedtre::GetNextUpdate()
    print(nextUpdate)
}
