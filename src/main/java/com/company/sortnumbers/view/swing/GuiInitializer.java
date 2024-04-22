package com.company.sortnumbers.view.swing;

import com.company.sortnumbers.controller.MainMenuController;
import com.company.sortnumbers.util.settings.LookAndFeelUtils;
import javax.swing.SwingUtilities;
import lombok.RequiredArgsConstructor;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;
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

        TimingSource ts = new SwingTimerTimingSource();
        Animator.setDefaultTimingSource(ts);
        ts.init();
    }
}
