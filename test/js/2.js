'use strict';

var problem2 = require('../../src/js/2.js');

exports.testAnswer = function (test) {
  var answer = problem2();

  test.equal(answer, 4613732);
  test.done();
};
