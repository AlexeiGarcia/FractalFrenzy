import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

//This import statement, although not necessary to compile, allows the user to move with W,A,S,D,
import java.awt.event.*;

/*This Class is the main class that generates the Frame and fractal when it is called by the user.
 * The application itself is a Fractal Explorer, where the user is prompted to enter two double numbers
 * which the application uses to create a colorful fractal image. The user can then navigate through the 
 * application and save images or screenshots of the fractal.
 */

public class FractalFrenzy extends FractalMain{
  
  //Sets Frame Dimensions
  public static final int FRAME_WIDTH = 800;
  public static final int FRAME_HEIGHT = 800;
  
  //For navigating through fractal
  static final double ZOOM = 100.0;
  
  //Number of times computer will calculate a point using the Mandelbrot set
  static final int ITERATIONS = 200;
  
  //Finds position within fractal image
  static final double TOP_LEFT_X = -3.0;
  static final double TOP_LEFT_Y = +3.0;
  
  double zoomIncrement = ZOOM;
  double topLeftX = TOP_LEFT_X;
  double topLeftY = TOP_LEFT_Y;
  
  Body body;
  BufferedImage fractal;
  double value1;
  double value2;
  
  /*Constructor adds the properties of the viewing frame, adds the frame from the frame subclass, 
   * listens for frame adjustments based on user input, and updates the image
   */
  public FractalFrenzy(double a, double b) {
    value1 = a;
    value2 = b;
    addBody();
    setFrameProperties();
    body.addKeyStrokeMov();
    update(value1, value2);
  }
  
  //Creates the frame, sets boundaries and image type of fractal image, and adds the frame to the center of the screen 
  private void addBody() {
    body = new Body();
    fractal = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
    body.setVisible(true);
    this.add(body, BorderLayout.CENTER);
  }
  
