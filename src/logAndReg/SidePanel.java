package logAndReg;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    private JButton adminLoginButton;
    private JButton userLoginButton;
    private JLabel settingLabel;
    private JLabel iconLabel;
    private SidePanelListener spListener;

    public SidePanel() {
        // Initialize components
        adminLoginButton = new JButton("Admin Login");
        userLoginButton = new JButton("User Login");
        settingLabel = new JLabel("Settings");

        // Load the image icon and scale it
        ImageIcon settingIcon = createScaledIcon("/logAndReg/images/settingIcon.png", 100, 100);
        iconLabel = new JLabel(settingIcon);

        // Set panel properties
        this.setBackground(new Color(40, 54, 66)); // Steel blue background

        sidePanelLayout();

        // Ensure the panel expands with the frame
        this.setMinimumSize(new Dimension(250, 0)); // Set minimum width
    }

    private void sidePanelLayout() {
        // Set button properties
        adminLoginButton.setForeground(Color.WHITE);
        adminLoginButton.setBackground(new Color(0, 102, 204)); // Dark blue
        adminLoginButton.setFocusPainted(false); // Remove focus border
        adminLoginButton.setBorderPainted(false); // Remove border
        adminLoginButton.setOpaque(true); // Ensure background color is painted

        userLoginButton.setForeground(Color.WHITE);
        userLoginButton.setBackground(new Color(0, 102, 204)); // Dark blue
        userLoginButton.setFocusPainted(false); // Remove focus border
        userLoginButton.setBorderPainted(false); // Remove border
        userLoginButton.setOpaque(true); // Ensure background color is painted

        // Set label properties
        settingLabel.setForeground(Color.WHITE);
        settingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        settingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set layout and add components
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weighty = 0.2; // Adjust the weight for vertical resizing
        this.add(iconLabel, gc);

        gc.gridx = 1; // Move to the next column
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(10, 0, 10, 10); // Adjust insets for spacing
        this.add(settingLabel, gc);

        gc.gridx = 0; // Reset to the first column
        gc.gridy = 1; // Move to the next row
        gc.gridwidth = 2; // Span across two columns
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(10, 10, 10, 10); // Adjust insets for spacing
        this.add(adminLoginButton, gc);

        gc.gridy = 2; // Move to the next row
        this.add(userLoginButton, gc);
    }

    // Method to create scaled ImageIcon
    private ImageIcon createScaledIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Method to make admin login button invisible
    public void setAdminLoginButtonVisible(boolean visible) {
        adminLoginButton.setVisible(visible);
    }

    // Method to make user login button invisible
    public void setUserLoginButtonVisible(boolean visible) {
        userLoginButton.setVisible(visible);
    }

    public void setSpListener(SidePanelListener spListener) {
        this.spListener = spListener;
    }
}
