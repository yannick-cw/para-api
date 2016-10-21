package db_logic

import cats._
import db_logic.SearchTermOps._
import helpers.TestInterpreter
import org.scalatest.{Matchers, WordSpecLike}
import org.scalacheck.Prop.forAll
import org.scalatest.prop.Checkers

class SearchTermServiceSpec extends WordSpecLike with Matchers with Checkers {

  val someTags: List[String] = List("search", "tags")


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
