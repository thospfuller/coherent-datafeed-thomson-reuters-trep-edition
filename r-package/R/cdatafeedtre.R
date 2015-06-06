#'
#' @import rJava
#' @import RJSONIO
#'
#' @docType package
#'
#' @name cdatafeedtre
#'
NULL

#'
#' An environment which is used by this package when managing package-scope
#' variables.
#'
cdatafeedtrep.env <- new.env()

.onLoad <- function (libname, pkgname) {
    library("rJava")
    library("RJSONIO")
    #
    # From the RJava documentation:
    # If a package needs special Java parameters, "java.parameters" option can
    # be used to set them on initialization. Note, however, that Java
    # parameters can only be used during JVM initialization and other package
    # may have intialized JVM already.
    #
    # It may be a better idea to allow the user to set this theselves.
    #
    targetDir = paste ("-Djava.util.logging.config.file=", tempdir (), sep="")

    packageDir = paste ("-DrpackagePath=", system.file(package="cdatafeedtre"), sep="")

    .jpackage(pkgname, lib.loc = libname)

    client <- NULL
    client <<- .jnew("com.coherentlogic.coherent.datafeed.client.Client")
}

.onUnload <- function (libpath) {
    tryCatch(client$stop(), Throwable = function (e) {    
        stop (paste ("An exception was thrown when stopping the client -- ",
            "details follow.", e$getMessage(), sep=""))
    })
}

Initialize <- function () {
    tryCatch(client$start(), Throwable = function (e) {

        #
        # If an exception is thrown from here check that the
        # fieldDictionaryFactory is set to use the
        # RPackageDictionaryFactory.
        #
        stop (paste ("An exception was thrown when starting the client -- ",
            "details follow.", e$getMessage(), sep=""))
    })
}

InstallLicense <- function (licenseFile) {
    #
    # This function only needs to be called once in order to install a
    # license.
    #
    tryCatch(client$installLicense(licenseFile), Throwable = function (e) {
        stop (paste ("Unable to install the license '", licenseFile,"' -- ",
            "details follow. ", e$getMessage(), sep=""))
    })
}

UninstallLicense <- function () {
    #
    # This function only needs to be called once in order to uninstall a
    # license.
    #    
    tryCatch(client$uninstallLicense(), Throwable = function (e) {
        stop (paste ("Unable to uninstall the license -- ",
            "details follow. ", e$getMessage(), sep=""))
    })
}

Login <- function (dacsId) {
    # Establishes a session with Thomson Reuters.
    #
    # Args:
    #   dacsId: The user's Thomson Reuters DACS id.
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

# MOVE STOP TO A TEARDOWN METHOD.
Logout <- function () {
    # Terminates the session with Thomson Reuters.

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

Query <- function (serviceName="IDN_RDF", symbols) {
    # Sends a request to Thomson Reuters to receive market price updates for
    # the specified symbols.
    #
    # Args:
    #   symbols: A vector of one or more symbols, for example "GOOG.O".

    marketPriceService <- client$getMarketPriceService()

    #if ( is.null (serviceName) ) {
    #    serviceName <- "IDN_RDF"
    #}

    tryCatch(marketPriceService$query(serviceName, loginHandle, symbols),
        Throwable = function (e) {
            stop(
                paste ("Unable to query the following symbols [", symbols,
                    "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )
}

GetDictionaries <- function () {
    # Returns the dictionaries that have been loaded into this application.
    #
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

GetDirectories <- function () {
    # Returns the directories that have been loaded into this application.
    #
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

AsDataFrame <- function (result) {
    #
    # Function returns either NULL if result is equal to "null" or the
    # result as a data frame.
    #

    resultantFrame <- NULL
    resultantObject <- NULL
 
    resultantObject <- if (result == "null") NULL else RJSONIO::fromJSON(result)

    if (!is.null (resultantObject)) {
        resultantFrame <- as.data.frame(do.call("rbind" , resultantObject))
    }

    return(resultantFrame)
}

GetNextUpdate <- function (timeout="0") {
    # Function retrieves the next update from Thomson Reuters.
    #
    # Returns:
    #   A market price as a data frame consisting of the symbols, fields, and field values.
    
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
    return (AsDataFrame (result))
}

GetTimeSeriesDataFor <- function (serviceName="IDN_RDF", symbol, period, timeout="0") {
    # Sends a request to Thomson Reuters to receive time series data for
    # the specified symbols.
    #
    # Args:
    #   symbols: A vector of one or more symbols, for example "GOOG.O".
    
    timeSeriesService <- client$getTimeSeriesService()
    
    tryCatch(timeSeriesService$queryTimeSeriesFor(serviceName, loginHandle, symbol, period),
        Throwable = function (e) {
            stop(
                paste ("Unable to query the following symbols [", symbol,
                    "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    # Timeout is set to ten seconds -- if the result is null, then the symbol is
    # likely to be incorrect.
    tryCatch(result <- timeSeriesService$getNextUpdateAsJSON(timeout),
        Throwable = function (e) {
            stop(
                paste ("Unable to get the next update for the following symbol",
                    "[", symbol, "] -- details follow. ", e$getMessage(), sep="")
            )
        }
    )

    resultT <- t (result)

    resultTDf <- as.data.frame (resultT)

    return (resultTDf)
}

GetNextStatusResponse <- function (timeout="0") {
    # Function retrieves the next status response update from Thomson Reuters.
    #
    # Returns:
    #   A status response as a data frame.
    
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
    return (AsDataFrame (result))
}