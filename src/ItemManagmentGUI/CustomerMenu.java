package ItemManagmentGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CustomerMenu extends JFrame{


    private JTextField productId;

    private JTextField quantity;
    private JTextField price;
    private JTextField employeeId;
    private JTextField supplierId;
    private DefaultTableModel tableModel;
    private JTable orderTable;

    public CustomerMenu() {


        this.productId = new JTextField(10);
        this.price = new JTextField(10);
        this.quantity = new JTextField(10);
        this.employeeId = new JTextField(10);
        this.supplierId = new JTextField(10);

        this.setTitle("Purchases Management");
        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setBackground(new Color(145, 200, 90));

        JPanel NavigationBAR = new JPanel(new FlowLayout());
        NavigationBAR.setBackground(new Color(42, 59, 54));
        NavigationBAR.setPreferredSize(new Dimension(100, 100));

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(mainPanel, "Center");
        this.getContentPane().add(NavigationBAR, "North");

        String[] columnNames = new String[]{"Purchase ID","Product ID", "Price", "Quantity", "Total Price", "Date", "Employee ID", "Supplier ID"};

        this.tableModel = new DefaultTableModel(columnNames, 0);
        this.orderTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(this.orderTable);
        mainPanel.add(tableScrollPane, "Center");

        JPanel OrderInputPanel = new JPanel(new GridLayout(13, 2, 10, 5));
//            OrderInputPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));



        JLabel orderInput2 = new JLabel("Product ID");
        OrderInputPanel.add(orderInput2);
        OrderInputPanel.add(productId);

        JLabel orderInput3 = new JLabel("Product Quantity");
        OrderInputPanel.add(orderInput3);
        OrderInputPanel.add(quantity);

        JLabel productInput5 = new JLabel("Product Price");
        OrderInputPanel.add(productInput5);
        OrderInputPanel.add(price);

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
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setVisible(true);
    }

    public void placeOrder() {
        try {
            if (!isValidOrder( productId, price, quantity, employeeId, supplierId)) {
                throw new IllegalArgumentException("Invalid order details.");}



            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String Date = currentDate.format(formatter);

            double Price = Double.parseDouble(price.getText());
            int Quantity = Integer.parseInt(quantity.getText());
            String totalPrice = String.valueOf(Price*Quantity);

            Random random = new Random();
            int randomInt = random.nextInt(1000);
            String PurchaseID = String.valueOf(randomInt);

            String[] orderData = {
                    PurchaseID,
                    productId.getText(),
                    price.getText(),
                    quantity.getText(),
                    totalPrice,
                    Date,
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
            productId.setText("");
            price.setText("");
            quantity.setText("");
            employeeId.setText("");
            supplierId.setText("");


            // TODO add sql statement to reload database


            JOptionPane.showMessageDialog(this, "Page refreshed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while refreshing the page: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isValidOrder( JTextField productId, JTextField price, JTextField quantity,JTextField employeeId, JTextField supplierId) {
        try {
            // Check if any field is empty
            if (    productId.getText().isEmpty() ||
                    price.getText().isEmpty() ||
                    quantity.getText().isEmpty() ||
                    employeeId.getText().isEmpty() ||
                    supplierId.getText().isEmpty()) {
                return false;
            }

            // Parse values
            double priceValue = Double.parseDouble(price.getText());
            int quantityValue = Integer.parseInt(quantity.getText());


            // Check if values are positive
            if (priceValue <= 0 || quantityValue <= 0) {
                return false;
            }

            // Check if total price matches price * quantity


//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            dateFormat.setLenient(false);
//            try {
//                dateFormat.parse(date.getText());
//            } catch (ParseException e) {
//                return false;
//            }


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
