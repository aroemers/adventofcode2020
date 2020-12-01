(def expenses
  (->> (slurp "input/day01.txt")
       (clojure.string/split-lines)
       (map #(Long/parseLong %))))

(defn calc [[sum result]]
  (when (= sum 2020)
    result))

(def part1
  (some calc (for [i expenses j expenses] [(+ i j) (* i j)])))

(def part2
  (some calc (for [i expenses j expenses k expenses] [(+ i j k) (* i j k)])))

(println "Result part 1:" part1)
(println "Result part 2:" part2)
