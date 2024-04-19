package com.company.sortnumbers.view.swing;

import com.company.sortnumbers.controller.MainMenuController;
import com.company.sortnumbers.util.settings.LookAndFeelUtils;
import javax.swing.SwingUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuiInitializer implements CommandLineRunner {

    private final MainMenuController mainMenuController;

    @Override
    public void run(String... args) {
        LookAndFeelUtils.setWindowsLookAndFeel();
        SwingUtilities.invokeLater(mainMenuController::openFrame);
    }
}
