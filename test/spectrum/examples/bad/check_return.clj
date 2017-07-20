(ns spectrum.examples.bad.check-return
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef foo :args (s/cat :x integer?) :ret string?)
(defn foo [x]
  (+ x 1))
