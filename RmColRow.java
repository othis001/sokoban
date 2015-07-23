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

public class RmColRow extends JPanel implements EditorViewInterface {
  Editor editor;
  public RmColRow(Editor theeditor) {
    editor = theeditor;

    JButton ColButton = new JButton("Rem Col");
    ColButton.setMnemonic(KeyEvent.VK_L);
    ColButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.getModel().remColumn();
        editor.doDisplay();
      }
    });

    JButton RowButton = new JButton("Rem Row");
    RowButton.setMnemonic(KeyEvent.VK_L);
    RowButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.getModel().remRow();
        editor.doDisplay();
      }
    });

    this.add(ColButton, RowButton);
    this.add(RowButton, RowButton);
  }
  public void changedBoardLayout() {}
 public void changedEdCell(int x, int y) {}
 public void changeSelected(int x, int y, int z, int a) {}
 public void changedBoard() {}

}