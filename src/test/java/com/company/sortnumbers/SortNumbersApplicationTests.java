package com.company.sortnumbers;

import com.company.sortnumbers.controller.MainMenuController;
import com.company.sortnumbers.view.swing.MainMenuFrame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SortNumbersApplicationTests {

    @MockBean
    private MainMenuController mainMenuController;

    @MockBean
    private MainMenuFrame mainMenuFrame;

    @Test
    void contextLoads() {
    }

}
