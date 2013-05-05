(ns mikera.image-matrix.test-impl
  (:use clojure.test)
  (:use mikera.image-matrix)
  (:use clojure.core.matrix)
  (:require clojure.core.matrix.compliance-tester)
  (:import [java.awt.image BufferedImage]))

(deftest test-image
  (let [bi (new-image 5 5)]
    (is (== 100 (ecount bi)))
    (is (equals [0 0 0 0] (mget bi 1 1)))))

(deftest test-slices
  (let [bi (new-image 5 5)]
    (is (= [5 4] (shape (first (slices bi)))))))

(deftest test-coerce
  (let [bi (new-image 1 1)]
    (is (= [1 1 4] (shape bi)))
    (is (equals [[[0 0 0 0]]] (coerce [] bi)))))

(deftest test-convert
  (let [bi (new-image 2 1)]
    (is (= [1 2 4] (shape bi)))
    (.setRGB bi 0 0 (unchecked-int 0xFFFFFFFF))
    (is (== 8 (ecount bi)))
    (is (equals [[[1 1 1 1] [0 0 0 0]]] (coerce [] bi)))))

(deftest test-instances
  (clojure.core.matrix.compliance-tester/instance-test (new-image 5 5)))

(deftest test-implementation
  (clojure.core.matrix.compliance-tester/compliance-test (new-image 5 5)))