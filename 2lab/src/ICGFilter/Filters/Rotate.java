package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.Dialogs.RotateDialog;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Rotate implements Filter {
    private int angle = 25;
    RotateDialog dialog;
    public Rotate(){
        dialog = new RotateDialog(this);
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        double radians = (180-angle) * Math.PI / 180;
        double tworadians = angle * Math.PI / 180;
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        int four_cornerX = image.getWidth()-1;
        int four_cornerY = image.getHeight()-1;
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;
        double [] rotateup = calcRotation(-centerX, -centerY, radians);
        double [] rotatedown = calcRotation(-centerX, centerY, radians);
        int newWidth = (int)(2 * Math.max(Math.abs(rotateup[0]),Math.abs(rotatedown[1]))) + 1;
        int newHeight = (int)(2 * Math.max(Math.abs(rotateup[1]),Math.abs(rotatedown[1]))) + 1;
        BufferedImage fin = new BufferedImage(newWidth, newHeight, image.getType());
        if(getAngle()>45){
            rotateup = calcRotation(-centerX, -centerY, tworadians);
            rotatedown = calcRotation(-centerX, centerY, tworadians);
            newWidth = (int)(2 * Math.max(Math.abs(rotateup[0]),Math.abs(rotatedown[0]))) + 1;
            newHeight = (int)(2 * Math.max(Math.abs(rotateup[1]),Math.abs(rotatedown[1]))) + 1;
            fin = new BufferedImage(newWidth, newHeight, image.getType());
        }
        int newcenterx = fin.getWidth() / 2;
        int newcentery = fin.getHeight() / 2;
        final int WHITE = Color.WHITE.getRGB();
        Graphics g = fin.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.WHITE);
        Rectangle2D r2 = new Rectangle2D.Float(0,0,fin.getWidth(),fin.getHeight());
        g2.fill(r2);
        for(int y = 0; y < fin.getHeight(); ++y) {
            for(int x = 0; x < fin.getWidth(); ++x) {
                int srcX = (int)((x - newcenterx) * cos - (y - newcentery) * sin);
                int srcY = (int)((x - newcenterx) * sin + (y - newcentery) * cos);
                if(srcX<=-centerX || srcX >= centerX || srcY <= -centerY || srcY >= centerY){
                    continue;
                }
                fin.setRGB(x, y, image.getRGB(centerX-srcX, centerY-srcY));
            }
        }
        return fin;
    }

    private double [] calcRotation(double x , double y , double rotation){
    double [] res = new double [2];
    res[0] = Math.cos(rotation) * x - Math.sin(rotation) * y;
    res[1] = Math.sin(rotation) * x + Math.cos(rotation) * y;
    return res;
    }

    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }
    public int getAngle() {
        return angle;
    }
    public void setAngle(int angle) {
        this.angle = angle;
    }
}
