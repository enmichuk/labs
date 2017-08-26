package exchange.parser

import exchange.{Buy, Client, D, MarketMaker, Order, OrderTree, Sell}
import org.scalatest.{FlatSpec, Matchers}

class MarketMakerTest extends FlatSpec with Matchers {

  "MarketMaker" should "correctly process transaction" in {
    val buyClient = Client("C9", 7645, 336, 239, 734, 374)
    val sellClient = Client("C2", 4727, 369, 144, 928, 531)
    val order = Order("C9", Buy, D, 10, 10)
    MarketMaker.transaction(buyClient, sellClient, order) shouldBe
      (Client("C9", 7545, 336, 239, 734, 384), Client("C2", 4827, 369, 144, 928, 521))
  }

  "MarketMaker" should "correctly process order" in {
    val buyClient = Client("C9", 7645, 336, 239, 734, 374)
    val sellClient = Client("C2", 4727, 369, 144, 928, 531)
    val buyOrder = Order("C9", Buy, D, 10, 10)
    val sellOrder = Order("C2", Sell, D, 10, 10)
    val marketMaker = new MarketMaker(List(buyClient, sellClient))
    marketMaker.processOrder(buyOrder)
    marketMaker.processOrder(sellOrder)
    marketMaker.getClients should contain only
      (Client("C2", 4827, 369, 144, 928, 521), Client("C9", 7545, 336, 239, 734, 384))
  }

}