
import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class MyFiller extends MyShape {
    private int xLeft; // left span border
    private int xRight; // right span border
    private int y;
    public void floodFill(int x, int y, int bgcolor, Color color, Graphics g){
        ArrayList<Point> point = new ArrayList<Point>();
        point.add(new Point(x, y));
        while (point.size() > 0){
            Point p = point.remove(0);
            x = p.x;
            y = p.y;
            if(bgcolor == getImage().getRGB(x, y)){
                getImage().setRGB(x, y, getColor().getRGB());
                point.add(new Point(x + 1, y));
                point.add(new Point(x - 1, y));
                point.add(new Point(x, y + 1));
                point.add(new Point(x, y - 1));
            }
        }
    }
    public void fill(Color c) {
        for (int x = (xLeft + 1); x < xRight; x ++) {
            getImage().setRGB(x, y, c.getRGB());
        }
    }


    public void fillWithSpan(BufferedImage image, Color colorToFill, int seedX, int seedY) {
        int currentColor = getImage().getRGB(seedX, seedY);

       // Stack<Fill> spanStack = new Stack<>();
        // spanStack.push(MyFiller.getSpan(image, seedX, seedY));
        Stack <Point> spanStack = new Stack<>();
        spanStack.add(new Point(seedX, seedY));

        while (!spanStack.isEmpty()) {
            Point span = spanStack.pop(); fill(colorToFill);

            boolean isAbove = false;
            boolean isBelow = false;

            int y = (int) span.getY();
            for (int x = span.getX0() + 1; x < span.getX1(); x++) {
                if (image.getRGB(x, y - 1) == currentColor && image.getRGB(x, y - 1) != colorToFill.getRGB()) {
                    if (!isAbove) {
                        spanStack.push(getSpan(image, x, y - 1));
                        isAbove = true;
                    }
                } else {
                    isAbove = false;
                }

                if (image.getRGB(x, y + 1) == currentColor && image.getRGB(x, y + 1) != colorToFill.getRGB()) {
                    if (!isBelow) {
                        spanStack.push(getSpan(image, x, y + 1));
                        isBelow = true;
                    }
                } else {
                    isBelow = false;
                }
            }
        }
    }

    public Point getSpan(BufferedImage image,
                         int x0, int y0) {
        int curColor = image.getRGB(x0, y0);
        int xRight = x0;
        int xLeft = x0;
        int height = image.getHeight();
        int width  = image.getWidth();
        while (image.getRGB(xLeft, y0) == curColor && xLeft > 0 && y0 >0 && y0 < 849 && xLeft < 1439) {xLeft--;}
        while (image.getRGB(xRight, y0) == curColor && xRight > 0 && y0 >0 && y0 < 849 && xRight < 1439) { xRight++;}
        //return (new Fill(y0, xLeft, xRight, image));
//        for (int x = (xLeft + 1); x < xRight; x ++) {
//            image.setRGB(x, y0, getColor().getRGB());
//        }
        return (new Point(xLeft,xRight));
    }
    public MyFiller(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img, int Radius, int Corner, int Turn, int InternalRadius) {
        super(startX, startY, endX, endY, color, width, img, Radius, Corner, Turn, InternalRadius);
    }

    @Override
    public void draw(Graphics g ) {
        g = img.getGraphics();
        g.setColor(getColor());
        fillWithSpan(getImage(),getColor(),getStartX(),getStartY());
        //Color c = new Color(getImage().getRGB(getStartX(), getStartY()));
       // flood(getImage(),g,getStartX(),getStartY(), c, getColor());
        //floodFill(getStartX(),getStartY(), getImage().getRGB(getStartX(),getStartY()), getColor(),g);
        //g.drawImage(getImage(), 1920, 1080, null);
    }
}

