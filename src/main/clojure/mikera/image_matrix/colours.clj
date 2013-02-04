(ns mikera.image-matrix.colours
  )

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

(defmacro byte-to-component [b]
  `(let [v# (* (bit-and 0xFF ~b) ~(/ 1.0 255))]
     (cond 
       (< v# 0.0) 0.0
       (> v# 1.0) 1.0
       :else v#)))

(defmacro get-colour-component [x shift]
  `(byte-to-component (bit-shift-right ~x ~shift)))

(defn get-red ^double [^long argb]
  (get-colour-component argb 16))

(defn get-blue ^double [^long argb]
  (get-colour-component argb 0))

(defn get-green ^double [^long argb]
  (get-colour-component argb 8))

(defn get-alpha ^double [^long argb]
  (get-colour-component argb 24))

(defn rgba-to-double-array [^long argb]
  (let [^doubles arr (double-array 4)]
    (aset arr 0 (get-red argb)) 
    (aset arr 1 (get-green argb))
    (aset arr 2 (get-blue argb))
    (aset arr 3 (get-alpha argb))
    arr))
