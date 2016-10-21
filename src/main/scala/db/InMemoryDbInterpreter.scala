package db

import cats._
import db_logic.SearchTermService.{DbOpA, GetAll, GetTags, SetTags}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class InMemoryDbInterpreter extends (DbOpA ~> Future) {
  var map = Map.empty[String, List[String]]

  override def apply[A](fa: DbOpA[A]): Future[A] = fa match {
    case GetTags(email) => Future(map.getOrElse(email, List.empty))
    case SetTags(email, tagsToSet) => Future{ map = map.updated(email, tagsToSet) }
    case GetAll => Future(map)
  }
}
