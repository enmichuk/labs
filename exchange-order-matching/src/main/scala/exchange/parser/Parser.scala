package exchange.parser

import exchange._

import scala.io.Source

trait Parser[T] {
  protected def parse: List[T] = lines.map(parseEntity)

  protected def parseEntity(line: String): T

  protected def lines: List[String]
}

trait FileReader {
  protected def fileName: String

  protected def lines: List[String] = {
    val stream = getClass.getResourceAsStream(s"/$fileName")
    Source.fromInputStream(stream).getLines.toList
  }
}

trait ClientParser extends Parser[Client] {
  override protected def parseEntity(line: String): Client = {
    line.split("\\s") match {
      case Array(name, dollarBalance, aBalance, bBalance, cBalance, dBalance) =>
        Client(name, dollarBalance.toInt, aBalance.toInt, bBalance.toInt, cBalance.toInt, dBalance.toInt)
    }
  }

  def clients: List[Client] = parse
}

trait OrderParser extends Parser[Order] {
  override protected def parseEntity(line: String): Order = {
    line.split("\\s") match {
      case Array(client, operation, stock, price, amount) =>
        Order(client, operation.toOperation, stock.toStock, price.toInt, amount.toInt)
    }
  }

  def orders: List[Order] = parse
}

case class FileClientParser(fileName: String) extends ClientParser with FileReader

case class FileOrderParser(fileName: String) extends OrderParser with FileReader
