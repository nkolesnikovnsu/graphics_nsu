package ICGFilter.Filters;

import ICGFilter.Dialogs.FloydWindow;
import ICGFilter.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public class FloydDither implements Filter {
    FloydWindow dialog;
    int[] rgbCount;

    public FloydDither() {
        rgbCount = new int[3];
        rgbCount[0] =  8;
        rgbCount[1] =  8;
        rgbCount[2] =  8;
        dialog = new FloydWindow(this);
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        toReturn.getGraphics().drawImage(image, 0, 0, null);
        int[] err = new int[3];
        int[] newRGB = new int[3];
        for (int y = 0; y < toReturn.getHeight(); y++) {
            for (int x = 0; x < toReturn.getWidth(); x++) {
                int pixel = toReturn.getRGB(x, y);
                setNearest(toReturn, x, y, pixel, err, newRGB);
            }
        }
        return toReturn;
    }

    //устанавливаем ближайший
    private void setNearest(BufferedImage image, int x, int y, int rgba, int[] err, int[] newRGB) {
        int[] ORIGIN_RGB = new int[4];
        ORIGIN_RGB[0] = (rgba & 0x00FF0000) >> 16;//красный
        ORIGIN_RGB[1] = (rgba & 0x0000FF00) >> 8;//зеленый
        ORIGIN_RGB[2] = (rgba & 0x000000FF);//синий
        ORIGIN_RGB[3] = (rgba & 0xFF000000) >> 24;
        int[] newrgb = new int[4];
        for (int i = 0; i < 3; i++) {
            if(rgbCount[i] < 253) {
                newrgb[i] = (int) Math.round((Math.round((rgbCount[i] + 2 - 1) * ORIGIN_RGB[i] / 255.0) * (255.0 / (rgbCount[i] + 2 - 1))));
                err[i] = ORIGIN_RGB[i] - newrgb[i];
                spreadError(image, x, y, err);
            }
            else{
                newrgb[i] = (int) Math.round((Math.round((rgbCount[i] + 2 - 1) * ORIGIN_RGB[i] / 255.0) * (255.0 / (rgbCount[i] + 2))));
                err[i] = ORIGIN_RGB[i] - newrgb[i];
                spreadError(image, x, y, err);
            }
        }
        newrgb[3] = 0;
        image.setRGB(x, y, newrgb[2] | (newrgb[1] << 8) | (newrgb[0] << 16) | (newrgb[3] << 24));
    }

    //разброс ошибки
    private void spreadError(BufferedImage image, int x, int y, int[] err) {
        int ORIGIN_RGB;
        int[] orig = new int[4];
        if (x < image.getWidth() - 1) {
            ORIGIN_RGB = image.getRGB(x + 1, y);
            orig[0] = (ORIGIN_RGB & 0x00FF0000) >> 16;//красный
            orig[1] = (ORIGIN_RGB & 0x0000FF00) >> 8;//зеленый
            orig[2] = (ORIGIN_RGB & 0x000000FF);//синий
            orig[3] = (ORIGIN_RGB & 0xFF000000) >> 24;
            image.setRGB(x + 1, y, addErr(orig, err, 7));
        }
        if (y < image.getHeight() - 1) {
            if (x > 0) {
                ORIGIN_RGB = image.getRGB(x - 1, y + 1);
                orig[0] = (ORIGIN_RGB & 0x00FF0000) >> 16;//красный
                orig[1] = (ORIGIN_RGB & 0x0000FF00) >> 8;//зеленый
                orig[2] = (ORIGIN_RGB & 0x000000FF);//синий
                orig[3] = (ORIGIN_RGB & 0xFF000000) >> 24;
                image.setRGB(x - 1, y + 1, addErr(orig, err, 5));
            }
            ORIGIN_RGB = image.getRGB(x, y + 1);
            orig[0] = (ORIGIN_RGB & 0x00FF0000) >> 16;//красный
            orig[1] = (ORIGIN_RGB & 0x0000FF00) >> 8;//зеленый
            orig[2] = (ORIGIN_RGB & 0x000000FF);//синий
            orig[3] = (ORIGIN_RGB & 0xFF000000) >> 24;
            image.setRGB(x, y + 1, addErr(orig, err, 3));
            if (x < image.getWidth() - 1) {
                ORIGIN_RGB = image.getRGB(x + 1, y + 1);
                orig[0] = (ORIGIN_RGB & 0x00FF0000) >> 16;//красный
                orig[1] = (ORIGIN_RGB & 0x0000FF00) >> 8;//зеленый
                orig[2] = (ORIGIN_RGB & 0x000000FF);//синий
                orig[3] = (ORIGIN_RGB & 0xFF000000) >> 24;
                image.setRGB(x + 1, y + 1, addErr(orig, err, 1));
            }
        }
    }

    private int addErr(int[] orig, int[] err, int coef) {
        for (int i = 0; i < err.length; i++) {
            orig[i] = Math.max(Math.min(orig[i] + err[i] * coef / 16, 255), 0);
        }
        return orig[2] | (orig[1] << 8) | (orig[0] << 16) | (orig[3] << 24);
    }


    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }

    public int getBlue() {
        return rgbCount[0];
    }

    public int getGreen() {
        return rgbCount[1];
    }

    public int getRed() {
        return rgbCount[2];
    }

    public void setBlue(int blueCount) {
        rgbCount[0] = blueCount;
    }

    public void setGreen(int greenCount) {
        rgbCount[1] = greenCount;
    }

    public void setRed(int redCount) {
        rgbCount[2] = redCount;
    }
}
