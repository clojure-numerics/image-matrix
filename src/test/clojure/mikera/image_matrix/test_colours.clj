(ns mikera.image-matrix.test-colours
  (:use clojure.test)
  (:use mikera.image-matrix.colours)
  (:use clojure.core.matrix))

(deftest components
  (is (== 1.0 (get-red 0xFFFFFFFF)))
  (is (== 0.0 (get-red 0xFF0000FF)))
  (is (== 1.0 (get-alpha 0xFF000000)))
  (is (== 1.0 (get-blue 0x000000FF)))
  (is (== 0.0 (get-green 0xFFFF00FF))))

(deftest colour-vector 
  (is (equals [1.0 0.0 0.0 1.0] (rgba-to-double-array 0xFFFF0000))))