(ns spectrum.examples.bad.spec-arity-long
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef foo :args (s/cat :a integer? :b integer?) :ret integer?)
(defn foo [a]
  (inc a))

(foo 1)
