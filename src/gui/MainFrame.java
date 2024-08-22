package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private DashboardPanel dashboard;

    public MainFrame(String title) {
        super(title);

        dashboard = new DashboardPanel();

        setLayout(new BorderLayout());
        this.add(dashboard,BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
    }
}
