;!/usr/bin/env clisp

(defun hypoteneuse (length width)
    "Calculate the hypotenuse given the length and width"
    (sqrt (+ (* length length)
             (* width width))))

(defun fibonacci (n)
    "Return the nth fibonacci number"
    (round
        (*
            (/ 1 (sqrt 5))
            (-
                (expt (/ (+ 1 (sqrt 5)) 2) n)
                (expt (/ (- 1 (sqrt 5)) 2) n)))))
