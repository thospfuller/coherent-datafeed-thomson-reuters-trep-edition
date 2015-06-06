install.packages(repos=NULL, pkgs="C:/development/projects/CDATAFEED-SVN-CO/cdatafeedtre_1.0.zip")
library("cdatafeedtre")
cdatafeedtre::Initialize()
cdatafeedtre::Login("CoherentLogic_Fuller")
nextUpdate <- cdatafeedtre::GetTimeSeriesDataFor("ILMN.O", "daily")

print(paste ("nextUpdate: ", unlist (nextUpdate)))

cdatafeedtre::Logout()