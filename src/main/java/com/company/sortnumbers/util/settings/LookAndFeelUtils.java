package com.company.sortnumbers.util.settings;

import com.company.sortnumbers.util.constant.ConstMessages;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class LookAndFeelUtils {

    public static void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                ConstMessages.Messages.WINDOWS_STYLE_LOADING_ERROR_MESSAGE + e,
                ConstMessages.Messages.ALERT_TILE,
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
