package org.cis1200.twentyfourtyeight;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {
    private static final String FILE_PATH = "files/tfe_test.csv";

    @Test
    public void test() {
        assertNotEquals("CIS 120", "CIS 160");
    }

    /**
     * BASIC GAME STATE SLIDE TESTS
     */

    @Test
    public void testSlideRightCorner() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 3);
        tfe.slideRight();
        assertEquals(4, tfe.getCell(0, 3));
        assertEquals(0, tfe.getCell(0, 0));
    }

    @Test
    public void testSlideRightNoSlide() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 3);
        tfe.slideRight();
        assertEquals(2, tfe.getCell(0, 3));
    }

    @Test
    public void testSlideRightBothCorners() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 3);
        tfe.addNewTile(3, 0);
        tfe.addNewTile(3, 3);
        tfe.slideRight();
        assertEquals(4, tfe.getCell(0, 3));
        assertEquals(4, tfe.getCell(3, 3));
    }

    @Test
    public void testSlideRightFullRow() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 1);
        tfe.addNewTile(0, 2);
        tfe.addNewTile(0, 3);
        tfe.slideRight();
        assertEquals(4, tfe.getCell(0, 3));
        assertEquals(4, tfe.getCell(0, 2));
        tfe.slideRight();
        assertEquals(8, tfe.getCell(0, 3));
        assertEquals(0, tfe.getCell(0, 2));
    }

    @Test
    public void testSlideRight224() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 1);
        tfe.add4Tile(0, 2);
        tfe.slideRight();
        assertEquals(4, tfe.getCell(0, 3));
        assertEquals(4, tfe.getCell(0, 2));
        tfe.slideRight();
        assertEquals(8, tfe.getCell(0, 3));
        assertEquals(0, tfe.getCell(0, 2));
    }

    @Test
    public void testSlideRight422() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.add4Tile(0, 0);
        tfe.addNewTile(0, 1);
        tfe.addNewTile(0, 2);
        tfe.slideRight();
        assertEquals(4, tfe.getCell(0, 3));
        assertEquals(4, tfe.getCell(0, 2));
        tfe.slideRight();
        assertEquals(8, tfe.getCell(0, 3));
        assertEquals(0, tfe.getCell(0, 2));
    }

    @Test
    public void testSlideLeftCorner() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 3);
        tfe.slideLeft();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(0, 3));
    }

    @Test
    public void testSlideLeftNoSlide() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.slideLeft();
        assertEquals(2, tfe.getCell(0, 0));
    }

    @Test
    public void testSlideLeftBothCorners() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 3);
        tfe.addNewTile(3, 0);
        tfe.addNewTile(3, 3);
        tfe.slideLeft();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(3, 0));
    }

    @Test
    public void testSlideLeftFullRow() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 1);
        tfe.addNewTile(0, 2);
        tfe.addNewTile(0, 3);
        tfe.slideLeft();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(0, 1));
        tfe.slideLeft();
        assertEquals(8, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(0, 1));
    }

    @Test
    public void testSlideLeft422() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.add4Tile(0, 1);
        tfe.addNewTile(0, 2);
        tfe.addNewTile(0, 3);
        tfe.slideLeft();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(0, 1));
        tfe.slideLeft();
        assertEquals(8, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(0, 1));
    }

    @Test
    public void testSlideLeft224() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 1);
        tfe.addNewTile(0, 2);
        tfe.add4Tile(0, 3);
        tfe.slideLeft();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(0, 1));
        tfe.slideLeft();
        assertEquals(8, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(0, 1));
    }

    @Test
    public void testSlideUpCorner() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(3, 0);
        tfe.slideUp();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(3, 0));
    }

    @Test
    public void testSlideUpNoSlide() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.slideUp();
        assertEquals(2, tfe.getCell(0, 0));
    }

    @Test
    public void testSlideUpBothCorners() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 3);
        tfe.addNewTile(3, 0);
        tfe.addNewTile(3, 3);
        tfe.slideUp();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(0, 3));
    }

    @Test
    public void testSlideUpFullRow() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(1, 0);
        tfe.addNewTile(2, 0);
        tfe.addNewTile(3, 0);
        tfe.slideUp();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(1, 0));
        tfe.slideUp();
        assertEquals(8, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(1, 0));
    }

    @Test
    public void testSlideUp422() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.add4Tile(1, 0);
        tfe.addNewTile(2, 0);
        tfe.addNewTile(3, 0);
        tfe.slideUp();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(1, 0));
        tfe.slideUp();
        assertEquals(8, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(1, 0));
    }

    @Test
    public void testSlideUp224() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(1, 0);
        tfe.addNewTile(2, 0);
        tfe.add4Tile(3, 0);
        tfe.slideUp();
        assertEquals(4, tfe.getCell(0, 0));
        assertEquals(4, tfe.getCell(1, 0));
        tfe.slideUp();
        assertEquals(8, tfe.getCell(0, 0));
        assertEquals(0, tfe.getCell(1, 0));
    }

    @Test
    public void testSlideDownCorner() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(3, 0);
        tfe.slideDown();
        assertEquals(4, tfe.getCell(3, 0));
        assertEquals(0, tfe.getCell(0, 0));
    }

    @Test
    public void testSlideDownNoSlide() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(3, 0);
        tfe.slideDown();
        assertEquals(2, tfe.getCell(3, 0));
    }

    @Test
    public void testSlideDownBothCorners() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 3);
        tfe.addNewTile(3, 0);
        tfe.addNewTile(3, 3);
        tfe.slideDown();
        assertEquals(4, tfe.getCell(3, 0));
        assertEquals(4, tfe.getCell(3, 3));
    }

    @Test
    public void testSlideDownFullRow() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(1, 0);
        tfe.addNewTile(2, 0);
        tfe.addNewTile(3, 0);
        tfe.slideDown();
        assertEquals(4, tfe.getCell(2, 0));
        assertEquals(4, tfe.getCell(3, 0));
        tfe.slideDown();
        assertEquals(8, tfe.getCell(3, 0));
        assertEquals(0, tfe.getCell(2, 0));
    }

    @Test
    public void testSlideDown224() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(1, 0);
        tfe.add4Tile(2, 0);
        tfe.slideDown();
        assertEquals(4, tfe.getCell(2, 0));
        assertEquals(4, tfe.getCell(3, 0));
        tfe.slideDown();
        assertEquals(8, tfe.getCell(3, 0));
        assertEquals(0, tfe.getCell(2, 0));
    }

    @Test
    public void testSlideDown422() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.add4Tile(0, 0);
        tfe.addNewTile(1, 0);
        tfe.addNewTile(2, 0);
        tfe.slideDown();
        assertEquals(4, tfe.getCell(2, 0));
        assertEquals(4, tfe.getCell(3, 0));
        tfe.slideDown();
        assertEquals(8, tfe.getCell(3, 0));
        assertEquals(0, tfe.getCell(2, 0));
    }

    /**
     * SCORE AND END OF GAME TESTS
     */
    @Test
    public void scoreEqualsZero() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 1);
        tfe.slideDown();
        assertEquals(0, tfe.getScore());
    }

    @Test
    public void scoreEqualsFour() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(0, 1);
        tfe.slideRight();
        assertEquals(4, tfe.getScore());
    }

    @Test
    public void scoreEqualsSixteen() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.addNewTile(0, 0);
        tfe.addNewTile(3, 0);
        tfe.addNewTile(0, 3);
        tfe.addNewTile(3, 3);
        tfe.slideDown();
        assertEquals(4, tfe.getCell(3, 0));
        assertEquals(4, tfe.getCell(3, 3));
        tfe.slideRight();
        assertEquals(16, tfe.getScore());
    }

    @Test
    public void testWinner() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();
        tfe.add2048Tile(0, 0);
        assertTrue(tfe.checkWinner());
    }

    @Test
    public void gameOver() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        tfe.testing();

        tfe.addNewTile(0, 0);
        tfe.add4Tile(0, 1);
        tfe.addNewTile(0, 2);
        tfe.add4Tile(0, 3);

        tfe.add4Tile(1, 0);
        tfe.addNewTile(1, 1);
        tfe.add4Tile(1, 2);
        tfe.addNewTile(1, 3);

        tfe.addNewTile(2, 0);
        tfe.add4Tile(2, 1);
        tfe.addNewTile(2, 2);
        tfe.add4Tile(2, 3);

        tfe.add4Tile(3, 0);
        tfe.addNewTile(3, 1);
        tfe.add4Tile(3, 2);
        tfe.addNewTile(3, 3);

        assertFalse(tfe.isSlidePossible());
    }

    /**
     * Testing for functions used to implement File I/O
     */

    @Test
    public void csvDataToStringTest() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            List<String> entries = List.of("0", "2", "4");
            assertEquals(entries, tfe.csvDataToString(br));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stringsToGridAllZeros() {
        TwentyFourtyEight tfe = new TwentyFourtyEight();
        int[][] zero = new int[4][4];
        List<String> csv = new LinkedList<>();
        for (int i = 0; i < 16; i++) {
            csv.add("0");
        }
        assertArrayEquals(zero, tfe.stringsToGrid(csv));
    }

    /**
     * Testing for functions used to implement the "Undo" button (Collections)
     */
    @Test
    public void testIntArrays() {
        LinkedList<int[][]> a = new LinkedList<>();
        LinkedList<int[][]> b = new LinkedList<>();

        int[][] first = new int[4][4];
        for (int r = 0; r < first.length; r++) {
            for (int c = 0; c < first[0].length; c++) {
                first[r][c] = 0;
            }
        }
        int[][] second = new int[4][4];
        for (int r = 0; r < second.length; r++) {
            for (int c = 0; c < second[0].length; c++) {
                second[r][c] = 2;
            }
        }
        a.add(first);
        System.out.println(a);
        b.add(first);
        System.out.println(b);

        assertEquals(a, b);
    }

}