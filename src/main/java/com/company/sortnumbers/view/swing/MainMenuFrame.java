package com.company.sortnumbers.view.swing;

import static com.company.sortnumbers.util.constant.ConstMessages.Messages.ANIMATION_IS_STARTED;
import static com.company.sortnumbers.util.constant.ConstMessages.Messages.NUMBER_LIMIT_ERROR;
import static com.company.sortnumbers.util.constant.ConstMessages.Panels.INPUT_PANEL;
import static com.company.sortnumbers.util.constant.ConstMessages.Panels.NUMBER_PANEL;
import static com.company.sortnumbers.util.constant.ConstSettings.BUTTON_HEIGHT;
import static com.company.sortnumbers.util.constant.ConstSettings.BUTTON_MARGIN;
import static com.company.sortnumbers.util.constant.ConstSettings.BUTTON_WIDTH;
import static com.company.sortnumbers.util.constant.ConstSettings.MAX_COLUMN;
import static com.company.sortnumbers.util.constant.ConstSettings.TEXT_FIELD_SIZE;

import aurelienribon.tweenengine.Tween;
import com.company.sortnumbers.service.NumberService;
import com.company.sortnumbers.util.constant.ConstMessages.Buttons;
import com.company.sortnumbers.util.constant.ConstMessages.Labels;
import com.company.sortnumbers.util.constant.ConstMessages.Messages;
import com.company.sortnumbers.util.model.SwapPair;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class MainMenuFrame extends JFrame {

    private final NumberService numberService;
    private final MessageDisplay messageDisplay;
    private final ValidationUtil validation;
    private final List<JButton> numberButtons = new ArrayList<>();
    private final Map<Integer, Integer> swapPairs = new LinkedHashMap<>();
    private final SwingAnimation swingAnimation;
    private int inputNumber;
    private boolean isAnimating = false;
    private JButton enterBtn;
    private JTextField enterField;
    private JLabel enterLabel;
    private JPanel cards;
    private CardLayout cardLayout;
    private JButton sortButton;
    private JButton resetButton;
    private JPanel numberPanel;
    private JPanel numberButtonPanel;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JScrollPane scrollPane;

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
        cards.setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        add(cards, BorderLayout.CENTER);
    }

    private void initComponents() {
        initInputPanel();
        initNumberPanel();
        Tween.registerAccessor(JButton.class, new JButtonAccessor());
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
        numberPanel = new JPanel(new BorderLayout());
        numberButtonPanel = new JPanel();
        initButtonPanel();

        scrollPane = new JScrollPane(numberButtonPanel);
        numberPanel.add(buttonPanel, BorderLayout.NORTH);
        numberPanel.add(scrollPane, BorderLayout.CENTER);

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
        resetButton.addActionListener(e -> handleResetClick());

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

    private void handleResetClick() {
        if (isAnimating) {
            messageDisplay.showMessage(ANIMATION_IS_STARTED);
            return;
        }

        cardLayout.show(cards, INPUT_PANEL);
    }

    private void handleNumberClick(int number) {
        if (isAnimating) {
            messageDisplay.showMessage(ANIMATION_IS_STARTED);
            return;
        }

        if (numberService.shouldGenerateNewNumbers(number)) {
            generateRandomNumbers(inputNumber);
        } else {
            messageDisplay.showMessage(NUMBER_LIMIT_ERROR);
        }
    }

    private void handleSortButtonClick() {
        if (isAnimating) {
            messageDisplay.showMessage(ANIMATION_IS_STARTED);
            return;
        }

        Set<SwapPair> swapPairs = numberService.quickSort(getNumbersFromButtons());
        if (!swapPairs.isEmpty()) {
            swingAnimation.startAnimation(this, swapPairs);
        }
    }

    private List<Integer> getNumbersFromButtons() {
        return numberButtons.stream()
            .map(AbstractButton::getText)
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    public JButton getButtonWithNumber(int number) {
        return numberButtons.stream()
            .filter(b -> Integer.parseInt(b.getText()) == number)
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException("Button with number " + number + " not found"));
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
        numberButtonPanel.setLayout(null);

        IntStream.range(0, numbers.size()).forEach(i -> {
            int x = BUTTON_MARGIN + (BUTTON_WIDTH + BUTTON_MARGIN) * (i / MAX_COLUMN);
            int y = BUTTON_MARGIN + (BUTTON_HEIGHT + BUTTON_MARGIN) * (i % MAX_COLUMN);
            addButtonToPanel(numbers.get(i), x, y);
        });

        int panelWidth = (BUTTON_WIDTH + BUTTON_MARGIN) * ((numbers.size() - 1) / MAX_COLUMN + 1);
        int panelHeight = (BUTTON_HEIGHT + BUTTON_MARGIN) * MAX_COLUMN;
        numberButtonPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    private void addButtonToPanel(Integer number, int x, int y) {
        JButton button = createButton(number);
        button.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        numberButtonPanel.add(button);
        numberButtons.add(button);
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
