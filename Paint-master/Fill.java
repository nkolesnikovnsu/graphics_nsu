import java.awt.*;
import java.awt.image.BufferedImage;

public class Fill {
    private int y;
    private int x0; // left span border
    private int x1; // right span border
    private BufferedImage image;

    public Fill(int y, int x0, int x1, BufferedImage image) {
        this.y = y;
        this.x0 = x0;
        this.x1 = x1;
        this.image = image;
    }

    public void fill(Color c) {
        for (int x = (x0 + 1); x < x1; x ++) {
            image.setRGB(x, y, c.getRGB());
        }
    }

    public int getY() {
        return y;
    }

    public int getX0() {
        return x0;
    }

    public int getX1() {
        return x1;
    }
}