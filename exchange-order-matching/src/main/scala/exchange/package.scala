package object exchange {

  case class Client(name: String, dollarBalance: Int, aBalance: Int, bBalance: Int, cBalance: Int, dBalance: Int)

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
