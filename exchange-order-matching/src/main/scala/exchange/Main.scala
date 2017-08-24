package exchange

import exchange.parser.{FileClientParser, FileOrderParser}

object Main extends App {

  val clients = FileClientParser("clients.txt").clients

  val orders = FileOrderParser("orders.txt").orders

}
