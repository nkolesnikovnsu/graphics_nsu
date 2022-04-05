package ICGFilter;

import ICGFilter.Dialogs.MyDialog;
import ICGFilter.InitView.CurrentFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class InitMainWindow extends MainFrame {
    InitView view;
    int lastx;
    int lasty;
    private JScrollPane scrollPane;
    private boolean isFitToScreenMode = false;
    private JPanel panelWithScroll;
    MouseAdapter ma;
    String aboutText;
    private JRadioButtonMenuItem fitToScreenMenuItem;
    private JToggleButton fitToScreenButton;
    private JToggleButton blur;
    private JToggleButton roberts;
    private JToggleButton inversion;
    private JToggleButton sobel;
    private JToggleButton emboss;
    private JToggleButton gamma;
    private JToggleButton ordered;
    private JToggleButton aqua;
    private JToggleButton floyd;
    private JToggleButton grey;
    private JToggleButton narash;
    private JToggleButton sharp;
    private JToggleButton rotate;
    private ButtonGroup Buttons;
    private ButtonGroup Menu;
    private JRadioButtonMenuItem mblur;
    private JRadioButtonMenuItem mroberts;
    private JRadioButtonMenuItem minversion;
    private JRadioButtonMenuItem msobel;
    private JRadioButtonMenuItem memboss;
    private JRadioButtonMenuItem mgamma;
    private JRadioButtonMenuItem mordered;
    private JRadioButtonMenuItem maqua;
    private JRadioButtonMenuItem mfloyd;
    private JRadioButtonMenuItem mgrey;
    private JRadioButtonMenuItem mnarash;
    private JRadioButtonMenuItem msharp;
    private JRadioButtonMenuItem mrotate;


    public InitMainWindow() {
        super(1000, 1000, "PhotoShop");
        try {
            Buttons = new ButtonGroup();
            Menu = new ButtonGroup();
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Open", "Open image", KeyEvent.VK_X, "open.png", "onOpen", false);
            addMenuItem("File/Save", "Save image", KeyEvent.VK_X, "save.png", "onSave", false);
            addMenuItem("File/Apply", "apply changes", KeyEvent.VK_X, "apply.png", "onApply", false);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit", false);
            addSubMenu("Filter", KeyEvent.VK_F);

            minversion = (JRadioButtonMenuItem) addMenuItem("Filter/Inversion", "Инверсия", KeyEvent.VK_X, "invert.png", "onInverison", true);
            Menu.add(minversion);

            mblur = (JRadioButtonMenuItem) addMenuItem("Filter/Blur", "Блюр", KeyEvent.VK_X, "blur.png", "onBlur", true);
            Menu.add(mblur);

            mroberts = (JRadioButtonMenuItem) addMenuItem("Filter/Roberts", "Робертса", KeyEvent.VK_X, "roberts.png", "onRoberts", true);
            Menu.add(mroberts);

            msobel = (JRadioButtonMenuItem) addMenuItem("Filter/Sobel", "Собеля", KeyEvent.VK_X, "sobel.png", "onSobel", true);
            Menu.add(msobel);

            memboss = (JRadioButtonMenuItem) addMenuItem("Filter/Emboss", "Тиснение", KeyEvent.VK_X, "emboss.png", "onEmboss", true);
            Menu.add(memboss);

            mgamma = (JRadioButtonMenuItem) addMenuItem("Filter/Gamma", "Гамма коррекция", KeyEvent.VK_X, "gamma.png", "onGamma", true);
            Menu.add(mgamma);

            mordered = (JRadioButtonMenuItem) addMenuItem("Filter/OrderedDither", "Упорядоченный дизеринг", KeyEvent.VK_X, "ordered.png", "onOrderedDither", true);
            Menu.add(mordered);

            maqua = (JRadioButtonMenuItem) addMenuItem("Filter/Aquarelle", "Акварелизация", KeyEvent.VK_X, "aquarelle.png", "onAquarelle", true);
            Menu.add(maqua);

            mfloyd = (JRadioButtonMenuItem) addMenuItem("Filter/FloydDither", "Дизеринг Флойда", KeyEvent.VK_X, "floyd.png", "onFloydDither", true);
            Menu.add(mfloyd);

            mgrey = (JRadioButtonMenuItem) addMenuItem("Filter/Grey", "Серый", KeyEvent.VK_X, "grey.png", "onGrey", true);
            Menu.add(mgrey);

            mnarash = (JRadioButtonMenuItem) addMenuItem("Filter/BuildUp", "Фильтр наращивание", KeyEvent.VK_X, "buildup.png", "onBuildUp", true);
            Menu.add(mnarash);

            msharp = (JRadioButtonMenuItem) addMenuItem("Filter/Sharp", "Резкость", KeyEvent.VK_X, "sharp.png", "onSharp", true);
            Menu.add(msharp);

            addSubMenu("Parameters", KeyEvent.VK_H);
            addMenuItem("Parameters/Parameters", "Открытие окна с параметрами", KeyEvent.VK_X, "settings.png", "onParametersButton", false);
            mrotate = (JRadioButtonMenuItem) addMenuItem("Parameters/Rotate", "Поворот изображения", KeyEvent.VK_X, "rotate.png", "onRotate", true);
            Menu.add(mrotate);
            fitToScreenMenuItem = (JRadioButtonMenuItem) addMenuItem("Parameters/FitToScreen", "Режим отображения", KeyEvent.VK_X, "resize.png", "onFitToScreen", true);
            addMenuItem("Parameters/FitSettings", "Настройки режима дисплея", KeyEvent.VK_X, "FitSettings.png", "onFitSettings", false);
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "О программе", KeyEvent.VK_A, "about.png", "onAbout", false);
            addToolBarButton("File/Open", true, "File/Open","open.png");
            addToolBarButton("File/Save", true, "File/Save", "save.png");
            addToolBarButton("File/Apply", true, "File/Apply","apply.png");
            addToolBarSeparator();
            sharp = (JToggleButton) addToolBarButton("Filter/Sharp", true,"Filter/Sharp", "sharp.png");
            Buttons.add(sharp);
            blur = (JToggleButton) addToolBarButton("Filter/Blur", true,"Filter/Blur", "blur.png" );
            Buttons.add(blur);
            emboss = (JToggleButton) addToolBarButton("Filter/Emboss", true,"Filter/Emboss","emboss.png");
            Buttons.add(emboss);
            aqua = (JToggleButton) addToolBarButton("Filter/Aquarelle", true,"Filter/Aquarelle","aquarelle.png");
            Buttons.add(aqua);
            addToolBarSeparator();
            inversion = (JToggleButton) addToolBarButton("Filter/Inversion", true,"Filter/Inversion","invert.png");
            Buttons.add(inversion);
            grey = (JToggleButton) addToolBarButton("Filter/Grey", true,"Filter/Grey","grey.png");
            Buttons.add(grey);
            gamma = (JToggleButton) addToolBarButton("Filter/Gamma", true,"Filter/Gamma","gamma.png");
            Buttons.add(gamma);
            addToolBarSeparator();
            roberts = (JToggleButton) addToolBarButton("Filter/Roberts", true,"Filter/Roberts","roberts.png");
            Buttons.add(roberts);
            sobel = (JToggleButton) addToolBarButton("Filter/Sobel", true,"Filter/Sobel","sobel.png");
            Buttons.add(sobel);
            narash = (JToggleButton) addToolBarButton("Filter/BuildUp", true, "Filter/BuildUp","buildup.png");
            Buttons.add(narash);
            addToolBarSeparator();
            ordered = (JToggleButton) addToolBarButton("Filter/OrderedDither", true,"Filter/OrderedDither","ordered.png");
            Buttons.add(ordered);
            floyd = (JToggleButton) addToolBarButton("Filter/FloydDither", true,"Filter/FloydDither","floyd.png");
            Buttons.add(floyd);
            addToolBarSeparator();
            rotate = (JToggleButton) addToolBarButton("Parameters/Rotate", true,"Parameters/Rotate", "rotate.png");
            Buttons.add(rotate);
            addToolBarButton("Parameters/Parameters", true,"Parameters/Parameters","settings.png");
            fitToScreenButton = (JToggleButton) addToolBarButton("Parameters/FitToScreen", true,"Parameters/FitToScreen","resize.png");
            fitToScreenButton.setSelected(isFitToScreenMode);
            addToolBarButton("Parameters/FitSettings", true,"Parameters/FitSettings","FitSettings.png");
            addToolBarSeparator();
            addToolBarButton("Help/About...", true,"Help/About...","about.png");
            view = new InitView();
            panelWithScroll = new JPanel();
            panelWithScroll.setLayout(new BorderLayout());
            panelWithScroll.add(view);
            add(panelWithScroll);
            setFitToScreen(isFitToScreenMode);
            this.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    view.setImageSize();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        setMinimumSize(new Dimension(640, 480));
        pack();
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(null, "lab2 Nikita 19207", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onExit() {
        System.exit(0);
    }

    public void onSave() {
        File file = FileUtils.getSaveImageFileName(this);
        try {
            if(file != null) {
                view.save(file);
            } else {
                System.out.println("save error");
            }
        } catch (IOException e) {
            System.out.println("save error");
        }
    }

    public void onApply() {
        view.applyChanges();
        view.setFilter(CurrentFilter.NONE);
        setButton(CurrentFilter.NONE);
    }
    public void onGamma() {
        selectInstrument(gamma,mgamma, CurrentFilter.GAMMA);
    }

    public void onInverison() {
        selectInstrument(inversion,minversion, CurrentFilter.INVERSION);
    }

    private void selectInstrument(JToggleButton b, JRadioButtonMenuItem m, CurrentFilter instrument) {
        if(instrument == view.getFilter()) {
            Menu.clearSelection();
            Buttons.clearSelection();
            view.setFilter(CurrentFilter.NONE);
            view.useFilter();
            return;
        }
        boolean isOk = onParameters(instrument);

        if(isOk) {
            Buttons.clearSelection();
            Buttons.clearSelection();
            b.setSelected(true);
            m.setSelected(true);
            view.setFilter(instrument);
            view.useFilter();
        } else {
            setButton(view.getFilter());
        }
    }

    private void setButton(CurrentFilter instrument) {
        Buttons.clearSelection();
        Menu.clearSelection();
        switch (instrument) {
            case AQUARELLE:
                aqua.setSelected(true);
                maqua.setSelected(true);
                break;
            case BLUR:
                blur.setSelected(true);
                mblur.setSelected(true);
                break;
            case EMBOSS:
                emboss.setSelected(true);
                memboss.setSelected(true);
                break;
            case FLOYD:
                floyd.setSelected(true);
                mfloyd.setSelected(true);
                break;
            case GAMMA:
                gamma.setSelected(true);
                mgamma.setSelected(true);
                break;
            case GREY:
                grey.setSelected(true);
                mgrey.setSelected(true);
                break;
            case INVERSION:
                inversion.setSelected(true);
                minversion.setSelected(true);
                break;
            case ORDERED:
                ordered.setSelected(true);
                mordered.setSelected(true);
                break;
            case ROBERTS:
                roberts.setSelected(true);
                mroberts.setSelected(true);
                break;
            case ROTATE:
                rotate.setSelected(true);
                mrotate.setSelected(true);
                break;
            case BUILDUP:
                narash.setSelected(true);
                mnarash.setSelected(true);
            case SHARP:
                sharp.setSelected(true);
                msharp.setSelected(true);
                break;
            case SOBEL:
                sobel.setSelected(true);
                msobel.setSelected(true);
                break;
            case NONE:
                break;
        }
    }

    public void onEmboss() {
        selectInstrument(emboss,memboss, CurrentFilter.EMBOSS);
    }

    public void onBuildUp(){
        selectInstrument(narash,mnarash,CurrentFilter.BUILDUP);
    }

    public void onOrderedDither() {
        selectInstrument(ordered,mordered, CurrentFilter.ORDERED);
    }

    public void onGrey() {
        selectInstrument(grey,mgrey, CurrentFilter.GREY);
    }

    public void onBlur() {
        selectInstrument(blur,mblur, CurrentFilter.BLUR);
    }

    public void onSharp() {
        selectInstrument(sharp,msharp, CurrentFilter.SHARP);
    }

    public void onFloydDither() {
        selectInstrument(floyd,mfloyd, CurrentFilter.FLOYD);
    }

    public void onAquarelle() {
        selectInstrument(aqua,maqua,CurrentFilter.AQUARELLE);
    }

    public void onRoberts() {
        selectInstrument(roberts,mroberts, CurrentFilter.ROBERTS);
    }

    public void onSobel() {
        selectInstrument(sobel,msobel, CurrentFilter.SOBEL);
    }

    public void onRotate() {
        selectInstrument(rotate,mrotate, CurrentFilter.ROTATE);
        setScreen();
    }

    public void onOpen() {
        File image = FileUtils.getOpenImageFileName(this);
        view.open(image);
        view.setFilter(CurrentFilter.NONE);
        setButton(CurrentFilter.NONE);
        if (scrollPane != null) {
            scrollPane.revalidate();
        }
    }

    public boolean onParameters(MyDialog p) {
        System.out.println(p);
        if(p != null) {
            JDialog dialog = new JDialog(this,"Set parameters",true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.add((Component) p);
            dialog.pack();
            dialog.setBounds((int)(screenSize.getWidth()/2 - dialog.getWidth()/2),(int)(screenSize.getHeight()/2 - dialog.getHeight()/2),dialog.getWidth(),dialog.getHeight());
            dialog.setVisible(true);
            return p.isDialogResult();
        }
        return true;
    }

    public boolean onParameters() {
        return onParameters(view.getParametersPanel());
    }

    public void onParametersButton() {
        if(onParameters()) {
            view.useFilter();
        }
    }

    public boolean onParameters(CurrentFilter eInstrument) {
        return onParameters(eInstrument.getFilter().getParameterDialog());
    }

    public void onFitToScreen() {
        isFitToScreenMode = !isFitToScreenMode;
        setFitToScreen(isFitToScreenMode);
    }

    public void setScreen(){
        panelWithScroll.setPreferredSize(panelWithScroll.getSize());
        pack();
        view.setImage();
        panelWithScroll.setPreferredSize(null);
        repaint();
    }

    private void setFitToScreen(boolean isFitToScreenMode) {
        fitToScreenButton.setSelected(isFitToScreenMode);
        fitToScreenMenuItem.setSelected(isFitToScreenMode);
        if (isFitToScreenMode) {
            view.showFitDialog();
            removeScrolls();
        } else {
            addScrolls();
        }
        view.setFitToScreen(isFitToScreenMode);
        panelWithScroll.setPreferredSize(panelWithScroll.getSize());
        pack();
        view.setImage();
        panelWithScroll.setPreferredSize(null);
        repaint();
    }

    public void onFitSettings() {
        view.showFitDialog();
        if(isFitToScreenMode) {
            view.setImage();
        }
    }

    private void addScrolls() {
        panelWithScroll.remove(view);
        view.setAutoscrolls(true);
        scrollPane = new JScrollPane(view);
        panelWithScroll.add(scrollPane);
        ma = new MouseAdapter() {
            private Point origin;
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
                lastx = e.getX();
                lasty = e.getY();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, view);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();
                        Point scroll = viewPort.getViewPosition();
                        scroll.x += ( lastx - e.getX() );
                        scroll.y += ( lasty - e.getY() );

                        Rectangle rect = viewPort.getViewRect();
                        rect.x += deltaX;
                        rect.y += deltaY;
                        view.scrollRectToVisible(rect);
                    }
                }
            }
        };
        view.addMouseListener(ma);
        view.addMouseMotionListener(ma);
        scrollPane.revalidate();
    }

    private void removeScrolls() {
        panelWithScroll.remove(scrollPane);
        view.setAutoscrolls(false);
        view.removeMouseListener(ma);
        view.removeMouseMotionListener(ma);
        panelWithScroll.add(view);
    }
}