  //Sets frame properties such as title, dimensions, resizability, closing, and 
  private void setFrameProperties() {
    this.setTitle("Fractal Frenzy");
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Doesn't allow user to resize frame, this makes it easier for the computer to handle calculations
    this.setResizable(false);
    //The window is placed in the center of the screen when created
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
  
  //Resets and updates zoomIncrement or zoomFactor after zooming and moving through application
  private void adjustZoom(double tempX, double tempY, double tempZoomIncrement) {
    topLeftX += tempX/zoomIncrement;
    topLeftY -= tempY/zoomIncrement;
    zoomIncrement = tempZoomIncrement;
    topLeftX -= (FRAME_WIDTH/2) / zoomIncrement;
    topLeftY += (FRAME_HEIGHT/2) / zoomIncrement;
    update(value1, value2);
  }
  
  //Moves frame up by approximately 1/5 of current height
  private void moveUp() {
    double currentHeight = FRAME_HEIGHT / zoomIncrement;
    topLeftY += currentHeight / 5;
    update(value1, value2);
  }
  
  //Moves frame down by approximately 1/5 of current height
  private void moveDown() {
    double currentHeight = FRAME_HEIGHT / zoomIncrement;
    topLeftY -= currentHeight / 5;
    update(value1, value2);
  }
  //Moves frame left by approximately 1/5 of current width
  private void moveLeft() {
    double currentWidth = FRAME_WIDTH / zoomIncrement;
    topLeftX -= currentWidth / 5;
    update(value1, value2);
  }
  //Moves frame right by approximately 1/5 of current width
  private void moveRight() {
    double currentWidth = FRAME_WIDTH / zoomIncrement;
    topLeftX += currentWidth / 5;
    update(value1, value2);
  }
  
  /*Updates frame and image
   *Creates fractal image by changing the color of each pixel based on the number of iterations
   *for that point.
   */
  public void update(double value1, double value2) {
    for(int x = 0; x < FRAME_WIDTH; x++) {
      for(int y = 0; y < FRAME_HEIGHT; y++) {
        double a = getXPos(x);
        double b = getYPos(y);
        int count = computeValues(a, b, value1, value2);
        int pointColor = generateColor(count);
        fractal.setRGB(x, y, pointColor);
      }
    }
    body.repaint();
  }
  
  //Get X position
  private double getXPos(double x) {
    return x/zoomIncrement + topLeftX;
  }
  
  //Get Y position
  private double getYPos(double y) {
    return y/zoomIncrement - topLeftY;
  }
  
  //Calculations for generating fractal (checks 200 iterations of users points of C_real (a) and C_imaginary (b) )
  private int computeValues(double a, double b, double value1, double value2) {
 double z1 = value1 ;
    double z2 = value2;

    int count = 0;
    while(z1*z1 + z2*z2 <= 4.0) {
      double ztemp = z1;
      z1 = z1*z1 - z2*z2 + a;
      z2 = 2*z2*ztemp + b;
      if(count >= ITERATIONS) {
        return ITERATIONS;
      }
      count++;
    }
    return count;
  }
  
  //Generates colors that correspond to areas of Fractal and iteration count
  private int generateColor(int count) {
    int color = 0b001010010100100101111111; 
    int cover = 0b000101000100010001010100; 
    int shift = count / 13;
    
    if (count == ITERATIONS) 
      return Color.BLACK.getRGB();
    
    return color | (cover << shift);
  }
  
  //Frame object that inherits from JPanel but is used as a separate class/object  by FractalExplorer
  public class Body extends JPanel implements MouseListener {
    
    //Constructor adds MouseListener for user input for exploration
    public Body() {
      addMouseListener(this);
    }
    
    //overrides Dimension to create width and height
    @Override public Dimension getSize() {
      return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    }
    
    @Override public void paintComponent(Graphics drawingObj) {
      drawingObj.drawImage(fractal, 0, 0, null);
    }
    
    
    //Basic switch statement dictated by left and right clicks of mouse
    @Override public void mousePressed(MouseEvent mouse) {
      double x = (double) mouse.getX();
      double y = (double) mouse.getY();
      switch(mouse.getButton()) {
        
        //This creates the zooming out for the right mouse click which multiplies the zoom by 2
        case MouseEvent.BUTTON1:
          adjustZoom(x, y, zoomIncrement*2);
          break;
          
          //This creates the zooming in for the left mouse click which divides the zoom by 2
        case MouseEvent.BUTTON3:
          adjustZoom(x, y, zoomIncrement/2);
          break;
      }
    }
    
    //Override of all other mouse functions
    @Override public void mouseReleased(MouseEvent mouse){}
    @Override public void mouseClicked(MouseEvent mouse) {}
    @Override public void mouseEntered(MouseEvent mouse) {}
    @Override public void mouseExited (MouseEvent mouse) {}
    
    //Method allows for the W,A,S,D keys to be used to linearly move through program, uses "move" methods from above
    public void addKeyStrokeMov() {
      KeyStroke wKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
      KeyStroke aKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
      KeyStroke sKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
      KeyStroke dKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
      
      Action wPressed = new AbstractAction() {
        @Override public void actionPerformed(ActionEvent e) {
          moveUp();
        }
      };
      
      Action aPressed = new AbstractAction() {
        @Override public void actionPerformed(ActionEvent e) {
          moveLeft();
        }
      };
      
      Action sPressed = new AbstractAction() {
        @Override public void actionPerformed(ActionEvent e) {
          moveDown();
        }
      };
      
      Action dPressed = new AbstractAction() {
        @Override public void actionPerformed(ActionEvent e) {
          moveRight();
        }
      }; 
      
      this.getInputMap().put( wKey, "w_key" );
      this.getInputMap().put( aKey, "a_key" );
      this.getInputMap().put( sKey, "s_key" );
      this.getInputMap().put( dKey, "d_key" );
      
      this.getActionMap().put( "w_key", wPressed );
      this.getActionMap().put( "a_key", aPressed );
      this.getActionMap().put( "s_key", sPressed );
      this.getActionMap().put( "d_key", dPressed );
    }
  }
}
