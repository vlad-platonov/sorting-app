package com.company.sortnumbers.view.swing;

import com.company.sortnumbers.view.MessageDisplay;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

@Component
public class SwingMessageDisplay implements MessageDisplay {

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
