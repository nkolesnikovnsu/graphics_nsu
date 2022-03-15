
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Класс содержит координаты и цвет фигур
 */

abstract class MyShape {
  private Color color;
  private int startX, startY, endX, endY;
  private int width;
  public BufferedImage img;
  public int Radius;
  public int Corner;
  public int Turn;
  public int InternalRadius;

  public MyShape() {
    startX = 0; startY = 0; endX = 0; endY = 0;
    color = Color.BLACK; width=1; Radius = 10; Corner =1;Turn=1;
  }

  public MyShape(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img, int Radius, int Corner, int Turn, int InternalRadius) {
      this.startX = startX;
      this.startY = startY;
      this.endX = endX;
      this.endY = endY;
      this.color = color;
      this.width = width;
      this.img= img;
      this.Corner = Corner;
      this.Radius = Radius;
      this.Turn = Turn;
      this.InternalRadius = InternalRadius;
  }

  /**
   * Mutator methods
   */

//  public void setStartX(int startX) {
//    this.startX = startX;
//  }

//  public void setEndX(int endX) {
//    this.endX = endX;
//  }

//  public void setStartY(int startY) {
//    this.startY = startY;
//  }

//  public void setEndY(int endY) {
//    this.endY = endY;
//  }

  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Accessor methods
   */

  public BufferedImage getImage() {
    return img;
  }

  public int getWidth() {
    return width;
  }

  public int getStartX() {
    return startX;
  }

  public int getEndX() {
    return endX;
  }

  public int getStartY() {
    return startY;
  }

  public int getEndY() {
    return endY;
  }
 
  public Color getColor() {
      return color;
  }

  abstract public void draw( Graphics g );
}
