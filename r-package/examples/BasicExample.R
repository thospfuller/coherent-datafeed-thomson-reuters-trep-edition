install.packages(repos=NULL, pkgs="C:/development/projects/CDATAFEED-SVN-CO/cdatafeedtre_1.0.zip")
library("cdatafeedtre")
cdatafeedtre::Initialize()

dacsId <- Sys.getenv("DACS_ID")
cdatafeedtrep::Login(dacsId)

rics <- c("GOOG.O", "MSFT.O", "ODFL.OQ", "LKQ.OQ", "MDVN.OQ", "BFb.N")

cdatafeedtre::Query(rics)

while (TRUE) {
    nextUpdate <- cdatafeedtre::GetNextUpdate()
    print(nextUpdate)
}

cdatafeedtre::Logout()
