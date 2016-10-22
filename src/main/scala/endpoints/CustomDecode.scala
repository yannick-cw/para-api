package endpoints

object CustomDecode {
  object T {
    def unapply(params: Map[String, Seq[String]]): Option[Seq[String]] = params.get("tags")
  }
}
