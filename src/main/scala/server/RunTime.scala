package server

import cats._
import cats.instances.future._
import db_logic.SearchTermService.DbOpA
import endpoints.Endpoints.Route
import org.http4s.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait RunTime {
  def frameworkifyRoutes(routes: Route, interpreter: (DbOpA ~> Future)): HttpService =
    HttpService(
      routes.andThen(_.foldMap(interpreter).asTask.flatMap(identity)))
}
