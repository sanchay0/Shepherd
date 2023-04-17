package org.shepherd.server

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{BadRequest, Forbidden, MethodNotAllowed, ScalatraServlet}
import org.shepherd.Logging
import org.shepherd.store.DataStore

import java.security.AccessControlException

class ShepherdServlet(dataStore: DataStore, accessManager: AccessManager)
  extends ScalatraServlet
  with JacksonJsonSupport
  with Logging {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  case class ResponseMessage(msg: String)

  before() {
    contentType = formats("json")
  }

  error {
    case e: IllegalArgumentException => BadRequest(ResponseMessage(e.getMessage))
    case e: AssertionError => BadRequest(ResponseMessage(e.getMessage))
    case e: ArithmeticException => MethodNotAllowed(ResponseMessage(e.getMessage))
    case e: AccessControlException => Forbidden(ResponseMessage(e.getMessage))
    case e: Exception => BadRequest(ResponseMessage(e.getMessage))
  }

  get("/model") {
    withProtectedSession { () =>
      info("/model pinged.")

      if (params.size == 0)
        throw new IllegalArgumentException(
          "No model inputs provided. Please provide the ISO code, Years of Experience, State and Tech Usage Grade of the contractor.")

      val isoCode = params.get("iso")
      val yearsOfExpereince = params.get("yoe")
      val state = params.get("state")
      val techGrade = params.get("grade")

      ResponseMessage(dataStore.getFinalRiskTier(isoCode, yearsOfExpereince, state, techGrade).toString)
    }
  }

  override def shutdown(): Unit = {
    dataStore.shutdown()
  }

  private def withProtectedSession(fn: () => Any): Any = {
    if (accessManager.checkAuthCookie(request.getCookies)) {
      fn()
    } else {
      Forbidden()
    }
  }

}
