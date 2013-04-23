;!/usr/bin/env clojure

(def fib-seq
	((fn fib [a b]
		(lazy-seq (cons a (fib b (+ a b)))))
	1 2))

(reduce + (filter #(<= % 4000000) (filter even? (take 35 fib-seq))))
