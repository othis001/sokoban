import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;


public class ScoreBoard extends JPanel implements ViewInterface {
  BoardModel model;

  public ScoreBoard(BoardModel currmodel) {
    model = currmodel;
    model.AddView(this);
  }
  public void updateScoreBoard() {
    repaint();
  }
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());
    g.setColor(Color.black);
    g.setFont(new Font("Helvetica", Font.BOLD, 25) );
    g.drawString("Moves: " + model.getMoves(), 10, 50);
    g.drawString("Pushes: " + model.getPushes(), 150, 50);

  }
  public Dimension getPreferredSize() {
    int boardWidth;
    if((boardWidth = model.getColCount() * model.getCellSize()) < 250)
      return (new Dimension (250, 64));
    else
      return new Dimension(boardWidth, 64);
  }
  public void changedMoves() {
    repaint();
  }
  public void changedPushes() {
    repaint();
  }
  public void changedCell(int x, int y) {
  }
  public void changedSize() {
  }
  public void changedColor() {
  }
}