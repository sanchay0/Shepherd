import org.eclipse.jetty.server._
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.util.resource.Resource

import java.io.File
import java.net.InetAddress

class JettyServer(var host: String, var port: Int) extends Logging {

  val server = new Server

  server.setStopTimeout(1000)
  server.setStopAtShutdown(true)

  val connector = new ServerConnector(server, new HttpConnectionFactory(new HttpConfiguration))
  connector.setHost(host)
  connector.setPort(port)

  server.setConnectors(Array(connector))

  val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
  context.setContextPath("/")

  val resourceHandler = new ResourceHandler
  resourceHandler.setDirectoriesListed(false)
  resourceHandler.setWelcomeFiles(Array("index.html"))
  resourceHandler.setBaseResource(Resource.newResource(new File("out").getAbsolutePath))

  context.setBaseResource(resourceHandler.getBaseResource)
  context.setWelcomeFiles(resourceHandler.getWelcomeFiles)
  context.setHandler(resourceHandler)

  context.addServlet(classOf[DefaultServlet], "/")

  server.setHandler(context)

  def start(): Unit = {
    server.start()

    val connector = server.getConnectors()(0).asInstanceOf[NetworkConnector]

    if (host == "0.0.0.0") {
      host = InetAddress.getLocalHost.getCanonicalHostName
    }
    port = connector.getLocalPort

    info("Starting server on http://%s:%d" format(host, port))
  }

  def join(): Unit = {
    server.join()
  }

  def stop(): Unit = {
    context.stop()
    server.stop()
  }
}
