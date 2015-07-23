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

public class BoardView
    extends JPanel implements ViewInterface{
  protected Image ballgif;
  protected Image mangif;
  public char thechar;
  /*public BoardView() {
  }*/

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
  protected int cellSize = 20;
  protected Color wallColor = Color.black;
  protected Color shadedColor = Color.gray;
  protected Color blankColor = Color.white;


  int modelx = 0, modely = 0;


  public BoardView(BoardModel newModel) {
    model = newModel;
    myToolkit = Toolkit.getDefaultToolkit();
    model.AddView(this);
    addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        ( (JPanel) e.getSource()).requestFocus();
      }

      public void mouseClicked(MouseEvent e) {
        Coord cell = new Coord();
        cell = findCell(e.getX(), e.getY());
        if (mouseMan(cell.getX(), cell.getY())) {
          //repaint();
        //The mouse has successfully moved, the moves
        //are theoretically incremented and characters are moved
        }
        else {
        //The mouse click was not in a good spot
        //so we just sit like an asshole
        }
      }
    });
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_LEFT || KeyCode == KeyEvent.VK_H) {
          moveMan("left");
//          repaint();
        }
        else
        if (KeyCode == KeyEvent.VK_RIGHT || KeyCode == KeyEvent.VK_L) {
          moveMan("right");
//          repaint();
        }
        if (KeyCode == KeyEvent.VK_UP || KeyCode == KeyEvent.VK_K) {
          moveMan("up");
//          repaint();
        }
        else
        if (KeyCode == KeyEvent.VK_DOWN || KeyCode == KeyEvent.VK_J) {
          moveMan("down");
//          repaint();
        }
        if (KeyCode == KeyEvent.VK_U) {
          //undo();
//          repaint();
        }
      }
    });
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
// g.drawString(Integer.toString(model.getCell(0, 0)), 0, 15);
    int X = 0;
    int Y = 0;
    int CellWidth = cellSize;
    int CellHeight = cellSize;
    int numRows = model.getRowCount();
    int numCols = model.getColCount();
    for (int i = 0; i < numRows; i++) { //NOTE Y is independant
      for (int j = 0; j < numCols; j++) {
        char cell = model.getCell(j, i);
        if (cell == ball) {
          /* Load up the ball GIF */
          /* I believe the GIF is non-transparent so */
          /* we won't actually have to draw a background here */
          ballgif = new ImageIcon("ball.gif").getImage();
          g.drawImage(ballgif, j * CellWidth, i * CellHeight, null);
          ( (Graphics2D) g).drawImage(ballgif, j * CellWidth, i * CellHeight, CellWidth, CellHeight, Color.white, null);
        }
        else if (cell == man) {
          /* Load up the man GIF, same as above */
          mangif = new ImageIcon("character.gif").getImage();
          ( (Graphics2D) g).drawImage(mangif, j * CellWidth, i * CellHeight, CellWidth, CellHeight, Color.white, null);
        }
        else if (cell == wall) {
          g.setColor(wallColor);
          g.fillRect(j * CellWidth, i * CellHeight, CellWidth, CellHeight);
          int slicesize = 4;
          drawSlices(j, i, g, cellSize, cellSize, slicesize);
        }
        else if (cell == zone) {
          g.setColor(Color.orange);
          g.fillRect(j * CellWidth, i * CellHeight, CellWidth, CellHeight);
        }
        else if (cell == maninzone) {
          /* Load up the man GIF, same as above */
          mangif = new ImageIcon("character.gif").getImage();
          ( (Graphics2D) g).drawImage(mangif, j * CellWidth, i * CellHeight, CellWidth, CellHeight, Color.white, null);
        }
        else if (cell == ballinzone) {
          /* Load up the ball GIF */
          /* I believe the GIF is non-transparent so */
          /* we won't actually have to draw a background here */
          ballgif = new ImageIcon("ball.gif").getImage();
          g.drawImage(ballgif, j * CellWidth, i * CellHeight, null);
          ( (Graphics2D) g).drawImage(ballgif, j * CellWidth, i * CellHeight, CellWidth, CellHeight, Color.white, null);
        }
        else {
          g.setColor(blankColor);
          /* Easy background draw */
          g.fillRect(j * CellWidth, i * CellHeight, CellWidth, CellHeight);
        }
      }
    }
    /*
     g.fillRect(0, 0, 30, 30);
     g.setColor(Color.BLUE);
     g.fillRect(30, 0, 30, 30); */
  }

  public void drawSlices(int x, int y, Graphics g, int Width, int Height,
                         int border) {
    //Black background is already drawn
    g.setColor(shadedColor);
    //Deal with right block
    if (model.getCell(x + 1, y) != wall) {
      g.fillRect(x * Width + (Width - border), y * Height, border, Height);
    }
    else if (model.getCell(x, y - 1) == wall) {
      //Deal with upper-right block
      g.fillRect(x * Width + (Width - border), y * Height, border, border);
    }
    //Deal with left block
    if (model.getCell(x - 1, y) != wall) {
      g.fillRect(x * Width, y * Height, border, Height);
    }
    else if (model.getCell(x, y + 1) == wall) {
    //Deal with lower-left block
      g.fillRect(x * Width, y * Height + (Height - border), border, border);
    }
    //Deal with up block
    if (model.getCell(x, y - 1) != wall) {
      g.fillRect(x * Width, y * Height, Width, border);
    }
    else if (model.getCell(x - 1, y) == wall) {
    //Deal with upper-left block
      g.fillRect(x * Width, y * Height, border, border);
    }
    //Deal with down block
    if (model.getCell(x, y + 1) != wall) {
      g.fillRect(x * Width, y * Height + (Height - border), Width, border);
    }
    else if (model.getCell(x + 1, y) == wall) {
    //Deal with lower-right block
      g.fillRect(x * Width + (Width - border), y * Height + (Height - border),
                 border, border);
    }
  }

  public Dimension getPreferredSize() {
    return new Dimension(model.getColCount() * cellSize,
                         model.getRowCount() * cellSize);
  }

  public Coord findCell(int x, int y) {
    Coord foundCoord = new Coord();
    if (x >= 0 || x <= model.getDimensions().getX() * cellSize ||
        y >= 0 || y <= model.getDimensions().getY() * cellSize) {
      foundCoord.setX(x / cellSize);
      foundCoord.setY(y / cellSize);
      return foundCoord;
    }
    else {
      Coord nothing = new Coord();
      return nothing;
    }
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

  public boolean mouseMan(int X, int Y) { //Based on cells, not geometry
    if (model.getCell(X, Y) == open) {
      if (FindPathFromTo(model.getMan().getX(), model.getMan().getY(), X, Y)) {
        //Code to move man
        model.setCell(model.getMan().getX(), model.getMan().getY(), open); //Set man to empty
// lastmanmove.setX(model.getMan().getX());
// lastmanmove.setY(model.getMan().getY());
        model.setCell(X, Y, man); //set new found cell to man
        model.Man.setX(X); //reassign our Man values
        model.Man.setY(Y);
        return true;
      }
    }
    myToolkit.beep();
    return false;
  }

  public boolean FindPathFromTo(int manx, int many, int x, int y) {
    model.setflagCell(manx, many, 1);
    if (manx == x && many == y) {
      return true;
    }
    if (model.getCell(manx, many - 1) == open &&
        model.getflagCell(manx, many - 1) == 0) {
      if (FindPathFromTo(manx, many - 1, x, y)) { //Look up
        model.setflagCell(manx, many, 0);
        return true;
      }
    }
    if (model.getCell(manx, many + 1) == open &&
        model.getflagCell(manx, many + 1) == 0) {
      if (FindPathFromTo(manx, many + 1, x, y)) { //Look down
        model.setflagCell(manx, many, 0);
        return true;
      }
    }
    if (model.getCell(manx - 1, many) == open &&
        model.getflagCell(manx - 1, many) == 0) {
      if (FindPathFromTo(manx - 1, many, x, y)) { //Look left
        model.setflagCell(manx, many, 0);
        return true;
      }
    }
    if (model.getCell(manx + 1, many) == open &&
        model.getflagCell(manx + 1, many) == 0) {
      if (FindPathFromTo(manx + 1, many, x, y)) { //Look right
        model.setflagCell(manx, many, 0);
        return true;
      }
    }
    //Recursive min/max search to find x, y
    model.setflagCell(manx, many, 0);
    return false;
  }
  public void changedCell(int x, int y) {
    repaint(x*cellSize, y*cellSize, cellSize, cellSize);

  }
  public void changedMoves() {

  }
  public void changedPushes() {

  }
  public void changedSize() {
    cellSize = model.getCellSize();
    repaint();
  }
  public void changedColor() {
    wallColor = model.getWallColor();
    shadedColor = model.getShadedColor();
    blankColor = model.getBlankColor();
    repaint();
  }
}

