package tm.gg.math

import org.specs2.Specification
import Implicits._

/**
  * ProblemSixSpec
  *
  * @author Terence Munro <terry@zenkey.com.au>
  * @version 0.2.1
  */
class ProblemSixSpec extends Specification { def is = s2"""
  Sum of squares from
    1 to 10 = 385       $e1
    1 to 100 = 338350   $e4

  Square of the sum from
    1 to 10 = 3025      $e2
    1 to 100 = 25502500 $e5

  Difference of the sum of squares to square of the sum for
    1 to 10 = 2640      $e3
    1 to 100 = 25164150 $e6
  """

  def e1 = (1 to 10).sumOfSquares must_=== 385
  def e2 = (1 to 10).squareOfSum must_=== 3025
  def e3 = (1 to 10).differenceBetweenSumOfSquaresAndSquareOfSum must_=== 2640

  def e4 = (1 to 100).sumOfSquares must_=== 338350
  def e5 = (1 to 100).squareOfSum must_=== 25502500
  def e6 = (1 to 100).differenceBetweenSumOfSquaresAndSquareOfSum must_=== 25164150
}
