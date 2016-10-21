package db_logic
import db_logic.SearchTermOps._

import cats.free.Free
import cats.free.Free.liftF

object SearchTermService {

  def writeTags(email: String, tags: List[String]): EntityDB[Unit] = for {
    _ <- setTags(email, tags)
  } yield ()

  def retrieveTags(email: String): EntityDB[List[String]] = for {
    tags <- getTags(email)
  } yield tags
}

object SearchTermOps {
  sealed trait EntityDBA[A]
  case class GetTags(email: String) extends EntityDBA[List[String]]
  case class SetTags(email: String, tags: List[String]) extends EntityDBA[Unit]

  type EntityDB[A] = Free[EntityDBA, A]

  def getTags(email: String): EntityDB[List[String]] = liftF(GetTags(email))
  def setTags(email: String, tags: List[String]): EntityDB[Unit] = liftF(SetTags(email, tags))
}