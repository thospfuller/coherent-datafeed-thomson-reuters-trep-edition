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
rics <- c("GOOG.O")

ricsLength <- length (rics)

print (paste ("rics length: ", ricsLength))

cdatafeedtre::Query(rics)

total <- c()

updateCtr <- 0

while (updateCtr < 10) {
    nextUpdate <- cdatafeedtre::GetNextUpdate()

    next <- nextUpdate$V1["BIDSIZE"]

    nextNumeric <- as.numeric(next)

    total <- c(total, nextNumeric)

    print(paste ("Update ctr: ", updateCtr, ", next: ", next, ", nextNumeric: ", nextNumeric))

    updateCtr <- updateCtr + 1
}

plot (total)

cdatafeedtre::Logout()
