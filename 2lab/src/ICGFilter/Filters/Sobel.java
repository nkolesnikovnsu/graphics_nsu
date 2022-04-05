package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.Dialogs.SobeWindowSetting;

import java.awt.image.BufferedImage;

public class Sobel implements Filter {
    private int[][] matrix;
    private int k;
    private int binParameter = 10;
    SobeWindowSetting dialog;
    public Sobel() {
        dialog = new SobeWindowSetting(this);
        matrix = new int[][]{
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1},
        };
        k = 4;
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        return sobel(image);
    }

    private BufferedImage sobel(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        final int shift = matrix.length / 2;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int mR = (mainPixel & 0x00FF0000) >> 16; //красный
                int mG = ((mainPixel & 0x0000FF00) >> 8); // зеленый
                int mB = (mainPixel & 0x000000FF); // синий
                int mA = (mainPixel & 0xFF000000) >> 24;
                int mBright = (int)Math.round(0.299*mR + 0.587*mG + 0.114*mB);//центрального
                int bA = 0;
                int bB = 0;
                for (int i = -shift; i <= shift; i++) {
                    for (int j = -shift; j <= shift; j++) {
                        if (x + i >= 0 & x + i < image.getWidth() & y + j >= 0 & y + j < image.getHeight()) {
                            int widowPixel = image.getRGB(x + i, y + j);
                            int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                            int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                            int wB = (widowPixel & 0x000000FF); // синий
                            int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                            bA += wBright * matrix[i + shift][j + shift];
                            bB += wBright * matrix[j + shift][i + shift];
                        } else {
                            if(x + i < 0 && y + j < 0){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(x + i < 0 && y + j > image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(x + i >= image.getWidth() && y + j >= image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(x + i >= image.getWidth() && y + j < 0){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(x + i < 0 && y + j >= 0 && y + j < image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y + j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(y + j < 0 && x + i >= 0 && x + i < image.getWidth()){
                                int widowPixel = image.getRGB(x + i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(y + j >= image.getHeight() && x + i >= 0 && x + i < image.getWidth()){
                                int widowPixel = image.getRGB(x + i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                            if(x + i <= image.getWidth() && y + j >= 0 && y + j < image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y + j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                int wBright = (int)Math.round(0.299*wR + 0.587*wG + 0.114*wB);
                                bA += wBright * matrix[i + shift][j + shift];
                                bB += wBright * matrix[j + shift][i + shift];
                            }
                        }
                    }
                }

                int resultGrey = Math.min((Math.abs(bA) + Math.abs(bB)) / (k * 2), 255);
                if(resultGrey > binParameter) {
                    resultGrey = 255;
                } else {
                    resultGrey = 0;
                }
                int resultPixel = resultGrey | (resultGrey << 8) | (resultGrey << 16) | (mA << 24);
                toReturn.setRGB(x, y, resultPixel);
            }
        }
        return toReturn;
    }
    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }
    public int getBinParameter() {
        return binParameter;
    }
    public void setBinParameter(int binParameter) {
        this.binParameter = binParameter;
    }
}