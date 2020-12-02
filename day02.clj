(defn parse-line [line]
  (let [[match n1 n2 char password] (re-matches #"^(\d+)-(\d+) (\w): (\w+)$" line)]
    (if match
      [(Long/parseLong n1) (Long/parseLong n2) (first char) password]
      (throw (ex-info "Failed to parse line" {:line line})))))

(def passwords (->> (slurp "input/day02.txt")
                    (clojure.string/split-lines)
                    (map parse-line)))

(defn valid-1? [[n1 n2 char password]]
  (<= n1 (get (frequencies password) char 0) n2))

(defn valid-2? [[n1 n2 char password]]
  (not= (= (get password (dec n1)) char)
        (= (get password (dec n2)) char)))

(println "Result part 1:" (count (filter valid-1? passwords)))
(println "Result part 2:" (count (filter valid-2? passwords)))
