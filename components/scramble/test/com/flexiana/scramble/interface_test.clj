(ns com.flexiana.scramble.interface-test
  (:require [clojure.test :refer :all]
            [com.flexiana.scramble.interface :as scramble]))

(deftest scramble?
  (testing "scramble? returns true"
    (are [str-1 str-2] (is (scramble/scramble? str-1 str-2))
      "abc" "abc"
      "abc" "bca"
      "abc" "bc"
      "abc" ""
      "boomshankar" "boom"
      "boomshandkar" "shankar"
      "rekqodlw" "world"
      "cedewaraaossoqqyt" "codewars"))
  (testing "scramble? returns false"
    (are [str-1 str-2] (is (not (scramble/scramble? str-1 str-2)))
      "abc" "abcd"
      "abc" "bcaa"
      "abc" "xbc"
      "katas" "steak"
      "hello" "world")))
