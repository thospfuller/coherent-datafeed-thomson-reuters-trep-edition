# In this example, we:
# 1.) Establish a session with Thomson Reuters.
# 2.) Query the XYZ and AAA symbols, with the LAST_PRICE fields.
# 3.) Print the next quote.
#
# Note that we use the cdatafeed namespace in each of the function calls below.
# We could remove the namespace if we chose to, but we could run into name
# clashes if there are, for example, two packages, each exporting a function
# named "Login".

library("cdatafeedtre")
cdatafeedtre::Initialize()
dacsId <- Sys.getenv("DACS_ID")
cdatafeedtrep::Login(dacsId)
rics <- c("GOOG.O", "MSFT.O", "ODFL.OQ", "LKQ.OQ", "MDVN.OQ", "BFb.N", "KO.N", "OIBR.K", "SWM.N", "ERICb.F", "ERICb.DE", "ERICb.D", "ERICb.BE", "ERICa.ST", "ERICa.F", "ERICa.DE", "ERICa.BE", "ERIC.W", "ERIC.PH", "ERIC.P", "ERIC.OQ", "ERIC.MW", "ERIC.DF", "ERIC.C", "ERIC.A", "DRICqf.BO", "BRICUSDNAV.DE", "BRICGBPNAV.DE", "BRICDX.MI", "BRIC.S", "BRIC.MI", "BRIC.AS", "ARICqf.BO", "ARIC.F", "ALRIC.PA1", ".VBRICUTR", "ERIC.O", "RIC.A", "ALRIC.PA", "ARIC.BO", "BRIC.L", "DRIC.BO", "AAT.N", "ABV.N", "ABVc.N", "ABX.N", "ACAS.O", "ACC.N", "ADGE.A", "AEL.N", "AEO.N", "AEP.N", "AEP_pa.N", "AFA.N", "AFE.N", "AFF.N", "AFG.N", "AFQ.N", "AFW.N", "AGM.N", "AGMa.N", "AGNC.O", "AGNCP.O", "AIG.N", "ALN.A", "AM.N", "AMID.N", "AMNB.O", "AMOV.O", "AMRB.O", "AMS.A", "AMSC.O", "AMSWA.O", "AMT.N", "AMWD.O", "AMX.N", "ANAT.O", "APEI.O", "APP.A", "AQQ.A", "ARC.N", "ARCT.O", "ARII.O", "ANAT.O", "APEI.O", "APP.A", "AQQ.A", "ARC.N", "ARCT.O", "ARII.O", "ARL.N", "ARSD.N", "ASEI.O", "ASI.N", "ASP.N", "ATAX.O", "AUQ.N", "AVD.N", "AVF.N", ".DAXBRIC", ".DAXBRICGB", ".DAXBRICGBN", ".DAXBRICGBP")
#
# Below are equity rics -- for more examples see here: http://thomsonreuters.com/content/financial/318731/global_equity_master_list.xls
#
rics <- c(rics, ".TRXFLDAFPU", ".TRXFLDAFPUMAT", ".TRXFLDAFPUM11")

ricsLength <- length (rics)

print (paste ("rics length: ", ricsLength))

cdatafeedtre::Query(rics)

totalUpdates <- 0

while (TRUE) {
    nextUpdate <- cdatafeedtre::GetNextUpdate()
    print(nextUpdate)
    print(paste ("Total updates: ", totalUpdates))
    totalUpdates <- totalUpdates + 1
}

cdatafeedtre::Logout()
