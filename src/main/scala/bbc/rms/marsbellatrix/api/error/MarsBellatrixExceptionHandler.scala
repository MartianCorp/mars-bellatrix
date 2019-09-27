package bbc.rms.marsbellatrix.api.error

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ExceptionHandler
import bbc.rms.marsbellatrix.api.directives.ErrorResponseDirectives
import bbc.rms.marsbellatrix.api.directives.ErrorResponseDirectives.ErrorResponseData
import bbc.rms.marsbellatrix.config.Config
import bbc.rms.client.monitoring.Monitoring
import org.slf4s.Logging

trait MarsBellatrixExceptionHandler extends ErrorResponseDirectives
  with Config
  with Monitoring
  with Logging {

  implicit val routingExceptionHandler =
    ExceptionHandler {
      case exception => {
        log.error(exception.getMessage, exception)
        recordException(exception.getClass.getSimpleName)
        completeWithError(errorSchemaUrl, errorDocumentationUrl) {
          ErrorResponseData(StatusCodes.InternalServerError, "Server Error")
        }
      }
    }
}
