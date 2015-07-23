import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>CS494 Lab4: Sokoban/p>
 * <p>Description: Sokoban Game </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Joeseph Vannucci and Oliver Thistlethwaite
 * @version 1.0
 */

public class TextBoardView
    extends JPanel implements ViewInterface{

  Toolkit myToolkit;
  public String up = "up";
  public String down = "down";
  public String left = "left";
  public String right = "right";
  public char wall = '#';
  public char ball = '$';
  public char man = '@';
  public char zone = '.';
  public char open = ' ';
  public char maninzone = 'O';
  public char ballinzone = 'J';
  public Coord lastmanmove, lastballmove;
  protected BoardModel model;
  protected int cellWidth;
  protected int cellHeight;

  int modelx = 0, modely = 0;


  public TextBoardView(BoardModel newModel) {
    model = newModel;
    myToolkit = Toolkit.getDefaultToolkit();
    model.AddView(this);

    repaint();



    addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        ( (JPanel) e.getSource()).requestFocus();
      }
    });
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_LEFT || KeyCode == KeyEvent.VK_H) {
          moveMan("left");
        }
        else
        if (KeyCode == KeyEvent.VK_RIGHT || KeyCode == KeyEvent.VK_L) {
          moveMan("right");
        }
        if (KeyCode == KeyEvent.VK_UP || KeyCode == KeyEvent.VK_K) {
          moveMan("up");
        }
        else
        if (KeyCode == KeyEvent.VK_DOWN || KeyCode == KeyEvent.VK_J) {
          moveMan("down");
        }
        if (KeyCode == KeyEvent.VK_U) {
        }
      }
    });
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
// g.drawString(Integer.toString(model.getCell(0, 0)), 0, 15);
    int X = 0;
    int Y = 0;
  //  int CellWidth = cellSize;
  //  int CellHeight = cellSize;
    int numRows = model.getRowCount();
    int numCols = model.getColCount();

    g.setColor(Color.black);
    setBackground(Color.white);
    g.setFont(new Font("Monospaced", Font.PLAIN, 10));

    FontMetrics fm = g.getFontMetrics();
    cellWidth = fm.charWidth('a');
    cellHeight = fm.getHeight();

    for (int i = 0; i < numRows; i++) { //NOTE Y is independant
      String therow = "";
      for (int j = 0; j < numCols; j++) {
         char thecell = model.getCell(j, i);
         if(thecell == maninzone)
           therow+= man;
         else if(thecell == ballinzone)
           therow+= ball;
         else
           therow+= thecell;

         g.drawString(therow, 0, i * fm.getHeight() + fm.getHeight() );
      }
    }
  }
  public Dimension getPreferredSize() {
    return new Dimension(model.getColCount() * 6, model.getRowCount() * 14);
  }
  public boolean moveMan(String direction) {
    //check for a valid direction
    if (direction != up && direction != down &&
        direction != left && direction != right) {
      myToolkit.beep();
      return false;
    }

    if (direction == up) {
      if (model.getMan().getY() == 0) {
        myToolkit.beep();
        /*Sorry, out of bounds*/
        return false;
      }
      if (model.getCell(model.getMan().getX(), model.getMan().getY() - 1) ==
          open) { //If the move is open
        addMan(model.getMan().getX(), model.getMan().getY() - 1); // in the matrix
        model.incMoves(); //and increment Moves
        //lastmanmove.setX(model.getMan().getX());
        //lastmanmove.setY(model.getMan().getY());
        model.getMan().setY(model.getMan().getY() - 1);
        return true;
      }
      else if (model.getCell(model.getMan().getX(), model.getMan().getY() - 1) ==
               ball) { //If it's a ball
        if (model.getCell(model.getMan().getX(), model.getMan().getY() - 2) == open ||
            model.getCell(model.getMan().getX(), model.getMan().getY() - 2) == zone ) { //and the next cell is open
          addBall(model.getMan().getX(), model.getMan().getY() - 2); //reassign the three values
          addMan(model.getMan().getX(), model.getMan().getY() - 1);

          model.incPushes(); //increment pushes
          model.incMoves(); //increment moves
//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
          model.getMan().setY(model.getMan().getY() - 1);
          return true;
        }
      }
      else if (model.getCell(model.getMan().getX(), model.getMan().getY() - 1) ==
               zone) { //If the move is open
        addMan(model.getMan().getX(), model.getMan().getY() - 1); // in the matrix
        model.incMoves(); //and increment Moves

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
        model.getMan().setY(model.getMan().getY() - 1);
        return true;
      }
      else if (model.getCell(model.getMan().getX(), model.getMan().getY() - 1) ==
               ballinzone) { //If it's a ball
        if (model.getCell(model.getMan().getX(), model.getMan().getY() - 2) ==
            open || model.getCell(model.getMan().getX(), model.getMan().getY() - 2) ==
            zone) { //and the next cell is open
          addBall(model.getMan().getX(), model.getMan().getY() - 2); //reassign the three values
          addMan(model.getMan().getX(), model.getMan().getY() - 1);

          model.incPushes(); //increment pushes
          model.incMoves(); //increment moves

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
          model.getMan().setY(model.getMan().getY() - 1);
          return true;
        }
      }
    }
    else if (direction == down) {
      if (model.getMan().getY() == model.getRowCount() - 1) {
        /*Sorry, out of bounds*/
        return false;
      }
      if (model.getCell(model.getMan().getX(), model.getMan().getY() + 1) ==
          open) { //Same as above
        addMan(model.getMan().getX(), model.getMan().getY() + 1);
        model.incMoves();
        model.getMan().setY(model.getMan().getY() + 1);
        return true;
      }
      else if (model.getCell(model.getMan().getX(), model.getMan().getY() + 1) ==
               ball || model.getCell(model.getMan().getX(), model.getMan().getY() + 1) ==
               ballinzone) { //Same as above
        if (model.getCell(model.getMan().getX(), model.getMan().getY() + 2) ==
            open || model.getCell(model.getMan().getX(), model.getMan().getY() + 2) ==
            zone) {
          addBall(model.getMan().getX(), model.getMan().getY() + 2);
          addMan(model.getMan().getX(), model.getMan().getY() + 1);
          model.incPushes();
          model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
          model.getMan().setY(model.getMan().getY() + 1);
          return true;
        }
      }
      else if (model.getCell(model.getMan().getX(), model.getMan().getY() + 1) ==
          zone) { //Same as above
        addMan(model.getMan().getX(), model.getMan().getY() + 1);
        model.incMoves();

        model.getMan().setY(model.getMan().getY() + 1);
        return true;
      }
      else if (model.getCell(model.getMan().getX(), model.getMan().getY() + 1) ==
               ballinzone) { //Same as above
        if (model.getCell(model.getMan().getX(), model.getMan().getY() + 2) ==
            open) {
          addBall(model.getMan().getX(), model.getMan().getY() + 2);
          addMan(model.getMan().getX(), model.getMan().getY() + 1);
          model.incPushes();
          model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
          model.getMan().setY(model.getMan().getY() + 1);
          return true;
        }
      }

    }
    else if (direction == left) {
      if (model.getMan().getX() == 0) {
        myToolkit.beep();
        /*Sorry, out of bounds*/return false;
      }
      if (model.getCell(model.getMan().getX() - 1, model.getMan().getY()) ==
          open) { //Same as above
        addMan(model.getMan().getX() - 1, model.getMan().getY());
        model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
        model.getMan().setX(model.getMan().getX() - 1);
        return true;
      }
      else if (model.getCell(model.getMan().getX() - 1, model.getMan().getY()) == ball
            || model.getCell(model.getMan().getX() - 1, model.getMan().getY()) == ballinzone) { //Same as above
        if (model.getCell(model.getMan().getX() - 2, model.getMan().getY()) ==
            open || model.getCell(model.getMan().getX() - 2, model.getMan().getY()) ==
            zone) {
          addBall(model.getMan().getX() - 2, model.getMan().getY());
          addMan(model.getMan().getX() - 1, model.getMan().getY());
          model.incPushes();
          model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
          model.getMan().setX(model.getMan().getX() - 1);
          return true;
        }
      }
      if (model.getCell(model.getMan().getX() - 1, model.getMan().getY()) ==
          zone) { //Same as above
        addMan(model.getMan().getX() - 1, model.getMan().getY());
        model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
        model.getMan().setX(model.getMan().getX() - 1);
        return true;
      }
    }
    else if (direction == right) {
      if (model.getMan().getX() == model.getColCount() - 1) {
        myToolkit.beep();
        /*Sorry, out of bounds*/return false;
      }
      if (model.getCell(model.getMan().getX() + 1, model.getMan().getY()) ==
          open) { //Same as above
        addMan(model.getMan().getX() + 1, model.getMan().getY());
        model.incMoves();
//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
        model.getMan().setX(model.getMan().getX() + 1);
        return true;
      }
      else if (model.getCell(model.getMan().getX() + 1, model.getMan().getY()) ==
               ball || model.getCell(model.getMan().getX() + 1, model.getMan().getY()) ==
               ballinzone) { //Same as above
        if (model.getCell(model.getMan().getX() + 2, model.getMan().getY()) ==
            open || model.getCell(model.getMan().getX() + 2, model.getMan().getY()) ==
            zone) {
          addBall(model.getMan().getX() + 2, model.getMan().getY());
          addMan(model.getMan().getX() + 1, model.getMan().getY());
          model.incPushes();
          model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
          model.getMan().setX(model.getMan().getX() + 1);
          return true;
        }
      }
      if (model.getCell(model.getMan().getX() + 1, model.getMan().getY()) ==
          zone) { //Same as above
        addMan(model.getMan().getX() + 1, model.getMan().getY());
        model.incMoves();

//lastmanmove.setX(model.getMan().getX());
//lastmanmove.setY(model.getMan().getY());
        model.getMan().setX(model.getMan().getX() + 1);
        return true;
      }


    }
    myToolkit.beep();
    return false;
  }

  protected void addMan(int manx, int many) {
    removeMan();
    removeBall(manx, many);
    if (model.getCell(manx, many) ==
        zone) {
      model.setCell(manx, many, maninzone); //reassign the values
    }
    else {
      model.setCell(manx, many, man); //reassign the values
    }

  }

  protected void removeMan() {
    if (model.getCell(model.getMan().getX(), model.getMan().getY()) ==
        maninzone) {
      model.setCell(model.getMan().getX(), model.getMan().getY(), zone); //reassign the values
    }
    else {
      model.setCell(model.getMan().getX(), model.getMan().getY(), open); //reassign the values
    }
  }
  protected void removeBall(int ballx, int bally) {
    if (model.getCell(ballx, bally) == ballinzone) {
      model.setCell(ballx, bally, zone); //reassign the values
    }
    else if (model.getCell(ballx, bally) == ball) {
      model.setCell(ballx, bally, open); //reassign the values
    }
  }
  protected void addBall(int ballx, int bally) {
    if (model.getCell(ballx, bally) ==
        zone) {
      model.setCell(ballx, bally, ballinzone); //reassign the values
    }
    else {
      model.setCell(ballx, bally, ball); //reassign the values
    }

  }
  public void changedCell(int x, int y) {
     repaint(0, y*cellHeight, cellWidth * model.getColCount(), cellHeight * 2);
  }
  public void changedMoves() {

  }
  public void changedPushes() {

  }
  public void changedSize() {

  }
  public void changedColor() {
  }
}

