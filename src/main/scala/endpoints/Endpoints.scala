package endpoints

import db_logic.SearchTermService._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.{Request, Response}
import org.http4s.circe.CirceInstances
import org.http4s.dsl._

import scalaz.concurrent.Task

object Endpoints extends CirceInstances {

  case class Result(email: String, terms: List[String])
  type Route = PartialFunction[Request, DbOp[Task[Response]]]

  def routes: Route = {
    case GET -> Root / "tags" / email =>
      getTags(email)
        .map(terms => Ok(Result(email, terms).asJson))

    case POST -> Root / "tags" / email / tags =>
      setTags(email, tags.split(",").toList)
          .map(_ => Ok("done"))
    case GET -> Root / "all-tags" =>
      getAll.map(map => Ok(map.asJson))
  }
}