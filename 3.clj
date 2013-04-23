;!/usr/bin/env clojure
;
; TDD style
; Admittedly cheated with the tests by copying from: http://vimeo.com/7762511
; But it is a lot of fun building a function up nothing using tests along the way

(ns com.terrymunro.euler
	(:use clojure.test))

(defn prime-factors
	([n]
		(prime-factors n 2 nil))
	([n divisor accumulator]
		(cond
			(<= n 1)
			; We constructed the results from top down so reverse them to sort them ascending
				(reverse accumulator)
			; If n is evenly dividable by the divisor then recurse with divided n and add divisor to accumulating list
			(= (mod n divisor) 0)
				(recur (/ n divisor) divisor (cons divisor accumulator))
			; Otherwise increment divisor and try again
			:else
				(recur n (inc divisor) accumulator))))

(deftest should-factor-n
	(every? #(is (= (last %) (prime-factors (first %))))
		[[1 '()]
		 [2 '(2)]
		 [3 '(3)]
		 [4 '(2 2)]
		 [5 '(5)]
		 [6 '(2 3)]
		 [7 '(7)]
		 [8 '(2 2 2)]
		 [9 '(3 3)]
		 [13195 '(5 7 13 29)]
		 [(* 2 3 5 7 11 13) '(2 3 5 7 11 13)]
		 [(* 8191 131071) '(8191 131071)]
		 [600851475143 '(71 839 1471 6857)]]))

(run-tests)
