(ns spectrum.examples.good.defn
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/fdef foo :args (s/cat :x string?) :ret string?)
(defn foo [x]
  x)
