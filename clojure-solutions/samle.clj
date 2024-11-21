(defn op [f]
  (fn [a & b]
    (let [b (first b)]
      (if b
        (fn [env] (f (a env) (b env)))
        (fn [env] (f (a env)))))))

(def add (op +))
(def subtract (op -))
(def multiply (op *))
(defn divide [a b]
  (fn [env]
    (/ (double (a env)) (double (b env)))))
(def negate (op -))
(defn sin [a] (fn [env] (Math/sin (a env))))
(defn cos [a] (fn [env] (Math/cos (a env))))

(defn constant [value] (constantly value))
(defn variable [name] (fn [env] (get env name)))

(def operators
  {'+ add
   '- subtract
   '* multiply
   '/ divide
   'negate negate
   'sin sin
   'cos cos})

(defn build-expr [expr]
  ;  (println "Building expression for:" expr)
  (cond
    (number? expr)
    (constant expr)

    (symbol? expr)
    (variable (name expr))

    (list? expr)
    (let [op (get operators (first expr))
          a (build-expr (second expr))
          b (if (> (count expr) 2) (build-expr (nth expr 2)))]
      (if b
        (op a b)
        (op a))
      )))

(defn parseFunction [s]
  (let [parsed (read-string s)]
    (build-expr parsed)))
;(def expr-parsed (parseFunction "(sin 2.0)"))
;(println (expr-parsed {"y" 100 "x" 10}))






;; HW 11
(defn Constant [value]
  {:type 'const, :value value})
(defn Variable [name]
  {:type 'var, :name name})
(defn Add [a b]
  {:type 'add, :left a, :right b})
(defn Subtract [a b]
  {:type 'subtract, :left a, :right b})
(defn Multiply [a b]
  {:type 'multiply, :left a, :right b})
(defn Divide [a b]
  {:type 'divide, :left a, :right b})
(defn Negate [a]
  {:type 'negate, :operand a})
(defn evaluate [expr vars]
  (cond
    (= (:type expr) 'const) (:value expr)
    (= (:type expr) 'var) (get vars (:name expr))
    (= (:type expr) 'add) (+ (evaluate (:left expr) vars) (evaluate (:right expr) vars))
    (= (:type expr) 'subtract) (- (evaluate (:left expr) vars) (evaluate (:right expr) vars))
    (= (:type expr) 'multiply) (* (evaluate (:left expr) vars) (evaluate (:right expr) vars))
    (= (:type expr) 'divide) (/ (double (evaluate (:left expr) vars)) (double (evaluate (:right expr) vars)))
    (= (:type expr) 'negate) (- (evaluate (:operand expr) vars))))
(defn toString [expr]
  (cond
    (= (:type expr) 'const) (str (:value expr))
    (= (:type expr) 'var) (:name expr)
    (= (:type expr) 'add) (str "(+ " (toString (:left expr)) " " (toString (:right expr)) ")")
    (= (:type expr) 'subtract) (str "(- " (toString (:left expr)) " " (toString (:right expr)) ")")
    (= (:type expr) 'multiply) (str "(* " (toString (:left expr)) " " (toString (:right expr)) ")")
    (= (:type expr) 'divide) (str "(/ " (toString (:left expr)) " " (toString (:right expr)) ")")
    (= (:type expr) 'negate) (str "(negate " (toString (:operand expr)) ")")))
(def operators
  {'+ Add
   '- Subtract
   '* Multiply
   '/ Divide
   'negate Negate})
(defn build-expr [expr]
  ;  (println "Building expression for:" expr)
  (cond
    (number? expr)
    (Constant expr)

    (symbol? expr)
    (Variable (name expr))

    (list? expr)
    (let [op (get operators (first expr))
          a (build-expr (second expr))
          b (if (> (count expr) 2) (build-expr (nth expr 2)))]
      (if b
        (op a b)
        (op a))
      )))

(defn parseObject [s]
  (let [parsed (read-string s)]
    (build-expr parsed)))

;(def expr (Subtract (Multiply (Constant 2) (Variable "x")) (Constant 3)))
;(println (evaluate expr {"x" 2}))  ; Выведет 1
;(println (toString expr))          ; Выведет "(- (* 2 x) 3)"
