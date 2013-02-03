(ns mikera.image-matrix.impl
  (:use core.matrix)
  (:use core.matrix.utils)
  (:require core.matrix.impl.persistent-vector)
  (:require [core.matrix.implementations :as imp])
  (:require [core.matrix.multimethods :as mm])
  (:require [core.matrix.protocols :as mp])
  (:import [java.awt.image BufferedImage]))

;; =====================================================
;; Bufferedimage implementation
;;
;; represents a BufferedImage as a 3D matrix, with RGBA double vectors at the lowest level 

(defn rgba-to-vector [rgba]
  (let [arr (double-array 4)
        ]
    
    arr))

(extend-protocol mp/PImplementation
  BufferedImage
    (implementation-key [m] :buffered-image)
    (construct-matrix [m data] (TODO))
    (new-vector [m length] (TODO))
    (new-matrix [m rows columns] (TODO))
    (new-matrix-nd [m shape] (TODO))
    (supports-dimensionality? [m dimensions]
      (return (== 3 dimensions))))

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
    (get-2d [m row column] )
    (get-nd [m indexes]))

