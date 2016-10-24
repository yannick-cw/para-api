package models

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

case class User(email: String, tags: List[String])

object User {
  implicit object ResultReaderWriter extends BSONDocumentReader[User] with BSONDocumentWriter[User] {
    def write(t: User): BSONDocument = BSONDocument("email" -> t.email, "tags" -> t.tags)
    def read(doc: BSONDocument): User = User(
        email = doc.getAs[String]("email").get,
        tags = doc.getAs[List[String]]("tags").get
      )
  }
  def selector(email: String): BSONDocument = BSONDocument("email" -> email)
  def selector(user: User): BSONDocument = selector(user.email)
}
