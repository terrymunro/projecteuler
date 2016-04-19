package tm.gg.poker

import CardSuit._
import CardValue._
import org.specs2.mutable.Specification

/**
  * tm.gg.problem54.Problem54.PokerHandEvaluatorSpec
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
class PokerHandEvaluatorSpec extends Specification {
  val parser = new PokerParser()

  "Poker hand Poker" should {
    "sort cards" in {
      val hand = Hand(Seq(
        Card(Diamonds, Five),
        Card(Spades, Jack),
        Card(Spades, Nine),
        Card(Clubs, Ace),
        Card(Clubs, Eight)
      ))
      val manuallySorted = Seq(
        Card(Clubs, Ace),
        Card(Spades, Jack),
        Card(Spades, Nine),
        Card(Clubs, Eight),
        Card(Diamonds, Five)
      )
      hand.cards.sorted must be equalTo manuallySorted
    }

    "evaluate high cards" in {
      "single high card" in {
        val player1Hand = Hand(Seq(
          Card(Diamonds, Five),
          Card(Clubs, Eight),
          Card(Spades, Nine),
          Card(Spades, Jack),
          Card(Clubs, Ace)
        ))
        val player2Hand = Hand(Seq(
          Card(Clubs, Two),
          Card(Clubs, Five),
          Card(Diamonds, Seven),
          Card(Spades, Eight),
          Card(Hearts, Queen)
        ))

        val winner = evaluate(player1Hand, player2Hand)
        winner must contain(exactly(player1Hand))
      }

      "single equal high card with kicker" in {
        val player1Hand = Hand(Seq(
          Card(Diamonds, Five),
          Card(Clubs, Eight),
          Card(Spades, Nine),
          Card(Spades, Ten),
          Card(Clubs, Ace)
        ))
        val player2Hand = Hand(Seq(
          Card(Clubs, Two),
          Card(Clubs, Five),
          Card(Diamonds, Seven),
          Card(Spades, Jack),
          Card(Hearts, Ace)
        ))
        val winner = evaluate(player1Hand, player2Hand)
        winner must contain(exactly(player2Hand))
      }

      "multiple equal high cards with a kicker" in {
        val player1Hand = Hand(Seq(
          Card(Clubs, Three),
          Card(Spades, Five),
          Card(Clubs, Seven),
          Card(Clubs, Jack),
          Card(Clubs, Ace)
        ))
        val player2Hand = Hand(Seq(
          Card(Clubs, Two),
          Card(Clubs, Five),
          Card(Diamonds, Seven),
          Card(Spades, Jack),
          Card(Hearts, Ace)
        ))
        val winner = evaluate(player1Hand, player2Hand)
        winner must contain(exactly(player1Hand))
      }

      "split pot" in {
        val player1Hand = Hand(Seq(
          Card(Clubs, Two),
          Card(Diamonds, Four),
          Card(Hearts, Six),
          Card(Hearts, Eight),
          Card(Diamonds, Ten)
        ))
        val player2Hand = Hand(Seq(
          Card(Diamonds, Two),
          Card(Clubs, Four),
          Card(Spades, Six),
          Card(Spades, Eight),
          Card(Clubs, Ten)
        ))
        val winner = evaluate(player1Hand, player2Hand)
        winner must contain(exactly(player1Hand, player2Hand))
      }
    }

    "evaluate pairs" in {
      "single pair vs high card" in {
        val player1Hand = Hand(Seq(
          Card(Spades, Five),
          Card(Clubs, Five),
          Card(Spades, Nine),
          Card(Spades, Jack),
          Card(Clubs, Two)
        ))
        val player2Hand = Hand(Seq(
          Card(Hearts, Two),
          Card(Hearts, Five),
          Card(Diamonds, Seven),
          Card(Spades, Eight),
          Card(Hearts, Ace)
        ))

        val winner = evaluate(player1Hand, player2Hand)
        winner must contain(exactly(player1Hand))
        winner.head.value.handType === HandType.OnePair
      }

      "higher pair vs lower pair" in {
        val p1 = parser.parseHand("5H 5C 6S 7S KD")
        val p2 = parser.parseHand("2C 3S 8S 8D TD")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }

      "pair vs same pair with kicker" in {
        val p1 = parser.parseHand("5H 9C 6S AS KD")
        val p2 = parser.parseHand("2C 3S 8S 8D JD")
        val p3 = parser.parseHand("2S 7S 8H 8C TD")
        val winner = evaluate(p1, p2, p3)
        winner must contain(exactly(p2))
      }

      "pair split pot" in {
        val p1 = parser.parseHand("5H 9C 6S AS KD")
        val p2 = parser.parseHand("2C 7C 8S 8D JD")
        val p3 = parser.parseHand("2S 7S 8H 8C JH")
        val winner = evaluate(p1, p2, p3)
        winner must contain(exactly(p2,p3))
      }

      "two pair" in {
        val p1 = parser.parseHand("QD KH QS 2C 3S")
        val p2 = parser.parseHand("8S 8H 9H 9C JC")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
        winner.head.value.handType === HandType.TwoPair
      }

      "two pair lower kicker" in {
        val p1 = parser.parseHand("AD AH 3S 3D 9S")
        val p2 = parser.parseHand("AS AC KH KD QS")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }

      "two pair vs single pair" in {
        val p1 = parser.parseHand("KS KC 9S 6D 2C")
        val p2 = parser.parseHand("QH 9D 9H TS TC")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }
    }

    "evaluate three and four of a kinds" in {
      "three of a kind" in {
        val p1 = parser.parseHand("AS AC KH KD QS")
        val p2 = parser.parseHand("2C 2H 2S 3D 9S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
        winner.head.value.handType === HandType.ThreeOfAKind
      }

      "three of a kind vs lower three of a kind" in {
        val p1 = parser.parseHand("3S 3C 3H 2D 8C")
        val p2 = parser.parseHand("2C 2H 2S 3D 9S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
      }

      "four of a kind" in {
        val p1 = parser.parseHand("AS AC AH 2D 2C")
        val p2 = parser.parseHand("3C 3H 3S 3D KS")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
        winner.head.value.handType === HandType.FourOfAKind
      }

      "four of a kind vs lower four of a kind" in {
        val p1 = parser.parseHand("AS AC AH AD 2C")
        val p2 = parser.parseHand("3C 3H 3S 3D KS")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
      }
    }

    "evaluate full houses" in {
      "full house vs lesser" in {
        val p1 = parser.parseHand("AS AC AH 2D 2C")
        val p2 = parser.parseHand("6C 2H 3S 4D 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
        winner.head.value.handType === HandType.FullHouse
      }

      "full house vs greater" in {
        val p1 = parser.parseHand("AS AC AH 2D 2C")
        val p2 = parser.parseHand("6C 6H 6S 6D 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }

      "full house vs full house" in {
        val p1 = parser.parseHand("6C 6H 6S 5D 5S")
        val p2 = parser.parseHand("AS AC AH 2D 2C")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }
    }

    "evaluate straights and flushes" in {
      "straight vs lesser" in {
        val p1 = parser.parseHand("AS AC KH 2D 2C")
        val p2 = parser.parseHand("AH 2H 3S 4D 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
        winner.head.value.handType === HandType.Straight
      }

      "straight vs greater" in {
        val p1 = parser.parseHand("9D TD AD 2D JD")
        val p2 = parser.parseHand("AH 2H 3S 4D 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
      }

      "straight vs straight" in {
        val p1 = parser.parseHand("TS JC QH KD AC")
        val p2 = parser.parseHand("AH 2H 3S 4D 5S")
        val winner = evaluate(p1, p2)
        println(p2.lowestCard)
        println(p2.highestCard)
        winner must contain(exactly(p1))
      }

      "low ace straight" in {
        val p1 = parser.parseHand("2S 3C 4H 5D 6C")
        val p2 = parser.parseHand("AH 2H 3S 4D 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
        winner.head.value.handType === HandType.Straight
      }

      "flush vs lesser" in {
        val p1 = parser.parseHand("9D TD AD 2D JD")
        val p2 = parser.parseHand("AH KH QS JD TS")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
        winner.head.value.handType === HandType.Flush
      }

      "flush vs greater" in {
        val p1 = parser.parseHand("9D TD AD 2D JD")
        val p2 = parser.parseHand("AH 2H 2S 2C AS")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }

      "flush vs flush" in {
        val p1 = parser.parseHand("9D TD AD 2D JD")
        val p2 = parser.parseHand("AS 3S 2S 9S QS")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
      }

      "straight flush" in {
        val p1 = parser.parseHand("9D TD AD 2D JD")
        val p2 = parser.parseHand("AS 3S 2S 4S 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p2))
        winner.head.value.handType === HandType.StraightFlush
      }

      "royal flush" in {
        val p1 = parser.parseHand("KD TD AD QD JD")
        val p2 = parser.parseHand("AS 3S 2S 4S 5S")
        val winner = evaluate(p1, p2)
        winner must contain(exactly(p1))
        winner.head.value.handType === HandType.RoyalFlush
      }
    }
  }
}
