package tm.gg.poker

import CardSuit._
import CardValue._
import org.specs2.mutable.Specification

/**
  * tm.gg.problem54.Problem54.PokerParserSpec
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
class PokerParserSpec extends Specification {
  val parser = new PokerParser()

  "Poker parser" should {

    "parse one card" in {
      val unparsedCard = "7D"
      val parsedCard = parser.parseCard(unparsedCard)
      parsedCard.value must be equalTo Seven
      parsedCard.suit must be equalTo Diamonds
    }

    "parse one hand" in {
      val unparsedHand = "7D 8D 9D TD JD"
      val parsedHand = parser.parseHand(unparsedHand)
      parsedHand.cards must contain(exactly(
        Card(Diamonds, Seven),
        Card(Diamonds, Eight),
        Card(Diamonds, Nine),
        Card(Diamonds, Ten),
        Card(Diamonds, Jack)
      ))
    }

    "parse one line" in {
      val line = "5H 5C 6S 7S KD 2C 3S 8S 8D TD"
      val hands = parser.parse(line)
      hands must have size 2
      hands must contain(exactly(
        Hand(Seq(Card(Hearts, Five), Card(Clubs, Five), Card(Spades, Six), Card(Spades, Seven), Card(Diamonds, King))),
        Hand(Seq(Card(Clubs, Two), Card(Spades, Three), Card(Spades, Eight), Card(Diamonds, Eight), Card(Diamonds, Ten)))
      ))
    }
  }
}
