package bbc.rms.marsbellatrix.datasource

import bbc.rms.marsbellatrix.api.directives.ResponseDirectives.MultiEntityResponseData
import bbc.rms.marsbellatrix.domain.Item

import scala.concurrent.Future
import scala.util.Random

class RandomItemDataSource extends ItemDataSource {
  private def randomItem = Item(Random.nextString(length = 4))
  private val itemRepository: Seq[Item] = Stream.continually(randomItem)

  override def getSingleItem(id: Int): Future[Option[Item]] = {
    Future.successful(itemRepository.headOption)
  }

  override def getMultipleItems(limit: Int, offset: Int): Future[MultiEntityResponseData[Item]] = {
    val results: Seq[Item] = itemRepository.take(limit)
    Future.successful(MultiEntityResponseData(results, results.size))
  }
}
