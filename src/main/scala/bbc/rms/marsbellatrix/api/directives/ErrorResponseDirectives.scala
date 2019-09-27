package bbc.rms.marsbellatrix.api.directives

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import bbc.rms.marsbellatrix.api.directives.ErrorResponseDirectives.ErrorResponseData
import bbc.rms.marsbellatrix.domain.marshalling.JsonSerializers
import bbc.rms.marsbellatrix.domain.response.ErrorResponse

trait ErrorResponseDirectives extends JsonSerializers {

  def completeWithError(schemaUrl: String, documentationUrl: String)
                       (errorResponseData: ErrorResponseData): StandardRoute = {

    val errorResponse = ErrorResponse(
      jsonSchemaUrl = schemaUrl,
      documentationUrl = documentationUrl,
      httpStatus = errorResponseData.statusCode.intValue,
      message = errorResponseData.message)

    complete(errorResponseData.statusCode, errorResponse)
  }

}

object ErrorResponseDirectives {
  case class ErrorResponseData(statusCode: StatusCode, message: String)
}
