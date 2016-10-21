package server

import db.InMemoryDbInterpreter
import endpoints.Endpoints._
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}
import server.RunTime.frameworkifyRoutes

import scalaz.concurrent.Task

object Server extends ServerApp {

  val db: InMemoryDbInterpreter = new InMemoryDbInterpreter

  val endpoints = frameworkifyRoutes(routes, db)

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder.bindHttp(8080, "localhost").
      mountService(endpoints, "/").start
  }
}
