package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TablesPanel extends JPanel {
    private JTable highestSellingProductsTable;
    private JTable latestSalesTable;
    private JTable recentlyAddedProductsTable;

    public TablesPanel() {
        highestSellingProductsTable = createTable(new String[]{"Product Name", "Total Sold", "Total Quantity"});
        latestSalesTable = createTable(new String[]{"Product Name", "Date", "Total Sale"});
        recentlyAddedProductsTable = createTable(new String[]{"Product Name", "Category", "Price"});

        panelLayout();

        // Load data into tables
        loadRecentlyAddedProducts();
        loadHighestSellingProducts();
        loadLatestSales();
    }

    private void panelLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Allow components to grow in both dimensions
        gbc.weightx = 1.0; // Allow horizontal growth
        gbc.weighty = 1.0; // Allow vertical growth
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Create scroll panes with preferred size
        JScrollPane highestSellingProductsScrollPane = new JScrollPane(highestSellingProductsTable);
        JScrollPane latestSalesScrollPane = new JScrollPane(latestSalesTable);
        JScrollPane recentlyAddedProductsScrollPane = new JScrollPane(recentlyAddedProductsTable);

        // Add highest selling products table with title
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createTitledPanel("Highest Selling Products", highestSellingProductsScrollPane), gbc);

        // Add latest sales table with title
        gbc.gridx = 1;
        add(createTitledPanel("Latest Sales", latestSalesScrollPane), gbc);

        // Add recently added products table with title
        gbc.gridx = 2;
        add(createTitledPanel("Recently Added Products", recentlyAddedProductsScrollPane), gbc);
        this.setBackground(Color.LIGHT_GRAY);
    }

    private JPanel createTitledPanel(String title, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private JTable createTable(String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        return table;
    }

    private void loadRecentlyAddedProducts() {
        String query = "SELECT p.ProductName, p.ProductCatagory AS Category, p.Unit_price AS Price " +
                "FROM Product p " +
                "ORDER BY p.ProductId DESC " +
                "OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY;";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=inventory_management_system;trustServerCertificate=true;", "m", "mm");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            DefaultTableModel model = (DefaultTableModel) recentlyAddedProductsTable.getModel();

            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                String category = resultSet.getString("Category");
                double price = resultSet.getDouble("Price");
                model.addRow(new Object[]{productName, category, price});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load recently added products.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHighestSellingProducts() {
        String query = "SELECT p.ProductName, SUM(ps.Quantity) AS TotalSold, p.productQuantity AS TotalQuantity " +
                "FROM ProductSales ps " +
                "JOIN Product p ON ps.ProductID = p.ProductId " +
                "GROUP BY p.ProductName, p.productQuantity " +
                "ORDER BY TotalSold DESC;";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=inventory_management_system;trustServerCertificate=true;", "m", "mm");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            DefaultTableModel model = (DefaultTableModel) highestSellingProductsTable.getModel();

            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                int totalSold = resultSet.getInt("TotalSold");
                int totalQuantity = resultSet.getInt("TotalQuantity");
                model.addRow(new Object[]{productName, totalSold, totalQuantity});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load highest selling products.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadLatestSales() {
        String query = "SELECT p.ProductName, s.PaymentDate AS Date, (ps.Quantity * p.Unit_price) AS TotalSale " +
                "FROM Sales s " +
                "JOIN ProductSales ps ON s.SalesID = ps.SalesID " +
                "JOIN Product p ON ps.ProductID = p.ProductId " +
                "ORDER BY s.PaymentDate DESC "+
                "OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY;";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=inventory_management_system;trustServerCertificate=true;", "m", "mm");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            DefaultTableModel model = (DefaultTableModel) latestSalesTable.getModel();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Date format to convert Date to String

            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                Timestamp date = resultSet.getTimestamp("Date"); // Use getTimestamp for Date
                String formattedDate = dateFormat.format(date); // Convert Date to String
                double totalSale = resultSet.getDouble("TotalSale");
                model.addRow(new Object[]{productName, formattedDate, totalSale});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load latest sales.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
