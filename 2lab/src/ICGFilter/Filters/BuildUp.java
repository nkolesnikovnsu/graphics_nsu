package ICGFilter.Filters;

import ICGFilter.Dialogs.BuildUpDialog;
import ICGFilter.Dialogs.BuildUpDialog;
import ICGFilter.Dialogs.MyDialog;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildUp implements Filter{
    private int[][] matrix;
    private int matrixSize = 5;
    private BuildUpDialog buildupDialog;

    public BuildUp() {
        this.buildupDialog = new BuildUpDialog(this);
        setMatrix();
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int resultA = (mainPixel & 0xFF000000) >> 24;
                //List<Integer> pixelList = new ArrayList<>();
                List<Integer> RpixelList = new ArrayList<>();
                List<Integer> GpixelList = new ArrayList<>();
                List<Integer> BpixelList = new ArrayList<>();
                for (int i = -(matrix.length / 2); i <= matrix.length / 2; i++) {
                    for (int j = -(matrix.length / 2); j <= matrix.length / 2; j++) {
                        if (x + i >= 0 & x + i < image.getWidth() & y + j >= 0 & y + j < image.getHeight()) {
                            if(matrix[i + matrix.length / 2][j + matrix.length / 2] == 1){
                            int widowPixel = image.getRGB(x + i, y + j);
                            int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                            int wG = ((widowPixel & 0x0000FF00) >> 8); // зеленый
                            int wB = (widowPixel & 0x000000FF); // синий
                            RpixelList.add(wR);
                            GpixelList.add(wG);
                            BpixelList.add(wB);
                            }
                        }
                    }
                }
                Collections.sort(RpixelList);
                Collections.sort(GpixelList);
                Collections.sort(BpixelList);
                int resultPixel = BpixelList.get(BpixelList.size() - 1) | (GpixelList.get(GpixelList.size() - 1) << 8) | (RpixelList.get(RpixelList.size() - 1) << 16) | (resultA << 24);
                toReturn.setRGB(x, y, resultPixel);
            }
        }
        return toReturn;
    }

    @Override
    public MyDialog getParameterDialog() {
        return buildupDialog;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
        setMatrix();
    }

    private void setMatrix() {
        matrix = new int[][]{
                {0 ,0 ,1, 0, 0},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {0 ,0 ,1, 0, 0},
        };
    }
}
