package com.company.sortnumbers.controller;

import com.company.sortnumbers.view.swing.MainMenuFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MainMenuController {

    private final MainMenuFrame frame;

    public void openFrame() {
        frame.setVisible(true);
    }

}
