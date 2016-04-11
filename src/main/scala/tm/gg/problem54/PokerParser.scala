package tm.gg.problem54

import Poker._

/**
  * Poker Parser
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
class PokerParser {
  def parse(input: String): Traversable[Hand] = {
    val unparsedCards = input.split(" ")
    require(unparsedCards.size % Hand.HAND_SIZE == 0)

    unparsedCards
      .grouped(Hand.HAND_SIZE)
      .map(unparsedHand => Hand(unparsedHand.map(parseCard)))
      .toTraversable
  }

  def parseCard(input: String): Card = {
    val trimmedInput = input.trim()
    require(trimmedInput.size == 2)

    Card(
      CardSuit.withName(input.charAt(1).toString),
      CardValue.withName(input.charAt(0).toString)
    )
  }

  def parseHand(input: String): Hand = {
    val unparsedHand = input.split(" ")
    require(unparsedHand.size == 5)

    Hand(unparsedHand.map(parseCard))
  }

  def parseFromFile(file: String): Traversable[Traversable[Hand]] = {
//    tm.gg.io.using(getClass.getResourceAsStream(file)) { stream =>
//      tm.gg.io.using(io.Source.fromInputStream(stream)) { source =>
//        source.getLines.map(parse).toTraversable
//      }
//    }
    val stream = getClass.getResourceAsStream(file)
    io.Source.fromInputStream(stream).getLines.map(parse).toTraversable
  }
}
