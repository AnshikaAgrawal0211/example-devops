package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import controller.Controller

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object devopsApp extends App {
  implicit val system: ActorSystem = ActorSystem("actorSystem")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val materialize: ActorMaterializer = ActorMaterializer()
  private val routes = (new Controller).routes
  private val serverBinding =  Http().bindAndHandle(
      routes, "localhost", 9000)
  serverBinding.onComplete {
    case Success(bound) =>
      println(
        s"Server online at http://${ bound.localAddress.getHostName }:${ bound.localAddress.getPort }/")
    case Failure(error) =>
      Http()(system)
        .shutdownAllConnectionPools()
        .andThen {
          case _ =>
            println("failed" + error)
            system.terminate()
        }
  }
}
