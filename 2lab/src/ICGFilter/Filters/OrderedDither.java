package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.Dialogs.OrderedDitherDialog;

import java.awt.image.BufferedImage;

public class OrderedDither implements Filter {
    private final int cSize = 256;
    private int[][] matrix;
    private int matrixRank = 0;
    private int k = 0;
    private int quantumCountR = 5;
    private int quantumCountG = 5;
    private int quantumCountB = 5;
    private OrderedDitherDialog dialog;

    public OrderedDither() {
        dialog = new OrderedDitherDialog(this);
        checkRelevanceOfMatrix();
    }

    private void generatePart(int[][] matrixN, int[][] matrix2N, int offset) {
        int x, y;
        switch (offset) {
            case 0:
                x = 0;
                y = 0;
                break;
            case 1:
                x = 1;
                y = 1;
                break;
            case 2:
                x = 1;
                y = 0;
                break;
            case 3:
                x = 0;
                y = 1;
                break;
            default:
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < matrixN.length; ++i) {
            for (int j = 0; j < matrixN.length; ++j) {
                matrix2N[i + y * matrixN.length][j + x * matrixN.length] = 4 * matrixN[i][j] + offset;
            }
        }
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int R = (mainPixel & 0x00FF0000) >> 16; //красный
                int G = ((mainPixel & 0x0000FF00) >> 8); // зеленый
                int B = (mainPixel & 0x000000FF); // синий
                int resultA = (mainPixel & 0xFF000000) >> 24; // синий

                int resultR = dither(R, x, y, quantumCountR);
                int resultG = dither(G, x, y, quantumCountG);
                int resultB = dither(B, x, y, quantumCountB);

                int resultPixel = resultB | (resultG << 8) | (resultR << 16) | (resultA << 24);
                toReturn.setRGB(x, y, resultPixel);
            }
        }
        return toReturn;
    }

    private void checkRelevanceOfMatrix() {
        int newLen = (int) Math.floor(Math.sqrt((double) 256 / Math.min(Math.min(quantumCountR, quantumCountG), quantumCountB)));
        if (matrix == null || matrix.length != newLen) {
            generateMatrix(newLen);
        }
        k = matrix.length * matrix.length;
    }

    public void generateMatrix(int rank) {
        if (rank > 5) {
            rank = 5;
        }
        if (rank == 1) {
            matrix = new int[][]{
                    {0, 2},
                    {3, 1}
            };
            matrixRank = rank;
            return;
        }
        if (matrixRank == rank) {
            return;
        }
        generateMatrix(rank - 1);
        int[][] oldMatrix = matrix;
        matrix = new int[oldMatrix.length * 2][oldMatrix.length * 2];
        for (int i = 0; i < 4; i++) {
            generatePart(oldMatrix, matrix, i);
        }
        matrixRank = rank;
    }

    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }

    //вызвращаем полученныую яркость
    int dither(int bright, int x, int y, int quantumCount) {
        int quantumSize = (cSize - 1) / quantumCount;
        int resultBright = Math.round(Math.round((bright + quantumSize * ((double) matrix[y % matrix.length][x % matrix.length] / k - 0.5)) / quantumSize) * quantumSize);
        return Math.max(0, Math.min(resultBright, cSize - 1));
    }

    public int getRed() {
        return quantumCountR;
    }
    public int getGreen() {
        return quantumCountG;
    }
    public int getBlue() {
        return quantumCountB;
    }

    public void setRed(int red) {
        this.quantumCountR = red;
        checkRelevanceOfMatrix();
    }
    public void setGreen(int green) {
        this.quantumCountG = green;
        checkRelevanceOfMatrix();
    }
    public void setBlue(int blue) {
        this.quantumCountB = blue;
        checkRelevanceOfMatrix();
    }
}
