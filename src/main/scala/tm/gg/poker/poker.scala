package tm.gg

/**
  * ProblemFiftyFour - https://projecteuler.net/problem=54
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
package object poker {
  object CardValue extends Enumeration {
    val Ace   = Value(4096, "A")
    val King  = Value(2048, "K")
    val Queen = Value(1024, "Q")
    val Jack  = Value(512, "J")
    val Ten   = Value(256, "T")
    val Nine  = Value(128, "9")
    val Eight = Value(64, "8")
    val Seven = Value(32, "7")
    val Six   = Value(16, "6")
    val Five  = Value(8, "5")
    val Four  = Value(4, "4")
    val Three = Value(2, "3")
    val Two   = Value(1, "2")
  }
  object CardSuit extends Enumeration {
    val Hearts    = Value("H")
    val Spades    = Value("S")
    val Clubs     = Value("C")
    val Diamonds  = Value("D")
  }
  case class Card(suit: CardSuit.Value, value: CardValue.Value) extends Ordered[Card] {
    override def compare(that: Card): Int = (value.id compare that.value.id) * -1
    override def toString: String = s"[$value$suit]"
  }

  object HandType extends Enumeration {
    val RoyalFlush    = Value(10)
    val StraightFlush = Value(9)
    val FourOfAKind   = Value(8)
    val FullHouse     = Value(7)
    val Flush         = Value(6)
    val Straight      = Value(5)
    val ThreeOfAKind  = Value(4)
    val TwoPair       = Value(3)
    val OnePair       = Value(2)
    val HighCard      = Value(1)
  }
  case class HandEvaluation(handType: HandType.Value, firstKicker: Int = 0, secondKicker: Int = 0) extends Ordered[HandEvaluation] {
    override def compare(that: HandEvaluation): Int = {
      val typeComparison = handType compare that.handType
      if (typeComparison != 0)
        typeComparison
      else {
        val firstKickerComparison = firstKicker compare that.firstKicker
        if (firstKickerComparison != 0)
          firstKickerComparison
        else
          secondKicker compare that.secondKicker
      }
    }
  }
  case class Hand(cards: Seq[Card]) extends Ordered[Hand] {
    require(cards.size == Hand.HAND_SIZE)
    require(cards.distinct.size == cards.size)

    import CardValue._
    import HandType._

    override def toString: String =
      "Hand(" + cards.map(_.toString).mkString(" ") + s") <{$value}>"
    override def compare(that: Hand): Int = (value compare that.value) * -1

    def highestCard: Card = cards.maxBy(_.value.id)
    def lowestCard: Card = cards.maxBy(_.value.id * -1)

    def hasFlush: Boolean = cards.forall(_.suit == cards.head.suit)

    def hasStraight: Boolean =
      cards.toStream
        .sorted
        .reverse
        .foldLeft[(Boolean, Option[Card])]((true, None)) {
        case ((false, _), cur) => (false, None)
        case ((acc, None), cur) => (acc, Some(cur))
        case ((true, Some(prevCard)), curCard) =>
          ((prevCard.value == Five && curCard.value == Ace) ||
            (prevCard.value.id * 2 == curCard.value.id), Some(curCard))
      }._1

    def value: HandEvaluation = {
      val sets = cards.groupBy(_.value)
      if (hasFlush && hasStraight) {
        if (highestCard.value == Ace && lowestCard.value == Ten)
          HandEvaluation(RoyalFlush)
        else
          HandEvaluation(StraightFlush, straightKicker)
      } else if (sets.exists(_._2.length > 1)) {
        val maxSet = sets.maxBy(_._2.length)
        if (maxSet._2.length == 4)
          HandEvaluation(FourOfAKind, maxSet._1.id, sets.find(_._1 != maxSet._1).head._1.id)
        else if (sets.size == 2 && maxSet._2.length == 3)
          HandEvaluation(FullHouse, maxSet._1.id, sets.find(_._2.length == 2).head._1.id)
        else if (maxSet._2.length == 3)
          HandEvaluation(ThreeOfAKind, maxSet._1.id, sets.filter(_._2.length == 1).foldLeft(0)(_ + _._1.id))
        else if (sets.count(p = _._2.length > 1) == 2 && maxSet._2.length == 2)
          HandEvaluation(TwoPair, sets.filter(_._2.length > 1).foldLeft(0)(_ + _._1.id), sets.find(_._2.length == 1).head._1.id)
        else
          HandEvaluation(OnePair, maxSet._1.id, sets.filter(_._1 != maxSet._1).foldLeft(0)(_ + _._1.id))
      }
      else if (hasFlush)
        HandEvaluation(Flush, highCardKicker)
      else if (hasStraight)
        HandEvaluation(Straight, straightKicker)
      else
        HandEvaluation(HighCard, highCardKicker)
    }

    private def straightKicker: Int =
      if (highestCard.value == Ace && lowestCard.value == Two) Five.id
      else highestCard.value.id

    private def highCardKicker: Int =
      cards.foldLeft(0)(_ + _.value.id)
  }
  object Hand {
    val HAND_SIZE = 5
  }

  def evaluate(hands: Hand*): Seq[Hand] = {
    val sortedHands = hands.sorted
    sortedHands.takeWhile(_.value == sortedHands.head.value)
  }
}
