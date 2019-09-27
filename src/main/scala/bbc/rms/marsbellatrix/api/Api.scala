package bbc.rms.marsbellatrix.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import bbc.rms.marsbellatrix.MarsBellatrixContext
import bbc.rms.marsbellatrix.api.error.MarsBellatrixExceptionHandler
import bbc.rms.marsbellatrix.api.rejection.MarsBellatrixRejectionHandler
import bbc.rms.marsbellatrix.datasource.ItemDataSource

import scala.concurrent.ExecutionContextExecutor

trait Api extends StatusApi
  with ItemApi
  with MarsBellatrixRejectionHandler
  with MarsBellatrixExceptionHandler {

  override def itemDataSource: ItemDataSource = MarsBellatrixContext.itemDataSource

  lazy val routes = {
    logRequestResult("mars-bellatrix") {
      statusRoutes ~ itemRoutes
    }
  }
}
