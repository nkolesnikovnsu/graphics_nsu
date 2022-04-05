package ICGFilter.Dialogs;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Listener implements PropertyChangeListener {
    private double minvalue;
    private double maxvalue;
    private JFormattedTextField text;
    private JSlider slider;
    private int system;

    public Listener(double minvalue, double maxvalue, JFormattedTextField text, JSlider slider, int system) {
        super();
        this.maxvalue = maxvalue;
        this.minvalue = minvalue;
        this.text = text;
        this.slider = slider;
        this.system = system;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(system == 1){
            if ("".equals(text.getText())) {
                text.setText(String.valueOf(1));
                return;
            }
            double inputValue = Double.parseDouble(text.getText());
            if (minvalue > inputValue) {
                text.setText(String.valueOf(minvalue));
                return;
            }
            if (maxvalue < inputValue) {
                text.setText(String.valueOf(maxvalue));
                return;
            }
            double value = Double.parseDouble(text.getText());
            slider.setValue((int)(Double.parseDouble(text.getText())*10));

        }else{

        if ("".equals(text.getText())) {
            text.setText(String.valueOf(1));
            return;
        }

        double inputValue = Double.parseDouble(text.getText());
        if (minvalue > inputValue) {
            text.setText(String.valueOf(minvalue));
            return;
        }

        if (maxvalue < inputValue) {
            text.setText(String.valueOf(maxvalue));
            return;
        }
        if (inputValue % 2 == 0) {
            inputValue+=1;
            JOptionPane.showMessageDialog(null,"Введите нечетное значение");
            text.setText(String.valueOf(inputValue));
            return;
        }
        double value = Double.parseDouble(text.getText());
        slider.setValue(Integer.parseInt(text.getText()));
    }

    }
}
