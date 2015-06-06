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
cdatafeedtre::Login("CoherentLogic_Fuller")
rics <- c("GOOG.O")

ricsLength <- length (rics)

print (paste ("rics length: ", ricsLength))

cdatafeedtre::Query(rics)

total <- c()

updateCtr <- 0

while (updateCtr < 10) {
    nextUpdate <- cdatafeedtre::GetNextUpdate()

    nextValue <- as.character(nextUpdate$V1["BID"])

    total <- c(total, nextValue)

    updateCtr <- updateCtr + 1
}

plot (total)
lines (total)

cdatafeedtre::Logout()