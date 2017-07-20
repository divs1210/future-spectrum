(ns spectrum.examples.bad.intermediate-invalid
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef foo :args (s/cat :x string?) :ret string?)
(defn foo [x]
  (even? x)
  x)
