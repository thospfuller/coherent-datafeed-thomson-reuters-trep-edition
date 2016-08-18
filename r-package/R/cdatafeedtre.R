#'
#' @title The Coherent Datafeed Thomson Reuters Elektron Edition.
#'
#' @description Provides real-time market price updates and time series data direct from the Thomson Reuters Elektron
#' Platform.
#'
#' @import RJSONIO
#' @import rJava
#'
#' @docType package
#'
#' @name cdatafeedtre
#'
NULL

#' An environment which is used by this package when managing package-scope
#' variables.
#'
.cdatafeedtrep.env <- new.env()

#' Function instantiates the client which is used to bridge the gap between the
#' R script and the underlying API.
#'
#' @param libname The library name.
#'
#' @param pkgname The package name.
#'
.onLoad <- function (libname, pkgname) {

    targetDir = paste ("-Djava.util.logging.config.file=", tempdir (), sep="")

    packageDir = paste ("-DrpackagePath=", system.file(package="cdatafeedtre"), sep="")

    cdatafeedJars = getOption ("CDATAFEED_JARS")

    if (is.null(cdatafeedJars)) {
        cdatafeedJars <- list()
        warning (
            paste (
                "The CDATAFEED_JARS option is NULL and this means that, absent the rfa.jar, this package will not ",
                "work (expect to see ClassNotFoundException(s)). The RFA dependency can be added as follows: ",
                "options(CDATAFEED_JARS=list(\"C:/Temp/rfa.jar\")) prior to using this package (that means *before* ",
                "executing library (\"cdatafreedtre\"). Specifically RFA version 8.0.1.E3 is required by this package ",
                "and can be downloaded direct from Thomson Reuters by visiting the following link: ",
                "https://customers.reuters.com/a/support/technical/softwaredownload/", sep="\n"))
    } else {
        print(paste("cdatafeedJars: ", cdatafeedJars, sep=""))
    }

    .jpackage(pkgname, lib.loc = libname, morePaths=cdatafeedJars)
}

.onUnload <- function (libpath) {

    client<- .cdatafeedtrep.env$client

    tryCatch(client$stop(), Throwable = function (e) {
        stop (paste ("An exception was thrown when stopping the client -- ",
            "details follow.", e$getMessage(), sep=""))
    })
}

#' This function must be called exactly one time before the package can be used.
#'
#' @export
#'
Initialize <- function () {

    ElektronQueryBuilderClient <- J("com.coherentlogic.coherent.datafeed.rproject.integration.client.ElektronQueryBuilderClient")

    client <- ElektronQueryBuilderClient$initialize()

    assign("client", client, envir = .cdatafeedtrep.env)

    tryCatch(client$start(), Throwable = function (e) {
        stop (paste ("An exception was thrown when starting the client -- details follow.", e$getMessage(), sep=""))
    })
}

#' Establishes a session with Thomson Reuters.
#'
#' @param dacsId The user's Thomson Reuters DACS id.
#'
#' @export
#'
Login <- function (dacsId) {

    client <- .cdatafeedtrep.env$client

    tryCatch(
        client$login(dacsId), Throwable = function (e) {
            stop(
                paste ("Unable to login using the dacsId '", dacsId, "' -- details follow. ", e$getMessage(), sep="")
            )
        }
    )
}

#' Terminates the session with Thomson Reuters.
#'
#' @export
#'
Logout <- function () {

    client <- .cdatafeedtrep.env$client

    tryCatch(
        client$logout(), Throwable = function (e) {
            stop(paste ("Unable to logout -- details follow. ", e$getMessage(), sep=""))
        }
    )
}

