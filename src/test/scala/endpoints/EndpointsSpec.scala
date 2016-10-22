package endpoints

import helpers.TestInterpreter
import org.http4s._
import org.http4s.dsl._
import org.scalatest.{Matchers, WordSpecLike}

class EndpointsSpec extends WordSpecLike with Matchers with Endpoints {

  val interpreter = new TestInterpreter
  val endpoints: HttpService = HttpService(routes.andThen(_.foldMap(interpreter)))

  "The Endpoints" should {
    "respond with 200 for a basic request" in {
      val request = Request(method = GET, uri = uri("/tags/email"))
      val response: Response = endpoints.run(request).run
      response.status should be(Status.Ok)
    }

    "respond with 200 for a post" in {
      val request = Request(method = POST, uri = uri("/tags/email?tags=123,32"))
      val response: Response = endpoints.run(request).run
      response.status should be(Status.Ok)
    }

    "respond with 404 for a non existing endpoint" in {
      val request = Request(method = GET, uri = uri("/nothingHere"))
      val response: Response = endpoints.run(request).run
      response.status should be(Status.NotFound)
    }

    "respond with 404 for an invalid method" in {
      val request = Request(method = GET, uri = uri("/tags/email/search,for,this"))
      val response: Response = endpoints.run(request).run
      response.status should be(Status.NotFound)
    }

    "respond with 404 for missing parameter" in {
      val request = Request(method = POST, uri = uri("/tags/email"))
      val response: Response = endpoints.run(request).run
      response.status should be(Status.NotFound)
    }

    "respond with 404 for wrong parameter" in {
      val request = Request(method = POST, uri = uri("/tags/email?tag=dsa,das"))
      val response: Response = endpoints.run(request).run
      response.status should be(Status.NotFound)
    }
  }
}