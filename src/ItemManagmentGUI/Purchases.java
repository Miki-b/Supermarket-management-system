package ItemManagmentGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Purchases extends JFrame{

        private JTable orderTable;
        private JTextField productId;
        private JTextField purchaseID;
        private JTextField quantity;
        private JTextField price;
        private JTextField totalPrice;
        private JTextField date;
        private JTextField employeeId;
        private JTextField supplierId;
        private DefaultTableModel tableModel;

    public Purchases() {

        this.purchaseID = new JTextField(10);
        this.productId = new JTextField(10);
        this.price = new JTextField(10);
        this.quantity = new JTextField(10);
        this.totalPrice = new JTextField(10);
        this.employeeId = new JTextField(10);
        this.supplierId = new JTextField(10);
        this.date = new JTextField(10);

        this.setTitle("Purchases Management");
        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setBackground(new Color(145, 200, 90));

        JPanel NavigationBAR = new JPanel(new FlowLayout());
        NavigationBAR.setBackground(new Color(42, 59, 54));
        NavigationBAR.setPreferredSize(new Dimension(100, 100));

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(mainPanel, "Center");
        this.getContentPane().add(NavigationBAR, "North");

        String[] columnNames = new String[]{"Product ID", "Product name", "Purchase ID","Price", "Quantity", "Total Price", "Employee ID", "Supplier ID"};

        this.tableModel = new DefaultTableModel(columnNames, 0);
        this.orderTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(this.orderTable);
        mainPanel.add(tableScrollPane, "Center");

        JPanel OrderInputPanel = new JPanel(new GridLayout(13, 2, 10, 5));
//            OrderInputPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

        JLabel orderInput1 = new JLabel("Product ID");
        OrderInputPanel.add(orderInput1);
        OrderInputPanel.add(productId);

        JLabel orderInput2 = new JLabel("Purchase ID");
        OrderInputPanel.add(orderInput2);
        OrderInputPanel.add(purchaseID);

        JLabel orderInput3 = new JLabel("Product Quantity");
         OrderInputPanel.add(orderInput3);
        OrderInputPanel.add(quantity);

        JLabel productInput5 = new JLabel("Product Price");
         OrderInputPanel.add(productInput5);
        OrderInputPanel.add(price);

        JLabel orderInput6 = new JLabel("Total Price");
         OrderInputPanel.add(orderInput6);
        OrderInputPanel.add(totalPrice);

        JLabel orderInput7 = new JLabel("Order Date");
         OrderInputPanel.add(orderInput7);
        OrderInputPanel.add(date);

        JLabel orderInput8 = new JLabel("Employee ID");
         OrderInputPanel.add(orderInput8);
        OrderInputPanel.add(employeeId);

        JLabel orderInput9 = new JLabel("Supplier ID");
         OrderInputPanel.add(orderInput9);
        OrderInputPanel.add(supplierId);

        OrderInputPanel.setBorder(BorderFactory.createTitledBorder(" Place Order "));

        JPanel SideBar = new JPanel(new GridLayout(1, 1, 4, 4));
        SideBar.add(OrderInputPanel);
        mainPanel.add(SideBar, "East");

        JPanel BottomPanel = new JPanel(new FlowLayout(0));
        JPanel buttonPanel = new JPanel(new FlowLayout());


        JButton addOrder = new JButton("Order");
        addOrder.setBackground(new Color(120, 180, 150));
        addOrder.addActionListener(e -> placeOrder());

        JButton cancelOrder = new JButton("Cancel order");
        cancelOrder.setBackground(new Color(120, 180, 150));
        cancelOrder.addActionListener(e -> cancelOrder());

        JButton refresh = new JButton("Refresh");
        refresh.setBackground(new Color(120, 180, 150));
        refresh.addActionListener(e -> refreshPage());

        buttonPanel.add(addOrder);
        buttonPanel.add(cancelOrder);
        buttonPanel.add(refresh);
        BottomPanel.add(buttonPanel, "West");
        mainPanel.add(BottomPanel, "South");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void placeOrder() {
        try {
            if (!isValidOrder(purchaseID, productId, price, quantity, totalPrice, date , employeeId, supplierId)) {
                throw new IllegalArgumentException("Invalid order details.");}

            String[] orderData = {
                    purchaseID.getText(),
                    productId.getText(),
                    price.getText(),
                    quantity.getText(),
                    totalPrice.getText(),
                    date.getText(),
                    employeeId.getText(),
                    supplierId.getText()

            };



            tableModel.addRow(orderData);
            JOptionPane.showMessageDialog(this, "Order placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelOrder() {
        try {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow < 0) {
                throw new Exception("No order selected.");
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Order canceled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void refreshPage() {
        try {
            // Clear text fields
            purchaseID.setText("");
            productId.setText("");
            price.setText("");
            quantity.setText("");
            totalPrice.setText("");
            employeeId.setText("");
            supplierId.setText("");
            date.setText("");

            // TODO add sql statement to reload database


            JOptionPane.showMessageDialog(this, "Page refreshed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while refreshing the page: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isValidOrder(JTextField purchaseID, JTextField productId, JTextField price, JTextField quantity, JTextField totalPrice, JTextField date, JTextField employeeId, JTextField supplierId) {
        try {
            // Check if any field is empty
            if (purchaseID.getText().isEmpty() ||
                    productId.getText().isEmpty() ||
                    price.getText().isEmpty() ||
                    quantity.getText().isEmpty() ||
                    totalPrice.getText().isEmpty() ||
                    date.getText().isEmpty() ||
                    employeeId.getText().isEmpty() ||
                    supplierId.getText().isEmpty()) {
                return false;
            }

            // Parse values
            double priceValue = Double.parseDouble(price.getText());
            int quantityValue = Integer.parseInt(quantity.getText());
            double totalPriceValue = Double.parseDouble(totalPrice.getText());

            // Check if values are positive
            if (priceValue <= 0 || quantityValue <= 0 || totalPriceValue <= 0) {
                return false;
            }

            // Check if total price matches price * quantity
            if (totalPriceValue != (double) priceValue * quantityValue) {
                return false;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date.getText());
            } catch (ParseException e) {
                return false;
            }


            // Additional validation checks can be added here (e.g., date format, existing IDs)
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

         public static void main(String[] args) {
            new Purchases();
        }


}
