
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * This class inherits from MyBoundedShape and is responsible for drawing a oval.
 */

public class MyOval extends MyBoundedShape 
{ 
    public MyOval() {
        super();
    }
    
    public MyOval(int startX, int startY, int endX, int endY, Color color, boolean fill, int width, BufferedImage img, int Radius, int Corner, int Turn,int InternalRadius) {
        super(startX, startY, endX, endY, color, fill, width, img, Radius, Corner, Turn, InternalRadius);
    }

    public Shape drawStar(Graphics g)
    {
        int koordX = getStartX();
        int koordY = getStartY();
        int innerRadius = getInternalRadius();
        int outerRadius = getRadius();
        int numRays = getCorner();
        int startAngleR = getTurn();
        Path2D p = new Path2D.Double();
        int x1 = 0;
        int y1 = 0;
        double deltaAngleR = Math.PI / numRays;
        for (int i = 0; i < numRays * 2; i++)
        {
            double angleR = startAngleR + i * deltaAngleR;
            double ca = Math.cos(angleR);
            double sa = Math.sin(angleR);
            double relX = ca;
            double relY = sa;
            if ((i & 1) == 0)
            {
                relX *= outerRadius;
                relY *= outerRadius;
            }
            else
            {
                relX *= innerRadius;
                relY *= innerRadius;
            }
            if (i == 0)
            {
                p.moveTo(koordX + relX, koordY + relY);
                //x1 = (int) (koordX + relX);
                //y1 = (int) (koordY + relY);

            }
            else
            {
                //g.drawLine(x1,y1,(int)(koordX + relX),(int)(koordY + relY));
                p.lineTo(koordX + relX, koordY + relY);
            }
        }
        p.closePath();
        return p;
    }

    @Override
    public void draw( Graphics g ) {
        g = img.getGraphics();
        int width = getWidth();
        g.setColor(getColor());
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(width));
        if (getFill()) {
            g2.draw(drawStar(g2));
           // g.fillOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
        }
        else {
            //g2.draw(drawStar(g));
            g2.draw(drawStar(g2));
           // g.drawOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
        }
    }   
}
