package db

import cats._
import db_logic.SearchTermService.{DbOpA, GetAll, GetTags, SetTags}
import models.User

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class InMemoryDbInterpreter extends (DbOpA ~> Future) {
  var map = Map.empty[String, User]

  override def apply[A](fa: DbOpA[A]): Future[A] = fa match {
    case GetTags(email) => Future(map.getOrElse(email, User(email, List.empty)))
    case SetTags(usr) => Future(map = map.updated(usr.email, usr))
    case GetAll => Future(map.values.toSet)
  }
}
