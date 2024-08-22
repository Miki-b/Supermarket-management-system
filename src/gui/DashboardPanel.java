package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private UpperPanel upperPanel;
    private TablesPanel tablePanel;
    private JPanel lowerPanel;

    public DashboardPanel() {
        upperPanel = new UpperPanel();
        tablePanel = new TablesPanel();
        lowerPanel = createLowerPanel();

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.DARK_GRAY);

        Border outerBorder = new LineBorder(Color.DARK_GRAY, 2);
        Border innerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        upperPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        tablePanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        lowerPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        add(upperPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
    }

    private JPanel createLowerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel footerLabel = new JLabel("Footer Content", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(footerLabel, BorderLayout.CENTER);

        return panel;
    }

}
