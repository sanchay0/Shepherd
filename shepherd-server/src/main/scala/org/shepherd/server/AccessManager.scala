package org.shepherd.server

import org.shepherd.Logging

import javax.servlet.http.Cookie

private[shepherd] class AccessManager extends Logging {

  def checkAuthCookie(cookies: Seq[Cookie]): Boolean = {
    if (cookies == null) {
      debug("No valid cookies associated with the request.")
      false
    } else {
      cookies.foreach(c => {
        if (c.getName == "auth" && c.getValue == "shepherd") {
          return true
        }
      })
      false
    }
  }
}
