package bbc.rms.marsbellatrix

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import bbc.rms.marsbellatrix.config.Config

import bbc.rms.marsbellatrix.datasource.{ItemDataSource, RandomItemDataSource}

object MarsBellatrixContext extends Config {
  implicit val system: ActorSystem = ActorSystem(serviceName)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val itemDataSource: ItemDataSource = new RandomItemDataSource
}
