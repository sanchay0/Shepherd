package org.shepherd.server

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{BadRequest, Forbidden, ScalatraServlet}
import org.shepherd.store.DataStore

import java.security.AccessControlException

class ShepherdServlet(dataStore: DataStore)
  extends ScalatraServlet
  with JacksonJsonSupport {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  case class ResponseMessage(msg: String)

  before() {
    contentType = formats("json")
  }

  error {
    case e: IllegalArgumentException => BadRequest(ResponseMessage(e.getMessage))
    case e: AccessControlException => Forbidden(ResponseMessage(e.getMessage))
  }

  get("/test") {
    ResponseMessage("hello!")
  }

  override def shutdown(): Unit = {
    dataStore.shutdown()
  }

}
