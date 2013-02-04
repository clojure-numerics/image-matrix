image-matrix
============

core.matrix support for Java images

### Motivation

For a lot of image processing tasks, it is useful to treat an image as a 3D matrix. This matrix has the following properties:

 - Indexes into the array look like [row column component]
 - There are 4 different component channels (red, green, blue, alpha)
 - Colour values for each component are double values between 0.0 and 1.0

### Usage

Dead simple.

    (use 'core.matrix)
    (require 'mikera.image-matrix)
    
    (def bi (BufferedImage. (int width) (int height) BufferedImage/TYPE_INT_ARGB))



