package ItemManagmentGUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

class Product {
    private String productId;
    private String productName;
    private String productBrand;
    private int productQuantity;
    private double unitPrice;
    private String productCatagory;
    private Date productExpireDate;

    public Product(String productId, String productName, String productBrand, int productQuantity, double unitPrice, String productCatagory, Date productExpireDate) {
        this.productId = productId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productQuantity = productQuantity;
        this.unitPrice = unitPrice;
        this.productCatagory = productCatagory;
        this.productExpireDate = productExpireDate;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getProductCatagory() {
        return productCatagory;
    }

    public Date getProductExpireDate() {
        return productExpireDate;
    }
}
class NonEditableTableModel extends DefaultTableModel {

    public NonEditableTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // All cells are non-editable
    }
}


public class PlayGround extends JFrame {
    DefaultListModel<String> arr = new DefaultListModel<>();
    ItemManagmentFrame products = new ItemManagmentFrame();
    private JTable orderTable;

    private JScrollPane scrollPane = new JScrollPane();
    private JTextField employeeNameField, orderDateField, totalAmountField, quantityField, supplierIDField;
    private JLabel product_ID, product_name, product_Brand, product_Price, product_Catagory, product_ExpiryDate;
    private DefaultListModel<String> cartModel = new DefaultListModel<>();
    private JList<String> cartList = new JList<>(cartModel);
    private ArrayList<Product> cartItems = new ArrayList<>();
    private Product selectedProduct;
    String productId;
    ArrayList<String> productID=new ArrayList<>();


    String productName;
    String productBrand;
    ArrayList<String> productarr=new ArrayList<>();

    ArrayList<Integer> productQuantity=new ArrayList<>();
    ArrayList<Double> Amount=new ArrayList<>();

    String productCatagory;
    Date productExpireDate;
    public PlayGround() {

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(145, 200, 90));

        JPanel navigationBar = createNavigationBar();
        JPanel orderTablePanel = createOrderTablePanel();
        JPanel sideBar = createSideBar();
        JPanel cartPanel = createCartPanel();
        JPanel bottomPanel = createBottomPanel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(navigationBar, BorderLayout.NORTH);

        mainPanel.add(orderTablePanel, BorderLayout.CENTER);
        mainPanel.add(sideBar, BorderLayout.WEST);
        mainPanel.add(cartPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        ImageIcon image = new ImageIcon("C:\\Users\\Yon\\Downloads\\pexels-magda-ehlers-pexels-1337380.jpg");
        setIconImage(image.getImage());
    }

    private JPanel createNavigationBar() {
        JPanel navigationBar = new JPanel(new FlowLayout());
        navigationBar.setBackground(new Color(91, 129, 138, 187));
        navigationBar.setPreferredSize(new Dimension(100, 100));
        return navigationBar;
    }

