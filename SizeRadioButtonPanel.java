import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import java.awt.event.*;

public class SizeRadioButtonPanel extends JPanel implements ActionListener{
  BoardModel board_model;
  Sokoban sokoban;
  public SizeRadioButtonPanel(Sokoban thesokoban, BoardModel the_board_model) {
    board_model = the_board_model;
    sokoban = thesokoban;

    JLabel info = new JLabel("Select Desired Size");
    JRadioButton[] radioButtons = new JRadioButton[3];
    ButtonGroup group = new ButtonGroup();
    radioButtons[0] = new JRadioButton("Small");
    radioButtons[0].setActionCommand("Small");
    radioButtons[1] = new JRadioButton("Medium");
    radioButtons[1].setActionCommand("Medium");
    radioButtons[2] = new JRadioButton("Large");
    radioButtons[2].setActionCommand("Large");

    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(info);
    for(int i=0; i < 3; i++) {
      group.add(radioButtons[i]);
      add(radioButtons[i]);
      radioButtons[i].addActionListener(this);
    }
    radioButtons[1].setSelected(true);



  }
  public void actionPerformed(ActionEvent e) {
   String size = e.getActionCommand();
   if(size == "Small") {
     board_model.notifyBoardSizeChange(10);
     sokoban.makeDisplay();
   }

   else if(size == "Medium") {
     board_model.notifyBoardSizeChange(20);
     sokoban.makeDisplay();
   }
   else if(size == "Large") {
     board_model.notifyBoardSizeChange(30);
     sokoban.makeDisplay();
   }
}


}