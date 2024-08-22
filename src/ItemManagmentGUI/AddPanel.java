package ItemManagmentGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddPanel extends JPanel {
    Border b1 = BorderFactory.createTitledBorder("Add An Item");
    Border b2 = BorderFactory.createEmptyBorder(5,5,5,5);
    JButton save;

    public AddPanel() {
        this.setBorder(BorderFactory.createCompoundBorder(b1,b2));
        this.setLayout(new BorderLayout());
        save = new JButton("Save");
        add(save, BorderLayout.SOUTH);

    }
}
