package exchange

object Main extends App {

  val clients = new FileClientParser("clients.txt").clients
  println(s"Total clients: ${clients.size}")

  val orders = new FileOrderParser("orders.txt").orders
  println(s"Total orders: ${orders.size}\n")

  val marketMaker = new MarketMaker(clients)

  orders.foreach(order => marketMaker.processOrder(order))
  println("All orders are processed")
  println(s"Remaining orders count: ${marketMaker.remainingOrdersCount}")

  new ClientWriter(marketMaker.getClients, "result.txt").write()
  println("result.txt is ready")

}
