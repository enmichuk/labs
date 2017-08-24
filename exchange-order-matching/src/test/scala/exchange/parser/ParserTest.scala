package exchange.parser

import exchange._
import org.scalatest.{FlatSpec, Matchers}

class ParserTest extends FlatSpec with Matchers {

  "FileClientParser" should "parse clients" in {
    val clients = FileClientParser("clients.txt").clients
    clients should contain only(Client("C1", 1000, 130, 240, 760, 320), Client("C2", 4350, 370, 120, 950, 560))
  }

  "FileOrderParser" should "parse orders" in {
    val orders = FileOrderParser("orders.txt").orders
    orders should contain only(Order("C8", Buy, C, 15, 4), Order("C2", Sell, C, 14, 5), Order("C2", Sell, C, 13, 2))
  }

}
