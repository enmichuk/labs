package exchange.parser

import exchange.{Buy, D, Order, OrderTree, Sell}
import org.scalatest.{FlatSpec, Matchers}

class OrderTreeTest extends FlatSpec with Matchers {

  "OrderTree" should "return opposite order if exists" in {
    val orderTree = new OrderTree
    val order = Order("C9", Buy, D, 6, 10)
    val oppositeOrder = Order("C9", Sell, D, 6, 10)
    orderTree.putOrderToTree(oppositeOrder)
    orderTree.getOppositeOrder(order) shouldBe Some(oppositeOrder)
  }

}
