package tm.gg.math

import org.specs2.mutable.Specification
import Implicits._

/**
  * ProblemFiveSpec
  * ---------------
  *
  * 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
  * What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
  *
  * @author Terence Munro <terry@zenkey.com.au>
  */
class ProblemFiveSpec extends Specification {

  "Range" should {
    "find smallest evenly divisible number of each number between" in {
      "1 to 10" in {
        (1 to 10).smallestEvenlyDivisible must be equalTo 2520
      }

      "1 to 20" in {
        (1 to 20).smallestEvenlyDivisible must be equalTo 232792560
      }
    }
  }
}
