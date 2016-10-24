package db

import cats._
import db_logic.SearchTermService.{DbOpA, GetAll, GetTags, SetTags}
import models.User
import models.User._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{MongoConnection, MongoDriver}
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MongoDb(mongoUri: String, mongoName: String, collection: String) extends (DbOpA ~> Future) {

  val col: Future[BSONCollection] = for {
    uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
    con = new MongoDriver().connection(uri)
    db <- con.database(mongoName)
  } yield db.apply(collection)

  def insertTags(user: User): Future[Unit] =
    col.flatMap(_.update(selector(user), user, upsert = true)).map(_ => {})

  def findByMail(email: String): Future[Option[User]] =
    col.flatMap(_.find(selector(email)).one[User])

  def getAll: Future[Set[User]] =
    col.flatMap(_.find(BSONDocument()).cursor[User]().collect[Set]())

  override def apply[A](fa: DbOpA[A]): Future[A] = fa match {
    case GetTags(email) => findByMail(email).map(_.getOrElse(User(email, List.empty)))
    case SetTags(usr) => insertTags(usr)
    case GetAll => getAll
  }
}
