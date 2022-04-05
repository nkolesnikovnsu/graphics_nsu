package ICGFilter.Filters;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.Dialogs.ScalerDialog;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Scaler implements Filter {
    boolean isFitToScreen = true;
    ScalerDialog dialog;
    private int transformType = AffineTransformOp.TYPE_BILINEAR;
    public Scaler() {
        dialog = new ScalerDialog(this);
    }

    public BufferedImage scale(BufferedImage image, int width, int height) {
        BufferedImage final_image;
        if(image == null) {
            return null;
        }
        final_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        if (isFitToScreen) {
            if (image.getWidth() > width || image.getHeight() > height) {
                AffineTransform at = new AffineTransform();
                double scale;
                double scaleW = 1;
                double scaleH = 1;
                if (image.getWidth() > width) {
                    scaleW = (double) width / image.getWidth();
                }
                if (image.getHeight() > height) {
                    scaleH = (double) height / image.getHeight();
                }
                scale = Math.min(scaleW, scaleH);
                at.scale(scale, scale);
                AffineTransformOp scaleOp =
                        new AffineTransformOp(at, transformType);
                final_image = new BufferedImage((int)(image.getWidth() * scale), (int)(image.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);
                final_image = scaleOp.filter(image, final_image);
                return final_image;
            }
            final_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            final_image.getGraphics().drawImage(image, 0, 0, null);
        } else {
            final_image.getGraphics().drawImage(image,0,0,null);
        }
        return final_image;
    }

    public boolean getFitToScreen() {
        return isFitToScreen;
    }

    public void setFitToScreen(boolean isFitToScreen) {
        this.isFitToScreen = isFitToScreen;
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        return null;
    }

    @Override
    public MyDialog getParameterDialog() {
        return dialog;
    }


    public void setTransformType(TransformType transformType) {
        switch (transformType) {
            case BICUBIC:
                this.transformType = AffineTransformOp.TYPE_BICUBIC;
                break;
            case BILINEAR:
                this.transformType = AffineTransformOp.TYPE_BILINEAR;
                break;
            case NEAREST_NEIGHBOR:
                this.transformType = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
                break;
        }

    }

    public enum TransformType {
        NEAREST_NEIGHBOR,
        BILINEAR,
        BICUBIC
    }
}