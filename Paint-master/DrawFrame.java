
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Objects;

public class DrawFrame extends JFrame
{
  private JToggleButton tglBtnSelect;
  private JToggleButton tglBtnDrawPoint;
  private JToggleButton tglBtnDrawCircle;
  private JToggleButton tglBtnDrawHexagon;
  private JToggleButton tglBtnDrawLine;
  private JToggleButton tglBtnDrawSquare;
  private JToggleButton tglBtnDrawRectangle;
  private JMenuBar menuBar = new JMenuBar();
  private JToolBar toolBar = new JToolBar();
  private final ButtonGroup buttonsGroup;
  private JRadioButtonMenuItem LINE = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem RECTANGLE = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem OVAL = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem FILLER = new JRadioButtonMenuItem();

  private JRadioButtonMenuItem NSIZE = new JRadioButtonMenuItem();

  private JMenu fileMenu = new JMenu("File");
  private String[] fileOptions = {"Clear", "Export To", "Import From", "About"};
  private String[] fileOptionsIcons = {"Clear.gif", "ExportTo.gif", "ImportFrom.gif", "About.gif", "OptionsForRadius.gif", "OptionsForNCorner.gif" , "OptionsForLine.gif" , "ОпцияПоворота.gif","internalRadius.gif","ColorChooser.gif"};

  private JMenu SizeWidth = new JMenu("Options");
  private String[] sizeOptions = {"OptionsForRadius", "OptionsForNCorner", "OptionsForLine", "Опция поворота", "internalRadius(for Star)", "ColorChooser"};

  private JMenu shapeMenu = new JMenu("Shapes");
  private String[] shapeOptions = {"Line", "Rectangle", "Oval" , "Filler"};

  private JMenu methodMenu = new JMenu("Method");
  private String[] methodOptions = {"Fill", "Draw" };

  private JPanel colorPanel = new JPanel();

  private String[] colors = {"Black", "Red", "Green", "Blue", "Cyan", "Magenta", "Yellow"};
  BufferedImage img = new BufferedImage(2560, 1440, BufferedImage.TYPE_INT_RGB);
  DrawPanel panel = new DrawPanel();


  public DrawFrame()
  {
    super("Paint");

    setJMenuBar(menuBar);


    toolBar = new JToolBar("Main toolbar");
    toolBar.setRollover(true);
    add(toolBar, BorderLayout.PAGE_START);
    colorPanel.setLayout(new GridLayout( 1, 6, 10, 10 ));
    add(colorPanel, BorderLayout.NORTH);

    add(panel, BorderLayout.CENTER);
    MenuHandler handler = new MenuHandler();


    addButtonsToJPanel(colorPanel, colors);
    addMenuItemsToJMenu(fileMenu, fileOptions, fileOptionsIcons);
    addMenuItemsToJMenu(shapeMenu, shapeOptions,fileOptionsIcons);
    addMenuItemsToJMenu(methodMenu, methodOptions,fileOptionsIcons);
    addMenuItemsToJMenu(SizeWidth, sizeOptions,fileOptionsIcons);

    JButton btnCalendar = new JButton(new ImageIcon("resources/Clear.png"));
    btnCalendar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.clearDrawing();
      }
    });

    JButton btnClock = new JButton(new ImageIcon("resources/About.gif"));
    btnClock.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Init, version 1.0\nCopyright © 2022 Nikita Kolesnikov, FF, group 19207", "About Init", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    JButton btnContacts = new JButton(new ImageIcon("resources/Exit.gif"));
    btnContacts.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    JButton btnMail = new JButton(new ImageIcon("resources/ColorChooser.jpg"));
    btnMail.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.ColorChooser();
      }
    });

    JButton btnMessages = new JButton(new ImageIcon("resources/ExportTo.jpg"));
    btnMessages.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.exportImageFromJPanel();
      }
    });

    JButton btnPhone = new JButton(new ImageIcon("resources/SaveFrom.png"));
    btnPhone.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.importImageToJPanel();
      }
    });

    JButton btnLine = new JButton(new ImageIcon("resources/line.png"));
    LINE.setSelected(true);
    btnLine.addActionListener(handler);
    btnLine.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.setCurrentShapeType("Line");
        LINE.setSelected(true);
        RECTANGLE.setSelected(false);
      }
    });
    JButton btnRectangle = new JButton(new ImageIcon("resources/rectangle.png"));
    btnRectangle.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LINE.setSelected(false);
        RECTANGLE.setSelected(true);
        panel.setCurrentShapeType("Rectangle");
      }
    });

    JButton btnStar = new JButton(new ImageIcon("resources/star.jpg"));
    btnStar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.setCurrentShapeType("Oval");
      }
    });

    JButton btnFill = new JButton(new ImageIcon("resources/filler.png"));
    btnFill.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.setCurrentShapeType("Filler");
      }
    });

    JButton btnIntRadius = new JButton("Внутренний радиус");
    btnIntRadius.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.OptionsForStar();
      }
    });

    JButton btnRadius = new JButton("Внешний радиус");
    btnRadius.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.Options();
      }
    });
    JButton btnSizeLine = new JButton("Толщина линии");
    btnSizeLine.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.OptionsForLine();
      }
    });
    JButton btnNCorner = new JButton("Количество углов");
    btnNCorner.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.OptionsForNCorner();
      }
    });
    JButton btnUgol = new JButton("Угол поворота следующей фигуры");
    btnUgol.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.OptionsForTurn();
      }
    });
    btnCalendar.setToolTipText("Очистка рабочей области");
    btnClock.setToolTipText("Информация о программе");
    btnContacts.setToolTipText("Выход из программы");
    btnMail.setToolTipText("Выбор цвета для рисования");
    btnMessages.setToolTipText("Сохранение рисунка");
    btnPhone.setToolTipText("Загрузка рисунка для рисования");
    btnLine.setToolTipText("Рисование линий");
    btnRectangle.setToolTipText("Рисование правильных N-угольников");
    btnStar.setToolTipText("Рисование звезд");
    btnFill.setToolTipText("Заливка области");

    btnIntRadius.setToolTipText("Выбор внутреннего радиуса для звезды");
    btnRadius.setToolTipText("Выбор внешнего радиуса для звезды");
    btnSizeLine.setToolTipText("Выбор толщины линии для рисования линии");
    btnNCorner.setToolTipText("Выбор количества углов у фигуры");
    btnUgol.setToolTipText("Выбор угла поворота");

    toolBar.add(btnCalendar);
    toolBar.add(btnClock);
    toolBar.add(btnContacts);
    toolBar.add(btnMail);
    toolBar.add(btnMessages);
    toolBar.add(btnPhone);
    toolBar.add(btnLine);
    toolBar.add(btnRectangle);
    toolBar.add(btnStar);
    toolBar.add(btnFill);

    toolBar.add(btnIntRadius);
    toolBar.add(btnRadius);
    toolBar.add(btnSizeLine);
    toolBar.add(btnNCorner);
    toolBar.add(btnUgol);

    //JToolBar tool = new JToolBar();
