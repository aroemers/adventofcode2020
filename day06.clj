(def groups
  (-> (slurp "input/day06.txt")
      (clojure.string/split #"\n\n")
      (->> (map #(map set (clojure.string/split-lines %))))))

(defn solve [f]
  (reduce (fn [total group]
            (+ total (count (apply f group))))
          0
          groups))

(println "Result part 1:" (solve clojure.set/union))

(println "Result part 2:" (solve clojure.set/intersection))
