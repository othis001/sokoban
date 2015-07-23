import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EditorView extends JPanel implements EditorViewInterface {


/* These should definately be in the model */
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
protected EditorModel model;
protected int cellSize = 20;
protected Color wallColor = Color.black;
protected Color shadedColor = Color.gray;
protected Color blankColor = Color.white;
Image ballgif, mangif;
  boolean moving;

  int left_diff, top_diff;
public Coord MovingCell = new Coord();
public char cur_selected = '@';


  public EditorView(EditorModel themodel) {
    model = themodel;
    model.AddView(this);

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int Xcell = x / cellSize;
        int Ycell = y / cellSize;

        if(Xcell < model.getColCount() && Ycell < model.getRowCount() &&
           Xcell >= 0 && Ycell >= 0) {
          if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == (InputEvent.BUTTON3_MASK))
              {
            model.setSelected(x / cellSize, y / cellSize);
            model.setCell(cur_selected);

          }
          if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == (InputEvent.BUTTON1_MASK)) {
            if(moving) {
             MovingCell.setX(x);
             MovingCell.setY(y);
             repaint();           //To facilitate dragging, again no model verification
           }
          }
        }
  }
});


    addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        ( (JPanel) e.getSource()).requestFocus();
      }
      public void mousePressed(MouseEvent e) {
          if((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {

          int x = e.getX();
          int y = e.getY();

          int Xcell = (int) x / cellSize;
          int Ycell = (int) y / cellSize;
          if(Xcell >= 0 && Xcell >= 0) {
            if (Xcell < model.getColCount() && Ycell < model.getRowCount()) {
              moving = true;
              model.setSelected(Xcell, Ycell);
              cur_selected = model.getCell(Xcell, Ycell);
              left_diff = e.getX() - cellSize * (Xcell);
              top_diff = e.getY() - cellSize * (Ycell);
              model.setCell(Xcell, Ycell, ' ');
            }
            else if (Ycell == model.getRowCount() + 1) {
              switch (Xcell) {
                case 1:
                  cur_selected = man;    //Note this if for our
                  repaint();           //the view's record keeping
                                        //So there is no Model verification
                  break;                 //(No notify methods invoked)
                case 3:
                  cur_selected = ball;
                  repaint();
                  break;
                case 5:
                  cur_selected = open;
                  repaint();
                  break;
                case 7:
                  cur_selected = wall;
                  repaint();
                  break;
                case 9:
                  cur_selected = zone;
                  repaint();
                  break;
              }
            }
          }
        }
        else if((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {

          int x = e.getX();
          int y = e.getY();

          int Xcell = (int) x / cellSize;
          int Ycell = (int) y / cellSize;

          if (Xcell < model.getColCount() && Ycell < model.getRowCount()) {
            model.setSelected(Xcell, Ycell);
            model.setCell(cur_selected);
          }
          model.setCell(cur_selected);
        }
        else  {
          int x = e.getX();
          int y = e.getY();

          int Xcell = (int) x / cellSize;
          int Ycell = (int) y / cellSize;

          if (Xcell < model.getColCount() && Ycell < model.getRowCount()) {
            model.setSelected(Xcell, Ycell);
            model.setCell(cur_selected);
          }
          model.setCell(' ');
        }

      }
      public void mouseReleased(MouseEvent e) {
        int x = e.getX() - left_diff;
        int y = e.getY() - top_diff;
        int XCell = x / cellSize;
        int YCell = y / cellSize;

        if(moving && XCell < model.getColCount() && XCell >= 0
           && YCell < model.getRowCount() && YCell >=0) {

          model.setCell(XCell, YCell, cur_selected);
          repaint();
        }
        moving = false;
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
    Coord Selected = model.getSelected();
    int selx = Selected.getX();
    int sely = Selected.getY();

    for (int i = 0; i < numRows; i++) { //NOTE Y is independant
      for (int j = 0; j < numCols; j++) {
        char cell = model.getCell(j, i);
        if (cell == ball) {
          /* Load up the ball GIF */
          /* I believe the GIF is non-transparent so */
          /* we won't actually have to draw a background here */
          ballgif = new ImageIcon("ball.gif").getImage();
          g.drawImage(ballgif, j * CellWidth, i * CellHeight, null);
          ( (Graphics2D) g).drawImage(ballgif, j * CellWidth, i * CellHeight,
                                      CellWidth, CellHeight, Color.white, null);

        }
        else if (cell == man) {
          /* Load up the man GIF, same as above */
          mangif = new ImageIcon("character.gif").getImage();
          ( (Graphics2D) g).drawImage(mangif, j * CellWidth, i * CellHeight,
                                      CellWidth, CellHeight, Color.white, null);


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
          ( (Graphics2D) g).drawImage(mangif, j * CellWidth, i * CellHeight, null);


        }
        else if (cell == ballinzone) {
          /* Load up the ball GIF */
          /* I believe the GIF is non-transparent so */
          /* we won't actually have to draw a background here */
          ballgif = new ImageIcon("ball.gif").getImage();
          g.drawImage(ballgif, j * CellWidth, i * CellHeight, null);
          ( (Graphics2D) g).drawImage(ballgif, j * CellWidth, i * CellHeight, null);

        }
        else {
          g.setColor(blankColor);
          /* Easy background draw */
          g.fillRect(j * CellWidth, i * CellHeight, CellWidth, CellHeight);
                  }

      }
    }
    if(moving) {
      switch (cur_selected) {
        case '$':
          ballgif = new ImageIcon("ball.gif").getImage();
      //  g.drawImage(ballgif, MovingCell.getX(), MovingCell.getY(), null);
          ( (Graphics2D) g).drawImage(ballgif, MovingCell.getX() - left_diff, MovingCell.getY() - top_diff,
                            CellWidth, CellHeight, Color.white, null);
          break;
       case '#':
          g.setColor(wallColor);
          g.fillRect(MovingCell.getX() - left_diff, MovingCell.getY() - top_diff, CellWidth, CellHeight);
          int slicesize = 4;
          break;
       case '.':
          g.setColor(Color.orange);
          g.fillRect(MovingCell.getX() - left_diff, MovingCell.getY() - top_diff, CellWidth, CellHeight);
          break;
       case '@':
          mangif = new ImageIcon("character.gif").getImage();
          ( (Graphics2D) g).drawImage(mangif, MovingCell.getX() - left_diff, MovingCell.getY() - top_diff, null);
           break;
       case ' ':
         g.setColor(Color.white);
         g.fillRect(MovingCell.getX() - left_diff, MovingCell.getY() - top_diff, CellWidth, CellHeight);
         break;
      }


  }


    g.setColor(Color.black);
    for (int k = 0; k < model.getColCount(); k++) {
      g.drawLine(k * CellWidth, 0, k * CellWidth,
                 model.getRowCount() * cellSize);
    }
    for (int k = 0; k < model.getRowCount(); k++) {
      g.drawLine(0, k * CellHeight, model.getColCount() * CellHeight,
                 k * CellHeight);
    }


    g.setColor(Color.black);
    mangif = new ImageIcon("character.gif").getImage();
    g.drawImage(mangif, CellWidth, numRows * CellHeight + CellHeight,
                CellWidth, CellHeight, Color.white, null);
    g.drawString("Man", CellWidth, numRows * CellHeight + CellHeight * 3);

    ballgif = new ImageIcon("ball.gif").getImage();
    g.drawImage(ballgif, CellWidth * 3, numRows * CellHeight + CellHeight,
                CellWidth, CellHeight, Color.white, null);
    g.drawString("Ball", CellWidth * 3, numRows * CellHeight + CellHeight * 3);

    g.setColor(blankColor);
    g.fillRect(CellWidth * 5, numRows * CellHeight + CellHeight, CellWidth, CellHeight);
    g.setColor(Color.black);
    g.drawString("Empty", CellWidth * 5, numRows * CellHeight + CellHeight * 3);

    g.setColor(wallColor);
    g.fillRect(CellWidth * 7, numRows * CellHeight + CellHeight, CellWidth, CellHeight);
    g.setColor(Color.black);
    g.drawString("Wall", CellWidth * 7, numRows * CellHeight + CellHeight * 3);

    g.setColor(Color.orange);
    g.fillRect(CellWidth * 9, numRows * CellHeight + CellHeight, CellWidth, CellHeight);
    g.setColor(Color.black);
    g.drawString("Zone", CellWidth * 9, numRows * CellHeight + CellHeight * 3);


    g.setColor(Color.black);

    Coord wha = model.getSelected();
    selx = wha.getX();
    sely = wha.getY();
    highlight(g, selx, sely);
     switch(cur_selected) {
      case '@':
        highlight(g, 1, numRows + 1);
        break;
      case '$':
        highlight(g, 3, numRows + 1);
        break;
      case ' ':
        highlight(g, 5, numRows + 1);
        break;
      case '#':
        highlight(g, 7, numRows + 1);
        break;
      case '.':
        highlight(g, 9, numRows + 1);
        break;
    }

  }

  public void highlight(Graphics g, int selx, int sely) {
    g.drawRect(selx * cellSize + 1, sely * cellSize + 1, cellSize - 2,
               cellSize - 2);
    g.drawRect(selx * cellSize + 2, sely * cellSize + 2, cellSize - 4,
               cellSize - 4);
    g.drawRect(selx * cellSize + 3, sely * cellSize + 3, cellSize - 6,
               cellSize - 6);

  }

  public Dimension getPreferredSize() {
    if(model.getColCount() < 11)
      return new Dimension(11 * cellSize, model.getRowCount() * cellSize + cellSize * 3);
    else
      return new Dimension(model.getColCount() * cellSize, model.getRowCount() * cellSize + cellSize * 3);
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

 public void changeSelected(int Xcell, int Ycell, int z, int a){
 repaint(Xcell*cellSize, Ycell*cellSize, z, a);
//   repaint();  Bounding box computed in Model, could be just as easily here i suppose
 //But it was quicker to use the old values straight from model
 //could have easily just passed them here given a little mroe time
 }



 public void changedEdCell (int Xcell, int Ycell) {
 repaint(Xcell * cellSize, Ycell * cellSize, cellSize, cellSize);
 //Easy little redraw
  }
 public void changedBoardLayout () {

repaint();   //Have to redraw all board


 }
 public void changedBoard() {
   repaint();  //Same thing, two ppl working :)
 }


}