    private JPanel createOrderTablePanel() {
        String[] columnNames = {"Order ID", "PaymentMethod", "Total Price", "Total Quantity", "Current Date"};
        NonEditableTableModel tableModel = new NonEditableTableModel(null, columnNames); // Pass null data initially
        orderTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tableScrollPane, BorderLayout.CENTER);
        return panel;
    }


    private JPanel createSideBar() {
        JPanel sideBar = new JPanel(new GridLayout(2, 1, 4, 4));

        JPanel productListPanel = new JPanel(new FlowLayout());
        productListPanel.setBorder(BorderFactory.createTitledBorder("PRODUCT LIST"));
        JList<String> productList = new JList<>(arr);
        scrollPane.setViewportView(productList);
        productList.setLayoutOrientation(JList.VERTICAL);
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(50, 30));
        JPanel quantityPanel = new JPanel(new BorderLayout(2, 5));
        quantityPanel.add(new JLabel("Quantity: "), BorderLayout.NORTH);
        quantityPanel.add(quantityField, BorderLayout.SOUTH);
        productListPanel.add(scrollPane, BorderLayout.WEST);
        productListPanel.add(quantityPanel, BorderLayout.EAST);

        JPanel productDetailPanel = createProductDetailPanel();

        sideBar.add(productListPanel);
        sideBar.add(productDetailPanel);

        return sideBar;
    }

    private JPanel createProductDetailPanel() {
        JPanel productDetailPanel = new JPanel(new GridLayout(6, 1, 2, 2));
        productDetailPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        product_ID = new JLabel("Product ID: ");
        product_name = new JLabel("Product Name: ");
        product_Brand = new JLabel("Product Brand: ");
        product_Price = new JLabel("Product Price: ");
        product_Catagory = new JLabel("Product Catagory: ");
        product_ExpiryDate = new JLabel("Product Expiry Date: ");
        productDetailPanel.add(product_ID);
        productDetailPanel.add(product_name);
        productDetailPanel.add(product_Brand);
        productDetailPanel.add(product_Price);
        productDetailPanel.add(product_Catagory);
        productDetailPanel.add(product_ExpiryDate);
        return productDetailPanel;
    }

    private JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Cart"));
        JScrollPane cartScrollPane = new JScrollPane(cartList);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(new Color(120, 180, 150));
        addToCartButton.addActionListener(e -> addToCart());
        cartPanel.add(addToCartButton, BorderLayout.SOUTH);
        return cartPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addSaleButton = new JButton("ADD SALE");
        addSaleButton.setBackground(new Color(120, 180, 150));
        JButton cancelOrderButton = new JButton("Cancel Sale");
        cancelOrderButton.setBackground(new Color(120, 180, 150));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(120, 180, 150));

        buttonPanel.add(addSaleButton);
        buttonPanel.add(cancelOrderButton);
        buttonPanel.add(refreshButton);

        bottomPanel.add(buttonPanel, BorderLayout.WEST);

        addSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> productarr=new ArrayList<>();
                addSale();
            }
        });
        cancelOrderButton.addActionListener(e -> cancelOrder());
        refreshButton.addActionListener(e -> refreshProductList());

        return bottomPanel;
    }

    private void setupListeners() {
        // Add additional listeners here if needed
    }
    private String generateSalesId() {
        // Generate a product ID within 10 characters
        long timestamp = System.currentTimeMillis();
        String productId = "S" + timestamp;
        // If the generated ID is longer than 10 characters, truncate it
        if (productId.length() > 10) {
            productId = productId.substring(0, 10);
        }
        return productId;
    }
    private void addSale() {
        int totalQuantity=0;
        for (int num : productQuantity) {
            totalQuantity += num;
        }
        int totalamt=0;
        for (double num : Amount) {
            totalamt += num;
        }
        DatabaseConnection d = new DatabaseConnection();
        Timestamp paymentDate = new Timestamp(new Date().getTime());
        d.addsalesRow(d.connection, generateSalesId(), "cash", paymentDate, "EMP01", "00", productarr,productQuantity, Amount );
        ((DefaultTableModel) orderTable.getModel()).addRow(new Object[]{generateSalesId(), "cash",totalamt, totalQuantity, paymentDate});

        productarr.clear();
        productQuantity.clear();
        Amount.clear();
        // Clear the cart after adding sale
        cartModel.clear();
        cartItems.clear();
    }

    public void addToCart() {

        String quantityStr = quantityField.getText();
        if (!quantityStr.isEmpty() && selectedProduct != null) {
            int quantity = Integer.parseInt(quantityStr);
            String cartItem = selectedProduct.getProductName() + " - Quantity: " + quantity;
            productarr.add(selectedProduct.getProductId());
            productQuantity.add(quantity);
            Amount.add(selectedProduct.getUnitPrice());
            System.out.println(productarr);
            System.out.println(productQuantity);
            System.out.println(Amount);
            cartModel.addElement(cartItem);
            cartItems.add(new Product(
                    selectedProduct.getProductId(),
                    selectedProduct.getProductName(),
                    selectedProduct.getProductBrand(),
                    quantity, // Update the quantity here
                    selectedProduct.getUnitPrice(),
                    selectedProduct.getProductCatagory(),
                    selectedProduct.getProductExpireDate()
            ));
            quantityField.setText("");

        } else {
            JOptionPane.showMessageDialog(null, "Please select a product and enter a quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelOrder() {
        JOptionPane.showMessageDialog(null, "Order Canceled successfully!", "CancelOrder", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshProductList() {
        String sql = "SELECT ProductId, ProductName, productBrand, productQuantity, Unit_price, ProductCatagory, ProductExpireDate FROM Product";
        try {

            DatabaseConnection d = new DatabaseConnection();
            Statement stmt = d.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            JPanel productListPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String productName = rs.getString("ProductName");
                String productBrand = rs.getString("productBrand");
                int productQuantity = rs.getInt("productQuantity");
                double unitPrice = rs.getDouble("Unit_price");
                String productCatagory = rs.getString("ProductCatagory");
                Date productExpireDate = rs.getDate("ProductExpireDate");

                Product product = new Product(productId, productName, productBrand, productQuantity, unitPrice, productCatagory, productExpireDate);
                JButton button = new JButton(productName);
                button.setBackground(new Color(120, 180, 150));
                button.addActionListener(e -> {
                    updateProductDetails(product);
                    selectedProduct = product;
                });
                productListPanel.add(button);
            }
            scrollPane.setViewportView(productListPanel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to refresh the data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProductDetails(Product product) {
        product_ID.setText("Product ID: " + product.getProductId());
        product_name.setText("Product Name: " + product.getProductName());
        product_Brand.setText("Product Brand: " + product.getProductBrand());
        product_Price.setText("Product Price: " + product.getUnitPrice());
        product_Catagory.setText("Product Catagory: " + product.getProductCatagory());
        product_ExpiryDate.setText("Product Expiry Date: " + product.getProductExpireDate());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlayGround frame = new PlayGround();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
