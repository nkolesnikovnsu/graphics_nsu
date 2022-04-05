package ICGFilter.Dialogs;

import ICGFilter.Filters.OrderedDither;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class OrderedDitherDialog extends JPanel implements MyDialog {
    final int MIN_VALUE = 2;
    final int MAX_VALUE = 128;
    final int MAX_VALUE_LENGTH = 3;
    private boolean isOk = false;
    OrderedDither orderedDither;
    JTextField blueText;
    JTextField greenText;
    JTextField redText;
    TextAndSlider ts;

    public OrderedDitherDialog(OrderedDither orderedDither) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(3, 3));
        JPanel buttonPanel = new JPanel();
        this.orderedDither = orderedDither;
        ts = createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, orderedDither.getRed());
        paramsPanel.add(new Label("Красный:"));
        redText = ts.text;
        paramsPanel.add(redText);
        paramsPanel.add(ts.slider);

        ts = createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, orderedDither.getGreen());
        paramsPanel.add(new Label("Зеленый:"));
        greenText = ts.text;
        paramsPanel.add(greenText);
        paramsPanel.add(ts.slider);

        ts = createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, orderedDither.getBlue());
        paramsPanel.add(new Label("Синий:"));
        blueText = ts.text;
        paramsPanel.add(blueText);
        paramsPanel.add(ts.slider);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel p = this;
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            orderedDither.setBlue(Integer.parseInt(blueText.getText()));
            orderedDither.setGreen(Integer.parseInt(greenText.getText()));
            orderedDither.setRed(Integer.parseInt(redText.getText()));
            isOk = true;
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            blueText.setText(String.valueOf(orderedDither.getBlue()));
            greenText.setText(String.valueOf(orderedDither.getGreen()));
            redText.setText(String.valueOf(orderedDither.getRed()));
            isOk = false;
            frame.dispose();
        });
        add(paramsPanel);
        add(buttonPanel);
    }

    public static TextAndSlider createDefault(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
        TextAndSlider ts = createTextAndSLider(minvalue,maxvalue,maxlength,defaultvalue);
        ts.text.addPropertyChangeListener(evt -> {
            if("".equals(ts.text.getText())){
                ts.text.setText(String.valueOf(1));
                return;
            }
            int inputValue=Integer.parseInt(ts.text.getText());
            if(minvalue>inputValue){
                ts.text.setText(String.valueOf(minvalue));
                JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
                return;
            }
            if(maxvalue<inputValue) {
                ts.text.setText(String.valueOf(maxvalue));
                JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
                return;
            }
            int value = Integer.parseInt(ts.text.getText());
            ts.slider.setValue(value);
        });
        return ts;
    }

    private static TextAndSlider createTextAndSLider(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
        JFormattedTextField text =new JFormattedTextField();
        text.setPreferredSize(new Dimension(35,20));
        JSlider jSlider = new JSlider(minvalue, maxvalue, defaultvalue);
        text.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
                if(str.length()+getLength()>maxlength){
                    return;
                }
                for(int i=0;i<str.length();++i){
                    if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != '-'){
                        return;
                    }
                }
                super.insertString(offs,str,a);
            }
        });
        text.setText(String.valueOf(defaultvalue));
        text.setPreferredSize(new Dimension(35,20));
        text.setText(String.valueOf(defaultvalue));
        text.setVisible(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        jSlider.addChangeListener(changeEvent -> {
            int value = (jSlider.getValue());
            text.setText(String.valueOf(value));
        });
        return new TextAndSlider(text,jSlider);
    }

    @Override
    public boolean isDialogResult() {
        return isOk;
    }
}