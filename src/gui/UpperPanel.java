package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpperPanel extends JPanel {
    private JPanel userLabel;
    private JPanel productLabel;
    private JPanel salesLabel;
    private JLabel productValueLabel;
    private JLabel salesValueLabel;
    private Border outerBorder = BorderFactory.createEtchedBorder();
    private Border innerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);

    // Variables to hold initial values
    private int productCount = 0;
    private double totalSales = 0.0;

    public UpperPanel() {
        setLayout(new GridLayout(1, 3, 10, 0)); // Added horizontal gap between panels
        setBackground(Color.DARK_GRAY);

        userLabel = createInfoPanel("Users", "0", "/images/users.png");
        productLabel = createInfoPanel("Products", String.valueOf(productCount), "/images/products.png");
        salesLabel = createInfoPanel("Sales", String.format("$%.2f", totalSales), "/images/sales.png");

        add(userLabel);
        add(productLabel);
        add(salesLabel);

        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        // Load data from the database and update the labels
        updateProductLabel();
        updateSalesLabel();
    }

    private JPanel createInfoPanel(String title, String initialValue, String iconPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Load and resize the icon
        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel titleLabel = new JLabel(title, scaledIcon, SwingConstants.CENTER);
            titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(titleLabel, BorderLayout.CENTER);
        } else {
            System.err.println("Couldn't find file: " + iconPath);
            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(titleLabel, BorderLayout.CENTER);
        }

        JLabel valueLabel = new JLabel(initialValue, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(valueLabel, BorderLayout.SOUTH);
        panel.setBackground(Color.lightGray);

        // Store the value label reference for later updates
        if (title.equals("Products")) {
            productValueLabel = valueLabel;
        } else if (title.equals("Sales")) {
            salesValueLabel = valueLabel;
        }

        return panel;
    }

    private void updateProductLabel() {
        String query = "SELECT COUNT(*) AS ProductCount FROM Product";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=inventory_management_system;trustServerCertificate=true;", "m", "mm");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                productCount = resultSet.getInt("ProductCount");
                productValueLabel.setText(String.valueOf(productCount));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load product count.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSalesLabel() {
        String query = "SELECT SUM(AmountPaid) AS TotalSales " +
                "FROM Sales ";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=inventory_management_system;trustServerCertificate=true;", "m", "mm");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                totalSales = resultSet.getDouble("TotalSales");
                salesValueLabel.setText("$" + String.format("%.2f", totalSales));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load total sales.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            DashboardPanel dashboardPanel = new DashboardPanel();
            frame.add(dashboardPanel);

            frame.setVisible(true);
        });
    }
}