//    toolBar.add(new OpenAction());
//    toolBar.add(new ExitAction());
//    toolBar.addSeparator();
//    toolBar.add(new JButton("Стиль"));
//    // Блокируем возможность перетаскивания панели
//    toolBar.setFloatable(false);


//    addToolBarButton("File/Clear");
//    addToolBarSeparator();
//    addToolBarButton("File/About");
    buttonsGroup = new ButtonGroup();


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(640, 480));
    setSize(1000, 1000);;
    setVisible(true);
  }


  public JButton createToolBarButton(JMenuItem item)
  {
    JButton button = new JButton(item.getIcon());
    for(ActionListener listener: item.getActionListeners())
      button.addActionListener(listener);
    button.setToolTipText(item.getToolTipText());
    return button;
  }

  /**
   * Creates toolbar button which will behave exactly like specified menuitem
   * @param menuPath - path to menu item to create toolbar button from
   * @return created toolbar button
   * @see MainFrame.getMenuItem
   */
  public JButton createToolBarButton(String menuPath)
  {
    JMenuItem item = (JMenuItem)getMenuElement(menuPath);
    if(item == null)
      throw new InvalidParameterException("Menu path not found: "+menuPath);
    return createToolBarButton(item);
  }

  public MenuElement getMenuElement(String menuPath)
  {
    MenuElement element = menuBar;
    for(String pathElement: menuPath.split("/"))
    {
      MenuElement newElement = null;
      for(MenuElement subElement: element.getSubElements())
      {
        if((subElement instanceof JMenu && ((JMenu)subElement).getText().equals(pathElement))
                || (subElement instanceof JMenuItem && ((JMenuItem)subElement).getText().equals(pathElement)))
        {
          if(subElement.getSubElements().length==1 && subElement.getSubElements()[0] instanceof JPopupMenu)
            newElement = subElement.getSubElements()[0];
          else
            newElement = subElement;
          break;
        }
      }
      if(newElement == null) return null;
      element = newElement;
    }
    return element;
  }

  /**
   * Creates toolbar button which will behave exactly like specified menuitem and adds it to the toolbar
   * @param menuPath - path to menu item to create toolbar button from
   */
  public void addToolBarButton(String menuPath)
  {
    toolBar.add(createToolBarButton(menuPath));
  }

  /**
   * Adds separator to the toolbar
   */
  public void addToolBarSeparator()
  {
    toolBar.addSeparator();
  }


  public void addMenuItemsToJMenu(JMenu menu, String[] arr, String[] icon) {
    MenuHandler handler = new MenuHandler();
    for (int i = 0; i < arr.length; i++) {
      if(Objects.equals(arr[i], "Line")) {
        LINE = new JRadioButtonMenuItem(arr[i]);
        menu.add(LINE);
        LINE.addActionListener(handler);
      }
      if(Objects.equals(arr[i], "Rectangle")) {
        RECTANGLE = new JRadioButtonMenuItem(arr[i]);
        menu.add(RECTANGLE);
        RECTANGLE.addActionListener(handler);
      }
       if(!Objects.equals(arr[i], "Line") && !Objects.equals(arr[i], "Rectangle")) {
         JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(arr[i]);
         menu.add(menuItem);
         menuItem.addActionListener(handler);
       }
        //menuItem.setIcon(new ImageIcon(getClass().getResource("resources/" + icon[i]), arr[i]));
    }
    menuBar.add(menu);
  }

  public void addButtonsToJPanel(JPanel panel, String[] arr) {
    ColorHandler handler = new ColorHandler();
    for (int i = 0; i < arr.length; i++) {
      JRadioButton button = new JRadioButton(arr[i]);
      button.addActionListener(handler);
      colorPanel.add(button);
    }
  }

  private class ColorHandler implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      String actionCommand = event.getActionCommand();
      switch (actionCommand) {
        case "Black":
          panel.setCurrentShapeColor(Color.BLACK);
          break;
        case "Red":
          panel.setCurrentShapeColor(Color.RED);
          break;
        case "Green":
          panel.setCurrentShapeColor(Color.GREEN);
          break;
        case "Blue":
          panel.setCurrentShapeColor(Color.BLUE);
          break;
        case "Cyan":
          panel.setCurrentShapeColor(Color.CYAN);
          break;
        case "Magenta":
          panel.setCurrentShapeColor(Color.MAGENTA);
          break;
        case "Yellow":
          panel.setCurrentShapeColor(Color.YELLOW);
          break;
      }
    }
  }

  private class MenuHandler implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      String actionCommand = event.getActionCommand();
      switch (actionCommand) {
        case "1":
          panel.setCurrentWidth(1);
        break;
        case "2":
          panel.setCurrentWidth(2);
          break;
        case "3":
          panel.setCurrentWidth(3);
          break;
        case "4":
          panel.setCurrentWidth(4);
          break;
        case "5":
          panel.setCurrentWidth(5);
          break;
        case "6":
          panel.setCurrentWidth(6);
          break;
        case "7":
          panel.setCurrentWidth(7);
          break;
        case "8":
          panel.setCurrentWidth(8);
          break;
        case "9":
          panel.setCurrentWidth(9);
          break;
        case "10":
          panel.setCurrentWidth(10);
          break;
        case "Undo":
          panel.clearLastShape();
          break;
        case "Redo":
          panel.redoLastShape();
        break;
        case "OptionsForRadius":
          panel.Options();
          break;
        case "ColorChooser":
          panel.ColorChooser();
          break;
        case "internalRadius(for Star)":
          panel.OptionsForStar();
          break;
        case "OptionsForNCorner":
          panel.OptionsForNCorner();
          break;
        case "OptionsForLine":
          panel.OptionsForLine();
          break;
        case "Опция поворота":
          panel.OptionsForTurn();
          break;
        case "About":
          JOptionPane.showMessageDialog(null, "Nikita Kolesnikov, FF, group 19207", "About Init", JOptionPane.INFORMATION_MESSAGE);
          break;
        case "Clear":
          panel.clearDrawing();
        break;
        case "Export To":
          panel.exportImageFromJPanel();
          break;
        case "Import From":
          panel.importImageToJPanel();
          break;
        case "Line":
          LINE.setSelected(true);
          RECTANGLE.setSelected(false);
          panel.setCurrentShapeType("Line");
          break;
        case "Filler":
          panel.setCurrentShapeType("Filler");
          break;
        case "Rectangle":
          RECTANGLE.setSelected(true);
          LINE.setSelected(false);
          panel.setCurrentShapeType("Rectangle");
          break;
        case "Oval":
          panel.setCurrentShapeType("Oval");
          break;
        case "Fill":
          panel.setCurrentShapeFilled(true);
          break;
        case "Draw":
          panel.setCurrentShapeFilled(false);
          break;
      }
    }
  }
}

class ExitAction extends AbstractAction {
  private static final long serialVersionUID = 1L;
  public ExitAction() {
    // Настройка иконок
    putValue(AbstractAction.SMALL_ICON, new ImageIcon("resources/Exit.gif"));
  }
  // Обработка действия
  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}

// Команда для кнопки "Открытия"
class OpenAction extends AbstractAction {
  private static final long serialVersionUID = 1L;
  public OpenAction() {
    // Настройка иконок
    putValue(AbstractAction.SMALL_ICON, new ImageIcon("resources/About.gif"));
  }
  // Обработка действия
  public void actionPerformed(ActionEvent e) {
    // ничего не делаем
  }
}
