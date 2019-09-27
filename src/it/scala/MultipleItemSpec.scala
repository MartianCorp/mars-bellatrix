import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.Specs2RouteTest
import bbc.rms.marsbellatrix.api.Api
import bbc.rms.marsbellatrix.domain.Item
import bbc.rms.marsbellatrix.domain.response.{ErrorResponse, Response}
import org.specs2.mutable.Specification

class MultipleItemSpec extends Specification
  with Specs2RouteTest
  with Api {

  override lazy val maximumPageLimit: Int = 50

  "The service" should {

    "return a response with items" in {
      Get("/items") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        contentType must be equalTo `application/json`
        responseAs[Response[Item]].total must be > 0
        header("Cache-Control").get.value must be equalTo "public, max-age=300, s-maxage=300, stale-while-revalidate=7200, stale-if-error=7200"
      }
    }

    "return a response with the requested limit" in {
      Get("/items?limit=1") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        responseAs[Response[Item]].total must be equalTo 1
      }
    }

    "return a 400 Bad Request when a limit greater than the maximum is requested" in {
      Get("/items?limit=51") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
        responseAs[ErrorResponse].errors.size must be > 0
      }
    }
  }

}
