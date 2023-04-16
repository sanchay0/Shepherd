class Server extends Logging {

  private var server: JettyServer = _

  def start(): Unit = ???

  def join(): Unit = ???

  def stop(): Unit = ???
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
