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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FractalMain extends JFrame {
  
  private JFrame mainFrame;
  private JPanel instructionPanel;
  private JLabel statusLabel;
  private JPanel controlPanel;
  private JLabel display;
  private JLabel instructions1;
  private JLabel instructions2;
  private JLabel instructions3;
  private JButton button;
  private JLabel label1;
  private JLabel label2;
  private JTextField valueField1;
  private JTextField valueField2;
  
  //Protected variables hides the variable from the user, but allows subclasses to access them
  protected double value1;
  protected double value2;
  
  //Universal width and height
  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 600;
  
  public FractalMain() {
    prepareGUI();
  }
  
  //Main method calls MainGui() to create JFrame and GUI
  public static void main(String[] args) {
    FractalMain mainGui = new FractalMain();
    mainGui.showEvent();
    //Doesn't allow user to resize for convenience
    mainGui.setResizable(false);
  }
  
  //Basic setup of 4x1 GridLayout to add instructionPanel, controlPanel and button
  private void prepareGUI() {
    mainFrame = new JFrame("FractalFrenzy");
    mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    mainFrame.setLayout(new GridLayout(4,1));
    mainFrame.setLayout(new FlowLayout());
    
    instructionPanel = new JPanel();
    instructionPanel.setLayout(new GridLayout(4, 1));
    
    controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(2, 2));
    
    button = new JButton("Start");
    
    mainFrame.add(instructionPanel);
    mainFrame.add(controlPanel);
    mainFrame.add(button, BorderLayout.CENTER);
    mainFrame.setVisible(true);
  }
  
  private void showEvent() {
 //Written instructions for user
    display = new JLabel("Fractal Explorer", SwingConstants.CENTER);
    instructions1 = new JLabel("This application explores the graphical representation of the Mendelbrot Set", SwingConstants.LEFT);
    instructions2 = new JLabel("Use the Left and Right mouse clicks to zoom in and out", SwingConstants.LEFT);
    instructions3 = new JLabel("Enter two values below for substitution into the set (hint: try the default)", SwingConstants.LEFT);
    instructionPanel.add(display);
    instructionPanel.add(instructions1);
    instructionPanel.add(instructions2);
    instructionPanel.add(instructions3);
    
    //controlPanel for the user to enter value inputs
    label1 = new JLabel("Value 1 (preferably number < 2): ", SwingConstants.CENTER);
    label2 = new JLabel("Value 2 (preferably number < 2): ", SwingConstants.CENTER);
    valueField1 = new JTextField(12);
    valueField2 = new JTextField(12);
    valueField1.setText("" + 0.0);
    valueField2.setText("" + 0.0);
    valueField1.addKeyListener(new CustomKeyListener());
    valueField2.addKeyListener(new CustomKeyListener());
    
    // limit valueField1 to 10 characters
    valueField1.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) { 
            if (valueField1.getText().length() >= 10 ) 
                e.consume(); 
        }  
    });
    
    // limit valueField2 to 10 characters
    valueField2.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) { 
            if (valueField2.getText().length() >= 10 ) 
                e.consume(); 
        }  
    });
    
    controlPanel.add(label1);
    controlPanel.add(valueField1);
    controlPanel.add(label2);
    controlPanel.add(valueField2);
    
    button.setActionCommand("Start");
    button.addActionListener(new ButtonClickListener());
    
    mainFrame.setVisible(true);
  }
  
  //This ButtonClickListener class listens for button click input on the "Start" button
  private class ButtonClickListener implements ActionListener {
 double value1;
 double value2;
   
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();  
      if( command.equals("Start"))  {
     //Takes valueField1 and valueField2 text and converts into doubles for the FractalFrenzy program to use.
        String value1String = valueField1.getText();
        String value2String = valueField2.getText();
        value1 = Double.parseDouble(value1String);
        value2 = Double.parseDouble(value2String);
        new FractalFrenzy(value1, value2);
      }
    }
  }
  
  //This KeyListener class listens for the "enter" key input then runs the main FractalFrenzy program
  private class CustomKeyListener implements KeyListener {
 double value1;
 double value2;
   
 /*All KeyEvents, including keyTyped and keyReleased, had to be accounted for, even if they have no 
  * logic programmed into them.
  * (non-Javadoc)
  * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
  */
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
     //Takes valueField1 and valueField2 text and converts into doubles for the FractalFrenzy program to use.
        String value1String = valueField1.getText();
        String value2String = valueField2.getText();
        value1 = Double.parseDouble(value1String);
        value2 = Double.parseDouble(value2String);
        new FractalFrenzy(value1, value2);
      }
    }
    
    public void keyReleased(KeyEvent e) {
    } 
  }
}