package db_logic
import db_logic.SearchTermOps._

import cats.free.Free
import cats.free.Free.liftF

object SearchTermService {

  def writeTags(email: String, tags: List[String]): DbOp[Unit] = for {
    _ <- setTags(email, tags)
  } yield ()

  def retrieveTags(email: String): DbOp[List[String]] = for {
    tags <- getTags(email)
  } yield tags
}

object SearchTermOps {
  sealed trait DbOpA[A]
  case class GetTags(email: String) extends DbOpA[List[String]]
  case class SetTags(email: String, tags: List[String]) extends DbOpA[Unit]

  type DbOp[A] = Free[DbOpA, A]

  def getTags(email: String): DbOp[List[String]] = liftF(GetTags(email))
  def setTags(email: String, tags: List[String]): DbOp[Unit] = liftF(SetTags(email, tags))
}