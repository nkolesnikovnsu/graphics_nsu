package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public interface Filter {
    BufferedImage doWork(BufferedImage image);

    MyDialog getParameterDialog();
}
