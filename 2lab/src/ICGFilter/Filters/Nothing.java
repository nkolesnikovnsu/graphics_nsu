package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public class Nothing implements Filter {
    @Override
    public BufferedImage doWork(BufferedImage image) {
        return image;
    }

    @Override
    public MyDialog getParameterDialog() {
        return null;
    }
}
