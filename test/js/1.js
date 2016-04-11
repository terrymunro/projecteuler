'use strict';

var problem1 = require('../../src/js/1.js');

exports.testAnswer = function (test) {
  var answer = problem1();

  test.equal(answer, 233168);
  test.done();
};
