
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

// Это абстрактный класс с абстрактным методом рисования. Он наследуется от MyShape
 // и содержит методы, необходимые для рисования овалов и прямоугольников. Он также содержит переменную экземпляра, называемую fill.

abstract class MyBoundedShape extends MyShape
{

    private boolean fill; // boolean variable that determines whether the shape is filled or not
    
    public MyBoundedShape() {
        super();
        fill = false;
    }
    
    public MyBoundedShape(int startX, int startY, int endX, int endY, Color color, boolean fill, int width, BufferedImage img, int Radius, int Corner, int Turn, int InternalRadius) {
        super(startX, startY, endX, endY, color, width, img, Radius, Corner, Turn, InternalRadius);
        this.fill = fill;
    }
    
    /**
     * Mutator methods
     */

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     * Accessor Methods
     */
    public int getRadius() {
        return Radius;
    }
    public int getCorner() {
        return Corner;
    }

    public int getTurn(){
        return Turn;
    }
    public int getInternalRadius(){
        return InternalRadius;
    }

    public int getUpperLeftX() {
        return Math.min(getStartX(), getEndX());
    }

    public int getUpperLeftY() {
        return Math.min(getStartY(), getEndY());
    }
    
    public int getWidth() {
        return Math.abs(getStartX() - getEndX());
    }
  
    public int getHeight() {
        return Math.abs(getStartY() - getEndY());
    }
    
    public boolean getFill() {
        return fill;
    }
    
    abstract public void draw( Graphics g );
}
