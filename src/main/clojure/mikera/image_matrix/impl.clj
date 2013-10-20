(ns mikera.image-matrix.impl
  (:use clojure.core.matrix)
  (:use clojure.core.matrix.utils)
  (:use mikera.image-matrix.colours)
  (:require clojure.core.matrix.impl.persistent-vector)
  (:require [clojure.core.matrix.implementations :as imp])
  (:require [clojure.core.matrix.multimethods :as mm])
  (:require [clojure.core.matrix.protocols :as mp])
  (:import [java.awt.image BufferedImage]))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

(defn new-image
  "Creates a new BufferedImage with the specified width and height.
   When considered as a core.matrix array, the image will have a shape of [height width 4]."
  [^long width ^long height]
  (BufferedImage. (int width) (int height) BufferedImage/TYPE_INT_ARGB))

(defn image-to-nested-vectors 
  ([^BufferedImage m]
    (mapv #(image-to-nested-vectors m %) (range (.getHeight m))))
  ([^BufferedImage m row]
    (mapv #(image-to-nested-vectors m row %) (range (.getWidth m))))
  ([^BufferedImage m row col]
    (vec (rgba-to-double-array (.getRGB m (int col) (int row))))))

(defn valid-image-shape? 
  "Returns true iff the shape is a valid image shape (rows * columns * 4)"
  ([shape]
    (and (== 3 (count shape))
         (== 4 (last shape)))))

;; =====================================================
;; Bufferedimage implementation
;;
;; represents a BufferedImage as a 3D matrix, with RGBA double vectors at the lowest level 

(extend-protocol mp/PImplementation
  BufferedImage
    (implementation-key [m] :buffered-image)
    (construct-matrix [m data] 
      (if (valid-image-shape? (shape data))
        (let [sh (shape data)
            h (first sh)
            w (second sh)
            ^BufferedImage img (new-image w h)]
	        ;; (println (str "constructing image: " w "x" h))
          (dotimes [y h] 
	          (dotimes [x w]
              (.setRGB img x y (int (argb-int (double (mget data y x 0))
	                                          (double (mget data y x 1))
	                                          (double (mget data y x 2))
	                                          (double (mget data y x 3)))))))
	        img)
        (matrix :ndarray data)))
    (new-vector [m length] (error "Can't create 1D image"))
    (new-matrix [m rows columns] 
      (mp/new-matrix-nd m [rows columns]))
    (new-matrix-nd [m shape] 
      (if (valid-image-shape? shape)
        (new-image (second shape) (first shape))
        (error "Shape not supported by BufferedImage")))
    (supports-dimensionality? [m dimensions]
      (== 3 dimensions)))

(extend-protocol mp/PDimensionInfo
  BufferedImage
    (dimensionality [m] 3)
    (get-shape [m] [ (.getHeight m) (.getWidth m) 4])
    (is-scalar? [m] false)
    (is-vector? [m] false)
    (dimension-count [m dimension-number] 
      (case (long dimension-number)
        0 (.getHeight m) 
        1 (.getWidth m) 
        2 4
        (error "Bufferedimage has dimensionality of 3"))))

(extend-protocol mp/PConversion
  BufferedImage
    (convert-to-nested-vectors [m]
      (image-to-nested-vectors m)))

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

(imp/register-implementation (new-image 1 1)) 
