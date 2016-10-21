import endpoints.Endpoints
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}

import scalaz.concurrent.Task

object Server extends ServerApp {
  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder.bindHttp(8080, "localhost").
      mountService(Endpoints.retrieveTags, "/").start
  }
}
