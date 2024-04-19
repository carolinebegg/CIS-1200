=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: cbegg
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
        The 4x4 game board is modelled as a 2D int array. 2D arrays are well suited for this
        game because the actual 2048 board is a 4x4 grid, so a 2D array is a very intuitive
        choice. Further, it is easy to manipulate and move the entries in each space in the
        array through slides.

  2. Testing
        The core game state/model is fully JUnit testable. All four of the key methods that
        control the tile slides (slideRight(), slideLeft(), slideUp(), and slideDown()) are
        tested for a variety of different types of slides. These include a double slide,
        sliding and merging multiple tiles at one time, sliding a full row of matching tiles,
        preventing aa unintentional double merge within one slide, and a null slide (a "null"
        slide is not actually "null," but is rather the name I am using to refer to the situation
        in which a slide function is called but the tiles are unable to move based on their
        position. In addition to the many test cases in GameTest, the TwentyFourtyEight.java file
        contains a main function in which you can manually add in game moves such as slideLeft()
        or slideRight() and addNewTile(r,c), as well as other test-specific functions, such as
        add2048Tile() (which can be used to test what happens when the player reaches the 2048 tile).

  3. File I/O
        File I/O is used to auto-save and store the game board. This allows a player to exit out of
        game and then re-run the program without losing the game that they were previously playing.
        Although this is not a part of the File I/O implementation for the concept points (it is
        more fully handled in the Collections/Maps section below), File I/O is also used in the
        "Undo" button feature, as it stores all the game boards.

  4. Collections / Maps
        A LinkedList<int[][]> is used to store the game board at every move. This allows for the
        implementation of the "Undo" button. A LinkedList is the ideal data structure for this
        sort of functionality because it is well suited for accessing and deleting the last
        item in a list, and also because it allows for duplicates.


===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  My game uses five total classes: TwentyFourtyEight, Tile, Grid,
  RunTwentyFourtyEight, and Game.

   1) Game is the overall class that contains the main method that starts and
  runs the interactive 2048 game. It does this by initializing and running the
  runnable game class RunTwentyFourtyEight.

  2) RunTwentyFourtyEight is the runnable game class. This class implements
  the Runnable interface and all instances in this class are initialized within
  the method run() and are intended to be executed by a thread. This class creates
  the GUI for the game by initializing and handling all the Java Swing components.
  Additionally, it initializes an instance of the Grid class (which acts as the
  game board) and calls grid.saved(), which loads in the auto-saved game board from
  the last time the game was played.

  3) Grid adds keyListeners to the up, down, left, and right arrow keys, and also
  calls the relevant direction's slide function on the initialized instance of
  TwentyFourtyEight; if the slide is successful (ie, actually caused a shift on
  the game board), the list of past board grids and the current board will be
  saved and a new random tile will be added to the game board. After each key
  click, the game status will be updated and the grid will be repainted. Grid
  also contains three functions for updating the game state: saved(), reset(),
  and undo(); these three functions are nearly identical, except that they each
  their respective analogous function on the TwentyFourtyEight. These functions
  modify the game board by either loading in the saved board, resetting the board
  to a new random initial state, or by undoing the previous move(s).

  4) Tile is used to create the individual colorful tiles of the 2048 game board.
  The Tile constructor takes in a value for the height, width, and value of the
  tile. This class defines all the custom colors used for each distinct tile and also
  contains a function called linkTileValueAndColor, which is responsible for linking
  the numeric value of a tile to the designated custom color. Tile also contains
  the draw() function for drawing tiles, which is called in the paintComponent()
  function in Grid.

  5) TwentyFourtyEight.java is the primary class and handles most the actual
  "action," so to speak. This file contains all the slide functions for shifting
  the values in the 2D array, as well as all the methods that handle reading in
  and out of the file. Additionally, it contains methods that allow for
  checking if the game is over.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

    One of the more significant stumbling blocks that I encountered was that there
  was an unfortunate bit of confusion with the requirements for the implementation
  of the "Undo" button; a few days before the project was due, I was at office hours
  to ask for some help with an issue I was having with the undo button, and the TA
  told me that if the user clicked out and back into the game (ie, calling the saved()
  function), the undo button did not need to be able to account for the previous game
  boards. However, after visiting office hours again the day before the project was due,
  I was informed by some of the other TAs that this was mistaken information. This severely
  impacted how I had implemented my File I/O, which caused a major time crunch and forced
  me to design a sizable part of my project.

    Another area that I initially had a lot of trouble with was the 2048 logic itself.
  Although I figured out how to design the actual game board pretty early on, as I was
  debugging at the end, I came across two bugs. The first was an issue with how my tiles
  merged when the slide function was called; this is very clearly demonstrated in my test
  file. The issue was that although, for example, if the slideRight() was called on a row
  [2,2,4,0], it would return the intended row [0,0,4,4]. However, when slideRight() was
  called on [4,2,2,0], it would return [0,0,0,8], instead of [0,0,4,4]. After thinking about
  this issue for a while and playing around with my tests and the actual slide functions, I
  decided to use a boolean[] merged to prevent a double-merge by signalling if a tile had
  previously been merged. This solution worked, but initially presented a new issue, as
  since I had placed the initial array outside the nested for-loop, it was inadvertently
  preventing tiles in the same column but different rows from merging (when its original
  purpose was to prevent double merges within the same row). To fix this, I moved merged
  inside the first for-loop but outside the second one, so that the array would be reset
  to its default value ([false, false, false, false]) for each new row.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think that my design has solid separation of functionality, as the separation of
  the classes provides a logical and organized code. The private state is well encapsulated,
  which also adds to the positives of the design. Given the chance to refactor, I would probably
  add a separate class for handling the functions related to reading in and out of the file.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  External Resource #1: Oracle Java Docs
  In addition to using the provided class materials (specifically the class slides, in-class codes,
  online textbook/notes, and recitation slides), I also heavily utilized the Oracle Java Documentation
  website to help determine the best data structures and to find relevant helper functions for my
  implementation.

  External Resource #2: My own past homeworks (specifically HW8, Twitterbot)
  Additionally, I re-used some of the code that I wrote and many of the concepts that I learned from
  past homeworks. This is most notably seen in my implementation of the File I/O feature, which used
  HW8 (Twitterbot) as a jumping off point for building the customized functions that I used in this
  program to write in and read out of the CSV file storing the game board. ** There is a post on Ed
  from a TA confirming that it was alright for us to reuse code from past assignments and cite it
  in our README **

  External Resource #3: The real 2048 game
  In preparation for and while working on this project, I played the actual 2048 game at play2048.co
  many, many times. Even though I had played 2048 casually in the past, playing the game many times
  over and paying specific attention to how the game functions, how the tiles update, how the moves
  work, how the score updates, and how the game reaches its end state (all from the user point of
  view) helped me gain an initial starting point for which features I would need to implement and
  also what I would want the aesthetics of my user interface to look like to improve the user's
  experience; one specific example of this is using a coordinated color scheme for the tiles and
  updating the tile color based on the tile's numeric value.

  External Resource #4: Ed board postings
  Although I am not sure if this also counts as an external resource, I found it very helpful to read
  through all the postings on Ed, including both my own questions and others' questions, as well as the
  TA responses. In addition to helping me get answers to my own specific questions, it also prompted me
  to consider issues/concerns that I hadn't necessarily encountered in my own program, but that others
  were facing in their implementations. This helped me form a more fully developed view of my own code
  and also reminded me to consider things that I perhaps wouldn't have otherwise thought about.