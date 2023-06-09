package org.shepherd

import org.slf4j.LoggerFactory

trait Logging {
  System.setProperty("org.slf4j.simpleLogger.logFile", "System.out")
  lazy val logger = LoggerFactory.getLogger(this.getClass)

  def trace(message: => Any): Unit = {
    if (logger.isTraceEnabled) {
      logger.trace(message.toString)
    }
  }

  def debug(message: => Any): Unit = {
    if (logger.isDebugEnabled) {
      logger.debug(message.toString)
    }
  }

  def info(message: => Any): Unit = {
    if (logger.isInfoEnabled) {
      logger.info(message.toString)
    }
  }

  def warn(message: => Any): Unit = {
    logger.warn(message.toString)
  }

  def warn(message: => Any, t: Throwable): Unit = {
    logger.warn(message.toString, t)
  }

  def error1(message: => Any, t: Throwable): Unit = {
    logger.error(message.toString, t)
  }

  def error1(message: => Any): Unit = {
    logger.error(message.toString)
  }
}
