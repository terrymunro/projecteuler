/*******************************************************************************
 * Problem 3 - Largest prime factor                                            *
 *                                                                             *
 * The prime factors of 13195 are 5, 7, 13 and 29.                             *
 * What is the largest prime factor of the number 600851475143?                *
 *                                                                             *
 * @author Terence Munro                                                       *
 * @returns int The largest prime factor of input                              *
 *******************************************************************************/
'use strict';

var findLargestPrimeFactor = function (input) {
  if (input <= 1) {
    return 0;
  }

  var divisor = 2;
  while (input > 1) {
    if (input % divisor === 0) {
      // Input is evenly divisable by the divisor thus is a prime factor
      input = input / divisor;
    } else {
      // Otherwise we increment the divisor and continue looking
      divisor += 1;
    }
  }
  
  return divisor;
};

module.exports = findLargestPrimeFactor;
