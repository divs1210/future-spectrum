(ns spectrum.examples.bad.map-destructure
  (:require [clojure.future :refer :all]
            [clojure.spec.alpha :as s]))

(s/def ::message string?)
(s/fdef foo :args (s/cat :args (s/keys :req-un [::message])) :ret int?)

(defn foo [{:keys [message] :as args}]
  message)
