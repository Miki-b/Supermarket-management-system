package logAndReg;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {
    private JLabel oldUserLabel;
    private JTextField oldUserField;
    private JLabel oldPasswordLabel;
    private JPasswordField oldPasswordField;
    private JLabel newUserLabel;
    private JTextField newUserField;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordField;
    private JButton saveButton;
    private JPanel innerPanel;
    private LoginChangeListener loginChange;

    public LoginPanel() {
        oldUserLabel = new JLabel("Old Username:");
        oldUserField = new JTextField(20);
        oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordField = new JPasswordField(20);
        newUserLabel = new JLabel("New Username:");
        newUserField = new JTextField(20);
        newPasswordLabel = new JLabel("New Password:");
        newPasswordField = new JPasswordField(20);
        saveButton = new JButton("Save Changes");

        panelLayout();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        // Make saveButton reactive to Enter key
        setupKeyBindings();
    }

    private void panelLayout() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(34, 40, 49)); // Dark background

        innerPanel = new JPanel();
        innerPanel.setLayout(new GridBagLayout());
        innerPanel.setBackground(new Color(57, 62, 70)); // Slightly lighter dark background
        innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(238, 238, 238)),
                "Change Login Credentials",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(238, 238, 238))); // White text for title

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Customizing labels and fields
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        Color labelColor = new Color(238, 238, 238); // Light color for labels
        Color fieldBgColor = new Color(238, 238, 238); // Light color for text fields
        Color fieldTextColor = new Color(34, 40, 49); // Dark color for text fields

        oldUserLabel.setFont(labelFont);
        oldUserLabel.setForeground(labelColor);
        oldUserField.setBackground(fieldBgColor);
        oldUserField.setForeground(fieldTextColor);

        oldPasswordLabel.setFont(labelFont);
        oldPasswordLabel.setForeground(labelColor);
        oldPasswordField.setBackground(fieldBgColor);
        oldPasswordField.setForeground(fieldTextColor);

        newUserLabel.setFont(labelFont);
        newUserLabel.setForeground(labelColor);
        newUserField.setBackground(fieldBgColor);
        newUserField.setForeground(fieldTextColor);

        newPasswordLabel.setFont(labelFont);
        newPasswordLabel.setForeground(labelColor);
        newPasswordField.setBackground(fieldBgColor);
        newPasswordField.setForeground(fieldTextColor);

        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setBackground(new Color(95, 168, 211));
        saveButton.setForeground(Color.WHITE);

        // Old User Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        innerPanel.add(oldUserLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        innerPanel.add(oldUserField, gbc);

        // Old Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        innerPanel.add(oldPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        innerPanel.add(oldPasswordField, gbc);

        // New User Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        innerPanel.add(newUserLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        innerPanel.add(newUserField, gbc);

        // New Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        innerPanel.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        innerPanel.add(newPasswordField, gbc);

        // Save Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        innerPanel.add(saveButton, gbc);

        // Center inner panel in the outer panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(innerPanel, gbc);

        // Adjusting sizes and borders
        setPreferredSize(new Dimension(400, 300));
    }

    private void setupKeyBindings() {
        // Create an action for the Enter key press
        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        };

        // Bind the Enter key to the action
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        getActionMap().put("enter", enterAction);
    }

    private void saveChanges() {
        String oldUsername = oldUserField.getText();
        String oldPassword = new String(oldPasswordField.getPassword());
        String newUsername = newUserField.getText();
        String newPassword = new String(newPasswordField.getPassword());

        LoginPanelEvent event = new LoginPanelEvent(this, oldUsername, oldPassword, newUsername, newPassword);
        if (event != null && loginChange != null) {
            loginChange.changesSaved(event);
        }
    }

    public void setLoginChange(LoginChangeListener loginChange) {
        this.loginChange = loginChange;
    }
}
