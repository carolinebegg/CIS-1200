package org.cis1200.twentyfourtyeight;

import javax.swing.*;
import java.awt.*;

public class RunTwentyFourtyEight implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("2048");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Instruction window
        JOptionPane.showMessageDialog(
                frame,
                "Welcome to 2048! :)\n" +
                        "2048 is a single-player video game in which\n" +
                        "the goal is shift the tiles to form the\ng" +
                        "reatest possible tile before the game board\n" +
                        "fills up and there are no more possible moves.\n" +
                        "The game board pieces can be shifted up, down,\n" +
                        "left, or right using the respective arrow keys.\n" +
                        "When two bricks of the same value are adjacent to\n" +
                        "one another and then shifted into each other, they\n" +
                        "will merge to form a new tile with value equal to\n" +
                        "the value of the two tiles added together. You can\n" +
                        "the undo button to undo a move, but do be warned that\n" +
                        "your score will be reduced by half every time you do so."
        );

        // Game board
        final Grid grid = new Grid(status);
        frame.add(grid, BorderLayout.CENTER);

        /*
         * final JButton restart = new JButton("New Game");
         * restart.addActionListener(e -> grid.reset());
         * window.add(restart);
         * 
         * final JButton saved = new JButton("Resume Game");
         * saved.addActionListener(e -> grid.saved());
         * window.add(saved);
         */

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> grid.reset());
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> grid.undo());
        control_panel.add(undo);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        grid.saved();
    }
}
