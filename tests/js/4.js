'use strict';

var p4 = require('../../src/js/4.js');

exports.testIsPalindrome = function (test) {
  var answers = {
    1: true,
    12: false,
    22: true,
    434: true,
    9009: true,
    9909: false,
    42224: true,
    124421: true
  };

  test.expect(16);
  for (var input in answers) {
    if (answers.hasOwnProperty(input)) {
      test.equals(p4.isPalindrome(input), answers[input]);
      test.equals(p4.isNumericPalindrome(input), answers[input]);
    }
  }

  test.done();
};

exports.testProblem4 = function (test) {
  test.equals(p4.findMaxPalindrome(100, 999), 906609);
  test.done();
};
