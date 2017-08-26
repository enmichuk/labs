package exchange

import scala.collection.mutable

class OrderTree {

  case class OperationSize(price: Int, amount: Int)

  private val orderTree = mutable.Map[OperationSize, mutable.Map[Operation, mutable.Map[Stock, mutable.Queue[Order]]]]()

  def getOppositeOrder(order: Order): Option[Order] = {
    for {
      operationMap <- orderTree.get(OperationSize(order.price, order.amount))
      stockMap <- operationMap.get(order.operation.toOpposite)
      queue <- stockMap.get(order.stock)
      o <- if(queue.isEmpty) None else Option(queue.dequeue())
    } yield o
  }

  def putOrderToTree(order: Order): Unit = {
    val operationMap = orderTree.getOrElse(OperationSize(order.price, order.amount), {
      val newOperationMap = mutable.Map[Operation, mutable.Map[Stock, mutable.Queue[Order]]]()
      orderTree += OperationSize(order.price, order.amount) -> newOperationMap
      newOperationMap
    })
    val stockMap = operationMap.getOrElse(order.operation, {
      val newStockMap = mutable.Map[Stock, mutable.Queue[Order]]()
      operationMap += order.operation -> newStockMap
      newStockMap
    })
    val queue = stockMap.getOrElse(order.stock, {
      val newQueue = mutable.Queue[Order]()
      stockMap += order.stock -> newQueue
      newQueue
    })
    queue.enqueue(order)
    println(s"Order is put to tree")
  }

  def remainingOrdersCount: Int = {
    (for {
      operationMaps <- orderTree.values
      stockMaps <- operationMaps.values
      queue <- stockMaps.values
    } yield queue.size).sum
  }
}
