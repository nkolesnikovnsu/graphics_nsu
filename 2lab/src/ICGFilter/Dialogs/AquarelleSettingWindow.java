//package ICGFilter.Dialogs;
//
//import ICGFilter.Filters.Aquarelle;
//
//import javax.swing.*;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.PlainDocument;
//import java.awt.*;
//
//public class AquarelleSettingWindow extends JPanel implements MyDialog {
//    private static int d = 10;
//    final int MIN_VALUE = 1;
//    final int MAX_VALUE = 100;
//    final int MAX_VALUE_LENGTH = 3;
//    int SYSTEM = 0;
//    public JFormattedTextField valueText;
//    TextAndSlider ts;
//
//    private boolean isOk = false;
//
//    Aquarelle aquarelle;
//    public AquarelleSettingWindow(Aquarelle aquarelle) {
//        super();
//        setLayout(new GridLayout(2, 1));
//        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//        this.aquarelle = aquarelle;
//        ts = createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, aquarelle.getValue());
//        paramsPanel.add(new Label("Выберите значение:"));
//        valueText = ts.text;
//        paramsPanel.add(ts.text);
//        paramsPanel.add(ts.slider);
//        JButton okButton = new JButton("OK");
//        JButton cancelButton = new JButton("cancel");
//        buttonPanel.add(okButton);
//        buttonPanel.add(cancelButton);
//
//        okButton.addActionListener(e -> {
//            JDialog frame = (JDialog)this.getRootPane().getParent();
//            aquarelle.setValue(Integer.parseInt(valueText.getText()));
//            isOk = true;
//            frame.dispose();
//        });
//
//        cancelButton.addActionListener(e -> {
//            JDialog frame = (JDialog)this.getRootPane().getParent();
//            valueText.setText(String.valueOf(aquarelle.getValue()));
//            isOk = false;
//            frame.dispose();
//        });
//        add(paramsPanel);
//        add(buttonPanel);
//    }
//
//    public static TextAndSlider createDefault(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
//        TextAndSlider ts = createTextAndSLider(minvalue,maxvalue,maxlength,defaultvalue);
//        ts.text.addPropertyChangeListener(evt -> {
//            if("".equals(ts.text.getText())){
//                ts.text.setText(String.valueOf(1));
//                return;
//            }
//
//            double inputValue = Double.parseDouble(ts.text.getText());
//            if(minvalue > inputValue){
//                ts.text.setText(String.valueOf(minvalue));
//                JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
//                return;
//            }
//            if(maxvalue < inputValue) {
//                ts.text.setText(String.valueOf(maxvalue));
//                JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
//                return;
//            }
//            double value = Double.parseDouble(ts.text.getText());
//            ts.slider.setValue((int)value);
//        });
//        return ts;
//    }
//
//    private static TextAndSlider createTextAndSLider(int  minvalue, int maxvalue, int maxlength, int defaultvalue) {
//        JFormattedTextField text = new JFormattedTextField();
//        text.setPreferredSize(new Dimension(35,20));
//        JSlider jSlider = new JSlider(minvalue, maxvalue, defaultvalue);
//        jSlider.setPaintTicks(true);
//        jSlider.setMajorTickSpacing(10);
//        text.setDocument(new PlainDocument(){
//            @Override
//            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
//                if(str.length() + getLength() > maxlength){
//                    return;
//                }
//                for(int i=0;i<str.length();++i){
//                    if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != '-' && str.charAt(i) != '.'){
//                        return;
//                    }
//                }
//                super.insertString(offs,str,a);
//            }
//        });
//        text.setText(String.valueOf(defaultvalue));
//        text.setPreferredSize(new Dimension(20,20));
//        text.setVisible(true);
//
//        jSlider.addChangeListener(changeEvent -> {
//            int value = (jSlider.getValue());
//            text.setText(String.valueOf(value));
//        });
//        return new TextAndSlider(text,jSlider);
//    }
//
//    @Override
//    public boolean isDialogResult() {
//        return isOk;
//    }
//}
package ICGFilter.Dialogs;

import ICGFilter.Filters.Aquarelle;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class AquarelleSettingWindow extends JPanel implements MyDialog {
    private static int d = 10;
    final int MIN_VALUE = 1;
    final int MAX_VALUE = 100;
    final int MAX_VALUE_LENGTH = 3;
    int SYSTEM = 0;
    public JFormattedTextField valueText;
    TextAndSlider ts;

    private boolean isOk = false;

    Aquarelle aquarelle;

    public AquarelleSettingWindow(Aquarelle aquarelle) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.aquarelle = aquarelle;
        paramsPanel.add(new Label("Акварелизация"));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog)this.getRootPane().getParent();
            isOk = true;
            frame.dispose();
        });

        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog)this.getRootPane().getParent();
            isOk = false;
            frame.dispose();
        });
        add(paramsPanel);
        add(buttonPanel);
    }

    @Override
    public boolean isDialogResult() {
        return isOk;
    }
}