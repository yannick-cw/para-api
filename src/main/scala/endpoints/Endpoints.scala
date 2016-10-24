package endpoints

import db_logic.SearchTermService._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceInstances
import org.http4s.dsl._

import scalaz.concurrent.Task
import CustomDecode._
import endpoints.Endpoints.Route
import models.User

trait Endpoints extends CirceInstances {

  val routes: Route = {
    case GET -> Root / "tags" / email =>
      getTags(email)
        .map(usr => Ok(usr.asJson))

    case POST -> Root / "tags" / email :? T(tags) =>
      setTags(User(email, tags))
          .map(_ => Ok("done"))

    case GET -> Root / "all-tags" =>
      getAll.map(usrs => Ok(usrs.asJson))
  }
}

object Endpoints {
  type Route = PartialFunction[Request, DbOp[Task[Response]]]
}