
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.util.ArrayList;

import java.nio.file.FileAlreadyExistsException;

import static javax.swing.JOptionPane.showInputDialog;

public class DrawPanel extends JPanel
{
  static JSlider slider;
  static JLabel label;
  static JButton ok;
  static JTextField textField;

  private Dialog dialog;
  private int counter = 0;
  private int counter2 = 0;
  private LinkedList<MyShape> myShapes; // динамический набор фигур
  private LinkedList<MyShape> clearedShapes; // динамический стек очищенных фигур от отмены

  // переменные текущей формы
  BufferedImage img;
  private int currentWidth;
  private int currentTurn;
  private int currentRadius;
  private int currentNForСorner;
  private int currentInternalRadius;
  private String currentShapeType; // allowed values are "Line", "Rectangle", and "Oval"
  private MyShape currentShapeObject; // stores the current Shape being used
  private Color currentShapeColor; // current shape color
  private boolean currentShapeFilled; // determines whether shape is filled or not
  //public JColorChooser colorChooser = new JColorChooser();

  private JLabel statusLabel;

  public DrawPanel()
  {
    myShapes = new LinkedList<MyShape>();
    clearedShapes = new LinkedList<MyShape>();
    // Initialize default Shape variables
    img = new BufferedImage(2560, 1440, BufferedImage.TYPE_INT_RGB);

    currentShapeType = "Line";
    currentWidth = 1;
    currentShapeObject = null;
    currentShapeColor = Color.BLACK;
    currentShapeFilled = false;
    currentRadius = 10;
    currentNForСorner = 6;
    currentTurn = 90;
    currentInternalRadius = 5;
    Graphics g = img.createGraphics();
    //g.fillRect(0,0,1920,1080);
    for( int i =0 ; i<1440; i++){
      for(int j=0; j<=850;j++){
        img.setRGB(i ,j ,Color.WHITE.getRGB());
      }
    }
    for( int i =0 ; i < 2559; i++){
      for(int j=0; j < 1439;j++){
        if (i == 1) {
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
        if( i == 1435){
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
        if ( j == 845){
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
        if (j == 1) {
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
      }
    }
//    g.setColor(Color.BLACK);
//    g.drawLine(1,1,1,getHeight()-20);
//    g.drawLine(1,getHeight()-20,getWidth()-20,getHeight()-20);
//    g.drawLine(getWidth()-20,getHeight()-20,getWidth()-20,1);
//    g.drawLine(getWidth()-20,1,1,1);
    repaint();

    statusLabel = new JLabel("");
    setMinimumSize(new Dimension(640,480));
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);
    add(statusLabel, BorderLayout.SOUTH);

    MouseHandler handler = new MouseHandler();                             
    addMouseListener(handler);
    addMouseMotionListener(handler);
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    //g.setColor(Color.WHITE);
    g.drawImage(img,0,0,null);
    int width = getWidth(), height = getHeight();

//    ArrayList<MyShape> shapeArr = myShapes.getArray();
//    for (int counter = shapeArr.size() - 1; counter >= 0; counter --) {
//      shapeArr.get(counter).draw(g);
//    }

    if (currentShapeObject != null) {
      currentShapeObject.draw(g);
      repaint();
    }
  }

  // clears the entire drawing
  public void clearDrawing() {
    myShapes.makeEmpty();
    clearedShapes.makeEmpty();
    for( int i =0 ; i<1440; i++){
      for(int j=0; j<=850;j++){
        img.setRGB(i ,j ,Color.WHITE.getRGB());
      }
    }
    for( int i =0 ; i < 2559; i++){
      for(int j=0; j < 1439;j++){
        if (i == 1) {
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
        if( i == 1435){
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
        if ( j == 845){
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
        if (j == 1) {
          img.setRGB(i ,j ,Color.BLACK.getRGB());
        }
      }
    }
    repaint();
  }

  // called when undo menu item is clicked
  public void clearLastShape() {
    if (!myShapes.isEmpty()) {
        clearedShapes.addFront(myShapes.removeFront());
        repaint();
    }
  }

  // called when redo menu item is clicked
  public void redoLastShape() {
    if (!clearedShapes.isEmpty()) {
        myShapes.addFront(clearedShapes.removeFront());
        repaint();
    }
  }



  public void ColorChooser(){
    currentShapeColor = JColorChooser.showDialog(null, "ColorChooser", Color.BLUE); // NON-NLS: title
  }

  public void OptionsForLine(){
    //String width = JOptionPane.showInputDialog(null,"Введите ширину линии!","Выбор толщины линии");
    runSliderTest(currentWidth);
//    boolean input = isNumeric(width);
//    if (input == true) {
//      int current = Integer.parseInt(width);
//      if(current > 0 && current <10000) {
//        currentWidth = Integer.parseInt(width);
//      }
//      else{
//        JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
//      }
//    }
//    else{
//      JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
//    }

  }

  public void runSliderTest(int width) {
    // Create a new frame
    JFrame frame = new JFrame("Толщина линии");


    // Create a label
    //label = new JLabel();

    // Create a panel
    JPanel p = new JPanel();

    // Create a slider
    slider = new JSlider(0, 100, width);
    // Paint the track and label
    slider.setPaintTrack(true);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    // Set the spacing
    slider.setMajorTickSpacing(20);
    slider.setMinorTickSpacing(5);

    ok = new JButton("OK");
    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
      }
    });
    // Associate the Listener with the slider

    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        currentWidth = slider.getValue();
        textField.setText("" + currentWidth);
      }
    });

    // Add the slider to the panel
    p.add(slider);
    slider.setValue(currentWidth);
    // p.add(label);

    textField = new JTextField(10);
    textField.setToolTipText("Толщина линии");
    textField.setPreferredSize(new Dimension(50,50));
    p.add(textField);
    p.add(ok);

    // Add the panel to the frame
    frame.add(p);
    // Set the label text
    textField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Отображение введенного текста
        boolean output = isNumeric(textField.getText());
        if (output == true) {
          //int current = Integer.parseInt(text);
        }
        slider.setValue(Integer.parseInt(textField.getText()));
        currentWidth = Integer.parseInt(textField.getText());
        label.setText("The value of the Slider is : " + slider.getValue());
      }
    });
    textField.setText(""+currentWidth);

    frame.setSize(300, 150);
    frame.show();
  }


  public void OptionsForStar(){
    String InternalRadius = JOptionPane.showInputDialog(null,"Введите внутренний радиус!","Выбор внутреннего радиуса");
    boolean input = isNumeric(InternalRadius);
    if (input == true) {
      int current = Integer.parseInt(InternalRadius);
      if(current > 0 && current <10000) {
        currentInternalRadius = Integer.parseInt(InternalRadius);
      }
      else{
        JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public void OptionsForTurn(){
    String turn = JOptionPane.showInputDialog(null,"Введите угол поворота!","Выбор угла поворота");
    boolean input = isNumeric(turn);
    if (input == true) {
      int current = Integer.parseInt(turn);
      if (current > 0 && current <10000) {
        currentTurn = Integer.parseInt(turn);
      }
      else{
        JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public void OptionsForNCorner(){
    String corner = JOptionPane.showInputDialog(null,"Введите количество углов!","Выбор N-угольника");
    boolean input = isNumeric(corner);
    if (input == true) {
      int current = Integer.parseInt(corner);
      if (current >0 && current <10000) {
        currentNForСorner = Integer.parseInt(corner);
      }
      else{
        JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public static boolean isNumeric(String str) {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public void Options(){
    String m = JOptionPane.showInputDialog(null,"Введите радиус!","Выбор Радиуса");
    boolean input = isNumeric(m);
    if (input == true) {
      currentRadius = Integer.parseInt(m);
    }
    else{
      JOptionPane.showMessageDialog(null, "Неверные данные", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public void importImageToJPanel() {
    try {
      FileDialog fd = new FileDialog ((Dialog) null, "Загрузить изображение", FileDialog.LOAD);
      fd.setFile ("*.png;*.jpg; *.jpeg; *.gif; *.bmp");
      fd.setVisible(true);
      if (fd.getFile() == null){

      }
      File file = fd.getFiles()[0];
      String fileName = file.getName();

      // String fileName = showInputDialog("Enter a file name:");
//      if (fileName.equals(null) || fileName.trim().equals("") ) {
//        throw new Exception("Please enter a file name!");
//      }

      BufferedImage image = ImageIO.read(file);
      img = image;
      getGraphics().drawImage(img, 0,0, null);
      //add(new JLabel(new ImageIcon(image)));
    }
    catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(null, "The file was not found!");
    }
    catch (IOException e) {
      JOptionPane.showMessageDialog(null, "There was an error reading the file!");
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  public void exportImageFromJPanel() {
    try {
      FileDialog fd = new FileDialog ((Dialog) null, "Сохранить изображение", FileDialog.SAVE);
      fd.setFile ("*.png;*.jpg; *.jpeg; *.gif; *.bmp");
      fd.setVisible(true);

      if (fd.getFile() == null){

      }

      File file = fd.getFiles()[0];
      String fileName = file.getName();
     // String fileName = showInputDialog("Enter a file name:");

      if (fileName.equals(null) || fileName.trim().equals("") ) {
        throw new Exception("Please enter a file name!");
      }

      //File file = new File("./" + fileName + ".png");
      
      if (file.exists()) {
        throw new FileAlreadyExistsException("File already exists.");
      }

      BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
      paint(img.createGraphics());
      
      ImageIO.write(img, "PNG", file);
    }
    catch (FileAlreadyExistsException e) {
      JOptionPane.showMessageDialog(null, "This file already exists");
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /**
   * Mutator Methods
   */
  public void setCurrentWidth(int width) {
    currentWidth = width;
  }

  public void setCurrentShapeType(String type) {
    currentShapeType = type;
  }

  public void setCurrentShapeColor(Color color) {
      currentShapeColor = color;
  }

  public void setCurrentShapeFilled(boolean filled) {
      currentShapeFilled = filled;
  }

  private class MouseHandler extends MouseAdapter
  {
    int x1,x2;
    int y1,y2;
    int counter2 = 0;
    public void mousePressed(MouseEvent event)
    {
      switch (currentShapeType) {
          case "Line":
            if(counter == 1) {
              currentShapeObject = new MyLine(x1, y1,
                      event.getX(), event.getY(), currentShapeColor, currentWidth, img, currentRadius, currentNForСorner, currentTurn, currentInternalRadius);
              counter = 0;
              repaint();
            }
            else{
              counter = 0 ;
              x1=event.getX();
              y1=event.getY();
              counter = 1 ;
              repaint();
            }
              break;
          case "Rectangle":
            if(counter == 0) {
              counter++;
              currentShapeObject = new MyRectangle(event.getX(), event.getY(),
                      event.getX(), event.getY(), currentShapeColor, currentShapeFilled, currentWidth, img, currentRadius, currentNForСorner, currentTurn,currentInternalRadius);
              repaint();
              counter=0;
            }
              break;
          case "Oval":
              currentShapeObject = new MyOval( event.getX(), event.getY(), 
                                             event.getX(), event.getY(), currentShapeColor, currentShapeFilled, currentWidth, img,currentRadius, currentNForСorner, currentTurn, currentInternalRadius);
              repaint();
              break;
          case "Filler":
              currentShapeObject = new MyFiller( event.getX(), event.getY(),
                      event.getX(), event.getY(), currentShapeColor, currentWidth, img, currentRadius, currentNForСorner, currentTurn,currentInternalRadius);
              repaint();
          break;
      }
    }


    public void mouseReleased(MouseEvent event)
    {
      //  currentShapeObject.setEndX(x1);
      //  currentShapeObject.setEndY(y1);
        
        myShapes.addFront(currentShapeObject);
        
        currentShapeObject = null;
        clearedShapes.makeEmpty();
        repaint();
    }

    public void mouseMoved(MouseEvent event)
    {
        statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", event.getX(), event.getY()));
    }

    public void mouseDragged(MouseEvent event)
    {
        //currentShapeObject.setEndX(event.getX());
//        currentShapeObject.setEndY(event.getY());

        statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", event.getX(), event.getY()));
        
        repaint();
    }
  }
}
