package ICGFilter.Dialogs;

import ICGFilter.Filters.Gamma;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;



public class GammaDialog extends JPanel implements MyDialog {
    private static int d = 10;
    final double MIN_VALUE = 0.1;
    final double MAX_VALUE = 10;
    final int MAX_VALUE_LENGTH = 4;
    int SYSTEM = 1;
    private boolean isOk = false;
    JTextField valueText;
    Gamma gamma;
    TextAndSlider ts;
    private double minvalue;
    private double maxvalue;
    private JFormattedTextField text;
    private JSlider slider;

    public GammaDialog(Gamma gamma) {
        super();
        setLayout(new GridLayout(2, 1));
        this.gamma = gamma;
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ts = create(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, gamma.getGamme());
        ts.text.addPropertyChangeListener(new Listener(MIN_VALUE, MAX_VALUE, ts.text, ts.slider, SYSTEM));
        paramsPanel.add(new Label("Выберите значение:"));
        valueText = ts.text;
        paramsPanel.add(ts.text);
        paramsPanel.add(ts.slider);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel p = this;
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            gamma.setGamma((double)Math.round(Double.parseDouble(valueText.getText())*10)/10);
            valueText.setText(String.valueOf(gamma.getGamme()));
            isOk = true;
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            valueText.setText(String.valueOf(gamma.getGamme()));
            isOk = false;
            frame.dispose();
        });
        add(paramsPanel);
        add(buttonPanel);
    }

    public static TextAndSlider create(double minvalue, double maxvalue, int maxlength, double defaultvalue) {
        return createTextAndSLider(minvalue,maxvalue,maxlength,defaultvalue);
    }

    private static TextAndSlider createTextAndSLider(double minvalue, double maxvalue, int maxlength, double defaultvalue) {
        JFormattedTextField text =new JFormattedTextField();
        text.setPreferredSize(new Dimension(35,20));
        JSlider jSlider = new JSlider((int)(minvalue*d), (int)(maxvalue*d), (int)(defaultvalue*d));
        jSlider.setPaintTicks(true);
        jSlider.setMajorTickSpacing(10);
        text.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
                if(str.length()+getLength()>maxlength){
                    return;
                }
                for(int i=0;i<str.length();++i){
                    if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != '-' && str.charAt(i) != '.'){
                        return;
                    }
                }
                super.insertString(offs,str,a);
            }
        });
        text.setText(String.valueOf(defaultvalue));
        text.setPreferredSize(new Dimension(35,20));
        text.setVisible(true);
        jSlider.addChangeListener(changeEvent -> {
            int value = (jSlider.getValue());
            text.setText(String.valueOf((double)value/d));
        });
        return new TextAndSlider(text,jSlider);
    }

    @Override
    public boolean isDialogResult() {
        return isOk;
    }
}
