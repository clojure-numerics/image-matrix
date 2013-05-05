image-matrix
============

This small library provides `core.matrix` support for Java BufferedImage objects.

### Motivation

For a lot of image processing tasks, it is useful to treat an image as a 3D matrix. This matrix has the following properties:

 - Indexes into the array look like [row column component]
 - There are 4 different component channels (red, green, blue, alpha)
 - Colour values for each component are double values between 0.0 and 1.0

### Usage

Dead simple. Just require `mikera.image-matrix` and you can then used any Java BufferedImage with core.matrix.

    (use 'mikera.image-matrix)
    
    (def bi (new-image width height))
    
All the normal `core.matrix` operations should now work on your new image.

    (use 'core.matrix)
    
    ;; returns a double[] array containing the colour values for the pixel (1,1)
    (mget bi 1 1)
    
    ;; scale the RGB components of the image by 0.5 
    (emul! bi [0.5 0.5 0.5 1.0])
    

### Additional notes

 - Require Clojure 1.4 or above
 - Requires core.matrix 0.7.0 or above
 - Not yet optimised for performance. It may be faster to copy the 3D image data into a more optimised
   matrix format (e.g. vectorz-clj) if you plan to do a lot of complex numerical processing, then convert back
   to an image using image-matrix at the end.
 



