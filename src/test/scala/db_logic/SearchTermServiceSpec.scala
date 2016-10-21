package db_logic

import cats._
import db_logic.SearchTermOps._
import org.scalatest.{Matchers, WordSpecLike}
import org.scalacheck.Prop.forAll
import org.scalatest.prop.Checkers

class SearchTermServiceSpec extends WordSpecLike with Matchers with Checkers {

  val someTags: List[String] = List("search", "tags")

  class TestInterpreter extends (EntityDBA ~> Id) {
      var mail = ""
      var setTags = List.empty[String]

      override def apply[A](fa: EntityDBA[A]): Id[A] = fa match {
        case GetTags(email) => setTags
        case SetTags(email, tagsToSet) =>
          mail = email
          setTags = tagsToSet
    }
  }

  "The SeatchTermService" should {
    "should be able to write tags" in {
      check(forAll{
        (mail: String, terms: List[String]) =>
          val interpreter = new TestInterpreter
          SearchTermService.writeTags(mail, terms).foldMap(interpreter)

          interpreter.mail == mail &&
          interpreter.setTags == terms
      })
    }
    "should be able to retrieve tags for emails" in {
      check(forAll{
        (mail: String, terms: List[String]) =>
          val interpreter = new TestInterpreter
          interpreter.setTags = terms

          SearchTermService.retrieveTags(mail).foldMap(interpreter) == interpreter.setTags
      })
    }
  }
}
