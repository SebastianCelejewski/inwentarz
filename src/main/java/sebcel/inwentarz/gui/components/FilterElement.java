package sebcel.inwentarz.gui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

public class FilterElement extends JComponent {

    private static final long serialVersionUID = -8274637199561254220L;

    private JLabel label;
    private JTextField textField;
    private JButton clearButton;

    private Font labelFont = new Font("Dialog", Font.PLAIN, 12);
    private Font buttonFont = new Font("Dialog", Font.BOLD, 10);

    public FilterElement(String label, String toolTip) {
        this.label = new JLabel(label);
        this.textField = new JTextField();
        this.clearButton = new JButton("X");

        this.label.setFont(labelFont);

        clearButton.setFont(buttonFont);
        clearButton.setPreferredSize(new Dimension(40, 19));

        this.setLayout(new GridBagLayout());
        this.add(this.label, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
        this.add(textField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1));
        this.add(clearButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1));

        textField.setToolTipText(toolTip);
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        clearButton.setToolTipText("Wyczyść filtr");
    }

    public void addDocumentListener(DocumentListener documentListener) {
        this.textField.getDocument().addDocumentListener(documentListener);
    }

    public String getText() {
        if (textField.getText() == null || textField.getText().trim().length() == 0) {
            return null;
        }
        return textField.getText();
    }

    public Integer getInt() {
        if (textField.getText() == null || textField.getText().trim().length() == 0) {
            return null;
        }
        return Integer.parseInt(textField.getText());
    }
}