package ICGFilter.Dialogs;

import ICGFilter.Filters.Rotate;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class RotateDialog extends JPanel implements MyDialog {
    final int MIN_VALUE = -180;
    final int MAX_VALUE = 180;
    final int MAX_VALUE_LENGTH = 4;
    private boolean isOk = false;
    JTextField valueText;
    Rotate rotate;
    TextAndSlider ts;

    public RotateDialog(Rotate rotate) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.rotate = rotate;
        ts = createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, rotate.getAngle());
        paramsPanel.add(new Label("Угол поворота:"));
        valueText = ts.text;
        paramsPanel.add(valueText);
        paramsPanel.add(ts.slider);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel p = this;
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            rotate.setAngle(Integer.parseInt(valueText.getText()));
            isOk = true;
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            valueText.setText(String.valueOf(rotate.getAngle()));
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