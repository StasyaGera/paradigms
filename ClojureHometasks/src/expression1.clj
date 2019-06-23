(defn evaluate [expr vars]
  (((expr :methods) :evaluate) expr vars))
(defn diff [expr var]
  (((expr :methods) :diff) expr var))
(defn toString [expr]
  (((expr :methods) :toString) expr))

(defn create [object methods]
  (fn [& args] (apply (partial object {:methods methods}) args)))


(def Constant
  (create
    (fn [this value] (assoc this, :value value)),
    {:evaluate (fn [this vars] (this :value))
     :diff     (fn [this var] (Constant 0))
     :toString (fn [this] (str (this :value)))}))

(def ZERO (Constant 0))
(def ONE (Constant 1))

(def Variable
  (create
    (fn [this name] (assoc this, :name name)),
    {:evaluate (fn [this vars] (get vars (this :name)))
     :diff     (fn [this var] (if (= (this :name) var) ONE, ZERO))
     :toString (fn [this] (this :name))}))

(defn operation [this & operands] (assoc this, :operands (vec operands)))
(defn opDescription [calc representation rule]
  {:evaluate (fn [this operands]
               (apply calc (mapv
                             (fn [expr] (evaluate expr operands))
                             (this :operands))))
   :toString (fn [this]
               (cons representation (mapv (fn [expr] (toString expr)) (this :operands))))
   :diff (fn [this name] (rule (this :operands) name))})

(def Add (create operation (opDescription
                              +,
                              "+",
                              (fn [operands var]
                                (apply Add (mapv (fn [operand] (diff operand var)) operands))))))

(def Subtract (create operation (opDescription
                                   -,
                                   "-",
                                   (fn [operands var]
                                     (apply Subtract (mapv (fn [operand] (diff operand var)) operands))))))

(def Multiply (create operation (opDescription
                                   *,
                                   "*",
                                   (fn [operands var]
                                     ((def diffed (mapv (fn [operand] (diff operand var)) operands))
                                       (def indices (range 0 (- (count diffed) 1)))
                                       (apply Add (mapv (fn [ind] (apply Multiply (conj (filter (fn [el] (not= ind (.indexOf operands el))) operands) (get diffed ind)))) indices)))))))

(def Divide (create operation (opDescription
                                 (fn [& args] (reduce (fn [^double a, b] (/ a b)) args)),
                                 "/",
                                 (fn [operands var]
                                   ((def diffed (mapv (fn [operand] (diff operand var)) operands))
                                     (def indices (range 0 (- (count diffed) 1)))
                                     (apply Divide (conj (apply Subtract (mapv (fn [ind] (apply Multiply (conj (filter (fn [el] (not= ind (.indexOf operands el))) operands) (get diffed ind)))) indices))
                                            (apply Multiply (mapv (fn [el] (Multiply el, el)) (rest operands))))))))))

(def Negate (create operation (opDescription
                                 -,
                                 "-",
                                 (fn [operands var]
                                   (Negate (diff (first operands) var))))))



(println (toString (diff (Multiply (Constant 2), (Constant 3), (Variable "x")) "x")))
