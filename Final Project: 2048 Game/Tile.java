package org.cis1200.twentyfourtyeight;

import java.awt.*;

/* 11 Possible Total Tiles **/
public class Tile {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private int px;
    private int py;

    /* Size of object, in pixels. */
    private static final int TILE_SIZE = 150;
    private Color color;

    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */

    private int tileValue;
    private static final Color TILE_2 = new Color(42, 72, 88);
    private static final Color TILE_4 = new Color(33, 93, 110);
    private static final Color TILE_8 = new Color(8, 115, 127);
    private static final Color TILE_16 = new Color(0, 137, 138);
    private static final Color TILE_32 = new Color(8, 159, 143);
    private static final Color TILE_64 = new Color(57, 180, 142);
    private static final Color TILE_128 = new Color(100, 201, 135);
    private static final Color TILE_256 = new Color(146, 220, 126);
    private static final Color TILE_512 = new Color(196, 236, 116);
    private static final Color TILE_1024 = new Color(250, 250, 110);
    private static final Color TILE_2048 = new Color(0, 0, 0);

    public Tile(int px, int py, int tileValue) {
        this.px = px;
        this.py = py;
        this.tileValue = tileValue;
    }

    /* Getters **/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }

    public void linkTileValueAndColor(int tileValue) {
        if (tileValue == 0) {
            this.color = Color.WHITE;
        }
        if (tileValue == 2) {
            this.color = TILE_2;
        }
        if (tileValue == 4) {
            this.color = TILE_4;
        }
        if (tileValue == 8) {
            this.color = TILE_8;
        }
        if (tileValue == 16) {
            this.color = TILE_16;
        }
        if (tileValue == 32) {
            this.color = TILE_32;
        }
        if (tileValue == 64) {
            this.color = TILE_64;
        }
        if (tileValue == 128) {
            this.color = TILE_128;
        }
        if (tileValue == 256) {
            this.color = TILE_256;
        }
        if (tileValue == 512) {
            this.color = TILE_512;
        }
        if (tileValue == 1024) {
            this.color = TILE_1024;
        }
        if (tileValue == 2048) {
            this.color = TILE_2048;
        }
    }

    public void draw(int tileValue, Graphics g) {
        linkTileValueAndColor(tileValue);
        g.setColor(color);
        g.fillRect(this.getPx(), this.getPy(), TILE_SIZE, TILE_SIZE);
        g.setColor(Color.WHITE);
        g.drawString("" + tileValue, getPx() + (TILE_SIZE / 2) - 2, getPy() + (TILE_SIZE / 2) + 2);
    }
}
