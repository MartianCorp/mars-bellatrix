package bbc.rms.marsbellatrix.datasource

import bbc.rms.marsbellatrix.api.directives.ResponseDirectives.MultiEntityResponseData
import bbc.rms.marsbellatrix.domain.Item
import scala.concurrent.Future

trait ItemDataSource {
  def getSingleItem(id: Int): Future[Option[Item]]
  def getMultipleItems(limit: Int, offset: Int): Future[MultiEntityResponseData[Item]]
}
