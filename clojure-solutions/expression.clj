(ns your-namespace
    (:require [clojure.string :as str]))

(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn -show [result]
      (if (-valid? result)
        (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
        "!"))
(defn tabulate [parser inputs]
      (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (-show (parser input)))) inputs))

(def _empty (partial partial -return))
(defn _char [p]
      (fn [[c & cs]]
          (if (and c (p c))
            (-return c cs))))
(defn _map [f result]
      (if (-valid? result)
        (-return (f (-value result)) (-tail result))))

(defn _combine [f a b]
      (fn [input]
          (let [ar (a input)]
               (if (-valid? ar)
                 (_map (partial f (-value ar))
                       ((force b) (-tail ar)))))))

(defn _either [a b]
      (fn [input]
          (let [ar (a input)]
               (if (-valid? ar)
                 ar
                 ((force b) input)))))

(defn _parser [parser]
      (let [pp (_combine (fn [v _] v) parser (_char #{\u0000}))]
           (fn [input] (-value (pp (str input \u0000))))))

(defn +char [chars]
      (_char (set chars)))

(defn +char-not [chars]
      (_char (comp not (set chars))))

(defn +map [f parser]
      (comp (partial _map f) parser))

(def +ignore
  (partial +map (constantly 'ignore)))

(defn- iconj [coll value]
       (if (= value 'ignore)
         coll
         (conj coll value)))

(defn +seq [& parsers]
      (reduce (partial _combine iconj) (_empty []) parsers))

(defn +seqf [f & parsers]
      (+map (partial apply f) (apply +seq parsers)))

(defn +seqn [n & parsers]
      (apply +seqf #(nth %& n) parsers))


(defn +or [parser & parsers]
      (reduce _either parser parsers))

(defn +opt [parser]
      (+or parser (_empty nil)))

(defn +star [parser]
      (letfn [(rec [] (+or (+seqf cons parser (delay (rec))) (_empty ())))]
             (rec)))

(defn +plus [parser]
      (+seqf cons parser (+star parser)))

(defn +str [parser]
      (+map (partial apply str) parser))

(defn +parser [parser]
      (println parser)
      (let [pp (_combine (fn [v _] v) parser (_char #{\u0000}))]
           (fn [input] (-value (pp (str input \u0000))))))


(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+plus *digit))))

(def *string
  (+seqn 1
         (+char "\"")
         (+str (+star (+char-not "\"")))
         (+char "\"")))

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

(def *null (+seqf (constantly 'null) (+char "n") (+char "u") (+char "l") (+char "l")))

(def *all-chars (mapv char (range 32 128)))
(def *letter (+char (filter #(Character/isLetter %) *all-chars)))

(def *identifier (+str (+seqf cons *letter (+star (+or *letter *digit)))))

(defn parseObjectPostfix []
      (let
        [*null (+seqf (constantly 'null) (+char "n") (+char "u") (+char "l") (+char "l"))
         *all-chars (mapv char (range 0 128))
         *letter (+char (apply str (filter #(Character/isLetter %) *all-chars)))
         *digit (+char (apply str (filter #(Character/isDigit %) *all-chars)))
         *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars)))
         *ws (+ignore (+star *space))
         *number (+map read-string (+str (+plus *digit)))
         *identifier (+str (+seqf cons *letter (+star (+or *letter *digit))))
         *string (+seqn 1 (+char "\"") (+str (+star (+char-not "\""))) (+char "\""))]
        (letfn [(*seq [begin parser end]
                      (+seqn 1 (+char begin) (+opt (+seqf cons *ws parser (+star (+seqn 1 *ws (+char ",") *ws parser)))) *ws (+char end)))
                (*object [] (+map (partial reduce (partial apply assoc) {})
                                  (*seq "(" (*member) ")")))
                (*member [] (+seq *identifier *ws (+ignore (+char ":")) *ws (*value)))
                (*value [] (+or *null *number *string (*object)))]
               (+parser (+seqn 0 *ws (*value) *ws)))))

(defn toStringPostfix [expr]
      (println expr)
      (if (vector? expr)
        (str/join " " (map toStringPostfix expr))
        (str expr)))

