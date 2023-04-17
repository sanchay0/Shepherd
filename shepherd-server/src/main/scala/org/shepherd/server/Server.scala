package org.shepherd.server

import org.scalatra.metrics.MetricsBootstrap
import org.scalatra.servlet.ServletApiImplicits
import org.shepherd.Logging
import org.shepherd.store.DataStore

import javax.servlet.{Servlet, ServletContext, ServletContextEvent, ServletContextListener}

class Server extends Logging {

  private var server: JettyServer = _
  private val host = "0.0.0.0"
  private val port = 8080

  def start(): Unit = {
    server = new JettyServer(host, port)

    val dataStore = new DataStore
    val accessManager = new AccessManager

    // mount API server handler
    server.context.addEventListener(
      new ServletContextListener with MetricsBootstrap with ServletApiImplicits {
        private def mount(sc: ServletContext, servlet: Servlet, mappings: String*): Unit = {
          val registration = sc.addServlet(servlet.getClass.getName, servlet)
          registration.addMapping(mappings: _*)
        }

        override def contextInitialized(sce: ServletContextEvent): Unit = {
          try {
            val context = sce.getServletContext
            context.setInitParameter(org.scalatra.EnvironmentKey, "production")

            val shepherdServlet = new ShepherdServlet(dataStore, accessManager)
            mount(context, shepherdServlet, "/api/*")
            info("Mounted Shepherd API at /api/*")
          } catch {
            case e: Throwable =>
              error1("Exception thrown while initializing server", e)
              sys.exit(1)
          }
        }

        override def contextDestroyed(sce: ServletContextEvent): Unit = {}
      }
    )

    server.start()

    Runtime.getRuntime.addShutdownHook(new Thread("Shepherd server.Server Shutdown") {
      override def run(): Unit = {
        info("Shutting down Shepherd server.")
        server.stop()
      }
    })
  }

  def join(): Unit = server.join()

  def stop(): Unit = {
    if (server != null) {
      server.stop()
    }
  }
}

object Server {

  def main(args: Array[String]): Unit = {
    val server = new Server()
    try {
      server.start()
      server.join()
    } finally {
      server.stop()
    }
  }
}
