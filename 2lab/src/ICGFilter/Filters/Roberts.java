package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.Dialogs.RobertsDialog;

import java.awt.image.BufferedImage;

public class Roberts implements Filter {
    private int[][] matrix;
    private int k;
    int binParameter = 10;
    RobertsDialog dialog;
    public Roberts() {
        dialog = new RobertsDialog(this);
        matrix = new int[][]{
                {1, 0},
                {0, -1},
        };
        k = 1;
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        return roberts(image);
    }

    private BufferedImage roberts(BufferedImage image) {
        BufferedImage finalimage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int mR = (mainPixel & 0x00FF0000) >> 16; //красный
                int mG = ((mainPixel & 0x0000FF00) >> 8); // зеленый
                int mB = (mainPixel & 0x000000FF); // синий
                int mA = (mainPixel & 0xFF000000) >> 24;
                int bright = (int)Math.round(0.299*mR + 0.587*mG + 0.114*mB);
                int curA = 0;
                int curB = 0;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        if (x + i < image.getWidth() & y + j < image.getHeight()) {
                            int widowPixel = image.getRGB(x + i, y + j);
                            int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                            int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                            int wB = (widowPixel & 0x000000FF); // синий
                            int cur_bright =(int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                            curA += cur_bright * matrix[i][j];
                            curB += cur_bright * matrix[j][i];
                        } else {
                            curA += bright * matrix[i][j];
                            curB += bright * matrix[j][i];
                        }
                    }
                }
                int resultGrey = Math.min((Math.abs(curA) + Math.abs(curB)) / (k * 2), 255);
                if(resultGrey > binParameter) {
                    resultGrey = 255;
                } else {
                    resultGrey = 0;
                }
                int resultPixel = resultGrey | (resultGrey << 8) | (resultGrey << 16) | (mA << 24);
                finalimage.setRGB(x, y, resultPixel);
            }
        }
        return finalimage;
    }
    public int getBinParameter() {
        return binParameter;
    }
    public void setBinParameter(int binParameter) {
        this.binParameter = binParameter;
    }
    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }
}
