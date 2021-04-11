(ns com.flexiana.scramble.core)

(defn scramble?
  [str-1 str-2]
  (let [character-map (frequencies str-1)
        red-fn (fn [char-map char]
                 (when (and (contains? char-map char)
                            (pos? (get char-map char)))
                   (update char-map char dec)))
        res (reduce red-fn character-map str-2)]
    (boolean res)))

#_(scramble? "rekqodlw" "world")
#_(scramble? "cedewaraaossoqqyt" "codewars")
#_(scramble? "katas" "steak")
