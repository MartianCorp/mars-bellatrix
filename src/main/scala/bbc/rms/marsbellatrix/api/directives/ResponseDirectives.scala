package bbc.rms.marsbellatrix.api.directives

import bbc.rms.marsbellatrix.api.directives.ResponseDirectives.MultiEntityResponseData
import bbc.rms.marsbellatrix.api.params.Paginate
import bbc.rms.marsbellatrix.domain.response.Response
import scala.concurrent.{ExecutionContextExecutor, Future}

trait ResponseDirectives {
  implicit def executor: ExecutionContextExecutor

  def toResponse[T](schema: String, pagination: Paginate)
                   (resultsFuture: Future[MultiEntityResponseData[T]]): Future[Response[T]] = {

    resultsFuture.map { results =>
      Response(
        `$schema` = schema,
        total = results.total,
        limit = pagination.limit,
        offset = pagination.offset,
        results = results.entities
      )
    }
  }

  def toResponse[T](schema: String)
                   (resultsFuture: Future[Option[T]]): Future[Response[T]] = {

    resultsFuture.map { results =>
      Response(
        `$schema` = schema,
        total = 1,
        limit = 1,
        offset = 0,
        results = results.toSeq
      )
    }
  }

  def toResponse[T](schema: String, singleResult: T): Response[T] = {

    Response(
      `$schema` = schema,
      total = 1,
      limit = 1,
      offset = 0,
      results = Seq(singleResult)
    )
  }
}

object ResponseDirectives {
  case class MultiEntityResponseData[T](entities: Seq[T], total: Int)
}