#' Sends a request to Thomson Reuters to receive market price updates for the specified symbols.
#'
#' Note that querying a large amount of symbols can lead to an OutOfMemoryException due to the number of inbound
#' messages not being processed fast enough by R and hence memory is slowly eaten up until the VM limit is reached. If
#' this issue is encountered then you need to reduce the number of symbols until you find a number that works for the
#' machine this package is running on.
#'
#' @param serviceName For example "dELEKTRON_DD" (defaults to "dELEKTRON_DD").
#' @param symbols One or more symbols, for example "GOOG.O".
#'
#' @export
#'
Query <- function (serviceName="dELEKTRON_DD", symbols) {

    client <- .cdatafeedtrep.env$client

    jServiceName <- J("com.coherentlogic.coherent.datafeed.services.ServiceName")

    actualServiceName <- jServiceName$valueOf(serviceName)

    tryCatch(client$queryMarketPrice(actualServiceName, symbols),
        Throwable = function (e) {
            stop(
                paste ("Unable to query the following symbols [", symbols,
                    "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )
}

# Allows the user to inspect the dictionaries that have been loaded by this
# package.
#
# @return The dictionaries that have been loaded.
#
# @export
#
#GetDictionaries <- function () {
#
#    dictionaryService <- client$getDictionaryService()
#
#    tryCatch(result <- dictionaryService$getDictionaryEntriesAsJSON(),
#        Throwable = function (e) {
#            stop(
#                paste ("Unable to get the dictionaries",
#                    " -- details follow. ", e$getMessage(), sep="")
#            )
#        }
#    )
#
#    resultantObject <- RJSONIO::fromJSON(result)
#    resultantFrame <- as.data.frame(do.call("rbind" , resultantObject))
#    return(resultantFrame)
#}

# Allows the user to inspect the directories that have been loaded by this
# package.
#
# @return The directories that have been loaded.
#
# @export
#
#GetDirectories <- function () {
#
#    directoryService <- client$getDirectoryService()
#
#    tryCatch(result <- directoryService$getDirectoryEntriesAsJSON(),
#        Throwable = function (e) {
#            stop(
#                paste ("Unable to get the directories",
#                    " -- details follow. ", e$getMessage(), sep="")
#            )
#        }
#    )
#
#    resultantObject <- RJSONIO::fromJSON(result)
#    resultantFrame <- as.data.frame(do.call("rbind" , resultantObject))
#    return(resultantFrame)
#}

#' Returns either NULL if result is equal to "null" or the result as a data frame.
#'
#' @param result Result will be converted into a data frame.
#'
#' @return The result as a data frame.
#'
.AsDataFrame <- function (result) {

    resultantFrame <- NULL
    resultantObject <- NULL

    resultantObject <- if (result == "null") NULL else RJSONIO::fromJSON(result)

    if (!is.null (resultantObject)) {
        resultantFrame <- as.data.frame(do.call("rbind", resultantObject))
    }

    return(resultantFrame)
}

#' Retrieves the next update from Thomson Reuters.
#'
#' @param timeout Wait for timeout millis and then return null -- defaults to 10 seconds. Note that setting the timeout
#'  to zero will cause the thread to wait forever.
#'
#' @return A reference to the market price [Java] object.
#'
#' @export
#'
GetNextUpdateAsJavaObject <- function (timeout = "10000") {

    #bigLong <- J("java.lang.Long")
    # actualTimeoutValue <- bigLong$valueOf (timeout)

    client <- .cdatafeedtrep.env$client

    tryCatch(
        result <- client$getNextMarketPriceUpdateAsJavaObject(timeout), Throwable = function (e) {
            stop(
                 paste ("Unable to get the next update -- details follow. ",
                    e$getMessage(), sep="")
            )
        }
    )
    return (result)
}

#' Retrieves the next update from Thomson Reuters.
#'
#' @param timeout Wait for timeout millis and then return null -- defaults to 10 seconds. Note that setting the timeout
#'  to zero will cause the thread to wait forever.
#'
#' @return The updated market price as a data frame.
#'
#' @export
#'
GetNextUpdate <- function (timeout = "10000") {

    client <- .cdatafeedtrep.env$client

    tryCatch(
        result <- client$getNextMarketPriceUpdateAsJson(timeout), Throwable = function (e) {
            stop(
                paste ("Unable to get the next update -- details follow. ",
                       e$getMessage(), sep="")
            )
        }
    )

    return (.AsDataFrame (result))
}

#' Sends a request to Thomson Reuters to receive time series data for the specified symbols.
#'
#' @param serviceName The service can be, for example, dELEKTRON_DD -- the default is ELEKTRON_DD.
#'
#' @param symbol A single simbol -- for example "GOOG.O".
#'
#' @param period One of daily, weekly, or monthly -- the default is "daily".
#'
#' @param timeout The milliseconds to wait for a response to be returned. If nothing is returned when the timeout has
#' elapsed this function will return NULL. Defaults to 30 seconds.
#'
#' @return A data frame containing the time series data for the specified symbol.
#'
#' @examples {
#' result <- GetTimeSeriesDataFor(symbol = "MSFT.O", period="monthly")
#' tempDF <- data.frame(DATE=unlist(result$DATE),OPEN=unlist(result$OPEN))
#' tempDF <- tempDF[order(tempDF$DATE),]
#' tempDF$DATE <- as.POSIXct(as.numeric(substr(x = as.character(tempDF$DATE), start=1, stop=10)), origin="1970-01-01", tz="GMT")
#' tempDF$OPEN <- as.numeric(as.character(tempDF$OPEN))
#' plot(tempDF, type="l")
#' }
#'
#' @export
#'
GetTimeSeriesDataFor <- function (serviceName="ELEKTRON_DD", symbol, period = "daily", timeout="30000") {

    client <- .cdatafeedtrep.env$client

    if (!(period == "daily" || period == "weekly" || period == "monthly")) {
        stop (paste ("The '", period,"' period is invalid -- use on of daily, weekly, or monthly.", sep=""))
    }

    tryCatch(result <- client$getTimeSeriesAsJson(serviceName, symbol, period),
        Throwable = function (e) {
            e$printStackTrace()
            stop(
                paste ("Unable to query the following symbols [", symbol,
                    "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    resultDF <- RJSONIO::fromJSON(result)

    uncoercedResultDF <- as.data.frame(do.call("rbind", resultDF))

    coercedResultDF <- t(uncoercedResultDF)

    return (as.data.frame(coercedResultDF))
}

# Function retrieves the next status response update from Thomson Reuters.
#
# @param timeout The milliseconds to wait for a response to be returned. If
#  nothing is returned when the timeout has elapsed this function will return
#  NULL. Defaults to zero (forever).
#
# @return status response as a data frame or NULL if the timeout has elapsed.
#
# @export
#
#GetNextStatusResponse <- function (timeout="0") {
#
#    statusResponseService <- client$getStatusResponseService()
#
#     tryCatch(
#        result <- statusResponseService$getNextUpdateAsJSON(timeout),
#        Throwable = function (e) {
#            stop(
#                 paste ("Unable to get the next status response update ",
#                    "-- details follow. ", e$getMessage(), sep="")
#            )
#        }
#    )
#    return (.AsDataFrame (result))
#}

#' Function prints some information about this package.
#'
#' @examples
#'  \dontrun{
#'      About()
#'  }
#'
#' @export
#'
About <- function () {
    cat (
        " ***********************************************************************************\n",
        "***                                                                               ***\n",
        "*** Coherent Datafeed: Thomson Reuters Elektron Edition Package For the R Project ***\n",
        "***                                                                               ***\n",
        "***                                 version 1.0.8.                                ***\n",
        "***                                                                               ***\n",
        "***                            Follow us on LinkedIn:                             ***\n",
        "***                                                                               ***\n",
        "***                   https://www.linkedin.com/company/229316                     ***\n",
        "***                                                                               ***\n",
        "***                            Follow us on Twitter:                              ***\n",
        "***                                                                               ***\n",
        "***                    https://twitter.com/CoherentLogicCo                        ***\n",
        "***                                                                               ***\n",
        "*************************************************************************************\n")
}
