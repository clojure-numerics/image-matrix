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

(defmacro component-to-byte [c]
  `(let [b# (long (* (double ~c) 255.0))
         b# (if (< b# 0) 0 b#)
         b# (if (> b# 255) 255 b#)]
     b#))

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

(defn argb-int
  "Gets the integer ARGB value from four double colour vectors"
  (^long [^longs arr]
    (argb-int (aget arr 0) (aget arr 1) (aget arr 2) (aget arr 3)))
  (^long [r g b a]
    (+ (bit-shift-left (component-to-byte a) 24)
       (bit-shift-left (component-to-byte r) 16)
       (bit-shift-left (component-to-byte g) 8)
       (component-to-byte b)))) 
