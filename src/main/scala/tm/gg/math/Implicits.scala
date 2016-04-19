package tm.gg.math

/**
  * Implicit classes to extend the functionality of inbuilt data structures
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
object Implicits {
  implicit class EulerRange(range: Range) {
    /**
      * https://projecteuler.net/problem=5
      *
      * 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
      * What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
      */
    def smallestEvenlyDivisible: Int =
      range
        .toStream
        .flatMap(
          _.primeFactors
            .groupBy(i => i)
            .map { case (n, ns) => (n, ns.size) }
        )
        .groupBy(_._1)
        .foldLeft(1){ case (acc, (n, ns)) =>
          acc * math.pow(n, ns.maxBy(_._2)._2).toInt
        }

    /**
      * https://projecteuler.net/problem=6
      *
      * The sum of the squares of the first ten natural numbers is,
      *   12 + 22 + ... + 102 = 385
      * The square of the sum of the first ten natural numbers is,
      *   (1 + 2 + ... + 10)2 = 552 = 3025
      * Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.
      * Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.
      */
    def sumOfSquares: Int =
      range.foldLeft(0)(_ + math.pow(_, 2).toInt)

    def squareOfSum: Int =
      math.pow(range.sum, 2).toInt

    def differenceBetweenSumOfSquaresAndSquareOfSum: Int =
      math.abs(sumOfSquares - squareOfSum)
  }

  implicit class EulerInteger(n: Int) {
    /**
      * https://projecteuler.net/problem=3
      *
      * The prime factors of 13195 are 5, 7, 13 and 29.
      * What is the largest prime factor of the number 600851475143 ?
      */
    def primeFactors: List[Int] = {
      require(n > 0)

      def inner(n: Int, divisor: Int, acc: List[Int]): List[Int] =
        if (n <= 1)
          acc.reverse
        else if (n % divisor == 0)
          inner(n / divisor, divisor, divisor :: acc)
        else
          inner(n, divisor + 1, acc)

      inner(n, 2, Nil)
    }

    // TODO: Sieves buckets of sieves
    def thPrime: Int = ???
    def ndPrime = thPrime
    def stPrime = thPrime
  }
}
