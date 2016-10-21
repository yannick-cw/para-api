package helpers

import cats._
import db_logic.SearchTermOps.{DbOpA, GetTags, SetTags}

/**
 * Created by 437580 on 21/10/16.
 */
class TestInterpreter extends (DbOpA ~> Id) {
  var mail = ""
  var setTags = List.empty[String]

  override def apply[A](fa: DbOpA[A]): Id[A] = fa match {
    case GetTags(email) => setTags
    case SetTags(email, tagsToSet) =>
      mail = email
      setTags = tagsToSet
  }
}
