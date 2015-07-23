import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.colorchooser.*;


/**
 * <p>CS494 Lab5: Sokoban/p>
 * <p>Description: Sokoban Game </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Joeseph Vannucci and Oliver Thistlethwaite
 * @version 1.0
 */


public class Sokoban extends JFrame {
  JMenuBar jMenuBar1 = new JMenuBar();      // Menu Items
  JMenu jMenuAction = new JMenu();
  JMenu jMenuColors = new JMenu();
  JMenuItem jMenuActionReset = new JMenuItem();
  JMenuItem jMenuActionLoad = new JMenuItem();
  JMenuItem jMenuActionQuit = new JMenuItem();
  JMenuItem jMenuColorsWall = new JMenuItem();
  JMenuItem jMenuColorsShaded = new JMenuItem();
  JMenuItem jMenuColorsBlank = new JMenuItem();


  FileDialog loadfile = new FileDialog(this, "Open", FileDialog.LOAD);
  String gameBoardName;

  BoardView board_view;  // MVC Model implemented
  BoardModel board_model;
  ScoreBoard score_board;
  TextBoardView text_board_view;
  SizeRadioButtonPanel size_radio_button_panel;

  public static void main(String args[]) {

    Sokoban app = new Sokoban();

    app.pack();
    app.show();
  }

  public Sokoban() {
   super("Sokoban");
   init_menu(); // initalize the menu

   JPanel empty = new JPanel();
   empty.setPreferredSize(new Dimension(500, 400));
   getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
   getContentPane().add(empty);

   Editor editor = new Editor(16, 16);
   editor.setLocation(550, 0); // creates the editor window
   editor.setSize(editor.getPreferredSize());
   editor.setVisible(true);

   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   getContentPane().validate();


 }

  protected void init_menu() { // initalize the menu
    jMenuAction.setText("Action");
    jMenuActionReset.setText("Reset Game");
    jMenuActionReset.addActionListener(new Sokoban_jMenuActionReset_ActionAdapter(this));
    jMenuActionLoad.setText("Load Board");
    jMenuActionLoad.addActionListener(new Sokoban_jMenuActionLoad_ActionAdapter(this));
    jMenuActionQuit.setText("Quit");
    jMenuActionQuit.addActionListener(new Sokoban_jMenuActionQuit_ActionAdapter(this));
    jMenuAction.add(jMenuActionReset);
    jMenuAction.add(jMenuActionLoad);
    jMenuAction.add(jMenuActionQuit);
    jMenuBar1.add(jMenuAction);

    jMenuColors.setText("Colors");
    jMenuColorsWall.setText("Wall Color");
    jMenuColorsWall.addActionListener(new Sokoban_jMenuColorsWall_ActionAdapter(this));
    jMenuColorsShaded.setText("Shaded Color");
    jMenuColorsShaded.addActionListener(new Sokoban_jMenuColorsShaded_ActionAdapter(this));
    jMenuColorsBlank.setText("Blank Color");
    jMenuColorsBlank.addActionListener(new Sokoban_jMenuColorsBlank_ActionAdapter(this));
    jMenuColors.add(jMenuColorsWall);
    jMenuColors.add(jMenuColorsShaded);
    jMenuColors.add(jMenuColorsBlank);
    jMenuBar1.add(jMenuColors);
    this.setJMenuBar(jMenuBar1);

  }
  // Action | Reset action performed
  public void jMenuActionReset_actionPerformed(ActionEvent e) {
    displayBoard();
  }
  //Action | Load action performed
  public void jMenuActionLoad_actionPerformed(ActionEvent e) {

    loadfile.show(); // brings up the load file dialog box
    gameBoardName = loadfile.getFile();
    displayBoard();
  }
  public void displayBoard() { // loads the board file
    if (gameBoardName != null) { // in case user clicks cancel
      board_model = new BoardModel(gameBoardName); // creates new MVC
      board_view = new BoardView(board_model);
      text_board_view = new TextBoardView(board_model);
      score_board = new ScoreBoard(board_model);
      size_radio_button_panel = new SizeRadioButtonPanel(this, board_model);

      makeDisplay();
    }
  }
  public void makeDisplay() { // does the layout managing
    JPanel cont1 = new JPanel(); // add the gameboard view
    JPanel cont2 = new JPanel();
    cont2.setLayout(new FlowLayout(FlowLayout.RIGHT));

    cont2.add(board_view);
    cont2.add(text_board_view);
    cont1.add(cont2, BorderLayout.CENTER);

    Container ContentPane = getContentPane();
    ContentPane.removeAll();
    ContentPane.add(Box.createGlue() );
    ContentPane.add(cont1);
    ContentPane.add(Box.createGlue() );
    ContentPane.add(score_board);
    ContentPane.add(size_radio_button_panel);

    this.pack();
    this.show();

  }
  //Action | Quit action performed
  public void jMenuActionQuit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  //Colors | Wall action performed
  public void jMenuColorsWall_actionPerformed(ActionEvent e) {
    if(board_model != null) {
      Color wallColor = JColorChooser.showDialog(this, "Choose Wall Color", Color.black);
      if(wallColor != null)
        board_model.notifyBoardColorChange("wall", wallColor);
    }
  }
  //Colors | Shaded action performed
  public void jMenuColorsShaded_actionPerformed(ActionEvent e) {
    if(board_model != null) {
      Color shadedColor = JColorChooser.showDialog(this, "Choose Shaded Color", Color.gray);
      if(shadedColor != null)
        board_model.notifyBoardColorChange("shaded", shadedColor);
    }

  }
  //Colors | Blank action performed
  public void jMenuColorsBlank_actionPerformed(ActionEvent e) {
    if(board_model != null) {
      Color blankColor = JColorChooser.showDialog(this, "Choose Blank Color", Color.white);
      if(blankColor != null)
        board_model.notifyBoardColorChange("blank", blankColor);
    }

  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuActionQuit_actionPerformed(null);
    }
  }
}

class Sokoban_jMenuActionReset_ActionAdapter implements ActionListener {
  Sokoban adaptee;

  Sokoban_jMenuActionReset_ActionAdapter(Sokoban adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuActionReset_actionPerformed(e);
  }
}
class Sokoban_jMenuActionLoad_ActionAdapter implements ActionListener {
  Sokoban adaptee;

  Sokoban_jMenuActionLoad_ActionAdapter(Sokoban adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuActionLoad_actionPerformed(e);
  }
}
class Sokoban_jMenuActionQuit_ActionAdapter implements ActionListener {
  Sokoban adaptee;

  Sokoban_jMenuActionQuit_ActionAdapter(Sokoban adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuActionQuit_actionPerformed(e);
  }
}
class Sokoban_jMenuColorsWall_ActionAdapter implements ActionListener {
  Sokoban adaptee;

  Sokoban_jMenuColorsWall_ActionAdapter(Sokoban adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuColorsWall_actionPerformed(e);
  }
}
class Sokoban_jMenuColorsShaded_ActionAdapter implements ActionListener {
  Sokoban adaptee;

  Sokoban_jMenuColorsShaded_ActionAdapter(Sokoban adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuColorsShaded_actionPerformed(e);
  }
}
class Sokoban_jMenuColorsBlank_ActionAdapter implements ActionListener {
  Sokoban adaptee;

  Sokoban_jMenuColorsBlank_ActionAdapter(Sokoban adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuColorsBlank_actionPerformed(e);
  }
}
