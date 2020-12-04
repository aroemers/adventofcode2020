(defn parse [s]
  (->> (re-seq #"(\w{3}):(.+?)(\s|$)" s)
       (map (fn [[match field value]] [(keyword field) value]))
       (into {})))

(def passports (-> (slurp "input/day04.txt")
                   (clojure.string/split #"\n\n")
                   (->> (map parse))))

(def required-fields #{:byr :iyr :eyr :hgt :hcl :ecl :pid})

(defn valid-1? [passport]
  (every? passport required-fields))

(defn valid-2? [passport]
  (and (valid-1? passport)
       (<= 1920 (Long/parseLong (:byr passport)) 2002)
       (<= 2010 (Long/parseLong (:iyr passport)) 2020)
       (<= 2020 (Long/parseLong (:eyr passport)) 2030)
       (let [[_ value unit] (re-matches #"(\d+)(in|cm)" (:hgt passport))]
         (case unit
           "cm" (<= 150 (Long/parseLong value) 193)
           "in" (<= 59  (Long/parseLong value) 76)
           false))
       (re-matches #"#[0-9a-f]{6}" (:hcl passport))
       (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} (:ecl passport))
       (re-matches #"\d{9}" (:pid passport))))

(println "Result part 1:" (count (filter valid-1? passports)))
(println "Result part 2:" (count (filter valid-2? passports)))
