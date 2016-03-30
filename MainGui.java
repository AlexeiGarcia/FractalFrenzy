import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class MainGui extends JFrame
{
  private JFrame mainFrame;
  private JPanel instructionPanel;
  private JLabel statusLabel;
  private JPanel controlPanel;
  private JLabel display;
  private JLabel instructions1;
  private JLabel instructions2;
  private JLabel instructions3;
  private JLabel instructions4;
  private JButton button;
  private JLabel label1;
  private JLabel label2;
  private JTextField valueField1;
  private JTextField valueField2;
  
  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 600;
  
  public MainGui() {
    prepareGUI();
  }
  
  public static void main(String[] args) {
    MainGui mainGui = new MainGui();
    mainGui.showEvent();
  }
  
  private void prepareGUI() {
    mainFrame = new JFrame("Fractal Frenzy");
    mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    mainFrame.setLayout(new GridLayout(4,1));
    mainFrame.setLayout(new FlowLayout());
    
    statusLabel = new JLabel("", JLabel.CENTER);
    statusLabel.setSize(350,100);
    
    instructionPanel = new JPanel();
    instructionPanel.setLayout(new GridLayout(4, 1));
    
    controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(2, 2));
    
    button = new JButton("Start");
    
    mainFrame.add(instructionPanel);
    mainFrame.add(controlPanel);
    mainFrame.add(button, BorderLayout.CENTER);
    mainFrame.add(statusLabel);
    mainFrame.setVisible(true);
  }
  
  private void showEvent() {
    display = new JLabel("Fractal Explorer", SwingConstants.CENTER);
    instructions1 = new JLabel("This application explores the graphical representation of the Mandelbrot Set.", SwingConstants.LEFT);
    instructions2 = new JLabel("Use the Left and Right mouse clicks to zoom in and out.", SwingConstants.LEFT);
    instructions3 = new JLabel("Enter two values below for substitution into the set.", SwingConstants.LEFT);
    instructionPanel.add(display);
    instructionPanel.add(instructions1);
    instructionPanel.add(instructions2);
    instructionPanel.add(instructions3);
    
    label1 = new JLabel("Value 1: ");
    label2 = new JLabel("Value 2: ");
    final int FIELD_WIDTH = 10;
    valueField1 = new JTextField(FIELD_WIDTH);
    valueField2 = new JTextField(FIELD_WIDTH);
    valueField1.setText("" + 0.0);
    valueField2.setText("" + 0.0);
    valueField1.addKeyListener(new CustomKeyListener());
    valueField2.addKeyListener(new CustomKeyListener());
    
    controlPanel.add(label1);
    controlPanel.add(valueField1);
    controlPanel.add(label2);
    controlPanel.add(valueField2);
    
    button.setActionCommand("Start");
    button.addActionListener(new ButtonClickListener());
    
    mainFrame.setVisible(true);
  }
  
  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();  
      if( command.equals("Start"))  {
        statusLabel.setText("Run Program");
      }
    }
  }
  
  private class CustomKeyListener implements KeyListener {
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        statusLabel.setText("Run Program");
      }
    }
    
    public void keyReleased(KeyEvent e) {
    }
  }
}
Status API Training Shop Blog About
© 2016 GitHub, Inc. Terms Privacy Security Contact Help
