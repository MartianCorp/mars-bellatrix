package bbc.rms.marsbellatrix

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import bbc.rms.marsbellatrix.api.Api
import bbc.rms.marsbellatrix.config.Config
import kamon.Kamon
import org.slf4s.Logging
import scala.concurrent.ExecutionContextExecutor

object MarsBellatrixService extends App
  with Api
  with Config
  with Logging {

  implicit val system: ActorSystem = MarsBellatrixContext.system
  implicit val materializer: ActorMaterializer = MarsBellatrixContext.materializer

  implicit def executor: ExecutionContextExecutor = system.dispatcher

  Kamon.start()

  Http().bindAndHandle(routes, httpInterface, httpPort)

  log.info(s"Starting $serviceName on $httpInterface:$httpPort")
}
