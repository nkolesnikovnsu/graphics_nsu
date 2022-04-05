package ICGFilter;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.Filters.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class InitView extends JPanel {

    private int shift = 4;
    private BufferedImage imageToPaint;
    private BufferedImage RefactoredImage;
    private BufferedImage originalImage;
    private Filter filter;
    static private Gamma gamma = new Gamma();
    static private Grey grey = new Grey();
    static private Scaler scaler = new Scaler();
    static private Inversion inversion = new Inversion();
    static private Blur blur = new Blur();
    static private Sharp sharp = new Sharp();
    static private Emboss emboss = new Emboss();
    static private OrderedDither orderedDither = new OrderedDither();
    static private Aquarelle aquarelle = new Aquarelle();
    static private FloydDither floydDither = new FloydDither();
    static private Sobel sobel = new Sobel();
    static private Roberts roberts = new Roberts();
    static private Rotate rotate = new Rotate();
    static private Nothing nothing = new Nothing();
    static private BuildUp buildup = new BuildUp();
    static private CurrentFilter cur_filter;
    private int width = 640;
    private int height = 480;
    private boolean isInEffect = false;
    private JLabel label;

    public InitView() {
        filter = null;
        cur_filter = CurrentFilter.NONE;
        RefactoredImage
                = null;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onMouseClick();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float[] dash1 = {2f, 0f, 2f};
        BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
        g2d.setStroke(bs1);
        if(isInEffect) {
            imageToPaint = scaler.scale(RefactoredImage
                    , width, height);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } else {
            imageToPaint =  scaler.scale(originalImage, width, height);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        if (imageToPaint != null) {
            g2d.drawRect(shift, shift, imageToPaint.getWidth() + 1, imageToPaint.getHeight() + 1);
            g.drawImage(imageToPaint, shift + 1, shift + 1, null);
        } else {
            g2d.drawRect(shift, shift, getWidth() - 2 * shift, getHeight() - 2 * shift);
        }
    }

    private void openError() {
        JOptionPane.showMessageDialog(this, "Ошибка открытия", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void open(File file) {
        BufferedImage newImage;
        if (file == null) {
            return;
        }
        try {
            newImage = ImageIO.read(file);
        } catch (IOException e) {
            openError();
            return;
        }
        if (newImage == null) {
            openError();
            return;
        }
        isInEffect = false;
        originalImage = newImage;
        setImage();
    }

    public void setImage(){
        width = getWidth() - 2 * shift - 2;
        height = getHeight() - 2 * shift - 2;
        paintImage();
        if(!scaler.getFitToScreen() && originalImage!=null) {
            if(!isInEffect){
                setPreferredSize(new Dimension(originalImage.getWidth() + 2*shift + 1, originalImage.getHeight() + 2*shift + 1));
            }
            else{
                setPreferredSize(new Dimension(RefactoredImage
                        .getWidth() + 2*shift + 1, RefactoredImage
                        .getHeight() + 2*shift + 1));
            }
        }
    }

    void paintImage() {
        if (isInEffect) {
            imageToPaint = scaler.scale(RefactoredImage
                    , RefactoredImage
                            .getWidth(), RefactoredImage
                            .getHeight());
        } else {
            if (originalImage == null) {
                return;
            }
            imageToPaint = scaler.scale(originalImage, width, height);
        }
        repaint();
    }

    private void onMouseClick() {
        if (!isInEffect) {
            if (RefactoredImage
                    == null) {
                if (filter != null && originalImage != null) {
                    RefactoredImage
                            = filter.doWork(originalImage);
                }
            }
        }
        isInEffect = !isInEffect;
        paintImage();
    }

    public void useFilter() {
        if (filter != null && originalImage != null) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RefactoredImage
                    = filter.doWork(originalImage);
            isInEffect = true;
        }
        paintImage();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void setFilter(CurrentFilter cur_filter) {
        isInEffect = false;
        RefactoredImage
                = null;
        InitView.cur_filter = cur_filter;
        filter = cur_filter.getFilter();
    }

    public CurrentFilter getFilter() {
        return cur_filter;
    }

    public MyDialog getParametersPanel() {
        return filter.getParameterDialog();
    }

    public void setImageSize() {
        if(scaler.getFitToScreen() && originalImage != null) {
            this.width = getWidth() - 2*shift - 2;
            this.height = getHeight() - 2*shift - 2;
            paintImage();
        }
    }

    public void setFitToScreen(boolean isFitToScreenMode) {
        scaler.setFitToScreen(isFitToScreenMode);
        setImage();
    }

    public void showFitDialog() {
        JOptionPane.showConfirmDialog(null, scaler.getParameterDialog(), "Задайте параметры", JOptionPane.DEFAULT_OPTION);
    }

    public void applyChanges() {
        if(isInEffect & RefactoredImage != originalImage) {
            originalImage = RefactoredImage;
        }
        isInEffect = false;
    }

    public void save(File image) throws IOException {
        if(isInEffect) {
            ImageIO.write(RefactoredImage, "png" , image);
        } else if(originalImage != null) {
            ImageIO.write(originalImage, "png" , image);
        } else {
            JOptionPane.showMessageDialog(this, "no image",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    enum CurrentFilter{
        AQUARELLE(aquarelle),
        BLUR(blur),
        EMBOSS(emboss),
        FLOYD(floydDither),
        GAMMA(gamma),
        GREY(grey),
        INVERSION(inversion),
        ORDERED(orderedDither),
        ROBERTS(roberts),
        ROTATE(rotate),
        SHARP(sharp),
        SOBEL(sobel),
        NONE(nothing),
        BUILDUP(buildup);
        Filter filter;
        CurrentFilter(Filter filter) {
            this.filter = filter;
        }
        public Filter getFilter() {
            return filter;
        }
    }
}
