package bbc.rms.marsbellatrix.api

import akka.http.scaladsl.server.Directives._
import bbc.rms.marsbellatrix.api.directives.CustomDirectives
import bbc.rms.marsbellatrix.domain.response.Status
import bbc.rms.marsbellatrix.domain.marshalling.JsonSerializers

trait StatusApi extends JsonSerializers
  with CustomDirectives {

  val statusRoutes = {
    path("status") {
      get {
        respondWithNoCacheHeaders {
          complete {
            Status("OK")
          }
        }
      }
    }
  }
}
