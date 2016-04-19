package tm.gg.math

import org.specs2.mutable.Specification
import Implicits._

/**
  * ProblemThreeSpec
  * ----------------
  * Prime factors
  *
  * @author Terence Munro <terry@zenkey.com.au>
  * @version 0.2.1
  */
class ProblemThreeSpec extends Specification {
  "PrimeFactorer" should {
    "throw exceptions for" in {
      "negative number" in {
        -232.primeFactors must throwAn[IllegalArgumentException]
      }

      "zero" in {
        0.primeFactors must throwAn[IllegalArgumentException]
      }
    }

    "find the prime factors of" in {
      "1" in {
        1.primeFactors must_== List()
      }
      "2" in {
        2.primeFactors must_== List(2)
      }
      "3" in {
        3.primeFactors must_== List(3)
      }
      "4" in {
        4.primeFactors must_== List(2, 2)
      }
      "5" in {
        5.primeFactors must_== List(5)
      }
      "6" in {
        6.primeFactors must_== List(2, 3)
      }
      "7" in {
        7.primeFactors must_== List(7)
      }
      "8" in {
        8.primeFactors must_== List(2, 2, 2)
      }
      "9" in {
        9.primeFactors must_== 3 :: 3 :: Nil
      }
      "13195" in {
        13195.primeFactors must_== List(5, 7, 13, 29)
      }
      List(2, 3, 5, 7, 11, 13).product.toString in {
        (2 * 3 * 5 * 7 * 11 * 13).primeFactors must_== List(2, 3, 5, 7 , 11, 13)
      }
      List(8191, 131071).product.toString in {
        (8191 * 131071).primeFactors must_== List(8191, 131071)
      }
    }
  }
}
