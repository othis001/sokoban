import java.awt.*;
import java.awt.Event;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


public class EditorCreator extends JFrame{
  protected JLabel w_info = new JLabel("25");
  protected JLabel h_info = new JLabel("25");
  protected JSlider w_slider = new JSlider(1, 50);
  protected JSlider h_slider = new JSlider(1, 50);
  protected int boardHeight = 25;
  protected int boardWidth = 25;

  public EditorCreator() {
    super("Create new Sokoban Editor");

    w_slider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        boardWidth = ((JSlider) e.getSource() ).getValue();
        w_info.setText(Integer.toString(boardWidth));
      }
    });

    h_slider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        boardHeight = ((JSlider) e.getSource() ).getValue();
        h_info.setText(Integer.toString(boardHeight));
      }
    });


    Container contentPane = getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));


    contentPane.add(new JLabel("Select Board Width"));
    contentPane.add(w_info);
    contentPane.add(w_slider);

    contentPane.add(new JLabel("Select Board Height"));
    contentPane.add(h_info);
    contentPane.add(h_slider);
    contentPane.add(Box.createHorizontalStrut(312));


    JButton ok = new JButton("OK");
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Editor editor = new Editor(boardWidth, boardHeight); // the new editor window
        editor.setSize(editor.getPreferredSize());
        editor.setVisible(true);
        dispose();
      }
    });

    JButton cancel = new JButton("Cancel");
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });


    Container cont1 = new Container();
    cont1.setLayout(new BoxLayout(cont1, BoxLayout.X_AXIS));
    cont1.add(ok);
    cont1.add(cancel);
    contentPane.add(Box.createVerticalStrut(32));
    contentPane.add(cont1);

    pack();
    show();


  }

}