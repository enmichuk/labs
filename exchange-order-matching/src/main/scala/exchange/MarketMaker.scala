package exchange

import scala.collection.mutable

class MarketMaker {

  import MarketMaker._

  private val clientMap = mutable.Map[String, Client]()
  private val orderTree = new OrderTree

  def this(clients: List[Client]) {
    this()
    clientMap ++= clients.map(c => (c.name, c)).toMap
  }

  def processOrder(order: Order): Unit = {
    println(s"Start to process order: $order")
    orderTree.getOppositeOrder(order) match {
      case Some(oppositeOrder) =>
        println(s"Opposite order is found: $oppositeOrder")
        if(order.client != oppositeOrder.client) {
          val orderClient = clientMap(order.client)
          println(s"Order client: $orderClient")
          val oppositeOrderClient = clientMap(oppositeOrder.client)
          println(s"Opposite order client: $oppositeOrderClient")
          val (newOrderClient, newOppositeOrderClient) = order.operation match {
            case Buy => transaction(orderClient, oppositeOrderClient, order)
            case Sell => transaction(oppositeOrderClient, orderClient, oppositeOrder) match {
              case (oppositeClient, client) => (client, oppositeClient)
            }
          }
          println(s"Order client after transaction: $newOrderClient")
          println(s"Opposite order client after transaction: $newOppositeOrderClient")
          clientMap += newOrderClient.name -> newOrderClient
          clientMap += newOppositeOrderClient.name -> newOppositeOrderClient
        }
      case None =>
        println(s"Opposite order is not found")
        orderTree.putOrderToTree(order)
    }
    println("End to process order")
    println
  }

  def getClients: List[Client] = clientMap.map { case (name, client) => client }.toList.sortBy(_.name)

  def remainingOrdersCount: Int = orderTree.remainingOrdersCount
}

object MarketMaker {
  def transaction(buyClient: Client, sellClient: Client, order: Order): (Client, Client) =
    (buyClient.executeOrder(order.copy(operation = Buy)), sellClient.executeOrder(order.copy(operation = Sell)))
}
