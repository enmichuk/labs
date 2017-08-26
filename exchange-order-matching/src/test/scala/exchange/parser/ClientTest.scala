package exchange.parser

import exchange.{Buy, Client, D, Order, Sell}
import org.scalatest.{FlatSpec, Matchers}

class ClientTest extends FlatSpec with Matchers {

  "Client" should "buy correctly stocks" in {
    val client = Client("C2", 7645, 336, 239, 734, 374)
    val order = Order("C9", Buy, D, 6, 10)
    client.executeOrder(order) shouldBe Client("C2", 7585, 336, 239, 734, 384)
  }

  it should "sell correctly stocks" in {
    val client = Client("C2", 7645, 336, 239, 734, 374)
    val order = Order("C9", Sell, D, 6, 10)
    client.executeOrder(order) shouldBe Client("C2", 7705, 336, 239, 734, 364)
  }

}
