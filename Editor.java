import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Editor extends JFrame {
  CommandBar commBar;
  RmColRow colBar;
  EditorModel themodel;
  EditorView drawSpace;
  AddColRow Adder;
  String boardName;

  public Editor(int boardWidth, int boardHeight) {
    super("Sokoban Editor");
    getContentPane().setLayout(new BorderLayout());
    createNewBoard(boardWidth, boardHeight);
  }
  public void makeDisplay() {
    commBar = new CommandBar(this);
 //   toolBar = new Toolbar();
    colBar = new RmColRow(this);
    drawSpace = new EditorView(themodel);
    Adder = new AddColRow(this);

    doDisplay();
   /* themodel.AddView(drawSpace);
    themodel.AddView(colBar);
    themodel.AddView(Adder); */  //Added in constructors for each

  }
  public void doDisplay() {
     JPanel cont3 = new JPanel();
     cont3.add(drawSpace, BorderLayout.CENTER);

     JPanel cont4 = new JPanel();
     cont4.setLayout(new BoxLayout(cont4, BoxLayout.Y_AXIS));
     cont4.add(colBar);
     cont4.add(Adder);
   //  cont4.add(toolBar);

     cont3.add(cont4, BorderLayout.WEST);

     Container ContentPane = getContentPane();
     ContentPane.removeAll();
     ContentPane.add(drawSpace, BorderLayout.CENTER);
     ContentPane.add(commBar, BorderLayout.NORTH);
     ContentPane.add(cont4, BorderLayout.WEST);

     pack();
     show();

  }

  public void loadBoard() {
    FileDialog loadfile = new FileDialog(this, "Open", FileDialog.LOAD);
    loadfile.show(); // brings up the load file dialog box
    boardName = loadfile.getFile();

    if(boardName != null) {
      themodel = new EditorModel(boardName);
      makeDisplay();
    }
  }
  public void createNewBoard(int width, int height) {
    themodel = new EditorModel(width, height);
    makeDisplay();
  }
  public void saveBoard() {

    if(boardName != null) {
      FileWriter outputFile;
      BufferedWriter fileOut = null;
      try {
        outputFile = new FileWriter(boardName);
        fileOut = new BufferedWriter(outputFile);
        int board[][] = themodel.getBoard();
        for (int i = 0; i < board[0].length; i++) {
          for (int j = 0; j < board.length; j++) {

            fileOut.write(board[j][i]);
          }
          fileOut.newLine();
        }
      }
      catch (Exception e) {
        System.err.println(e);
      }
      finally {
        try {
          if (fileOut != null) {
            fileOut.close();
          }
        }
        catch (IOException e) {
          System.err.println(e);
        }
      }
    }
    else // we want to ask for a filename to save as if none exists
      saveBoardAs();
  }

  public void saveBoardAs() { // Save board as some file name
    FileDialog savefileas = new FileDialog(this, "Save As", FileDialog.SAVE);
    savefileas.show();
    String saveName = savefileas.getFile();

    if (saveName != null) {
      String temp = boardName;
      boardName = saveName;
      saveBoard();
      boardName = temp;
    }
  }
  public EditorModel getModel() {
    return themodel;
  }
}
