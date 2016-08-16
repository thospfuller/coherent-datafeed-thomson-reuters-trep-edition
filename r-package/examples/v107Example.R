library(cdatafeedtre)
Initialize()
cdatafeedtre::Login("CLL_thomas.fuller")
result <- GetTimeSeriesDataFor(symbol = "MSFT.O", period="monthly")
tempDF <- data.frame(DATE=unlist(result$DATE),HIGH=unlist(result$HIGH))
tempDF$DATE <- as.POSIXct(as.numeric(as.character(tempDF$DATE)),origin="1970-01-01",tz="GMT")
tempDF$HIGH <- as.numeric(as.character(tempDF$HIGH))
plot(tempDF, type="l")

tempDF <- data.frame(DATE=unlist(result$DATE), LOW=unlist(result$LOW))
tempDF$DATE <- as.POSIXct(as.numeric(as.character(tempDF$DATE)),origin="1970-01-01",tz="GMT")
tempDF$LOW <-as.numeric(as.character(tempDF$LOW))

plot(tempDF, type="l")

Query(symbols = c("MSFT.O", "ORCL.O", "GOOG.O", "ILMN.O", "TRI.N"))

while (TRUE) {
    nextUpdate <- cdatafeedtre::GetNextUpdate()
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
