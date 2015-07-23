import java.util.LinkedList;
import java.util.ListIterator;
import java.io.*;
import java.awt.Color;
import java.util.LinkedList;
import java.util.ListIterator;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EditorModel{
   protected int boardMatrix[][];
   protected Coord BoardDimensions = new Coord();
   protected int cellSize;
   protected LinkedList views = new LinkedList();
   protected Coord Selected = new Coord();
   protected LinkedList edviews = new LinkedList();
   protected Coord EdSelected = new Coord();


   public EditorModel(int cols, int rows) {
     Selected.setX(0);
     Selected.setY(0);
     EdSelected.setX(0);
     EdSelected.setY(0);

     boardMatrix = new int[cols][rows];
     int i, j;
     for(i=0; i<rows; i++) {
       for(j=0; j<cols; j++) {
         boardMatrix[j][i] = ' ';
       }
     }
     BoardDimensions.setX( cols );
     BoardDimensions.setY( rows );
   }



   public EditorModel(String gameBoardName) {
    FileReader inputFile = null;
    BufferedReader fileIn = null;
    LinkedList cells = new LinkedList();
    ListIterator cells_itr;



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

      int i, j;

      cells_itr = cells.listIterator();
      for (i = 0; i < BoardDimensions.getY(); i++) {
        String theline = (String) cells_itr.next();
        for (j = 0; j < theline.length(); j++) {
          boardMatrix[j][i] = theline.charAt(j); //NOTE Y is independant in this crazy world
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

  /*Pulling methods from Boardmodel*/
  public int getRowCount() { //Get Y count
  int Y = BoardDimensions.getY();
  return Y;
}
public int getColCount() { //Get X Count
    int X = BoardDimensions.getX();
    return X;
  }
  public char getCell(int x, int y) { //What is the ascii char stuck in this cell
    if (x < getColCount() && y < getRowCount() && x >= 0 && y >= 0) {
      return (char) boardMatrix[x][y];
    }
    return '!';
  }
  public void setSelected(int x, int y) {
    int oldx = Selected.getX();
    int oldy = Selected.getY();
    Selected.setX(x);
    Selected.setY(y);

    int xbound, ybound, height, width;
    //get box
    if(x < oldx) {
      xbound = x;
      width = oldx + cellSize - x;
    } else {
      xbound = oldx;
      width = x + cellSize - oldx;
    }
    if(y < oldy) {
      ybound = y;
      height = oldy + cellSize - y;
    } else {
      ybound = oldy;
      height = y + cellSize - oldy;
    }

    notifySelected(xbound, ybound, width, height);
  }

  public void AddView(EditorViewInterface View) {
    edviews.add(View);
  }
  public void notifySelected(int x, int y, int z, int a) {
        ListIterator views_itr;
        views_itr = edviews.listIterator();
        while(views_itr.hasNext()) {
          EditorViewInterface theview = (EditorViewInterface) views_itr.next();
          theview.changeSelected(x, y, z, a);
        }
  }
  public void addColumn() {
    int  newBoard [][] = new int [BoardDimensions.getX() + 1]
                                 [BoardDimensions.getY()];

    for (int i = 0, k = 0; i < boardMatrix[0].length; i++, k++)
          for (int j = 0, m = 0; j < boardMatrix.length; j++, m++) {
            if(j == Selected.getX()) {
              newBoard[m][k] = boardMatrix[j][i];
              if(boardMatrix[j][i] == '#')
                newBoard[++m][k] = '#';
              else
                newBoard[++m][k] = ' ';

            }
            else {
              newBoard[m][k] = boardMatrix[j][i];
            }
          }
    boardMatrix = newBoard;
    BoardDimensions.setX(BoardDimensions.getX() + 1);
    notifyBoardChange();
  }

  public void addRow() {
    int newBoard[][] = new int[BoardDimensions.getX()]
        [BoardDimensions.getY() + 1];

    for (int j = 0, m = 0; j < boardMatrix.length; j++, m++)
      for (int i = 0, k = 0; i < boardMatrix[0].length; i++, k++) {
        if (i == Selected.getY()) {
          newBoard[m][k] = boardMatrix[j][i];
          if(boardMatrix[j][i] == '#')
            newBoard[m][++k] = '#';
          else
            newBoard[m][++k] = ' ';
        }
        else {
          newBoard[m][k] = boardMatrix[j][i];
        }
      }
    boardMatrix = newBoard;
    BoardDimensions.setY(BoardDimensions.getY() + 1);
    notifyBoardChange();

  }
  public void remColumn() {
    int  newBoard [][] = new int [BoardDimensions.getX() - 1]
                                 [BoardDimensions.getY()];

    for (int i = 0, k = 0; i < boardMatrix[0].length; i++, k++)
          for (int j = 0, m = 0; j < boardMatrix.length; j++, m++) {
            if(j == Selected.getX()) {
              m--;
            }
            else {
              newBoard[m][k] = boardMatrix[j][i];
            }
          }
    boardMatrix = newBoard;
    BoardDimensions.setX(BoardDimensions.getX() - 1);

    if(Selected.getX() >= BoardDimensions.getX() )
      Selected.setX(Selected.getY() - 1);
    notifyBoardChange();
  }

  public void remRow() {
    int newBoard[][] = new int[BoardDimensions.getX()]
        [BoardDimensions.getY() - 1];

    for (int j = 0, m = 0; j < boardMatrix.length; j++, m++)
      for (int i = 0, k = 0; i < boardMatrix[0].length; i++, k++) {
        if (i == Selected.getY()) {
          k--;
        }
        else {
          newBoard[m][k] = boardMatrix[j][i];
        }
      }
    boardMatrix = newBoard;
    BoardDimensions.setY(BoardDimensions.getY() - 1);
    if(Selected.getY() >= BoardDimensions.getY() )
      Selected.setY(Selected.getY() - 1);
    notifyBoardChange();
  }

  public void addRowAbove() {
    int newBoard[][] = new int[BoardDimensions.getX()]
        [BoardDimensions.getY() + 1];

    for (int j = 0, m = 0; j < boardMatrix.length; j++, m++)
      for (int i = boardMatrix[0].length - 1, k = boardMatrix[0].length; i >= 0; i--, k--) {
        if (i == Selected.getY()) {
          newBoard[m][k] = boardMatrix[j][i];
          if(boardMatrix[j][i] == '#')
            newBoard[m][--k] = '#';
          else
            newBoard[m][--k] = ' ';
        }
        else {
          newBoard[m][k] = boardMatrix[j][i];
        }
      }
    boardMatrix = newBoard;
    BoardDimensions.setY(BoardDimensions.getY() + 1);
    Selected.setY(Selected.getY() + 1);
    notifyBoardChange();

  }
  public void addColumnAbove() {
    int  newBoard [][] = new int [BoardDimensions.getX() + 1]
                                 [BoardDimensions.getY()];

    for (int i = 0, k = 0; i < boardMatrix[0].length; i++, k++)
          for (int j = boardMatrix.length - 1, m = boardMatrix.length; j >= 0; j--, m--) {
            if(j == Selected.getX()) {
              newBoard[m][k] = boardMatrix[j][i];
              if(boardMatrix[j][i] == '#')
                newBoard[--m][k] = '#';
              else
                newBoard[--m][k] = ' ';


            }
            else {
              newBoard[m][k] = boardMatrix[j][i];
            }
          }
    boardMatrix = newBoard;
    BoardDimensions.setX(BoardDimensions.getX() + 1);
    Selected.setX(Selected.getX() + 1);
    notifyBoardChange();
  }


  public Coord getSelected() {
    return Selected;
  }
  public int[][] getBoard() {
    return boardMatrix;
  }
  public void setCell(char cur_selected) {
    boardMatrix[Selected.getX()][Selected.getY()] = cur_selected;
    notifyBoardChange(Selected.getX(), Selected.getY());
  }
  public void setCell(int x, int y, int z) {
    boardMatrix[x][y] = z;
    notifyBoardChange(x, y);
  }
  public void notifyBoardChange() {
        ListIterator views_itr;
        views_itr = edviews.listIterator();

        while(views_itr.hasNext()) {
          EditorViewInterface theview = (EditorViewInterface) views_itr.next();
          theview.changedBoard();
        }
  }
  public void notifyBoardChange(int x, int y) {
      ListIterator views_itr;
      views_itr = edviews.listIterator();

      while(views_itr.hasNext()) {
        EditorViewInterface theview = (EditorViewInterface) views_itr.next();
        theview.changedEdCell(x, y);
      }
}



}
