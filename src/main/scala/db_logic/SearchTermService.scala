package db_logic

import cats.free.Free
import cats.free.Free.liftF
import models.User

object SearchTermService {
  sealed trait DbOpA[A]
  final case class GetTags(email: String) extends DbOpA[User]
  final case class SetTags(user: User) extends DbOpA[Unit]
  final case object GetAll extends DbOpA[Set[User]]

  type DbOp[A] = Free[DbOpA, A]

  // the operations
  def getTags(email: String): DbOp[User] = liftF(GetTags(email))
  def setTags(user: User): DbOp[Unit] = liftF(SetTags(user))
  def getAll: DbOp[Set[User]] = liftF(GetAll)
}