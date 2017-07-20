(ns spectrum.util
  (:require [clojure.future :refer :all]
            [clojure.tools.analyzer.jvm :as ana.jvm]
            [clojure.spec.alpha :as s])
  (:import clojure.lang.Var))

(defn literal? [x]
  (let [a (ana.jvm/analyze x)]
    (and (:literal? a) (not= :unknown (:type a)))))

(defn fn-literal? [x]
  (and (seq? x)
       (= 'fn* (first x))
       (let [a (ana.jvm/analyze x)]
         (= :fn (:op a)))))

(s/fdef var-name :args (s/cat :v var?) :ret symbol?)
(defn var-name [^Var v]
  (symbol (str (.ns v)) (str (.sym v))))

(s/fdef strip-namespace :args (s/cat :k keyword?) :ret simple-keyword?)
(defn strip-namespace [k]
  (keyword (name k)))

(defn zip
  "Returns (get x key), with x attached as metadata"
  [a key]
  (let [v (get a key)]
    (assert v)
    (with-meta v {:a a})))

(defn with-a [x a]
  (with-meta x {:a a}))

(defn unwrap-a [x]
  (-> x meta :a))

(defn unwrap-while [x f]
  (let [a (unwrap-a)]
    (when a
      (if (f a)
        a
        (recur a f)))))

(defn print-once* [& args]
  (apply println args))

(def print-once (memoize print-once*))

(s/fdef protocol? :args (s/cat :x any?) :ret boolean?)
(defn protocol? [x]
  (and (map? x)
       (var? (:var x))
       (class? (:on-interface x))
       (map? (:method-map x))))

(s/fdef namespace? :args (s/cat :x any?) :ret boolean?)
(defn namespace? [x]
  (instance? clojure.lang.Namespace x))

(s/fdef queue? :args (s/cat :x any?) :ret boolean?)
(defn queue? [x]
  (instance? clojure.lang.PersistentQueue x))

(s/fdef queue :args (s/cat :coll (s/? coll?)) :ret queue?)
(defn queue
   ([] clojure.lang.PersistentQueue/EMPTY)
   ([coll] (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))

(defn conj-seq [x coll]
  (reduce (fn [x a]
            (conj x a)) x coll))

(defmethod print-method clojure.lang.PersistentQueue
  [q ^java.io.Writer w]
  (.write w "#queue ")
  (print-method (sequence q) w))

(defn var-sym [v]
  (symbol (str (.name (.-ns v))) (str (.-sym v))))

(defmacro predicate-spec
  "fdef name any? -> boolean?"
  [x]
  (let [sym (cond
              (var? x) (var-sym x)
              (symbol? x) x
              :else (assert false)) ]
    `(s/fdef ~sym :args (s/cat :x any?) :ret boolean?)))

(defn validate! [s args]
  (if-not (s/valid? s args)
    (throw (ex-info "spec does not conform" {:spec s
                                             :args args
                                             :data (s/explain-data s args)}))))

(defn multimethod-dispatch-values
  "Returns the seq of allowed dispatch values in the multimethod"
  [^clojure.lang.MultiFn ms]
  (->> (.getMethodTable ms)
       (keys)))
