#'
#' @title The Coherent Datafeed Thomson Reuters Edition.
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
cdatafeedtrep.env <- new.env()

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
                "executing library (\"cdatafreedtre\"). Specifically RFA version 7.6.0.L1 is required by this package ",
                "and can be downloaded direct from Thomson Reuters by visiting the following link: ",
                "https://customers.reuters.com/a/support/technical/softwaredownload/", sep="\n"))
    } else {
        print(paste("cdatafeedJars: ", cdatafeedJars, sep=""))
    }

    .jpackage(pkgname, lib.loc = libname, morePaths=cdatafeedJars)

    client <- NULL
    client <<- .jnew("com.coherentlogic.coherent.datafeed.client.Client")
}

.onUnload <- function (libpath) {
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
    tryCatch(client$start(), Throwable = function (e) {

        stop (paste ("An exception was thrown when starting the client -- ",
            "details follow.", e$getMessage(), sep=""))
    })
}

#' Establishes a session with Thomson Reuters.
#'
#' @param dacsId The user's Thomson Reuters DACS id.
#'
#' @export
#'
Login <- function (dacsId) {

    authenticationService <- client$getAuthenticationService()

    tryCatch(
        loginHandle <<- client$login(dacsId), Throwable = function (e) {
            stop(
                paste ("Unable to login using the dacsId '", dacsId,
                    "' -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    tryCatch(
        client$waitForInitialisationToComplete(), Throwable = function (e) {
            stop(
                paste ("The application login has succeeded but the ",
                    "application initialisation has failed. Try again or ",
                    "inspect the log file for details pertaining to what ",
                    "the problem is.", e$getMessage(), sep="")
            )
        }
    )
}

#' Terminates the session with Thomson Reuters.
#'
#' @export
#'
Logout <- function () {

    authenticationService <- client$getAuthenticationService()

    tryCatch(
        authenticationService$logout(), Throwable = function (e) {
            stop(
                paste ("Unable to logout -- details follow. ", e$getMessage(),
                    sep="")
            )
        }
    )
}

#' Sends a request to Thomson Reuters to receive market price updates for the
#' specified symbols.
#'
#' @param serviceName For example "dIDN_RDF" (defaults to "IDN_RDF").
#' @param symbols One or more symbols, for example "GOOG.O".
#'
#' @export
#'
Query <- function (serviceName="IDN_RDF", symbols) {

    marketPriceService <- client$getMarketPriceService()

    tryCatch(marketPriceService$query(serviceName, loginHandle, symbols),
        Throwable = function (e) {
            stop(
                paste ("Unable to query the following symbols [", symbols,
                    "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )
}

#' Allows the user to inspect the dictionaries that have been loaded by this
#' package.
#'
#' @return The dictionaries that have been loaded.
#'
#' @export
#'
GetDictionaries <- function () {

    dictionaryService <- client$getDictionaryService()

    tryCatch(result <- dictionaryService$getDictionaryEntriesAsJSON(),
        Throwable = function (e) {
            stop(
                paste ("Unable to get the dictionaries",
                    " -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    resultantObject <- RJSONIO::fromJSON(result)
    resultantFrame <- as.data.frame(do.call("rbind" , resultantObject))
    return(resultantFrame)
}

#' Allows the user to inspect the directories that have been loaded by this
#' package.
#'
#' @return The directories that have been loaded.
#'
#' @export
#'
GetDirectories <- function () {

    directoryService <- client$getDirectoryService()

    tryCatch(result <- directoryService$getDirectoryEntriesAsJSON(),
        Throwable = function (e) {
            stop(
                paste ("Unable to get the directories",
                    " -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    resultantObject <- RJSONIO::fromJSON(result)
    resultantFrame <- as.data.frame(do.call("rbind" , resultantObject))
    return(resultantFrame)
}

#' Returns either NULL if result is equal to "null" or the
#' result as a data frame.
#'
#' @param result Result will be converted into a data frame.
#'
#' @return The result as a data frame.
#'
#' TODO Improve this documentation.
#'
.AsDataFrame <- function (result) {

    resultantFrame <- NULL
    resultantObject <- NULL

    resultantObject <- if (result == "null") NULL else RJSONIO::fromJSON(result)

    if (!is.null (resultantObject)) {
        resultantFrame <- as.data.frame(do.call("rbind" , resultantObject))
    }

    return(resultantFrame)
}

#' Retrieves the next update from Thomson Reuters.
#'
#' @param timeout Wait for timeout millis and then return null -- defaults to
#'  zero (forever).
#'
#' @return The market price as a data frame.
#'
#' @export
#'
GetNextUpdate <- function (timeout="0") {

    marketPriceService <- client$getMarketPriceService()

     tryCatch(
        result <- marketPriceService$getNextUpdateAsJSON(timeout),
        Throwable = function (e) {
            stop(
                 paste ("Unable to get the next update -- details follow. ",
                    e$getMessage(), sep="")
            )
        }
    )
    return (.AsDataFrame (result))
}

#' Sends a request to Thomson Reuters to receive time series data for the
#' specified symbols.
#'
#' @param serviceName The service can be, for example, dIDN_RDF -- the default
#' is IDN_RDF.
#'
#' @param symbol A single simbol -- for example "GOOG.O".
#'
#' @param period One of daily, weekly, or monthly -- the default is "daily".
#'
#' @param timeout The milliseconds to wait for a response to be returned. If
#' nothing is returned when the timeout has elapsed this function will return
#' NULL. Defaults to zero (forever).
#'
#' @return A data frame containing the time series data for the specified
#' symbol.
#'
#' @export
#'
GetTimeSeriesDataFor <- function (serviceName="IDN_RDF", symbol, period = "daily", timeout="0") {

    if (!(period == "daily" || period == "weekly" || period == "monthly")) {
        stop (paste ("The '", period,"' period is invalid -- use on of daily, weekly, or monthly.", sep=""))
    }

    timeSeriesService <- client$getTimeSeriesService()

    tryCatch(timeSeriesService$queryTimeSeriesFor(serviceName, loginHandle, symbol, period),
        Throwable = function (e) {
            stop(
                paste ("Unable to query the following symbols [", symbol,
                    "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    tryCatch(result <- timeSeriesService$getNextUpdateAsJSON(timeout),
        Throwable = function (e) {
            stop(
                paste ("Unable to get the next update for the following symbol",
                    "[", symbol, "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    convertedResult <- RJSONIO::fromJSON(result)

    return (convertedResult)

    #resultT <- t (result)

    #resultTDf <- as.data.frame (resultT)

    # return (resultTDf)
}

#' Function retrieves the next status response update from Thomson Reuters.
#'
#' @param timeout The milliseconds to wait for a response to be returned. If
#'  nothing is returned when the timeout has elapsed this function will return
#'  NULL. Defaults to zero (forever).
#'
#' @return status response as a data frame or NULL if the timeout has elapsed.
#'
#' @export
#'
GetNextStatusResponse <- function (timeout="0") {

    statusResponseService <- client$getStatusResponseService()

     tryCatch(
        result <- statusResponseService$getNextUpdateAsJSON(timeout),
        Throwable = function (e) {
            stop(
                 paste ("Unable to get the next status response update ",
                    "-- details follow. ", e$getMessage(), sep="")
            )
        }
    )
    return (.AsDataFrame (result))
}

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
        " ***********************************************************\n",
        "***                                                     ***\n",
        "***  Coherent Datafeed: Thomson Reuters Edition Package ***\n",
        "***                                                     ***\n",
        "***                   version 1.0.1.                    ***\n",
        "***                                                     ***\n",
        "***                Follow us on LinkedIn:               ***\n",
        "***                                                     ***\n",
        "***       https://www.linkedin.com/company/229316       ***\n",
        "***                                                     ***\n",
        "***                Follow us on Twitter:                ***\n",
        "***                                                     ***\n",
        "***        https://twitter.com/CoherentLogicCo          ***\n",
        "***                                                     ***\n",
        "***********************************************************\n")
}