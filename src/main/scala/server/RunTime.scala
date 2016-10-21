package server

import cats.instances.future._
import db.InMemoryDbInterpreter
import endpoints.Endpoints.Route
import org.http4s.HttpService

import scala.concurrent.ExecutionContext.Implicits.global

object RunTime {
  def frameworkifyRoutes(routes: Route, interpreter: InMemoryDbInterpreter): HttpService =
    HttpService(
      routes.andThen(_.foldMap(interpreter).asTask.flatMap(identity)))
}
