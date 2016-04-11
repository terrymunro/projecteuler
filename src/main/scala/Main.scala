import tm.gg.problem54.{Poker, PokerParser}

/**
  * Main
  * ----
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
object Main {
  def main(args: Array[String]): Unit = {
    val parser = new PokerParser
    val hands = parser.parseFromFile("/p054_poker.txt")
    val totalWinner = hands.foldLeft((0, 0)) { (acc, hands) =>
      val winner = Poker.evaluate(hands.toSeq: _*)
      if (hands.head.compare(winner.head) == 0)
        (acc._1 + 1, acc._2)
      else if (hands.tail.head.compare(winner.head) == 0)
        (acc._1, acc._2 + 1)
      else
        acc
    }
    println(s"Player 1 wins: ${totalWinner._1} and Player 2 wins: ${totalWinner._2}")
  }
}
