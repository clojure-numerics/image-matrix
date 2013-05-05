(ns mikera.image-matrix
  (:use clojure.core.matrix)
  (:require mikera.image-matrix.impl))

(defn new-image
  "Creates a new BufferedImage of the specified with and hight (in pixels)" 
  ^java.awt.image.BufferedImage [width height]
  (mikera.image-matrix.impl/new-image (long width) (long height)))
