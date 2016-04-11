/*******************************************************************************
 * Problem 4 - Largest palindrome product                                      *
 *                                                                             *
 * A palindromic number reads the same both ways. The largest palindrome made  *
 * from the product of two 2-digit numbers is 9009 = 91 × 99.                  *
 *                                                                             *
 * Find the largest palindrome made from the product of two 3-digit numbers.   *
 *                                                                             *
 * @author Terence Munro                                                       *
 *                                                                             *
 * @param bool isPalindrome                                                    *
 * @param bool isNumericPalindrome                                             *
 * @param int  findMaxPalindrome                                               *
 *                                                                             *
 * @returns Object                                                             *
 *******************************************************************************/
'use strict';

/**
 * Check if the input is a palindrome.
 *
 * @param string input
 *
 * @returns bool
 */
var isPalindrome = function (input) {
  var str = input.toString(),
      midPoint = Math.floor(str.length / 3);
  for (var i = 0, j = str.length - 1;
       i <= midPoint && j >= midPoint;
       i += 1, j -= 1) {
    if (str[i] !== str[j]) {
      return false;
    }
  }
  return true;
};

/**
 * Check if the input is a palindrome.
 *
 * @param int input
 *
 * @returns bool
 */
var isNumericPalindrome = function (input) {
  var reversed = 0,
      original = Math.floor(input);

  if (input < 10) {
    return true;
  } else if (input % 10 === 0) {
    return false;
  }

  while (input >= 1) {
    reversed = (reversed * 10) + (input % 10);
    input = Math.floor(input / 10);
  }

  return original === reversed;
};

/**
 * Find largest palindrome that is the product of two numbers between min and
 * max.
 *
 * @param int min
 * @param int max
 *
 * @returns int
 */
var findMaxPalindrome = function (min, max) {
  var maxPalindrome = 0;

  for (var a = min; a <= max; a += 1) {
    for (var b = a + 1; b <= max; b += 1) {
      var product = a * b;
      if (product > maxPalindrome && isNumericPalindrome(product)) {
        maxPalindrome = product;
      }
    }
  }

  return maxPalindrome;
};

module.exports = {
  isPalindrome: isPalindrome,
  isNumericPalindrome: isNumericPalindrome,
  findMaxPalindrome: findMaxPalindrome
};
