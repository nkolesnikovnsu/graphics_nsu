import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

class SliderTest extends JFrame implements ChangeListener {

    static JSlider slider;
    static JLabel label;
    static JButton ok;
    static JTextField textField;
    public static int curwidth;
    //public int curwidth;

    public static void runSliderTest(int width) {
        // Create a new frame
        JFrame frame = new JFrame("Толщина линии");

        // Create an object
        SliderTest obj = new SliderTest();

        // Create a label
        //label = new JLabel();

        // Create a panel
        JPanel p = new JPanel();

        // Create a slider
        slider = new JSlider(0, 100, width);
        // Paint the track and label
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        // Set the spacing
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);

        ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWidth(slider.getValue());
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        // Associate the Listener with the slider
        slider.addChangeListener(obj);
        // Add the slider to the panel
        p.add(slider);
       // p.add(label);

        textField = new JTextField(10);
        textField.setToolTipText("Толщина линии");
        textField.setPreferredSize(new Dimension(50,50));
        p.add(textField);
        p.add(ok);

        // Add the panel to the frame
        frame.add(p);
        // Set the label text
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Отображение введенного текста
                slider.setValue(Integer.parseInt(textField.getText()));
                label.setText("The value of the Slider is : " + slider.getValue());
            }
        });
        //label.setText("The value of the Slider is : " + slider.getValue());

        frame.setSize(300, 150);
        frame.show();
    }

    public static void saveWidth(int width){
        curwidth = width;
    }


    // Run this method if the value of the slider is modified
    public void stateChanged(ChangeEvent e)
    {
        //curwidth = Integer.parseInt(textField.getText());
        //label.setText("The value of the Slider is : " + slider.getValue());
        curwidth = slider.getValue();
        textField.setText("" + curwidth);
    }
}
