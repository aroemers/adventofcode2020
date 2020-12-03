(def map-lines (->> (slurp "input/day03.txt")
                    (clojure.string/split-lines)))

(def line-width (count (first map-lines)))

(def total-lines (count map-lines))

(defn slope [right down]
  (loop [lines (drop down map-lines)
         pos   0
         trees 0]
    (if-let [line (first lines)]
      (let [new-pos   (mod (+ pos right) line-width)
            has-tree? (= (get line new-pos) \#)
            new-trees (cond-> trees has-tree? inc)]
        (recur (drop down lines) new-pos new-trees))
      trees)))

(println "Result part 1:" (slope 3 1))
(println "Result part 2:" (* (slope 1 1)
                             (slope 3 1)
                             (slope 5 1)
                             (slope 7 1)
                             (slope 1 2)))
