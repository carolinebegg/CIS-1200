package org.cis1200.twentyfourtyeight;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Grid extends JPanel {
    TwentyFourtyEight tfe;
    private final JLabel status;
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 600;
    public static final int TILE_SIZE = 150;

    public Grid(JLabel scoreStatus) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        tfe = new TwentyFourtyEight(); // initializes model for the game

        status = scoreStatus; // initializes the status JLabel

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    tfe.slideLeft();
                    if (tfe.getTurnSuccess()) {
                        tfe.addBoardState();
                        tfe.addNewTile();
                        tfe.updateSavedGame();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    tfe.slideRight();
                    if (tfe.getTurnSuccess()) {
                        tfe.addBoardState();
                        tfe.addNewTile();
                        tfe.updateSavedGame();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tfe.slideDown();
                    if (tfe.getTurnSuccess()) {
                        tfe.addBoardState();
                        tfe.addNewTile();
                        tfe.updateSavedGame();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    tfe.slideUp();
                    if (tfe.getTurnSuccess()) {
                        tfe.addBoardState();
                        tfe.addNewTile();
                        tfe.updateSavedGame();
                    }
                }
                updateStatus();
                repaint();
            }
        });
    }

    public void reset() {
        tfe.reset();
        status.setText("Score: " + tfe.getScore());
        repaint();
        requestFocusInWindow();
    }

    public void saved() {
        tfe.saved();
        status.setText("Score: " + tfe.getScore());
        repaint();
        requestFocusInWindow();
    }

    public void undo() {
        tfe.undo();
        status.setText("Score: " + tfe.getScore());
        repaint();
        requestFocusInWindow();
    }

    private void updateStatus() {
        if (tfe.checkWinner()) {
            status.setText(
                    "Congratulations! You reached the 2048 tile! \n Final Score: " + tfe.getScore()
            );
        } else if (!tfe.isSlidePossible()) {
            status.setText("Game over. Better luck next time! \n Final Score: " + tfe.getScore());
        } else {
            status.setText("Score: " + tfe.getScore());
        }
    }

    public void paintComponent(Graphics g) {
        int[][] b = tfe.getBoard();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Tile tile = new Tile(c * TILE_SIZE, r * TILE_SIZE, b[r][c]);
                tile.draw(b[r][c], g);
            }
        }

        g.setColor(Color.BLACK);

        // right border
        g.drawLine(BOARD_WIDTH, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.drawLine(BOARD_WIDTH - 1, 0, BOARD_WIDTH - 1, BOARD_HEIGHT);
        g.drawLine(BOARD_WIDTH - 2, 0, BOARD_WIDTH - 2, BOARD_HEIGHT);
        g.drawLine(BOARD_WIDTH - 3, 0, BOARD_WIDTH - 3, BOARD_HEIGHT);
        g.drawLine(BOARD_WIDTH - 4, 0, BOARD_WIDTH - 4, BOARD_HEIGHT);

        // left border
        g.drawLine(0, 0, 0, BOARD_HEIGHT);
        g.drawLine(1, 0, 1, BOARD_HEIGHT);
        g.drawLine(2, 0, 2, BOARD_HEIGHT);
        g.drawLine(3, 0, 3, BOARD_HEIGHT);
        g.drawLine(4, 0, 4, BOARD_HEIGHT);

        // top border
        g.drawLine(0, 0, BOARD_WIDTH, 0);
        g.drawLine(0, 1, BOARD_WIDTH, 1);
        g.drawLine(0, 2, BOARD_WIDTH, 2);
        g.drawLine(0, 3, BOARD_WIDTH, 3);
        g.drawLine(0, 4, BOARD_WIDTH, 4);

        // bottom border
        g.drawLine(0, BOARD_HEIGHT, BOARD_WIDTH, BOARD_HEIGHT);
        g.drawLine(0, BOARD_HEIGHT - 1, BOARD_WIDTH, BOARD_HEIGHT - 1);
        g.drawLine(0, BOARD_HEIGHT - 2, BOARD_WIDTH, BOARD_HEIGHT - 2);
        g.drawLine(0, BOARD_HEIGHT - 3, BOARD_WIDTH, BOARD_HEIGHT - 3);
        g.drawLine(0, BOARD_HEIGHT - 4, BOARD_WIDTH, BOARD_HEIGHT - 4);

        g.drawLine(TILE_SIZE - 2, 0, TILE_SIZE - 2, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE - 1, 0, TILE_SIZE - 1, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE, 0, TILE_SIZE, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE + 1, 0, TILE_SIZE + 1, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE + 2, 0, TILE_SIZE + 2, BOARD_HEIGHT);

        g.drawLine(TILE_SIZE * 2 - 2, 0, TILE_SIZE * 2 - 2, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 2 - 1, 0, TILE_SIZE * 2 - 1, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 2, 0, TILE_SIZE * 2, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 2 + 1, 0, TILE_SIZE * 2 + 1, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 2 + 2, 0, TILE_SIZE * 2 + 2, BOARD_HEIGHT);

        g.drawLine(TILE_SIZE * 3 - 2, 0, TILE_SIZE * 3 - 2, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 3 - 1, 0, TILE_SIZE * 3 - 1, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 3, 0, TILE_SIZE * 3, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 3 + 1, 0, TILE_SIZE * 3 + 1, BOARD_HEIGHT);
        g.drawLine(TILE_SIZE * 3 + 2, 0, TILE_SIZE * 3 + 2, BOARD_HEIGHT);

        g.drawLine(0, TILE_SIZE - 2, BOARD_WIDTH, TILE_SIZE - 2);
        g.drawLine(0, TILE_SIZE - 1, BOARD_WIDTH, TILE_SIZE - 1);
        g.drawLine(0, TILE_SIZE, BOARD_WIDTH, TILE_SIZE);
        g.drawLine(0, TILE_SIZE + 1, BOARD_WIDTH, TILE_SIZE + 1);
        g.drawLine(0, TILE_SIZE + 2, BOARD_WIDTH, TILE_SIZE + 2);

        g.drawLine(0, TILE_SIZE * 2 - 2, BOARD_WIDTH, TILE_SIZE * 2 - 2);
        g.drawLine(0, TILE_SIZE * 2 - 1, BOARD_WIDTH, TILE_SIZE * 2 - 1);
        g.drawLine(0, TILE_SIZE * 2, BOARD_WIDTH, TILE_SIZE * 2);
        g.drawLine(0, TILE_SIZE * 2 + 1, BOARD_WIDTH, TILE_SIZE * 2 + 1);
        g.drawLine(0, TILE_SIZE * 2 + 2, BOARD_WIDTH, TILE_SIZE * 2 + 2);

        g.drawLine(0, TILE_SIZE * 3 - 2, BOARD_WIDTH, TILE_SIZE * 3 - 2);
        g.drawLine(0, TILE_SIZE * 3 - 1, BOARD_WIDTH, TILE_SIZE * 3 - 1);
        g.drawLine(0, TILE_SIZE * 3, BOARD_WIDTH, TILE_SIZE * 3);
        g.drawLine(0, TILE_SIZE * 3 + 1, BOARD_WIDTH, TILE_SIZE * 3 + 1);
        g.drawLine(0, TILE_SIZE * 3 + 2, BOARD_WIDTH, TILE_SIZE * 3 + 2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
