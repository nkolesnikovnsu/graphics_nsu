
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class inherits from MyBoundedShape and is responsible for drawing a rectangle
 */

public class MyRectangle extends MyBoundedShape
{
    private int[] x;
    private int[] y;
    public MyRectangle() {
        super();
    }

    public MyRectangle(int startX, int startY, int endX, int endY, Color color, boolean fill, int width, BufferedImage img, int Radius, int Corner, int Turn,int InternalRadius) {
        super(startX, startY, endX, endY, color, fill, width, img, Radius, Corner, Turn, InternalRadius);
    }

    public void DrawNFigure(Graphics g){
    double a, b,  z = 0 ;  int i = 0;
    int alpha = 0;
    int n = getCorner();
    int X = getStartX();
    int Y = getStartY();
    int R = getRadius();
    int povorot = getTurn();
    x=new int[n+1];
    y=new int[n+1];
    int j = n;
    //цикл создающий массив из точек
        for(i=0;i<n;++i){
            x[i]= (int) (X + R*Math.cos(((2*Math.PI*i)/n)+povorot));
            y[i]= (int) (Y + R*Math.sin(((2*Math.PI*i)/n)+povorot));
        }
        Polygon poly = new Polygon(x, y, n);
        g.drawPolygon(poly);
}

    @Override
    public void draw( Graphics g ) {
        g = img.getGraphics();
        g.setColor(getColor());
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(getWidth()));
        if (getFill()) {
            g.fillRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
        }
        else {
            DrawNFigure(g);
            //g.drawRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
        }
    }
}
