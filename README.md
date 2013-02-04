image-matrix
============

core.matrix support for Java images

### Motivation

For a lot of image processing tasks, it is useful to treat an image as a 3D matrix. This matrix has the following properties:

 - Indexes into the array look like [row column component]
 - There are 4 different component channels (red, green, blue, alpha)
 - Colour values for each component are double values between 0.0 and 1.0

### Usage

Dead simple. Just require `mikera.image-matrix` and you can then used any Java BufferedImage with core.matrix.

    (use 'core.matrix)
    (use 'mikera.image-matrix)
    
    (def bi (BufferedImage. (int width) (int height) BufferedImage/TYPE_INT_ARGB))
    
All the normal matrix operations should work.
    
    ;; returns a double[] array containing the colour values for the pixel (1,1)
    (mget bi 1 1)
    
    ;; scale the RGB components of the image by 0.5 
    (emul! bi [0.5 0.5 0.5 1.0])
 



