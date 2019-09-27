package bbc.rms.marsbellatrix.api

import akka.http.scaladsl.server.Directives._
import bbc.rms.marsbellatrix.api.directives.CustomDirectives
import bbc.rms.marsbellatrix.config.Config
import bbc.rms.marsbellatrix.datasource.ItemDataSource
import bbc.rms.marsbellatrix.domain.marshalling.JsonSerializers
import bbc.rms.client.monitoring.Monitoring
import org.slf4s.Logging

trait ItemApi extends JsonSerializers
  with CustomDirectives
  with Config
  with Monitoring
  with Logging {

  def itemDataSource: ItemDataSource

  val itemRoutes = {
    path("items") {
      get {
        respondWithCacheHeaders {
          paginate(defaultPageLimit, maximumPageLimit) { pagination =>
            sort { itemSort =>
              complete {
                log.info("/items")

                timed("response_times.items") {
                  toResponse(itemSchemaUrl, pagination) {
                    itemDataSource.getMultipleItems(pagination.limit, pagination.offset)
                  }
                }
              }
            }
          }
        }
      }
    } ~ path("items" / IntNumber) { id =>
      get {
        respondWithCacheHeaders {
          complete {
            log.info(s"/items/$id")

            timed("response_times.item") {
              toResponse(itemSchemaUrl) {
                itemDataSource.getSingleItem(id)
              }
            }
          }
        }
      }
    }
  }
}
