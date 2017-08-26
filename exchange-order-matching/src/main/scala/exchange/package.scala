package object exchange {

  case class Client(name: String, dollarBalance: Int, aBalance: Int, bBalance: Int, cBalance: Int, dBalance: Int) {
    override def toString: String = s"$name\t$dollarBalance\t$aBalance\t$bBalance\t$cBalance\t$dBalance"

    private def withdrawDollars(amount: Int): Client = copy(dollarBalance = dollarBalance - amount)

    private def withdrawStocks(amount: Int, stock: Stock): Client = {
      stock match {
        case A => copy(aBalance = aBalance - amount)
        case B => copy(bBalance = bBalance - amount)
        case C => copy(cBalance = cBalance - amount)
        case D => copy(dBalance = dBalance - amount)
      }
    }

    private def depositDollars(amount: Int): Client = copy(dollarBalance = dollarBalance + amount)

    private def depositStocks(amount: Int, stock: Stock): Client = {
      stock match {
        case A => copy(aBalance = aBalance + amount)
        case B => copy(bBalance = bBalance + amount)
        case C => copy(cBalance = cBalance + amount)
        case D => copy(dBalance = dBalance + amount)
      }
    }

    def executeOrder(order: Order): Client = {
      order.operation match {
        case Buy => withdrawDollars(order.price * order.amount).depositStocks(order.amount, order.stock)
        case Sell => depositDollars(order.price * order.amount).withdrawStocks(order.amount, order.stock)
      }
    }
  }

  case class Order(client: String, operation: Operation, stock: Stock, price: Int, amount: Int)

  sealed trait Operation

  case object Buy extends Operation

  case object Sell extends Operation

  implicit class StringToOperation(val raw: String) extends AnyVal {
    def toOperation: Operation = {
      raw match {
        case "b" => Buy
        case "s" => Sell
      }
    }
  }

  implicit class OperationToOpposite(val operation: Operation) extends AnyVal {
    def toOpposite: Operation = {
      operation match {
        case Sell => Buy
        case Buy => Sell
      }
    }
  }

  sealed trait Stock

  case object A extends Stock

  case object B extends Stock

  case object C extends Stock

  case object D extends Stock

  implicit class StringToStock(val raw: String) extends AnyVal {
    def toStock: Stock = {
      raw match {
        case "A" => A
        case "B" => B
        case "C" => C
        case "D" => D
      }
    }
  }

}
