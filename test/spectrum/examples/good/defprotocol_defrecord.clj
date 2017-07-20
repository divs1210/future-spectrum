(ns spectrum.examples.good.defprotocol-defrecord
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(defprotocol Foo
  (foo [this x]))

(s/fdef foo :args (s/cat :obj any? :x int?) :ret int?)

;; (defrecord Bar []
;;   Foo
;;   (foo [this x]
;;     (inc x)))
