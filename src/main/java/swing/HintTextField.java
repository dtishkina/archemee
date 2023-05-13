package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class HintTextField extends JTextField implements FocusListener {
    private String hint;
    private boolean isHaveErrorCondition;

    public HintTextField(String hint) {
        this.hint = hint;
        addFocusListener(this);
        setText(hint);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().equals(hint)) {
            setText("");
        }
        if (isHaveErrorCondition){
            setText("");
            isHaveErrorCondition = false;
            setForeground(Color.BLACK);
        }
    }

    public void setErrorText(String text){
        setText(text);
        setForeground(Color.RED);
        isHaveErrorCondition = true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            setText(hint);
        }
    }
}