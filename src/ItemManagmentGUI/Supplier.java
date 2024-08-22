package ItemManagmentGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class Supplier extends JFrame {
   private JTextField SupplierName;
   private JTextField Location;
   private JTextField PhoneNo;
   private JTextField Email;
   private JTable supplierTable;
   private DefaultTableModel tableModel;

   public Supplier() {

      this.SupplierName = new JTextField(10);
      this.Location = new JTextField(10);
      this.PhoneNo = new JTextField(10);
      this.Email = new JTextField(10);

//      setTitle("Supplier Management");
//      setExtendedState(JFrame.MAXIMIZED_BOTH);
//      setDefaultCloseOperation(EXIT_ON_CLOSE);

      JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setBackground(new Color(145, 200, 90));

      JPanel NavigationBAR = new JPanel(new FlowLayout());
      NavigationBAR.setBackground(new Color(42, 59, 54));
      NavigationBAR.setPreferredSize(new Dimension(100, 100));

      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(mainPanel, "Center");
      this.getContentPane().add(NavigationBAR, "North");

      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(mainPanel, "Center");
      this.getContentPane().add(NavigationBAR, "North");

      String[] columnNames = new String[]{"Supplier ID", "Supplier Name", "Location", "Phone Number", "Email"};

      this.tableModel = new DefaultTableModel(columnNames, 0);
      this.supplierTable = new JTable(tableModel);

      JScrollPane tableScrollPane = new JScrollPane(this.supplierTable);
      mainPanel.add(tableScrollPane, "Center");

      JPanel supplierInputPanel = new JPanel(new GridLayout(13, 2, 10, 5));



      JLabel orderInput2 = new JLabel("Supplier Name");
      supplierInputPanel.add(orderInput2);
      supplierInputPanel.add(SupplierName);

      JLabel orderInput3 = new JLabel("Location");
      supplierInputPanel.add(orderInput3);
      supplierInputPanel.add(Location);

      JLabel productInput5 = new JLabel("Phone Number");
      supplierInputPanel.add(productInput5);
      supplierInputPanel.add(PhoneNo);

      JLabel orderInput6 = new JLabel("Email");
      supplierInputPanel.add(orderInput6);
      supplierInputPanel.add(Email);

      supplierInputPanel.setBorder(BorderFactory.createTitledBorder(" Place record "));
      JPanel sideBar = new JPanel();

      sideBar.add(supplierInputPanel);
      add(sideBar, BorderLayout.EAST);

      JPanel BottomPanel = new JPanel(new FlowLayout(0));
      JPanel buttonPanel = new JPanel(new FlowLayout());


      JButton addSupplier = new JButton("Register");
      addSupplier.setBackground(new Color(120, 180, 150));
      addSupplier.addActionListener(e -> register());

      JButton removeSupplier = new JButton("Remove");
      removeSupplier.setBackground(new Color(120, 180, 150));
      removeSupplier.addActionListener(e -> remove());

      JButton refresh = new JButton("Refresh");
      refresh.setBackground(new Color(120, 180, 150));
      refresh.addActionListener(e -> refreshPage());

      buttonPanel.add(addSupplier);
      buttonPanel.add(removeSupplier);
      buttonPanel.add(refresh);
      BottomPanel.add(buttonPanel, "West");
      mainPanel.add(BottomPanel, "South");

      //setVisible(true);
   }

   public void register() {
      try {
      if (!isValidSupplier(SupplierName, PhoneNo, Location, Email)) {
         throw new IllegalArgumentException("Invalid Supplier details.");}

      Random random = new Random();
      int randomInt = random.nextInt(1000);
      String SupplierID = String.valueOf(randomInt);

      String[] supplierData = {
              SupplierID,
              SupplierName.getText(),
              PhoneNo.getText(),
              Location.getText(),
              Email.getText()

      };

      tableModel.addRow(supplierData);
      JOptionPane.showMessageDialog(this, "Supplier " + getName()+ " registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
   } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
   }
}

   public void remove() {
      try {
         int selectedRow = supplierTable.getSelectedRow();
         if (selectedRow < 0) {
            throw new Exception("No Supplier selected.");
         }
         tableModel.removeRow(selectedRow);
         JOptionPane.showMessageDialog(this, "Supplier " + this.getName() + " removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
         JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }

   }

   public boolean isValidSupplier(JTextField SupplierName, JTextField PhoneNo, JTextField Email, JTextField Location) {
      try {
            // Check if any field is empty
            if (    SupplierName.getText().isEmpty() ||
                    PhoneNo.getText().isEmpty() ||
                    Email.getText().isEmpty() ||
                    Location.getText().isEmpty()
            ) {
               return false;
            }

//         if (!isValidEmail(Email.getText())) {
//            return false;
//         }

            return true;
         } catch (NumberFormatException e) {
            return false;
         }
   }

   public void refreshPage() {
         try {
            // Clear text fields

            SupplierName.setText("");
            PhoneNo.setText("");
            Email.setText("");
            Location.setText("");

            // TODO add sql statement to reload database


            JOptionPane.showMessageDialog(this, "Page refreshed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
         } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while refreshing the page: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
   }

   private boolean isValidEmail(String email) {
      // Use a regular expression to validate the email format
      String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
      return email.matches(regex);
   }


   public static void main (String[]args){
         new Supplier();
      }
}
