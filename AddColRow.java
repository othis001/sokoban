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

public class AddColRow extends JPanel implements EditorViewInterface{
  Editor editor;

  public AddColRow(Editor theeditor) {
    editor = theeditor;
    JButton up = new JButton("^");
    JButton down = new JButton("v");
    JButton left = new JButton("<");
    JButton right = new JButton(">");

    right.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.getModel().addColumn();
        editor.doDisplay();
      }
    });
    down.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.getModel().addRow();
        editor.doDisplay();
      }
    });
    up.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.getModel().addRowAbove();
        editor.doDisplay();
      }
    });
    left.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.getModel().addColumnAbove();
        editor.doDisplay();
      }
    });

    setLayout(new BorderLayout());

    add(up, BorderLayout.NORTH);
    add(down, BorderLayout.SOUTH);
    add(right, BorderLayout.EAST);
    add(left, BorderLayout.WEST);

    Container cont = new Container();
    cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
    cont.add(Box.createGlue());
    cont.add(new JLabel("add new row/column"), BorderLayout.CENTER);
    cont.add(Box.createGlue());


    add(cont, BorderLayout.CENTER);
  }
  public void changedBoardLayout() {}
 public void changedEdCell(int x, int y) {}
 public void changeSelected(int x, int y, int z, int a) {}
 public void changedBoard() {}

}