/**
  * CalculatorGUI_updated.java
  * @description simple calculator class
  * @author R Samman
  * @version 2.0 04-04-2022
*/

import javax.swing.*;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CalculatorGUI_updated class represents a simple calculator GUI.
 */
public class CalculatorGUI_updated extends JPanel {

    private JPanel buttonPanel, textPanel, headerPanel;
    private JButton buttonArray[] = new JButton[16];
    private String buttonText[] = {"7", "8", "9", "+", "4", "5", "6", "-",
                                    "1", "2", "3", "\u00D7", "C", "0", "=",
                                    "\u00F7"};
    private JTextField output;
    private Font calcFont;
    private Color softBlue, lightGray, buttonPastel, lightGold;
    private Response userActivity = new Response();
    private Calculations_updated calc = new Calculations_updated();

/**
 * Constructor to initialize the calculator GUI components.
 */
    public CalculatorGUI_updated() {
        setFontsColorsAndImages();
        setHeaderPanel();
        setTextPanel();
        setButtonPanel();
        placeElementsInFrame();
    }

/**
 * Set fonts, colors, and images used in the GUI.
 */
    private void setFontsColorsAndImages() {
        calcFont = new Font("Segoe UI", Font.BOLD, 50);
        softBlue = new Color(200, 216, 230);
        lightGray = new Color(240, 240, 240);
        buttonPastel = new Color(220, 220, 220);
        lightGold = new Color(255, 223, 128);

    }
/**
 * Set header panel labels.
 */
    private void setHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(470, 70));
        headerPanel.setBackground(softBlue);
        headerPanel.setLayout(new BorderLayout());
    }

/**
 * Set the text panel.
 */
    private void setTextPanel() {
        output = new JTextField(20);
        output.setEditable(false);
        output.setFont(calcFont);
        output.setText("0");

        textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(output, BorderLayout.CENTER);
        textPanel.setPreferredSize(new Dimension(470, 100));
        textPanel.setBackground(lightGray);
    }

/**
 * Set button panel.
 */
    private void setButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBackground(lightGray);
    
        for (int i = 0; i < buttonText.length; i++) {
            final int index = i;
            buttonArray[index] = new JButton(buttonText[index]);
            buttonArray[index].setFont(calcFont);
            buttonArray[index].setFocusPainted(false);
            buttonArray[index].setBorderPainted(false);
            buttonArray[index].addActionListener(userActivity);
    
            buttonArray[index].setOpaque(true);
            
            // Set light blue background for all buttons
            buttonArray[index].setBackground(softBlue);
    
            // Set gold background for operations, "=", and "C"
            if (index % 4 == 3 || index == 12 || index == 14) {
                buttonArray[index].setBackground(lightGold);
            }
    
            // Add soft press effect
            buttonArray[index].getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()) {
                    startClickAnimation(buttonArray[index]);
                }
            });
    
            // Add mouse listener for white background effect
            buttonArray[index].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    resetButtonsBackground();
                }
            });
    
            buttonPanel.add(buttonArray[index]);
        }
    
        buttonPanel.setPreferredSize(new Dimension(470, 470));
    }
    

    private void startClickAnimation(JButton button) {
        if (!( button.getText().equals("C") )) {
            button.setBackground(Color.WHITE);
    
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button.setBackground(buttonPastel);
                }
            });
    
            timer.setRepeats(false);
            timer.start();
        }
    }
    
/**
 * Resets the buttonPanel components features. 
 */
    private void resetButtonsBackground() {
        for (JButton button : buttonArray) {
            if (button.getText().equals("=") || button.getText().equals("C") || button.getText().equals("+") ||
                button.getText().equals("-") || button.getText().equals("\u00D7") || button.getText().equals("\u00F7")) {
                button.setBackground(lightGold);
            } else {
                button.setBackground(softBlue);
            }
        }
    }
/**
 * Set the layout and add components to the frame. 
 */
    private void placeElementsInFrame() {
        setBackground(lightGray);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(headerPanel);
        add(textPanel);
        add(buttonPanel);
    }

/**
 * ActionListener implementation to respond to button clicks.
 */
    private class Response implements ActionListener {
        private boolean lastOperation = false;
        private boolean equalsPressed = false;

        public void actionPerformed(ActionEvent press) {
            Object buttonPressed = press.getSource();
            String content = output.getText();

            // zero integer
            if (buttonPressed == buttonArray[13]) {
                if (!content.equals("0")) {
                    content += buttonArray[13].getText();
                    output.setText(content);
                }
            }
            // clear 'C'
            if (buttonPressed == buttonArray[12]) {
                output.setText("0");
                equalsPressed = false; // resets the flag when 'C' is pressed
            }
            // operations
            for (int i = 3; i < 16; i += 4) {
                if (buttonPressed == buttonArray[i]) {
                    lastOperation = true;
                    calc.handleOperation(output.getText(), buttonArray[i].getText());
                    equalsPressed = false; // resets the flag when an operator is pressed
                }
            }
            // equals
            if (buttonPressed == buttonArray[14]) {
                output.setText(calc.performCalculation(output.getText()));
                equalsPressed = true; // sets the flag when equals is pressed
            }

            for (int i = 0; i < buttonArray.length; i++) {
                if (buttonPressed == buttonArray[i] && i % 4 != 3 && i < 12) {
                    if (content.equals("0") || lastOperation || equalsPressed) {
                        output.setText(buttonArray[i].getText());
                        lastOperation = false;
                        equalsPressed = false; // resets the flag when a number is pressed after equals
                    } else {
                        content += buttonArray[i].getText();
                        output.setText(content);
                    }
                }
            }
        }
    }
/**
 * Main method to create and display the calculator GUI.
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("THE Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(580, 720);
            frame.setResizable(false);
            frame.add(new CalculatorGUI_updated());
            frame.setVisible(true);
        });
    }
}
