(defn parse-line [graph line]
  (let [[_ parent contents] (re-matches #"(\w+ \w+) bags contain (.+)" line)]
    (reduce (fn [graph' [_ number child]]
              (let [weight (Long/parseLong number)]
                (-> graph'
                    (update-in [:dependents child] assoc parent weight)
                    (update-in [:dependencies parent] assoc child weight))))
            graph
            (re-seq #"(\d+) (\w+ \w+) bags?" contents))))

(def graph (->> (slurp "input/day07.txt")
                (clojure.string/split-lines)
                (reduce parse-line {})))

(defn solve-1 [initial]
  (loop [found  #{}
         lookup #{initial}]
    (if-let [node (first lookup)]
      (let [children (get-in graph [:dependents node])]
        (recur (apply conj found (keys children))
               (apply conj (rest lookup) (keys children))))
      (count found))))

(defn solve-2 [node]
  (if-let [children (get-in graph [:dependencies node])]
    (reduce (fn [a [node weight]]
              (+ a weight (* weight (solve-2 node))))
            0
            children)
    0))

(println "Result part 1:" (solve-1 "shiny gold"))
(println "Result part 2:" (solve-2 "shiny gold"))
