package com.trustbirungi.calc;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

////////////////////////////////////////////////////////// class CalcGUI
class CalcGUI extends JFrame {
    //======================================================= constants
    private final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
    
    //=============================================== instance variables
    //--- Component referenced during execution
    private JTextField m_displayField;       // display result / input.
    
    //--- Variables representing state of the calculator
    private boolean   m_startNumber = true;  // true: num key next
    private String    m_previousOp  = "=";   // previous operation
    private CalcLogic m_logic = new CalcLogic(); // The internal calculator.

    //====================================================== constructor
    public CalcGUI() {
        //--- Display field
        m_displayField = new JTextField("0", 12);
        m_displayField.setHorizontalAlignment(JTextField.RIGHT);
        m_displayField.setFont(BIGGER_FONT);

        //--- Clear button
        JButton clearButton = new JButton("CLEAR");
        clearButton.setFont(BIGGER_FONT);
        clearButton.addActionListener(new ClearListener());

        //--- One listener for all numeric keys.
        ActionListener numListener = new NumListener();
        
        //--- Layout numeric keys in a grid.  Generate the buttons
        //    in a loop from the chars in a string.
        String buttonOrder = "789456123 0 ";
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 3, 5, 5));
        for (int i = 0; i < buttonOrder.length(); i++) {
            String keyTop = buttonOrder.substring(i, i+1);
            if (keyTop.equals(" ")) {
                buttonPanel.add(new JLabel(""));
            } else {
                JButton b = new JButton(keyTop);
                b.addActionListener(numListener);
                b.setFont(BIGGER_FONT);
                buttonPanel.add(b);
            }
        }
        
        //--- One ActionListener to use for all operator buttons.
        ActionListener opListener = new OpListener();
        
        //--- Create panel with gridlayout to hold operator buttons.
        //    Use array of button names to create buttons in a loop.
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new GridLayout(5, 1, 5, 5));
        String[] opOrder = {"+", "-", "*", "/", "="};
        for (int i = 0; i < opOrder.length; i++) {
            JButton b = new JButton(opOrder[i]);
            b.addActionListener(opListener);
            b.setFont(BIGGER_FONT);
            opPanel.add(b);
        }

        //--- Layout the top-level panel.
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(5, 5));
        content.add(m_displayField, BorderLayout.NORTH );
        content.add(buttonPanel   , BorderLayout.CENTER);
        content.add(opPanel       , BorderLayout.EAST  );
        content.add(clearButton   , BorderLayout.SOUTH );
        
        //--- Finish building the window (JFrame)
        this.setContentPane(content);
        this.pack();
        this.setTitle("Simple Calculator");
        this.setResizable(false);
    }//end constructor
    

    //======================================================== action_clear
    /** Called by Clear btn action listener and elsewhere.*/
    private void action_clear() {
        m_startNumber = true;         // Expecting number, not op.
        m_displayField.setText("0");
        m_previousOp  = "=";
        m_logic.setTotal("0");
    }

    /////////////////////////////////////// inner listener class OpListener
    /** Listener for all op buttons. */
    class OpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // The calculator is always in one of two states.
            // 1. A number must be entered -- an operator is wrong.
            // 2. An operator must be entered.
            if (m_startNumber) { // Error: needed number, not operator
                action_clear();
                m_displayField.setText("ERROR - No operator");
            } else {
                m_startNumber = true;  // Next thing must be a number
                try {
                    // Get value from display field, convert, do prev op
                    // If this is the first op, m_previousOp will be =.
                    String displayText = m_displayField.getText();
    
                    if (m_previousOp.equals("=")) {
                        m_logic.setTotal(displayText);
                    } else if (m_previousOp.equals("+")) {
                        m_logic.add(displayText);
                    } else if (m_previousOp.equals("-")) {
                        m_logic.subtract(displayText);
                    } else if (m_previousOp.equals("*")) {
                        m_logic.multiply(displayText);
                    } else if (m_previousOp.equals("/")) {
                        m_logic.divide(displayText);
                    }
    
                    m_displayField.setText("" + m_logic.getTotalString());
    
                } catch (NumberFormatException ex) {
                    action_clear();
                    m_displayField.setText("Error");
                }
    
                //--- set m_previousOp for the next operator.
                m_previousOp = e.getActionCommand();
            }//endif m_startNumber
        }//endmethod
    }//end class
    

    //////////////////////////////////// inner listener class ClearListener
    /** Action listener for numeric keys */
    class NumListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String digit = e.getActionCommand(); // Get text from button
            if (m_startNumber) {
                // This is the first digit, clear field and set
                m_displayField.setText(digit);
                m_startNumber = false;
            } else {
                // Add this digit to the end of the display field
                m_displayField.setText(m_displayField.getText() + digit);
            }
        }
    }
    
    
    //////////////////////////////////// inner listener class ClearListener
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            action_clear();
        }
    }
}//endclass CalcGUI
