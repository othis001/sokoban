import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JDialog;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CommandBar extends JPanel implements EditorViewInterface{
  Editor editor;
  String gameBoardName;

  public CommandBar(Editor theEditor) {
    editor = theEditor;

    JButton newButton = new JButton("New");
    newButton.setMnemonic(KeyEvent.VK_N);
    newButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EditorCreator editor_creator = new EditorCreator();
        editor_creator.setResizable(false);
        editor_creator.setLocation(getParent().getLocationOnScreen());
        editor_creator.setVisible(true);
      }
    });

    JButton loadButton = new JButton("Load");
    loadButton.setMnemonic(KeyEvent.VK_L);
    loadButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.loadBoard();
      }
    });
    JButton saveButton = new JButton("Save");
    saveButton.setMnemonic(KeyEvent.VK_S);
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.saveBoard();
      }
    });
    JButton asButton = new JButton("SaveAs");
    asButton.setMnemonic(KeyEvent.VK_A);
    asButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.saveBoardAs();
      }
    });
    JButton quitButton = new JButton("Quit");
    quitButton.setMnemonic(KeyEvent.VK_Q);
    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editor.dispose();
      }
    });



    JPanel what = new JPanel();
    what.add(newButton);
    what.add(loadButton);
    what.add(saveButton);
    what.add(asButton);
    what.add(quitButton);

    Container pane = this;
    pane.add(what);
  }
  public void changedBoardLayout() {}
  public void changedEdCell(int x, int y) {}
 public void changeSelected(int x, int y, int z, int a) {}
  public void changedBoard() {}

}