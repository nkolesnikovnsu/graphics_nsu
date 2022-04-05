package ICGFilter.Filters;

import ICGFilter.Dialogs.BlurSettingWindow;
import ICGFilter.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public class Blur implements Filter {
    private int[][] arr;
    private int k;
    private int arrSize = 5;
    private BlurSettingWindow blurDialog;

    public Blur() {
        this.blurDialog = new BlurSettingWindow(this);
        setarr();
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int resultR = 0;
                int resultG = 0;
                int resultB = 0;
                int resultA = (mainPixel & 0xFF000000) >> 24;

                for (int i = -(arr.length / 2); i <= arr.length / 2; i++) {
                    for (int j = -(arr.length / 2); j <= arr.length / 2; j++) {
                        if (x + i >= 0 & x + i < image.getWidth() & y + j >= 0 & y + j < image.getHeight()) {//если не вышли за границы изображения
                            int widowPixel = image.getRGB(x + i, y + j);
                            int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                            int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                            int wB = (widowPixel & 0x000000FF); // синий
                            resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                            resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                            resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                        } else {
                            if(x + i < 0 && y + j < 0){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(x + i < 0 && y + j > image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(x + i >= image.getWidth() && y + j >= image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(x + i >= image.getWidth() && y + j < 0){
                                int widowPixel = image.getRGB(x - i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(x + i < 0 && y + j >= 0 && y + j < image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y + j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(y + j < 0 && x + i >= 0 && x + i < image.getWidth()){
                                int widowPixel = image.getRGB(x + i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(y + j >= image.getHeight() && x + i >= 0 && x + i < image.getWidth()){
                                int widowPixel = image.getRGB(x + i, y - j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                            if(x + i <= image.getWidth() && y + j >= 0 && y + j < image.getHeight()){
                                int widowPixel = image.getRGB(x - i, y + j);
                                int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                                int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                                int wB = (widowPixel & 0x000000FF); // синий
                                resultR += wR * arr[i + arr.length / 2][j + arr.length / 2];
                                resultG += wG * arr[i + arr.length / 2][j + arr.length / 2];
                                resultB += wB * arr[i + arr.length / 2][j + arr.length / 2];
                            }
                        }
                    }
                }
                resultR = Math.min(resultR / k, 255);
                resultG = Math.min(resultG / k, 255);
                resultB = Math.min(resultB / k, 255);
                int resultPixel = resultB | (resultG << 8) | (resultR << 16) | (resultA << 24);
                toReturn.setRGB(x, y, resultPixel);
            }
        }
        return toReturn;
    }

    @Override
    public MyDialog getParameterDialog() {
        return blurDialog;
    }

    public int getarrSize() {
        return arrSize;
    }

    public void setarrSize(int arrSize) {
        this.arrSize = arrSize;
        setarr();
    }

    private void setarr() {
        if (arrSize == 3) {
            arr = new int[][]{
                    {1, 1, 1},
                    {1, 2, 1},
                    {1, 1, 1},
            };
            k = 10;

        } else if (arrSize == 5) {
            arr = new int[][]{
                    {1, 2, 3, 2, 1},
                    {2, 4, 5, 4, 2},
                    {3, 5, 6, 5, 3},
                    {2, 4, 5, 4, 2},
                    {1, 2, 3, 2, 1},
            };
            k = 74;
        } else {
            arr = new int[arrSize][arrSize];
            for (int i = 0; i < arrSize; i++) {
                for (int j = 0; j < arrSize; j++) {
                    arr[i][j] = 1;
                }
            }
            k = arrSize * arrSize;
        }
    }
}
