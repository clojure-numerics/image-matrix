(ns mikera.image-matrix.test-impl
  (:use clojure.test)
  (:use mikera.image-matrix)
  (:use clojure.core.matrix)
  (:require clojure.core.matrix.compliance-tester)
  (:import [java.awt.image BufferedImage]))

(deftest test-image
  (let [bi (new-image 6 5)]
    (is (== 6 (.getWidth bi)))
    (is (== 5 (.getHeight bi)))
    (is (= [5 6 4] (seq (shape bi))))
    (is (== 120 (ecount bi)))
    (is (equals [0 0 0 0] (mget bi 1 1)))))

(deftest test-new-image
  (let [bi (new-array :buffered-image [1 2 4])]
    (is (= [1 2 4] (seq (shape bi))))
    (is (instance? BufferedImage bi))))

(deftest test-slices
  (let [bi (new-image 6 5)]
    (is (= [6 4] (shape (first (slices bi)))))))

(deftest test-coerce
  (let [bi (new-image 1 1)]
    (is (= [1 1 4] (shape bi)))
    (is (equals [[[0 0 0 0]]] (coerce [] bi)))))

(deftest test-round-trip
  (let [d [[[1 1 1 1] [0 0 0 0]]]
        bi (matrix :buffered-image d)]
    (is (equals d (coerce [] bi)))
    (is (equals bi (coerce :buffered-image (coerce [] bi))))))

(deftest test-convert
  (let [bi (new-image 2 1)]
    (is (= [1 2 4] (shape bi)))
    (.setRGB bi 0 0 (unchecked-int 0xFFFFFFFF))
    (is (== 8 (ecount bi)))
    (is (equals [[[1 1 1 1] [0 0 0 0]]] (coerce [] bi))))
  (is (instance? BufferedImage (matrix :buffered-image [[[1 1 1 1]]])))
  (is (instance? BufferedImage (matrix :buffered-image [[[1 1 1 1] [0 0 0 0]]]))))

(deftest test-instances
  (clojure.core.matrix.compliance-tester/instance-test (new-image 5 5)))

(deftest test-implementation
  (clojure.core.matrix.compliance-tester/compliance-test (new-image 5 5)))