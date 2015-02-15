;!/usr/bin/env clojure

(def fib-seq
	((fn fib [a b]
		(lazy-seq (cons a (fib b (+ a b)))))
	1 2))

(defn less-than-4mil? [x]
	(<= x 4000000))

(reduce +
	(filter
		(fn [x]
			(and (less-than-4mil? x) (even? x)))
		(take 35 fib-seq)))
