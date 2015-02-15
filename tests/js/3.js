'use strict';

var findLargestPrimeFactor = require('../../src/js/3.js');

exports.testLargestPrimeFactors = function (test) {
  var answers = {
    1: 0,
    2: 2,
    3: 3,
    4: 2,
    5: 5,
    6: 3,
    7: 7,
    8: 2,
    9: 3,
    13195: 29,
    600851475143: 6857
  };

  for (var input in answers) {
    if (answers.hasOwnProperty(input)) {
      test.equal(
        findLargestPrimeFactor(input),    // Actual
        answers[input],                   // Expected
        "Largest prime factor of " + input + " is " + answers[input] + "."
      );
    }
  }

  test.done();
};
