package endpoints

object CustomDecode {
  object T {
    def unapply(params: Map[String, Seq[String]]): Option[List[String]] = params.get("tags").map(_.toList)
  }
}
