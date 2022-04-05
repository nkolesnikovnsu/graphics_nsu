//package ICGFilter.Dialogs;
//
//import ICGFilter.Filters.BuildUp;
//
//import javax.swing.*;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.PlainDocument;
//import java.awt.*;
//
//public class BuildUpDialog extends JPanel implements MyDialog {
//    final int MIN_VALUE = 3;
//    final int MAX_VALUE = 11;
//    final int MAX_VALUE_LENGTH = 2;
//    int SYSTEM = 0;
//    private boolean isOk = false;
//    TextAndSlider ts;
//    BuildUp blur;
//    JTextField valueText;
//
//    public BuildUpDialog(BuildUp blur) {
//        super();
//        setLayout(new GridLayout(2, 1));
//        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        setLayout(new GridLayout(2, 1));
//        this.blur = blur;
//        ts = create(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, blur.getMatrixSize());
//        Listener propertyListener = new Listener(MIN_VALUE, MAX_VALUE, ts.text, ts.slider,SYSTEM);
//        ts.text.addPropertyChangeListener(propertyListener);
//        paramsPanel.add(new Label("Выберите значение:"));
//        paramsPanel.add(ts.text);
//        paramsPanel.add(ts.slider);
//        valueText = ts.text;
//        paramsPanel.add(ts.text);
//        paramsPanel.add(ts.slider);
//        JButton okButton = new JButton("OK");
//        JButton cancelButton = new JButton("cancel");
//        buttonPanel.add(okButton);
//        buttonPanel.add(cancelButton);
//        JPanel p = this;
//        okButton.addActionListener(e -> {
//            JDialog frame = (JDialog) p.getRootPane().getParent();
//            blur.setMatrixSize(Integer.parseInt(valueText.getText()));
//            isOk = true;
//            frame.dispose();
//        });
//        cancelButton.addActionListener(e -> {
//            JDialog frame = (JDialog) p.getRootPane().getParent();
//            valueText.setText(String.valueOf(blur.getMatrixSize()));
//            isOk = false;
//            frame.dispose();
//        });
//        add(paramsPanel);
//        add(buttonPanel);
//    }
//
//    public static TextAndSlider create(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
//        return createTextAndSLider(minvalue,maxvalue,maxlength,defaultvalue);
//    }
//
//    private static TextAndSlider createTextAndSLider(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
//        JFormattedTextField text = new JFormattedTextField();
//        text.setPreferredSize(new Dimension(35,20));
//        JSlider jSlider = new JSlider(minvalue, maxvalue, defaultvalue);
//        text.setDocument(new PlainDocument(){
//            @Override
//            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
//                if(str.length() + getLength() > maxlength){
//                    return;
//                }
//                for(int i=0;i<str.length();++i){
//                    if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != '-'){
//                        return;
//                    }
//                }
//                super.insertString(offs,str,a);
//            }
//        });
//        text.setText(String.valueOf(defaultvalue));
//        text.setPreferredSize(new Dimension(35,20));
//        text.setText(String.valueOf(defaultvalue));
//        text.setVisible(true);
//        jSlider.setPaintTicks(true);
//        jSlider.setPaintLabels(true);
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

import ICGFilter.Filters.BuildUp;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class BuildUpDialog extends JPanel implements MyDialog {
    private static int d = 10;
    final int MIN_VALUE = 1;
    final int MAX_VALUE = 100;
    final int MAX_VALUE_LENGTH = 3;
    int SYSTEM = 0;
    public JFormattedTextField valueText;
    TextAndSlider ts;

    private boolean isOk = false;

    BuildUp buildup;

    public BuildUpDialog (BuildUp buildup) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.buildup = buildup;
        paramsPanel.add(new Label("Наращивание"));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog) this.getRootPane().getParent();
            isOk = true;
            frame.dispose();
        });

        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) this.getRootPane().getParent();
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