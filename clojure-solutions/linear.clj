(defn op [op]
  (fn [v1 v2] (mapv (fn [v1 v2] (op v1 v2)) v1  v2)))
(defn mul [op]
  (fn [v s] (mapv (fn [x] (op x s)) v)))

(def v+ (op +))
(def v- (op -))
(def v* (op *))
(def vd (op /))
(def v*s (mul *))
(defn scalar [v1 v2] (reduce + (map * v1 v2)))
(defn transpose [m] (apply mapv vector m))
(def m*v (mul scalar))
(defn m*m [m1 m2]
    (mapv (fn [x] (m*v (transpose m2) x)) m1))

(def m+ (op v+))
(def m- (op v-))
(def m* (op v*))
(def md (op vd))
(def m*s (fn [m s] (mapv (fn [v] (v*s v s)) m)))

(def vect (fn [v1 v2]
            [(- (* (v1 1) (v2 2)) (* (v2 1) (v1 2)))
             (- (* (v2 0) (v1 2)) (* (v1 0) (v2 2)))
             (- (* (v1 0) (v2 1)) (* (v2 0) (v1 1)))]))

(def c+ (op m+))
(def c- (op m-))
(def c* (op m*))
(def cd (op md))
