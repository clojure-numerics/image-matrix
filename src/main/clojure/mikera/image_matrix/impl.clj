(ns mikera.image-matrix.impl
  (:use core.matrix)
  (:use core.matrix.utils)
  (:use mikera.image-matrix.colours)
  (:require core.matrix.impl.persistent-vector)
  (:require [core.matrix.implementations :as imp])
  (:require [core.matrix.multimethods :as mm])
  (:require [core.matrix.protocols :as mp])
  (:import [java.awt.image BufferedImage]))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

(defn new-image
  [^long width ^long height]
  (BufferedImage. (int width) (int height) BufferedImage/TYPE_INT_ARGB))

;; =====================================================
;; Bufferedimage implementation
;;
;; represents a BufferedImage as a 3D matrix, with RGBA double vectors at the lowest level 

(extend-protocol mp/PImplementation
  BufferedImage
    (implementation-key [m] :buffered-image)
    (construct-matrix [m data] (TODO))
    (new-vector [m length] (TODO))
    (new-matrix [m rows columns] (TODO))
    (new-matrix-nd [m shape] (TODO))
    (supports-dimensionality? [m dimensions]
      (== 3 dimensions)))

(extend-protocol mp/PDimensionInfo
  BufferedImage
    (dimensionality [m] 3)
    (get-shape [m] [ (.getHeight m) (.getWidth m) 4])
    (is-scalar? [m] false)
    (is-vector? [m] false)
    (dimension-count [m dimension-number] 3))

(extend-protocol mp/PIndexedAccess
  BufferedImage
    (get-1d [m row] (error "Can't get-1D on BufferedImage"))
    (get-2d [m row column] (rgba-to-double-array (.getRGB m column row)))
    (get-nd [m indexes]
      (let [s (seq indexes)
            c (count s)] 
        (cond 
          (== c 0) m
          (== c 1) (mp/get-1d m (first s))
          (== c 2) (mp/get-2d m (first s) (second s))
          (== c 3) (mp/get-1d (mp/get-2d m (first s) (second s)) (nth s 2))
          :else (error "Can't get from BufferedImage with index: " (vec s))))))

