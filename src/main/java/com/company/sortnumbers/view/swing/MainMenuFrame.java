package com.company.sortnumbers.view.swing;

import static com.company.sortnumbers.util.constant.ConstMessages.Messages.NUMBER_LIMIT_ERROR;
import static com.company.sortnumbers.util.constant.ConstMessages.Panels.INPUT_PANEL;
import static com.company.sortnumbers.util.constant.ConstMessages.Panels.NUMBER_PANEL;
import static com.company.sortnumbers.util.constant.ConstSettings.MAX_COLUMN;
import static com.company.sortnumbers.util.constant.ConstSettings.TEXT_FIELD_SIZE;

import com.company.sortnumbers.service.NumberService;
import com.company.sortnumbers.util.constant.ConstMessages.Buttons;
import com.company.sortnumbers.util.constant.ConstMessages.Labels;
import com.company.sortnumbers.util.constant.ConstMessages.Messages;
import com.company.sortnumbers.util.settings.LookAndFeelUtils;
import com.company.sortnumbers.util.validation.ValidationUtil;
import com.company.sortnumbers.view.MessageDisplay;
import jakarta.annotation.PostConstruct;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class MainMenuFrame extends JFrame {

    private final NumberService numberService;
    private final MessageDisplay messageDisplay;
    private final ValidationUtil validation;
    private final List<JButton> numberButtons = new ArrayList<>();

    private int inputNumber;
    private JButton enterBtn;
    private JTextField enterField;
    private JLabel enterLabel;
    private JPanel cards;
    private CardLayout cardLayout;
    private JButton sortButton;
    private JButton resetButton;
    private boolean ascending = false;
    private JPanel numberPanel;
    private JPanel numberButtonPanel;
    private JPanel buttonPanel;
    private JPanel inputPanel;

    @PostConstruct
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void prepareFrame() {
        setFrameUp();
        initComponents();
        pack();
    }

    private void setFrameUp() {
        setTitle(Labels.MAIN_MENU);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        LookAndFeelUtils.setWindowsLookAndFeel();

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        add(cards, BorderLayout.CENTER);
    }

    private void initComponents() {
        initInputPanel();
        initNumberPanel();
    }

    private void initInputPanel() {
        enterField = new JTextField(TEXT_FIELD_SIZE);
        enterBtn = new JButton(Labels.ENTER);
        enterLabel = new JLabel(Messages.ENTER_INFO);
        inputPanel = new JPanel(new GridBagLayout());
        JPanel fixedSizeInputPanel = new JPanel(new GridBagLayout());

        enterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        enterField.setPreferredSize(new Dimension(200, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        inputPanel.add(enterLabel, gbc);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        inputPanel.add(enterField, gbc);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        inputPanel.add(enterBtn, gbc);

        enterBtn.addActionListener(e -> handleButtonClick());
        fixedSizeInputPanel.setPreferredSize(new Dimension(100, 100));
        fixedSizeInputPanel.add(inputPanel, new GridBagConstraints());
        cards.add(fixedSizeInputPanel, INPUT_PANEL);
    }

    private void initNumberPanel() {
        numberPanel = new JPanel();
        numberButtonPanel = new JPanel();
        JPanel fixedSizeNumberPanel = new JPanel();

        numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.Y_AXIS));
        initButtonPanel();
        numberPanel.add(numberButtonPanel);

        fixedSizeNumberPanel.setPreferredSize(new Dimension(400, 400));
        fixedSizeNumberPanel.add(numberPanel);
        cards.add(numberPanel, NUMBER_PANEL);
    }

    private void initButtonPanel() {
        buttonPanel = new JPanel();
        sortButton = new JButton(Buttons.SORT);
        resetButton = new JButton(Buttons.RESET);

        sortButton.setBackground(Color.GREEN);
        sortButton.setBorderPainted(false);
        sortButton.setFocusPainted(false);

        resetButton.setBackground(Color.GREEN);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);

        sortButton.addActionListener(e -> handleSortButtonClick());
        resetButton.addActionListener(e -> showInputPanel());

        buttonPanel.add(sortButton);
        buttonPanel.add(resetButton);
        numberPanel.add(buttonPanel);
    }

    private void handleButtonClick() {
        String text = enterField.getText();
        Optional<String> errorMessage = validation.validate(text);

        if (errorMessage.isPresent()) {
            messageDisplay.showMessage(errorMessage.get());
        } else {
            inputNumber = Integer.parseInt(text);
            generateRandomNumbers(Integer.parseInt(text));
            cardLayout.show(cards, NUMBER_PANEL);
        }
    }

    private void handleSortButtonClick() {
        ascending = !ascending;
        List<Integer> numbers = numberButtons.stream()
            .map(AbstractButton::getText)
            .map(Integer::parseInt)
            .toList();

        numberButtonPanel.removeAll();
        numberButtons.clear();

        numbers = numberService.sortNumberButtons(numbers, ascending);
        addNumbersToPanel(numbers);

        numberButtonPanel.revalidate();
        numberButtonPanel.repaint();
    }

    private void handleNumberClick(int number) {
        if (numberService.shouldGenerateNewNumbers(number)) {
            generateRandomNumbers(inputNumber);
        } else {
            JOptionPane.showMessageDialog(null, NUMBER_LIMIT_ERROR);
        }
    }

    private void showInputPanel() {
        cardLayout.show(cards, INPUT_PANEL);
    }

    private void generateRandomNumbers(int numberCount) {
        numberButtons.clear();
        numberButtonPanel.removeAll();

        List<Integer> numbers = numberService.generateNumbers(numberCount);
        addNumbersToPanel(numbers);

        revalidate();
        repaint();
    }

    private void addNumbersToPanel(List<Integer> numbers) {
        numberButtonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.set(5, 5, 5, 5);

        numbers.forEach(number -> {
            addButton(number, gbc);
            gbc.gridy++;
            if (gbc.gridy >= MAX_COLUMN) {
                gbc.gridy = 0;
                gbc.gridx++;
            }
        });
    }

    private void addButton(int number, GridBagConstraints gbc) {
        JButton button = createButton(number);

        numberButtons.add(button);
        numberButtonPanel.add(button, gbc);
    }

    private JButton createButton(int number) {
        JButton button = new JButton(String.valueOf(number));

        button.setBackground(Color.BLUE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> handleNumberClick(number));
        return button;
    }

}
