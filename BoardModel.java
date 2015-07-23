import java.util.LinkedList;
import java.util.ListIterator;
import java.io.*;
import java.awt.Color;


/**
 * <p>CS494 Lab4: Sokoban/p>
 * <p>Description: Sokoban Game </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Joeseph Vannucci and Oliver Thistlethwaite
 * @version 1.0
 */

public class BoardModel {
  protected int boardMatrix[][], flagMatrix[][];
  protected Coord BoardDimensions = new Coord();
  protected Coord Man = new Coord();
  protected int currPushes;
  protected int currMoves;
  protected int cellSize;
  protected Color wallColor = Color.black;
  protected Color shadedColor = Color.gray;
  protected Color blankColor = Color.white;
  protected LinkedList views = new LinkedList();

  public BoardModel() {
    BoardDimensions.setY(0);
    BoardDimensions.setX(0);
    Man.setX(0);
    Man.setY(0);
    currPushes = 0;
    currMoves = 0;
  }
  public int getPushes() {
    return currPushes;
  }

  public int getMoves() {
    return currMoves;
  }

  public int incPushes() {
    currPushes++;
    notifyPushChange();
    return currPushes;
  }

  public int incMoves() {
    currMoves++;
    notifyMoveChange();
    return currMoves;
  }

  public Coord getMan() {
    return Man;
  }
  public int getCellSize() {
    return cellSize;
  }
  public Color getWallColor() {
    return wallColor;
  }
  public Color getShadedColor() {
    return shadedColor;
  }
  public Color getBlankColor() {
    return blankColor;
  }



  public BoardModel(String gameBoardName) {
    FileReader inputFile = null;
    BufferedReader fileIn = null;
    LinkedList cells = new LinkedList();
    ListIterator cells_itr;
    currPushes = 0;
    currMoves = 0;

    try {
      String input;
      inputFile = new FileReader(gameBoardName); // load in the input maze
      fileIn = new BufferedReader(inputFile);
      for (int i = 0; (input = fileIn.readLine()) != null; i++) {
        if (input.length() > BoardDimensions.getX()) {
          BoardDimensions.setX(input.length());
        }
        cells.add(input);
      }
      BoardDimensions.setY(cells.size());
      boardMatrix = new int[BoardDimensions.getX()][BoardDimensions.getY()];
      flagMatrix = new int[BoardDimensions.getX()][BoardDimensions.getY()];
      int i, j;
      for (i = 0; i < BoardDimensions.getY(); i++) {
        for (j = 0; j < BoardDimensions.getX(); j++) {
          flagMatrix[j][i] = 0;
        }
      }
      cells_itr = cells.listIterator();
      for (i = 0; i < BoardDimensions.getY(); i++) {
        String theline = (String) cells_itr.next();
        for (j = 0; j < theline.length(); j++) {
          boardMatrix[j][i] = theline.charAt(j); //NOTE Y is independant in this crazy world
          if (boardMatrix[j][i] == '@') { //Man
            Man.setX(j);
            Man.setY(i); //We found the man WOOO!!!! poke
          }
        }
        while (j < BoardDimensions.getX()) { // Add blank cells to make all lines maximum length
          boardMatrix[j][i] = ' ';
          j++;
        }
      }
    }
    catch (FileNotFoundException e) { // file doesn't exist
      System.err.println("Error: file does not exist!");
    }
    catch (Exception e) {
    }
  }

  public Coord getDimensions() {
    return BoardDimensions;
  }

  public int getColCount() { //Get X Count
    int X = BoardDimensions.getX();
    return X;
  }

  public int getRowCount() { //Get Y count
    int Y = BoardDimensions.getY();
    return Y;
  }

  public char getCell(int x, int y) { //What is the ascii char stuck in this cell
    if (x < getColCount() && y < getRowCount() && x >= 0 && y >= 0) {
      return (char) boardMatrix[x][y];
    }
    return '!';
  }

  public boolean setCell(int x, int y, char z) { //Z is #defined ascii char code
    if (0 <= x && x < getColCount() && 0 <= y && y < getRowCount()) {
      boardMatrix[x][y] = (int) z;
      notifyCellChange(x, y);
      return true;
    }
    return false;
  }

  public int getflagCell(int x, int y) {
    return flagMatrix[x][y];
  }

  public void setflagCell(int x, int y, int z) {
    flagMatrix[x][y] = z;
  }
  public void AddView(ViewInterface View) {
    views.add(View);
  }
  public void notifyCellChange(int x, int y) {
        ListIterator views_itr;
        views_itr = views.listIterator();

        while(views_itr.hasNext()) {
          ViewInterface theview = (ViewInterface) views_itr.next();
          theview.changedCell(x, y);
        }
  }
  public void notifyMoveChange() {
        ListIterator views_itr;
        views_itr = views.listIterator();

        while(views_itr.hasNext()) {
          ViewInterface theview = (ViewInterface) views_itr.next();
          theview.changedMoves();
        }

  }
  public void notifyPushChange() {
        ListIterator views_itr;
        views_itr = views.listIterator();

        while(views_itr.hasNext()) {
          ViewInterface theview = (ViewInterface) views_itr.next();
          theview.changedPushes();
        }
  }
  public void notifyBoardSizeChange(int theCellSize) {
        cellSize = theCellSize;
        ListIterator views_itr;
        views_itr = views.listIterator();

        while(views_itr.hasNext()) {
          ViewInterface theview = (ViewInterface) views_itr.next();
          theview.changedSize();
        }
  }
  public void notifyBoardColorChange(String thetype, Color theColor) {
        if(thetype == "wall") wallColor = theColor;
        else if(thetype == "shaded") shadedColor = theColor;
        else if(thetype == "blank") blankColor = theColor;

        ListIterator views_itr;
        views_itr = views.listIterator();

        while(views_itr.hasNext()) {
          ViewInterface theview = (ViewInterface) views_itr.next();
          theview.changedColor();
        }
  }

}