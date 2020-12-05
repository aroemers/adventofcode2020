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

(defn seat-id [pass]
  (+ (* (binary (take 7 pass)) 8) (binary (drop 7 pass))))

(def passes (->> (slurp "input/day05.txt") (clojure.string/split-lines)))

(def seat-ids (->> passes (map seat-id) sort vec))

(def missing-seat-id (some (fn [index]
                             (let [seat-id (seat-ids index)]
                               (when (= (+ seat-id 2) (seat-ids (inc index)))
                                 (inc seat-id))))
                           (range (count seat-ids))))

(println "Result part 1:" (last seat-ids))
(println "Result part 2:" missing-seat-id)
