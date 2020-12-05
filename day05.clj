(defn binary [instructions]
  (let [max (->> instructions count (Math/pow 2) long dec)]
    (->> instructions
         (reduce (fn [[min max] instruction]
                   (let [half (long (+ (/ max 2) 0.5))]
                     [(case instruction
                        (\F \L) min
                        (\B \R) (+ min half))
                      half]))
                 [0 max])
         first)))

(defn seat-id [row column]
  (+ (* row 8) column))

(def passes (->> (slurp "input/day05.txt")
                 (clojure.string/split-lines)))

(def seat-ids
  (->>  passes
        (map (fn [pass]
               (seat-id (binary (take 7 pass))
                        (binary (drop 7 pass)))))
        (set)))

(def min-seat-id (apply min seat-ids))

(def max-seat-id (apply max seat-ids))

(def missing-seat-id (some (fn [id]
                             (and (seat-ids (dec id))
                                  (not (seat-ids id))
                                  (seat-ids (inc id))
                                  id))
                           (range min-seat-id max-seat-id)))

(println "Result part 1:" max-seat-id)
(println "Result part 2:" missing-seat-id)
