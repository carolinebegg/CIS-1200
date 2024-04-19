package org.cis1200;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Use this file to test your implementation of Pixel.
 * 
 * We will manually grade this file to give you feedback
 * about the completeness of your test cases.
 */

public class MyPixelTest {

    /*
     * Remember, UNIT tests should ideally have one point of failure. Below we
     * give you two examples of unit tests for the Pixel constructor, one that
     * takes in three ints as arguments and one that takes in an array. We use
     * the getRed(), getGreen(), and getBlue() methods to check that the values
     * were set correctly. These two tests do not comprehensively test all of
     * Pixel so you must add your own.
     * 
     * You might want to look into assertEquals, assertTrue, assertFalse, and
     * assertArrayEquals at the following:
     * http://junit.sourceforge.net/javadoc/org/junit/Assert.html
     *
     * Note, if you want to add global variables so that you can reuse Pixels
     * in multiple tests, feel free to do so.
     */

    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructArrayLongerThan3() {
        int[] arr = { 10, 20, 30, 40 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    /* ADD YOUR OWN TESTS BELOW */

    @Test
    public void testConstructArrayShorterThan3() {
        int[] arr = { 10, 20 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructArrayEmpty() {
        int[] arr = {};
        Pixel p = new Pixel(arr);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructNullArray() {
        Pixel p = new Pixel(null);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testGetComponents() {
        Pixel p = new Pixel(40, 50, 60);
        int[] test = new int[] { 40, 50, 60 };
        assertArrayEquals(test, p.getComponents());
    }

    @Test
    public void testDistanceToValidPixelsRedToBlue() {
        Pixel p = Pixel.BLUE;
        Pixel px = Pixel.RED;
        assertEquals(510, p.distance(px));
    }

    @Test
    public void testDistanceToValidPixelsWhiteToBlack() {
        Pixel p = Pixel.WHITE;
        Pixel px = Pixel.BLACK;
        assertEquals(765, p.distance(px));
    }

    @Test
    public void testDistanceToValidPixelsCustomColors() {
        Pixel p = new Pixel(10, 45, 74);
        Pixel px = new Pixel(89, 53, 230);
        assertEquals(243, p.distance(px));
    }

    @Test
    public void testDistanceToNullPixel() {
        Pixel p = Pixel.BLUE;
        assertEquals(-1, p.distance(null));
    }

    @Test
    public void testStringToStringBlackPixel() {
        Pixel p = Pixel.BLACK;
        String test = "(0, 0, 0)";
        assertEquals(0, test.compareTo(p.toString()));
    }

    @Test
    public void testStringToStringWhitePixel() {
        Pixel p = Pixel.WHITE;
        String test = "(255, 255, 255)";
        assertEquals(0, test.compareTo(p.toString()));
    }

    @Test
    public void testSamePixels() {
        Pixel p = Pixel.BLUE;
        Pixel px = Pixel.BLUE;
        assertTrue(p.sameRGB(px));
    }

    @Test
    public void testDifferentPixels() {
        Pixel p = Pixel.BLUE;
        Pixel px = Pixel.RED;
        assertFalse(p.sameRGB(px));
    }

    @Test
    public void testDifferentNullPixels() {
        Pixel p = Pixel.BLUE;
        assertFalse(p.sameRGB(null));
    }

}
