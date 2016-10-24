package server

import com.typesafe.config.ConfigFactory._
import db.{InMemoryDbInterpreter, MongoDb}
import endpoints.Endpoints
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}

import scalaz.concurrent.Task

object RunServer extends ServerApp with RunTime with Endpoints {

  val conf = load()
  val useMongo = conf.getBoolean("mongo.activated")
  val db =
    if(useMongo) new MongoDb(conf.getString("mongo.url"), conf.getString("mongo.name"), "tags")
    else new InMemoryDbInterpreter

  val endpoints = frameworkifyRoutes(routes, db)

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder.bindHttp(8080, "0.0.0.0").
      mountService(endpoints, "/").start
  }
}
