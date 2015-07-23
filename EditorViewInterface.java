/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface EditorViewInterface {

  public abstract void changedBoardLayout();
  public abstract void changedEdCell(int Xcell, int Ycell);
  public abstract void changeSelected(int Xcell, int Ycell, int wha, int to);
  public abstract void changedBoard();
}
