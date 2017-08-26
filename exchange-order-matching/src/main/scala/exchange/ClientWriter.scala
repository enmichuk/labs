package exchange

import java.io.{File, PrintWriter}

import scala.util.control.NonFatal

class ClientWriter(clients: List[Client], fileName: String) {

  def write(): Unit = {
    val result = clients.map(_.toString).mkString("\n")
    var printWriter: PrintWriter = null
    try {
      printWriter = new PrintWriter(new File(fileName))
      printWriter.write(result)
    } finally {
      try {
        if(printWriter != null) {
          printWriter.close()
        }
      } catch {
        case NonFatal(e) => e.printStackTrace()
      }
    }
  }

}
