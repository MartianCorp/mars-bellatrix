package bbc.rms.marsbellatrix.api.directives

import akka.http.scaladsl.server.{Directive1, Rejection}
import akka.http.scaladsl.server.Directives._
import bbc.rms.marsbellatrix.api.validation.PaginationValidator
import bbc.rms.marsbellatrix.api.params.Paginate
import bbc.rms.marsbellatrix.api.rejection.{InvalidOffsetRejection, InvalidPageLimitRejection}

trait PaginateDirectives {
  private val paginationValidator = new PaginationValidator

  private val isInvalidPageLimit: Int => (Int => Boolean) =
    paginationValidator.isInvalidPageLimit

  def paginate(defaultPageLimit: Int, maximumPageLimit: Int): Directive1[Paginate] = {
    parameters("offset".as[Int].?, "limit".as[Int].?).tflatMap {
      case (Some(offset), _) if paginationValidator.isInvalidOffset(offset) =>
        reject(InvalidOffsetRejection(offset))
      case (_, Some(limit)) if paginationValidator.isInvalidPageLimit(maximumPageLimit)(limit) =>
        reject(InvalidPageLimitRejection(maximumPageLimit))
      case (offset, limit) =>
        provide(Paginate(offset.getOrElse(0), limit.getOrElse(defaultPageLimit)))
    }
  }
}
