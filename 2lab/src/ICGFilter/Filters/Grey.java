package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public class Grey implements Filter {

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int R = (pixel & 0x00FF0000) >> 16; //красный
                int G = ((pixel & 0x0000FF00) >> 8); // зеленый
                int B = (pixel & 0x000000FF); // синий
                int A = (pixel & 0xFF000000) >> 24;
                int Grey = (int)Math.round(0.299*R + 0.587*G + 0.114*B);
                pixel = Grey | (Grey << 8) | (Grey << 16) | (A << 24);
                toReturn.setRGB(x, y, pixel);
            }
        }
        return toReturn;
    }
    @Override
    public MyDialog getParameterDialog() {
        return null;
    }
}
