package ItemManagmentGUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import java.security.SecureRandom;
import java.util.ArrayList;

public class ItemManagmentFrame extends JFrame {
    public String[] columnNames = {"Product ID", "Product name","Product Brand","Quantity","price","Catagory","Expiry Date","Total"};
    public DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public JTable orderTable= new JTable(tableModel);
    String productName;
    String brand;
    String qtyStr;
    String priceStr;
    String catagory;
    String expiryDateStr;
    
    public JTextField  ProductNameField, ProductBrandField, QuantityField, PriceField,CatagoryField,Expirydate;

    private String generateProductId() {
        // Generate a product ID within 10 characters
        long timestamp = System.currentTimeMillis();
        String productId = "P" + timestamp;
        // If the generated ID is longer than 10 characters, truncate it
        if (productId.length() > 10) {
            productId = productId.substring(0, 10);
        }
        return productId;
    }


    public void newProduct(JTextField ProductNameField,JTextField ProductBrandField,JTextField QuantityField,JTextField PriceField,JTextField CatagoryField,JTextField Expirydate){
        String ProductID=generateProductId();
        String ProductName = ProductNameField.getText();
        String brand = ProductBrandField.getText();
        int Qty = Integer.parseInt(QuantityField.getText());
        double price = Double.parseDouble(PriceField.getText());
        String catagory = CatagoryField.getText();
        String Expiry_date = Expirydate.getText();
        double total= Qty*price;
        ////   Clear the text fields for new input
        ProductNameField.setText("");
        ProductBrandField.setText("");
        QuantityField.setText("");
        PriceField.setText("");
        CatagoryField.setText("");
        Expirydate.setText("");

        ((DefaultTableModel) orderTable.getModel()).addRow(new Object[]{ProductID,ProductName, brand, Qty,price,catagory,Expiry_date,total});
        //     adding product to Table
        DatabaseConnection d = new DatabaseConnection();
        d.addProductRow(d.connection, ProductID, ProductName, brand, price, catagory, Expiry_date, Qty);
        ////   Clear the text fields for new input



    }
    //////
    public void HighestSelling () {

        String sql = "SELECT p.ProductID, p.ProductName, p.ProductCatagory, SUM(ps.Quantity) AS TotalSold + FROM Product p + JOIN ProductSales ps ON p.ProductID = ps.ProductID + GROUP BY p.ProductID, p.ProductName, p.ProductCatagory + ORDER BY TotalSold DESC + LIMIT 10";
        try {
            //DatabaseConnection connection=new DatabaseConnection();
            DatabaseConnection d = new DatabaseConnection();
            Statement stmt = d.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
            model.setRowCount(0); // Clear existing data

            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String productName = rs.getString("ProductName");
                String productBrand = rs.getString("productBrand");
                int productQuantity = rs.getInt("productQuantity");
                double unitPrice = rs.getDouble("Unit_price");
                String productCatagory = rs.getString("ProductCatagory");
                Date productExpireDate = rs.getDate("ProductExpireDate");
                double total = productQuantity * unitPrice;

                model.addRow(new Object[]{productId, productName, productBrand, productQuantity, unitPrice, productCatagory, productExpireDate, total});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to refresh the data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /////
    public void refresh() {

            String sql = "SELECT ProductId, ProductName, productBrand, productQuantity, Unit_price, ProductCatagory, ProductExpireDate FROM Product";
            try {
                //DatabaseConnection connection=new DatabaseConnection();
                DatabaseConnection d = new DatabaseConnection();
                Statement stmt = d.connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);


                DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
                model.setRowCount(0); // Clear existing data

                while (rs.next()) {
                    String productId = rs.getString("ProductId");
                    String productName = rs.getString("ProductName");
                    String productBrand = rs.getString("productBrand");
                    int productQuantity = rs.getInt("productQuantity");
                    double unitPrice = rs.getDouble("Unit_price");
                    String productCatagory = rs.getString("ProductCatagory");
                    Date productExpireDate = rs.getDate("ProductExpireDate");
                    double total = productQuantity * unitPrice;

                    model.addRow(new Object[]{productId, productName, productBrand, productQuantity, unitPrice, productCatagory, productExpireDate, total});
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to refresh the data.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    public void cancelOrder() {
        DatabaseConnection d=new DatabaseConnection();
        int selectedRow=orderTable.getSelectedRow();
        if (selectedRow >= 0){
            String cellValue = String.valueOf(orderTable.getValueAt(selectedRow,0));
            ((DefaultTableModel) orderTable.getModel()).removeRow(selectedRow);
            System.out.println(cellValue);
            d.deleteProductRow(d.connection,cellValue);
            JOptionPane.showMessageDialog(null, "Product deleted successfully!", "Delete Product", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public boolean isValid(JTextField ProductNameField, JTextField ProductBrandField, JTextField QuantityField,
                           JTextField PriceField, JTextField CatagoryField, JTextField Expirydate) {
        try {
            String productName = ProductNameField.getText();
            String brand = ProductBrandField.getText();
            String qtyStr = QuantityField.getText();
            String priceStr = PriceField.getText();
            String catagory = CatagoryField.getText();
            String expiryDateStr = Expirydate.getText();

            if (productName.isEmpty() ||  brand.isEmpty() || qtyStr.isEmpty() || priceStr.isEmpty() || catagory.isEmpty() || expiryDateStr.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            int qty = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Ensure strict date parsing
            Date expiryDate = sdf.parse(expiryDateStr);

            // Check if the expiry date exceeds the days limit of the month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expiryDate);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (dayOfMonth > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                throw new IllegalArgumentException("Invalid day for the given month.");
            }

            // Check if the expiry date is less than the current date
            Date currentDate = new Date();
            if (expiryDate.before(currentDate)) {
                throw new IllegalArgumentException("Expiry date must not be in the past.");
            }

            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantity and Price must be numeric.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Expiry Date must be in the format yyyy-MM-dd.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public void editProduct(){

        DatabaseConnection d=new DatabaseConnection();
        JFrame frame=new JFrame("Edit product");
        JPanel ProductEdit = new JPanel(new GridLayout(7,1));
        JPanel EditBar =new JPanel(new GridLayout(1,1,4,4));


        JLabel productInput2 = new JLabel("New Product Name");
        JTextField Input2 = new JTextField();
        ProductEdit.add(productInput2);
        ProductEdit.add(Input2);
        JLabel productInput3 = new JLabel("New Product Brand");
        JTextField Input3 = new JTextField();
        ProductEdit.add(productInput3);
        ProductEdit.add(Input3);
        JLabel productInput4 = new JLabel("New Product Quantity");
        JTextField Input4 = new JTextField();
        ProductEdit.add(productInput4);
        ProductEdit.add(Input4);
        JLabel productInput5 = new JLabel("New Product Price");
        JTextField Input5 = new JTextField();
        ProductEdit.add(productInput5);
        ProductEdit.add(Input5);
        JLabel productInput6 = new JLabel("New Product Catagory");
        JTextField Input6 = new JTextField();
        ProductEdit.add(productInput6);
        ProductEdit.add(Input6);
        JLabel productInput7 = new JLabel("New Product Expiry Date");
        JTextField Input7 = new JTextField();
        ProductEdit.add(productInput7);
        ProductEdit.add(Input7);
        JButton Edit=new JButton("EDIT");
        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValid(Input2,Input3,Input4,Input5,Input6,Input7)){
                    String productName = Input2.getText();
                    String brand = Input3.getText();
                    String qtyStr = Input4.getText();
                    String priceStr = Input5.getText();
                    String catagory = Input6.getText();
                    String expiryDateStr = Input7.getText();
                    int selectedRow=orderTable.getSelectedRow();
                    if (selectedRow >= 0){
                        String cellValue = String.valueOf(orderTable.getValueAt(selectedRow,0));
                        //((DefaultTableModel) orderTable.getModel()).removeRow(selectedRow);
                        System.out.println(cellValue);
                         d.updateProductRow(d.connection,cellValue,productName,brand,Integer.parseInt(qtyStr),Double.parseDouble(priceStr),catagory, java.sql.Date.valueOf(expiryDateStr));
                        ((DefaultTableModel) orderTable.getModel()).addRow(new Object[]{cellValue,productName,brand,qtyStr,priceStr,catagory,expiryDateStr});
//                        JOptionPane.showMessageDialog(null, "Product deleted successfully!", "Delete Product", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a product to Edit.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

//            //     adding product to Table
//            DatabaseConnection d = new DatabaseConnection();
//            d.addProductRow(d.connection, ProductID, ProductName, brand, price, catagory, Expiry_date, Qty);

                }
               //((DefaultTableModel) orderTable.getModel()).addRow(new Object[]{"ProductID","ProductName","brand","qty","price","catagory","Expiry_date","total"});

            }
        });
        ProductEdit.add(Edit);
        ProductEdit.setBorder(BorderFactory.createTitledBorder(" Edit PRODUCT "));
        EditBar.add(ProductEdit);
        frame.add(EditBar);
        frame.setSize(500,400);
        frame.setVisible(true);



//
//
    }

    public ItemManagmentFrame() {
        //refresh();
        //DatabaseConnection d=new DatabaseConnection();
        getContentPane().setBackground(new Color(145,200,90));
        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(145,200,90));
        // nav bar
        JPanel NavigationBAR=new JPanel(new FlowLayout());
        NavigationBAR.setBackground(new Color(105, 125, 136));
//        NavigationBAR.setSize(20,20);
        NavigationBAR.setPreferredSize(new Dimension(100,100));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);getContentPane().add(NavigationBAR, BorderLayout.NORTH);
        // Order table
//        String[] columnNames = {"Product ID", "Product name","Product Brand","Quantity","price","Catagory","Expiry Date","Total"};
//        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        //orderTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        //Reterived();
        // Order details panel
        JPanel ProductInputPanel = new JPanel(new GridLayout(13, 2, 10, 5));
        ProductInputPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        JPanel SideBar =new JPanel(new GridLayout(1,1,4,4));


        JLabel productInput2 = new JLabel("Product Name");
        JTextField Input2 = new JTextField();
        ProductInputPanel.add(productInput2);
        ProductInputPanel.add(Input2);
        JLabel productInput3 = new JLabel("Product Brand");
        JTextField Input3 = new JTextField();
        ProductInputPanel.add(productInput3);
        ProductInputPanel.add(Input3);
        JLabel productInput4 = new JLabel("Product Quantity");
        JTextField Input4 = new JTextField();
        ProductInputPanel.add(productInput4);
        ProductInputPanel.add(Input4);
        JLabel productInput5 = new JLabel("Product Price");
        JTextField Input5 = new JTextField();
        ProductInputPanel.add(productInput5);
        ProductInputPanel.add(Input5);
        JLabel productInput6 = new JLabel("Product Catagory");
        JTextField Input6 = new JTextField();
        ProductInputPanel.add(productInput6);
        ProductInputPanel.add(Input6);
        JLabel productInput7 = new JLabel("Product Expiry Date");
        JTextField Input7 = new JTextField();
        ProductInputPanel.add(productInput7);
        ProductInputPanel.add(Input7);
        ProductInputPanel.setBorder(BorderFactory.createTitledBorder(" ADD PRODUCT "));
        SideBar.add(ProductInputPanel);
        mainPanel.add(SideBar, BorderLayout.EAST);


        // Add buttons
        JPanel BottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton add_product = new JButton("Add product");
        add_product.setBackground(new Color(120,180,150));
        add_product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(isValid(Input2,Input3,Input4,Input5,Input6,Input7))
                    newProduct(Input2,Input3,Input4,Input5,Input6,Input7);
                //System.out.println("Test");
            }


        });
        JButton cancelOrder = new JButton("Edit product");
        cancelOrder.setBackground(new Color(120,180,150));
        cancelOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
                //cancelOrder();
            }
        });
        JButton refresh = new JButton("Refresh");
        refresh.setBackground(new Color(120,180,150));
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                refresh();
                //((DefaultTableModel) orderTable.getModel()).addRow(new Object[]{"ProductID","ProductName","brand","qty","price","catagory","Expiry_date","total"});

            }
        });
        buttonPanel.add(add_product);
        buttonPanel.add(cancelOrder);
        buttonPanel.add(refresh);
        BottomPanel.add(buttonPanel,BorderLayout.WEST);
        mainPanel.add(BottomPanel, BorderLayout.SOUTH);


        //refresh();
        ImageIcon image = new ImageIcon("C:\\Users\\Yon\\Downloads\\pexels-magda-ehlers-pexels-1337380.jpg");
        setIconImage(image.getImage());


    }
}
