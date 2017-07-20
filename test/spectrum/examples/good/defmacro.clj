(ns spectrum.examples.good.defmacro
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef foo :args (s/cat :x int?))

(defmacro foo [x]
  `(inc ~x))

(foo 5)
