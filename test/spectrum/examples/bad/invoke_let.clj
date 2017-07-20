(ns spectrum.examples.bad.invoke-let
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef bar :args (s/cat :s string?) :ret string?)
(defn bar [s]
  (name s))

(defn foo []
  (let [x (+ 1 2)]
    (bar x)))
