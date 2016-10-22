package endpoints

object CustomDecode {
  object Tags {
    def unapply(params: Map[String, Seq[String]]): Option[Seq[String]] = params.get("tags")
  }
}
