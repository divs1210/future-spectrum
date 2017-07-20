(ns spectrum.examples.bad.invoke-wrong-type-literal
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef foo :args (s/cat :x keyword?) :ret string?)

(defn foo [x]
  "foo")

(s/fdef bad :args empty? :ret string?)
(defn bad []
  (foo 3))
