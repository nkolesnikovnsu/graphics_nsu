package ICGFilter.Filters;

import ICGFilter.Dialogs.AquarelleSettingWindow;
import ICGFilter.Dialogs.MyDialog;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aquarelle implements Filter {
    private int size = 3;
    AquarelleSettingWindow dialog;

    public Aquarelle() {
        dialog = new AquarelleSettingWindow(this);
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage finalimage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int resultA = (mainPixel & 0xFF000000) >> 24;

                List<Integer> RpixelList = new ArrayList<>();
                List<Integer> GpixelList = new ArrayList<>();
                List<Integer> BpixelList = new ArrayList<>();

                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (x + i >= 0 & x + i < image.getWidth() & y + j >= 0 & y + j < image.getHeight()) {
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
                Collections.sort(RpixelList);
                Collections.sort(GpixelList);
                Collections.sort(BpixelList);
                int resultR = RpixelList.get(RpixelList.size()/2);
                int resultG = GpixelList.get(GpixelList.size()/2);
                int resultB = BpixelList.get(BpixelList.size()/2);
                int resultPixel = resultB | (resultG << 8) | (resultR << 16) | (resultA << 24);
                finalimage.setRGB(x, y, resultPixel);
            }
        }
        Sharp s = new Sharp();
        return s.doWork(finalimage);
    }

    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }

    public void setValue(int size) {
        this.size = size;
    }

    public int getValue() {
        return size;
    }
}
