package bbc.rms.marsbellatrix.api.rejection

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.RejectionHandler
import bbc.rms.marsbellatrix.api.directives.ErrorResponseDirectives.ErrorResponseData
import bbc.rms.marsbellatrix.api.directives.ErrorResponseDirectives
import bbc.rms.marsbellatrix.config.Config

trait MarsBellatrixRejectionHandler extends ErrorResponseDirectives
  with Config {

  implicit val rejectionHandler = RejectionHandler.newBuilder()
    .handle { case marsBellatrixRejection: MarsBellatrixRejection =>
      completeWithError(errorSchemaUrl, errorDocumentationUrl) {
        ErrorResponseData(marsBellatrixRejection.statusCode, marsBellatrixRejection.message)
      }
    }
    .handleNotFound {
      completeWithError(errorSchemaUrl, errorDocumentationUrl) {
        ErrorResponseData(StatusCodes.NotFound, "Not found")
      }
    }
    .result()
}
