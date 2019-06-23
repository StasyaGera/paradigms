(def constant constantly)
(defn variable [name] (fn [variables] (get variables name)))

(defn operation [calc]
  (fn [& operands]
    (fn [variables]
      (apply calc (mapv (fn [operand] (operand variables)) operands)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [& args] (reduce (fn [^double a, b] (/ a b)) args))))
(def negate subtract)
(def sin (operation (fn [a] (Math/sin a))))
(def cos (operation (fn [a] (Math/cos a))))

(defn parseFunction [input]
  (def operations {"+" add, "-" subtract, "*" multiply, "/" divide, "negate" negate, "cos" cos, "sin" sin})
  (defn parse [args]
    (cond
      (number? args) (constant args)
      (symbol? args) (variable (str args))
      :else (apply (get operations (str (first args))) (map parse (rest args)))))
  (parse (read-string input)))

;(def expr (divide (constant 1) (constant 0)))
;(println ((parseFunction "(/ (negate x), 2.0)") {"x" 1}))
;(println (expr {"z" 0.9534847357143016, "x" 0.5348049050106168, "y" 0.35124648271848025}))
;(println (expr {"x" 1}))
;(println (type ((divide (constant 1) (constant 0)) {})))