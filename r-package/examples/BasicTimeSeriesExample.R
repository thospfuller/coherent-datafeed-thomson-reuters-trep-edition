install.packages(repos=NULL, pkgs="C:/development/projects/CDATAFEED-SVN-CO/cdatafeedtre_1.0.zip")
library("cdatafeedtre")
cdatafeedtre::Initialize()

dacsId <- Sys.getenv("DACS_ID")
cdatafeedtrep::Login(dacsId)

nextUpdate <- cdatafeedtre::GetTimeSeriesDataFor("ILMN.O", "daily")

print(paste ("nextUpdate: ", unlist (nextUpdate)))

cdatafeedtre::Logout()
