package ItemManagmentGUI;

import gui.DashboardPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JPanel implements ActionListener {

    JButton itemButton;
    JButton orderButton;
    JButton customerButton;
    OptionFrames options;

    JPanel menu;
    Border b1 = BorderFactory.createTitledBorder("Welcome to The Menu");
    Border b2 = BorderFactory.createEmptyBorder(5,5,5,5);

    ItemManagmentFrame item=new ItemManagmentFrame();
    CustomerMenu customer=new CustomerMenu();
    PlayGround order=new PlayGround();
    Supplier supplier=new Supplier();

    public MenuFrame() {
        //super("Main Menu"); // Removed since we're no longer extending JFrame
        setLayout(new BorderLayout());
        menu = new JPanel();
        itemButton = new JButton("Item Managment");
        orderButton = new JButton("Order Managment");
        customerButton = new JButton("Customer Managment");
        itemButton.addActionListener(this);
        orderButton.addActionListener(this);
        customerButton.addActionListener(this);

        // nav bar
        JPanel NavigationBAR=new JPanel(new FlowLayout());
        NavigationBAR.setBackground(new Color(42, 59, 54));
        NavigationBAR.setPreferredSize(new Dimension(100,100));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT );

        // Create content panels for each tab
        DashboardPanel panel1 = new DashboardPanel();

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("This is content for tab 2"));

        // Add tabs to the pane with titles
        //tabbedPane.addTab("Dashboard", panel1);
        tabbedPane.addTab("Sales", order.getContentPane());
        tabbedPane.addTab("Product", item.getContentPane());
        tabbedPane.addTab("Purchase Order", customer.getContentPane());
        tabbedPane.addTab("Suppliers", supplier.getContentPane());
        tabbedPane.setPreferredSize(new Dimension(500,500));

        add(tabbedPane, BorderLayout.CENTER);
        menu.setVisible(true);
    }

    public void setOptions(OptionFrames options) {
        this.options = options;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton Clicked = (JButton) e.getSource();
        if(Clicked == itemButton){
            options.displayItem();
        }
        else if(Clicked == orderButton){
            options.displayOrder();
        }
        else if(Clicked == customerButton){
            options.displayCustomer();
        }
    }
}
