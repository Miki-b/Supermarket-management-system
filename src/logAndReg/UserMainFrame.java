package logAndReg;

import ItemManagmentGUI.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class UserMainFrame extends JFrame {
    private LoginPanel loginPanel;
    private IDAndPasswords loginInfo;
    private MenuFrame menuFrame;
    private JLabel backgroundLabel;

    public UserMainFrame(String title) {
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
                java.util.HashMap<String, String> userCredentials = loginInfo.getUserCredentials();
                if (userCredentials.containsKey(oldUsername) && userCredentials.get(oldUsername).equals(oldPassword)) {
                    // Update credentials using IDAndPasswords
                    loginInfo.modifyUserCredentials(oldUsername, newUsername, newPassword);
                    JOptionPane.showMessageDialog(UserMainFrame.this, "User credentials updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(UserMainFrame.this, "Old username or password is incorrect.");
                }
            }
        });

        // Create MenuFrame instance
        menuFrame = new MenuFrame();
        menuFrame.setVisible(true); // Initially visible

        this.setLayout(new BorderLayout());
        frameLayout();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // Adjusted size to accommodate MenuFrame
    }

    private void frameLayout() {
        setJMenuBar(menuBarLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(loginPanel, BorderLayout.NORTH);
        mainPanel.add(menuFrame, BorderLayout.CENTER);
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
                menuFrame.setVisible(!selected);
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


}
