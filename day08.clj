(defn parse-line [line]
  (let [[operation argument] (clojure.string/split line #"\s")]
    [(keyword operation) (Long/parseLong argument)]))

(def instructions
  (->> (slurp "input/day08.txt")
       (clojure.string/split-lines)
       (mapv parse-line)))

(def total-instructions (count instructions))

(defn solve-1 [override acc ip processed]
  (cond (= ip total-instructions)
        [:done acc]

        (processed ip)
        [:loop acc processed]

        :otherwise
        (let [[operation argument] (override ip (instructions ip))]
          (case operation
            :acc (recur override (+ acc argument) (inc ip) (conj processed ip))
            :jmp (recur override acc (+ ip argument) (conj processed ip))
            :nop (recur override acc (inc ip) (conj processed ip))))))

(defn solve-2 [alter altered]
  (let [[operation argument]   (instructions alter)
        override               {alter [(case operation :nop :jmp :jmp :nop :acc) argument]}
        [result acc processed] (solve-1 override 0 0 #{})]
    (case result
      :loop (let [altered' (conj altered alter)
                  alter'   (some (fn [ip]
                                   (and (not (altered' ip))
                                        (#{:nop :jmp} (first (instructions ip)))
                                        ip))
                                 processed)]
              (recur alter' altered'))
      acc)))

(println "Result part 1:" (second (solve-1 {} 0 0 #{})))
(println "Result part 2:" (solve-2 0 #{}))
