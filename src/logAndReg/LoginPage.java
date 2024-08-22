package logAndReg;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LoginPage extends JFrame {
    private JLabel userNameLabel;
    private JTextField userNameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JPanel loginPanel;
    private LoginListener loginListener;
    private int loginAttempt;
    private JComboBox<String> authenticationComboBox;

    public LoginPage(String title) {
        super(title);

        // Set the frame icon
        setIconImage(new ImageIcon(getClass().getResource("/logAndReg/images/loginImage.png")).getImage());

        // Initialize components
        userNameLabel = new JLabel("Username:");
        userNameField = new JTextField(15);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginBtn = new JButton("Login");
        loginPanel = new JPanel();
        authenticationComboBox = new JComboBox<>(new String[]{"Admin", "User"});

        setupUI();

        // Action listener for login button
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Detect Enter key press in passwordField
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false); // Prevent resizing
        this.setMinimumSize(new Dimension(350, 400));
        this.setMaximumSize(new Dimension(350, 400)); // Fixed size
        this.setSize(350, 400);
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        // Create a panel for login components
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(30, 30, 30)); // Dark background
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 5, 5, 5);

        // Authentication Type ComboBox
        contentPanel.add(createLabel("Login as: "), gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(authenticationComboBox, gc);

        gc.gridx = 0;
        gc.gridy++;
        contentPanel.add(createLabel(userNameLabel.getText()), gc);
        gc.gridx++;
        contentPanel.add(userNameField, gc);

        gc.gridx = 0;
        gc.gridy++;
        contentPanel.add(createLabel(passwordLabel.getText()), gc);
        gc.gridx++;
        contentPanel.add(passwordField, gc);

        gc.gridx = 1;
        gc.gridy++;
        gc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(loginBtn, gc);

        styleComponents();
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(173, 216, 230)); // Light blue color for text
        return label;
    }

    private void styleComponents() {
        authenticationComboBox.setBackground(new Color(255, 255, 255));
        authenticationComboBox.setForeground(new Color(0, 0, 0));

        userNameField.setBackground(new Color(50, 50, 50));
        userNameField.setForeground(new Color(255, 255, 255));
        userNameField.setCaretColor(Color.WHITE);

        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(new Color(255, 255, 255));
        passwordField.setCaretColor(Color.WHITE);

        loginBtn.setBackground(new Color(70, 130, 180)); // Steel blue
        loginBtn.setForeground(new Color(255, 255, 255));
        loginBtn.setFocusPainted(false);
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    private void performLogin() {
        String userText = userNameField.getText();
        String passwordText = String.valueOf(passwordField.getPassword());
        loginAttempt++;

        // Create LoginEvent and notify listener
        LoginEvent event = new LoginEvent(this, userText,
                passwordText, loginAttempt,
                (String) authenticationComboBox.getSelectedItem());
        if (loginListener != null) {
            loginListener.loginRequest(event);
        }
    }
}
