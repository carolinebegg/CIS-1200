package org.cis1200;

public class AdvancedManipulations {

    /** Helper Functions for AdvancedManipulations **/

    /* Round double value to a whole number and then cast it as an int */
    private static int rndCst(double input) {
        return (int) Math.round(input);
    }

    /* Records the red component of a given pixel p as an int value */
    private static int red(Pixel p) {
        return p.getRed();
    }

    /* Records the green component of a given pixel p as an int value */
    private static int green(Pixel p) {
        return p.getGreen();
    }

    /* Records the blue component of a given pixel p as an int value */
    private static int blue(Pixel p) {
        return p.getBlue();
    }

    /**
     * Change the contrast of a picture.
     *
     * Your job is to change the intensity of the colors in the picture.
     * The simplest method of changing contrast is as follows:
     *
     * 1. Find the average color intensity of the picture.
     * a) Sum the values of all the color components for each pixel.
     * b) Divide the total by the number of pixels times the number of
     * components (3).
     * 2. Subtract the average color intensity from each color component of
     * each pixel. Note that you could underflow into negatives.
     * This will make the average color intensity zero.
     * 3. Scale the intensity of each pixel's color components by multiplying
     * them by the "multiplier" parameter. Note that the multiplier is a
     * double (a decimal value like 1.2 or 0.6) and color values are ints
     * between 0 and 255.
     * 4. Add the original average color intensity back to each component of
     * each pixel.
     * 5. Clip the color values so that all color component values are between
     * 0 and 255. (This should be handled by the Pixel class anyway!)
     *
     * Hint: You should use Math.round() before casting to an int for
     * the average color intensity and for the scaled RGB values.
     * (I.e., in particular, the average should be rounded to an int
     * before being used for further calculations...)
     *
     * @param pic        the original picture
     * @param multiplier the factor by which each color component
     *                   of each pixel should be scaled
     * @return the new adjusted picture
     * 
     */
    public static PixelPicture adjustContrast(PixelPicture pic, double multiplier) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] adj = pic.getBitmap();

        double colorTotal = 0.0;

        /* Add up total r, g, and b values for all pixels in the Pixel[][] */
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = adj[row][col];
                colorTotal += (red(p) + green(p) + blue(p));
            }
        }

        /* Calculate the average color intensity of the image */
        int intensity = rndCst(colorTotal / (3.0 * w * h));

        /*
         * Create contrast by adding the average color intensity of the image to the
         * product of the multiplier and the difference between the original r/g/b value
         * and average color intensity for each r/g/b value of each pixel.
         */
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = adj[row][col];
                int r = intensity + rndCst(multiplier * (red(p) - intensity));
                int g = intensity + rndCst(multiplier * (green(p) - intensity));
                int b = intensity + rndCst(multiplier * (blue(p) - intensity));

                adj[row][col] = new Pixel(r, g, b);
            }
        }
        return new PixelPicture(adj);
    }

    /**
     * Reduce a picture to its most common colors.
     *
     * You will need to make use of the ColorMap class to generate a map from
     * Pixels of a certain color to the frequency with which pixels of that
     * color appear in the image. If you go to the ColorMap class, you will
     * notice that it does not have an explicitly declared constructor. In
     * those cases, Java provides a default constructor, which you can call
     * with no arguments as follows:
     * 
     * ColorMap m = new ColorMap();
     * 
     * You will then go on to populate your ColorMap by adding pixels and their
     * corresponding frequencies.
     * 
     * Once you have generated your ColorMap, select your palette by
     * retrieving the pixels whose color appears in the picture with the
     * highest frequency. Then change each pixel in the picture to one with
     * the closest matching color from your palette.
     *
     * Note that if there are two different colors that are the *same* minimal
     * distance from the given color, your code should select the most
     * frequently appearing one as the new color for the pixel. If both colors
     * appear with the same frequency, your code should select the one that
     * appears *first* in the output of the ColorMap's getSortedPixels.
     *
     * Algorithms like this are widely used in image compression. GIFs in
     * particular compress the palette to no more than 255 colors. The variant
     * we have implemented here is a weak one, since it only counts color
     * frequency by exact match. Advanced palette reduction algorithms (known as
     * "indexing" algorithms) calculate color regions and distribute the palette
     * over the regions. For example, if our picture had a lot of shades of blue
     * and little red, our algorithm would likely choose a palette of
     * all blue colors. An advanced algorithm would recognize that blues look
     * similar and distribute the palette so that it would be possible to
     * display red as well.
     *
     * @param pic       the original picture
     * @param numColors the maximum number of colors that can be used in the
     *                  reduced picture
     * @return the new reduced picture
     */
    public static PixelPicture reducePalette(PixelPicture pic, int numColors) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] rdp = pic.getBitmap();
        ColorMap m = new ColorMap();

        /* Add new colors and update existing color frequencies in the color map */
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = rdp[row][col];
                int frequency = 1; // re-declare frequency as 1 on each iteration
                if (m.contains(p)) {
                    frequency += m.getValue(p); // if the color is already in the map, increment the
                                                // frequency by 1
                }
                m.put(p, frequency); // add the color to the map or update its frequency
            }
        }

        Pixel[] pixSorted = m.getSortedPixels();

        /*
         * Compare the distance between the color of single pixel p and the most
         * frequent colors in the image; uses the invariant of the sorted color map to
         * deal with cases where the distance to the given color is the same for both
         * colors (and if both these colors have the same frequency), as the pixels are
         * sorted in the array from most to least frequent.
         */
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = rdp[row][col];
                Pixel compare = pixSorted[0]; // comparison value; initially equal to first pixel in
                                              // the sorted array
                for (int i = 0; i < numColors; i++) {
                    if (p.distance(pixSorted[i]) < p.distance(compare)) {
                        compare = pixSorted[i]; // reassign value to the current pixel if current
                                                // pixel is closer to p
                    }
                    rdp[row][col] = compare;
                }
            }
        }
        return new PixelPicture(rdp);
    }

    /**
     * This method blurs an image.
     *
     * PLEASE read about the *required* division implementation below - even
     * if you understand the rest of the implementation, slight floating-point
     * errors can cause significant autograder deductions!
     *
     * The general idea is that to determine the color of a pixel at
     * coordinate (x, y) of the result, look at (x, y) in the input image
     * as well as the pixels within a box (details below) centered at (x, y).
     * The average color of the pixels in the box - determined by separately
     * averaging R, G, and B - will be the color of (x, y) in the result.
     *
     * How big is the box? That's defined by {@code radius}. A radius of 1
     * yields a 3x3 box (all pixels 1 step away, including diagonals).
     * Similarly, a radius of 2 yields a 5x5 box, a radius of 3 a 7x7 box, etc.
     *
     * As an example, say we have the following image - each pixel is written
     * as (r, g, b) - and the radius parameter is 1.
     *
     * ( 1, 13, 25) ( 2, 14, 26) ( 3, 15, 27) ( 4, 16, 28)
     * ( 5, 17, 29) ( 6, 18, 30) ( 7, 19, 31) ( 8, 20, 32)
     * ( 9, 21, 33) (10, 22, 34) (11, 23, 35) (12, 24, 36)
     *
     * If we wanted the color of the output pixel at (1, 1), we would look at
     * the radius-1 box surrounding (1, 1) in the original image, which is
     *
     * ( 1, 13, 25) ( 2, 14, 26) ( 3, 15, 27)
     * ( 5, 17, 29) ( 6, 18, 30) ( 7, 19, 31)
     * ( 9, 21, 33) (10, 22, 34) (11, 23, 35)
     *
     * The average red component is
     * (1 + 2 + 3 + 5 + 6 + 7 + 9 + 10 + 11) / 9 = 6, so the result
     * pixel at (1, 1) should have red component 6.
     *
     * If the target pixel is on the edge, you should average the pixels
     * within the radius that exist. So in the same example above, the color of
     * the output at (0, 0) would be the average of:
     *
     * ( 1, 13, 25) ( 2, 14, 26)
     * ( 5, 17, 29) ( 6, 18, 30)
     *
     * **IMPORTANT FLOATING POINT NOTE:** To compute the average in a way that's
     * compatible with our autograder, please do the following steps in order:
     *
     * 1. Use floating-point division (not integer division) to divide the
     * total red/green/blue amounts by the number of pixels.
     * 2. Use Math.round() on the result of 1. This is still a float, but it
     * has been rounded to the nearest integer value.
     * 3. Cast the result of 2 to an int. That should be the component's value
     * in the output picture.
     *
     * @param pic    The picture to be blurred.
     * @param radius The radius of the blurring box.
     * @return A blurred version of the original picture.
     */

    public static PixelPicture blur(PixelPicture pic, int radius) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] src = pic.getBitmap();
        Pixel[][] blr = new Pixel[h][w];

        /*
         * Return the original image if the width or height is less than or equal to 1
         * pixel
         */
        if (w <= 1 && h <= 1) {
            return pic;
        }

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                blr[row][col] = blurPix(src, row, col, radius);
            }
        }
        return new PixelPicture(blr);
    }

    // NOTE: You may want to add a static helper function here to
    // help find the average color around the pixel you are blurring.
    public static Pixel blurPix(Pixel[][] src, int rw, int cl, int rad) {
        int w = src[0].length;
        int h = src.length;

        double redTotal = 0.0;
        double greenTotal = 0.0;
        double blueTotal = 0.0;
        double sumPix = 0.0;

        int topRad = rw - rad;
        int leftRad = cl - rad;
        int bottomRad = rw + rad;
        int rightRad = cl + rad;

        /* Checks and corrects if the top bound of the blur square is out of bounds */
        if (topRad < 0) {
            topRad = 0;
        }

        /* Checks and corrects if the left bound of the blur square is out of bounds */
        if (leftRad < 0) {
            leftRad = 0;
        }

        /*
         * Checks and corrects if the bottom bound of the blur square is out of bounds
         */
        if (bottomRad > h - 1) {
            bottomRad = h - 1;
        }

        /* Checks and corrects if the right bound of the blur square is out of bounds */
        if (rightRad > w - 1) {
            rightRad = w - 1;
        }

        /*
         * Sum the number of pixels in the blur square and calculate the total r/g/b
         * values of all the pixels
         */
        for (int r = topRad; r < bottomRad + 1; r++) {
            for (int c = leftRad; c < rightRad + 1; c++) {
                Pixel p = src[r][c];
                redTotal += red(p);
                greenTotal += green(p);
                blueTotal += blue(p);
                sumPix++;
            }
        }

        /* Calculate the average r/g/b value of the blur square */
        int avgRed = rndCst(redTotal / sumPix);
        int avgGreen = rndCst(greenTotal / sumPix);
        int avgBlue = rndCst(blueTotal / sumPix);

        return new Pixel(avgRed, avgGreen, avgBlue);
    }

    /**
     * Challenge Problem (this problem is worth 0 points):
     * Flood pixels of the same color with a different color.
     *
     * The name is short for flood fill, which is the familiar "paint bucket"
     * operation in graphics programs. In a paint program, the user clicks on a
     * point in the image. Every neighboring, similarly-colored point is then
     * "flooded" with the color the user selected.
     *
     * Suppose we want to flood color at (x,y). The simplest way to do flood
     * fill is as follows:
     *
     * 1. Let target be the color at (x,y).
     * 2. Create a set of points Q containing just the point (x,y).
     * 3. Take the first point p out of Q.
     * 4. Set the color at p to color.
     * 5. For each of p's non-diagonal neighbors - up, down, left, and right -
     * check to see if they have the same color as target. If they do, add
     * them to Q.
     * 6. If Q is empty, stop. Otherwise, go to 3.
     *
     * This is a naive algorithm that can be made significantly faster if you
     * wish to try.
     *
     * For Q, you should use the provided IntQueue class. It works very much
     * like the queues we implemented in OCaml.
     *
     * @param pic The original picture to be flooded.
     * @param c   The pixel the user "clicked" (representing the color that should
     *            be flooded).
     * @param row The row of the point on which the user "clicked."
     * @param col The column of the point on which the user "clicked."
     * @return A new picture with the appropriate region flooded.
     */
    public static PixelPicture flood(PixelPicture pic, Pixel c, int row, int col) {
        return pic;
    }
}
