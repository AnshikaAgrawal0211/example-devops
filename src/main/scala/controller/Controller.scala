package controller

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

class Controller {
val routes:Route =
  get {
    pathPrefix("welcome" / Segment) { name =>
      println(s"welcome $name \n")
      complete(
        s"welcome $name \n")
    }
  }
}
