(def expenses
  (->> (slurp "input/day01.txt")
       (clojure.string/split-lines)
       (map #(Long/parseLong %))))

(defn calc [[sum result]]
  (when (= sum 2020)
    result))

(def pairs1
  (for [i     expenses
        j     expenses
        :let  [sum (+ i j)]
        :when (<= sum 2020)]
    [sum (* i j)]))

(def pairs2
  (for [[s p] pairs1
        k     expenses
        :let  [sum (+ s k)]
        :when (= sum 2020)]
    [sum (* p k)]))

(println "Result part 1:" (some calc pairs1))
(println "Result part 2:" (some calc pairs2))
