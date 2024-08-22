package ItemManagmentGUI;

import gui.DashboardPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenuFrame extends JPanel implements ActionListener {

    JButton itemButton;
    JButton orderButton;
    JButton customerButton;
    OptionFrames options;

    JPanel menu;
    Border b1 = BorderFactory.createTitledBorder("Welcome to The Admin Menu");
    Border b2 = BorderFactory.createEmptyBorder(5,5,5,5);

    public AdminMenuFrame() {
        setLayout(new BorderLayout());
        menu = new JPanel();

        itemButton = new JButton("Item Management");
        orderButton = new JButton("Order Management");
        customerButton = new JButton("Customer Management");

        itemButton.addActionListener(this);
        orderButton.addActionListener(this);
        customerButton.addActionListener(this);

        // Navigation bar
        JPanel NavigationBAR = new JPanel(new FlowLayout());
        NavigationBAR.setBackground(new Color(42, 59, 54));
        NavigationBAR.setPreferredSize(new Dimension(100, 100));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        // Create content panel for the Dashboard tab
        DashboardPanel panel1 = new DashboardPanel();

        // Add the Dashboard tab to the pane with title
        tabbedPane.addTab("Dashboard", panel1);
        tabbedPane.setPreferredSize(new Dimension(500, 500));

        add(tabbedPane, BorderLayout.CENTER);
        menu.setVisible(true);
    }

    public void setOptions(OptionFrames options) {
        this.options = options;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == itemButton) {
            options.displayItem();
        } else if (clicked == orderButton) {
            options.displayOrder();
        } else if (clicked == customerButton) {
            options.displayCustomer();
        }
    }
}
