package db_logic

import cats.free.Free
import cats.free.Free.liftF

object SearchTermService {
  sealed trait DbOpA[A]
  case class GetTags(email: String) extends DbOpA[List[String]]
  case class SetTags(email: String, tags: List[String]) extends DbOpA[Unit]
  case object GetAll extends DbOpA[Map[String, List[String]]]

  type DbOp[A] = Free[DbOpA, A]

  def getTags(email: String): DbOp[List[String]] = liftF(GetTags(email))
  def setTags(email: String, tags: List[String]): DbOp[Unit] = liftF(SetTags(email, tags))
  def getAll: DbOp[Map[String, List[String]]] = liftF(GetAll)
}