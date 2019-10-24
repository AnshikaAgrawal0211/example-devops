package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import controller.Controller

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object devopsApp extends App {
  implicit val system: ActorSystem = ActorSystem("actorSystem")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val materialize: ActorMaterializer = ActorMaterializer()
  private val routes = (new Controller).routes
//  private val listOfNames = List("anshika","sachin","amit","ravi")
  private val serverBinding =  Http().bindAndHandle(
      routes, "localhost", 2004)
  serverBinding.onComplete {
    case Success(bound) =>
      println(
        s"Server online at http://${ bound.localAddress.getHostName }:${ bound.localAddress.getPort }/")
//      listOfNames.foreach{ name =>
//        val url = s"http://${ bound.localAddress.getHostName }:${ bound.localAddress.getPort }/welcome/${name}"
//        Http().singleRequest(HttpRequest(uri = url))
//        Thread.sleep(10000)
//      }
//      Http()(system)
//        .shutdownAllConnectionPools()
//      system.terminate()
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
