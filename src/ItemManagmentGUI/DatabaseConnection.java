package ItemManagmentGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection extends JFrame {
    Connection connection;
    public String[] columnNames = {"Product ID", "Product name","Product Brand","Quantity","price","Catagory","Expiry Date","Total"};
    public DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public JTable orderTable= new JTable(tableModel);
    public void HighestSellingProducts(){

    }
    public void addProductRow(Connection connection,String ID, String ProductName,String brand,double price,String catagory,String Expiry_date,int Qty){
        try {
            String insertQuery = "INSERT INTO Product (ProductID, ProductName, productBrand, productQuantity, ProductCatagory, ProductExpireDate, Unit_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, ProductName);
            preparedStatement.setString(3, brand);
            preparedStatement.setInt(4, Qty);
            preparedStatement.setString(5, catagory);
            preparedStatement.setDate(6, Date.valueOf(Expiry_date));
            preparedStatement.setDouble(7, price);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
            //connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateProductRow(Connection connection, String row, String ProductName, String brand,  int Qty,double price, String catagory,Date Expiry_date){
        try {
            System.out.println("trying to edit");
            String updateQuery = "UPDATE Product SET ProductName = ?,productBrand = ?,productQuantity = ?,ProductCatagory = ?,ProductExpireDate= ?,Unit_price=? WHERE ProductId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, ProductName);
            preparedStatement.setString(2, brand);
            preparedStatement.setInt(3, Qty);
            preparedStatement.setString(4, catagory);
            preparedStatement.setDate(5, Expiry_date);
            preparedStatement.setDouble(6, price);
            preparedStatement.setString(7, row);
            int rowsAffected = preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product updated successfully!", "Delete Product", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(rowsAffected + " row(s) updated successfully.");
            //connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void deleteProductRow(Connection connection,String row){
        try {
            String deleteQuery = "DELETE FROM Product WHERE ProductID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, row);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted successfully.");
            //connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
    public void addsalesRow(Connection connection, String salesId, String paymentMethod, Timestamp paymentDate, String employeeId, String customerId, ArrayList<String> productid, ArrayList<Integer> qty, ArrayList<Double> total) {
        try {
            // Insert into Sales table
            int totalQuantity=0;
            for (int num : qty) {
                totalQuantity += num;
            }
            int totalamt=0;
            for (double num : total) {
                totalamt += num;
            }
            System.out.println("yes I am adding");
            String insertSalesSQL = "INSERT INTO Sales (SalesID,Quantity, PaymentMethod,AmountPaid, PaymentDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSalesSQL);
            preparedStatement.setString(1, salesId);
            preparedStatement.setInt(2, totalQuantity);
            preparedStatement.setString(3, paymentMethod);
            preparedStatement.setDouble(4, totalamt);
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(paymentDate.getTime()));
            //preparedStatement.setString(6, employeeId);
            //preparedStatement.setString(7, customerId);
            preparedStatement.executeUpdate();

            // Insert into SalesDetails table
            String insertSalesDetailsSQL = "INSERT INTO ProductSales (SalesID, ProductID, Quantity) VALUES (?, ?, ?)";
           for (int j=0;j<productid.size();j++) {
               preparedStatement = connection.prepareStatement(insertSalesDetailsSQL);
                preparedStatement.setString(1, salesId);
                preparedStatement.setString(2, productid.get(j));
                preparedStatement.setInt(3, qty.get(j));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public ArrayList getProductRow(Connection connection) {
//    try {
//
//        //public JTable orderTable= new JTable(tableModel);
//        ItemManagmentFrame p=new ItemManagmentFrame();
//        String selectQuery = "SELECT * FROM Product";
//        Statement stmt = connection.createStatement();
//        ResultSet resultSet = stmt.executeQuery(selectQuery);
//
//        while (resultSet.next()) {
//
//
//            String ProductID = resultSet.getString("ProductID");
//            String ProductName = resultSet.getString("ProductName");
//            String brand = resultSet.getString("productBrand");
//            int Qty = Integer.parseInt(resultSet.getString("productQuantity"));
////            String catagory = resultSet.getString("productCatagory");
////            Date  Expiry_date = Date.valueOf(resultSet.getString("productExpiryDate"));
////            double  price = Double.parseDouble(resultSet.getString("ProductName"));
//            p.ReterivedProductRow(ProductID, ProductName, brand ,Qty);
//            ((DefaultTableModel) orderTable.getModel()).addRow(new Object[]{"ProductID","ProductName","brand","Qty","ProductID","ProductName","brand","Qty"});
//            ArrayList<String> myFriends = new ArrayList<String>(List.of(ProductID,ProductName,brand,ProductID,ProductName,brand));
//            JOptionPane.showMessageDialog(null, "Product added successfully!","New Order", JOptionPane.INFORMATION_MESSAGE);
//            return myFriends;
//
//
//            //System.out.println("ProductID: " + ProductID + ", ProductName: " + ProductName);
//        }
//       // stmt.close();
//       // connection.close();
//    }catch (SQLException e){
//        System.out.println(e.getMessage());
//    }catch(NumberFormatException n){
//        System.out.println(n.getMessage());
//    }
//    }
    public static void main(String[] args) {
        new DatabaseConnection();
    }


    String connectingString = "jdbc:sqlserver://localhost;databasename=inventory_management_system;trustServerCertificate=true;IntegratedSecurity=true";
    public DatabaseConnection(){
    try {

        ItemManagmentFrame i=new ItemManagmentFrame();

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(connectingString,"m","mm");
        Statement stmt=connection.createStatement();

        //getRow(connection);
        //deleteRow(connection);
        //getRow(connection);
//        ResultSet rs=stmt.executeQuery("SELECT ProductID,ProductName from Product");
//        while (rs.next()){
//            String id =rs.getString("ProductID");
//            String name=rs.getString("ProductName");
//            System.out.println(id+"  "+name);
//        }
//        rs.close();


    }catch (SQLException e){
        System.out.println(e.getMessage());
    }catch (ClassNotFoundException c){
        System.out.println(c.getMessage());
    }catch(ArrayIndexOutOfBoundsException ar){
        System.out.println(ar.getMessage());
    }

    }
    //frame.setVisible(true);
}


