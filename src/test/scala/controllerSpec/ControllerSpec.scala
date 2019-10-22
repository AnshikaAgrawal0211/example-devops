package controllerSpec

import akka.http.scaladsl.model.{ContentTypes, HttpRequest, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import controller.Controller
import org.scalatest.{Matchers, WordSpecLike}

class ControllerSpec extends WordSpecLike with Matchers with ScalatestRouteTest{
"Route" should {
  "be able to request a get message" in {
    val getRequest = HttpRequest(uri = "/welcome/anshika")
    getRequest ~> (new Controller).routes ~> check {
      status === StatusCodes.OK
      contentType === ContentTypes.`text/plain(UTF-8)`
      entityAs[String] contains "welcome"
      }
    }
  }
}
