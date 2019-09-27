package bbc.rms.marsbellatrix.api.rejection

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Rejection

sealed trait MarsBellatrixRejection extends Rejection {
  def statusCode: StatusCode
  def message: String
}

case class InvalidOffsetRejection(offset: Int) extends MarsBellatrixRejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Invalid value for offset: '$offset'"
}

case class InvalidPageLimitRejection(maximumPageLimit: Int) extends MarsBellatrixRejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Page limit must be between 0 and $maximumPageLimit"
}
