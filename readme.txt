Oliver Thistlethwaite
Joseph Vannucci
CS 494 Lab 5 Sokoban Editor readme


Graphical Presentation    

    Textual view of game properly presented--gameboard and   implemented
      score are both present    
    Gameboard editor initially appears with all required     implemented    
      tool and command pallettes
    Gameboard editor correctly displays a loaded gameboard   implemented

Interaction

    Textual view properly moves character in response        implemented
  to key strokes
    Both views updated when the character is moved in        implemented
  either view

    Graphical game view enhancements

  1. size of gameboard squares can be altered                implemented
    correctly
  2. color of different gameboard pieces can                 implemented
    be altered correctly

    File area of gameboard editor
  
  1. "new" button pops up a dialog box with                  implemented
    sliders  for rows and columns and
    then correctly displays a gameboard
    area
  2. "save" button saves an existing gameboard               implemented
    or prompts for a filename for a new
    gameboard and then saves the gameboard
  3. "save as" buttons prompts for a filename                implemented
    saves the gameboard
  4. "load" button uses Java's file dialog                   implemented
    widget to prompt for and load a
    gameboard
  5. "quit" button closes all gameboard editors              implemented
    but does not exit the game

    New column/row widget

  1. buttons are laid out as specified in lab                implemented
    writeup  
  2. buttons correctly add a row or column                   implemented
    as specified in lab writeup
  3. wall pieces are properly added to                       implemented
    prevent disconnected walls

    Delete row/column buttons correctly delete the           implemented
  specified row or column

    Drawing area of gameboard editor
    
        1. The user can create a new gamepiece by            implemented
             selecting a gamepiece from the tool bar 
             and then, with the right mouse button, 
             selecting squares in the gameboard on which 
             to place that gamepiece. 

        2. If the user presses down the right mouse          implemented
             button and drags, then all squares traversed 
             by the mouse cursor have that gamepiece 
             placed in them. 
 
        3. The user can delete a gamepiece by selecting      implemented
             it with the middle mouse button. The square 
             previously occupied by the gamepiece becomes 
             a blank square. 

        4. If the user presses the middle mouse button       not implemented
             and drags, all gamepieces traversed by the 
             mouse cursor are deleted. 

        5. The user can drag a gamepiece from one square     implemented
             to another square with the left mouse button. 

       6. The user can select a square in the                implemented
             gameboard with the left mouse button. 
             The selected square is highlighted. 

Code Style: Good object-oriented style and use of the     
              MVC model notify methods


Small Issues: While dragging all objects will appear to be selected until next click.  

Incremental redraws done when possible

