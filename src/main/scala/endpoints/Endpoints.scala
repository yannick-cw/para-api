package endpoints

import db.InMemoryDbInterpreter
import db_logic.SearchTermOps._
import org.http4s._
import org.http4s.dsl._

import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.future._
import org.http4s.EntityEncoder.futureEncoder
import org.http4s.circe.CirceInstances
import io.circe.generic.auto._
import io.circe.syntax._

object Endpoints extends CirceInstances {

  case class Result(email: String, terms: List[String])

  val db: InMemoryDbInterpreter = new InMemoryDbInterpreter

  val retrieveTags = HttpService {
    case GET -> Root / "tags" / email =>
      Ok(getTags(email)
        .foldMap(db)
        .map(Result(email, _).asJson))

    case POST -> Root / "tags" / email / tags =>
      Ok(setTags(email, tags.split(",").toList)
          .foldMap(db)
          .map(_ => "done"))
  }
}