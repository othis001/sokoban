/**
 * <p>CS494 Lab4: Sokoban/p>
 * <p>Description: Sokoban Game </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Joeseph Vannucci and Oliver Thistlethwaite
 * @version 1.0
 */


public class Coord {
  protected int x_value;
  protected int y_value;

  public Coord() {
    x_value = 0;
    y_value = 0;
  }
  public Coord(int x, int y) {
    x_value = x;
    y_value = y;
  }
  public int getX() {
    return x_value;
  }
  public int getY() {
    return y_value;
  }
  public void setX (int x) {
    x_value = x;
  }
  public void setY (int y) {
    y_value = y;
  }
}