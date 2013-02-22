(ns mikera.image-matrix.test-impl
  (:use clojure.test)
  (:use mikera.image-matrix)
  (:use clojure.core.matrix)
  (:import [java.awt.image BufferedImage]))

(deftest test-image
  (let [bi (new-image 5 5)]
    (is (== 100 (ecount bi)))
    (is (equals [0 0 0 0] (mget bi 1 1)))))