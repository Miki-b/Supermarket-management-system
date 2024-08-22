package logAndReg;

import ItemManagmentGUI.AdminMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private LoginPanel loginPanel;
    private IDAndPasswords loginInfo;
    private AdminMenuFrame adminMenuFrame;
    private JLabel backgroundLabel;

    public MainFrame(String title) {
        super(title);

        // Set frame icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/logAndReg/images/appIcon.png"));
        setIconImage(icon.getImage());

        loginPanel = new LoginPanel();
        loginInfo = new IDAndPasswords();

        loginPanel.setVisible(false); // Initially hidden

        setupBackground();

        loginPanel.setLoginChange(new LoginChangeListener() {
            @Override
            public void changesSaved(LoginPanelEvent e) {
                String oldUsername = e.getOldUsername();
                String oldPassword = e.getOldPassword();
                String newUsername = e.getNewUsername();
                String newPassword = e.getNewPassword();

                // Validate old credentials before modifying
                HashMap<String, String> adminCredentials = loginInfo.getAdminCredentials();
                if (adminCredentials.containsKey(oldUsername) && adminCredentials.get(oldUsername).equals(oldPassword)) {
                    // Update credentials using IDAndPasswords
                    loginInfo.modifyAdminCredentials(oldUsername, newUsername, newPassword);
                    JOptionPane.showMessageDialog(MainFrame.this, "Admin credentials updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Old username or password is incorrect.");
                }
            }
        });

        // Create AdminMenuFrame instance
        adminMenuFrame = new AdminMenuFrame();
        adminMenuFrame.setVisible(true); // Initially visible

        this.setLayout(new BorderLayout());
        frameLayout();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // Adjusted size to accommodate AdminMenuFrame
    }

    private void frameLayout() {
        setJMenuBar(menuBarLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(loginPanel, BorderLayout.NORTH);
        mainPanel.add(adminMenuFrame, BorderLayout.CENTER);
        this.add(mainPanel);
    }

    private JMenuBar menuBarLayout() {
        JMenuBar menuBar = new JMenuBar();

        // Option Menu
        JMenu optionMenu = new JMenu("Options");

        // Checkbox Menu Item for Login Panel visibility
        JCheckBoxMenuItem loginSettingItem = new JCheckBoxMenuItem("Login Settings");
        loginSettingItem.setSelected(false); // Initially not checked
        loginSettingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | ActionEvent.ALT_MASK)); // Alt + L shortcut
        loginSettingItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
                boolean selected = menuItem.isSelected();
                loginPanel.setVisible(selected);
                adminMenuFrame.setVisible(!selected);
            }
        });
        optionMenu.add(loginSettingItem);

        menuBar.add(optionMenu);

        return menuBar;
    }

    private void setupBackground() {
        // Create background label
        backgroundLabel = new JLabel();
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/logAndReg/images/panelBackground.jpg"));
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());

        // Add background label to frame
        this.setContentPane(backgroundLabel);
    }

    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static int adminLoginAttempts = 0;

    public static void main(String[] args) {
        IDAndPasswords loginInfo = new IDAndPasswords();

        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage("Authentication");
            loginPage.setLoginListener(new LoginListener() {
                @Override
                public void loginRequest(LoginEvent e) {
                    String user = e.getUserName();
                    String pass = e.getPassword();
                    String authType = e.getAuthenticationType();

                    HashMap<String, String> credentials;

                    if ("admin".equalsIgnoreCase(authType)) {
                        credentials = loginInfo.getAdminCredentials();
                    } else if ("user".equalsIgnoreCase(authType)) {
                        credentials = loginInfo.getUserCredentials();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid authentication type");
                        return;
                    }

                    if (credentials.containsKey(user) && credentials.get(user).equals(pass)) {
                        JOptionPane.showMessageDialog(null, "Login Successful as " + authType);
                        loginPage.dispose();
                        if ("admin".equalsIgnoreCase(authType)) {
                            showAdminMainFrame();
                        } else if ("user".equalsIgnoreCase(authType)) {
                            showUserMainFrame();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong credentials provided!");
                        if ("admin".equalsIgnoreCase(authType)) {
                            adminLoginAttempts++;
                            if (adminLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
                                JOptionPane.showMessageDialog(null, "Multiple attempts! Application closing.");
                                System.exit(0);
                            }
                        }
                    }
                }
            });
        });
    }

    private static void showAdminMainFrame() {
        MainFrame adminMainFrame = new MainFrame("Inventory Manager");
        adminMainFrame.setVisible(true);
    }

    private static void showUserMainFrame() {
        UserMainFrame userMainFrame = new UserMainFrame("Inventory Manager");
        userMainFrame.setVisible(true);
    }
}
