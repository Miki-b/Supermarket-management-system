package ItemManagmentGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionPanel extends JPanel implements ActionListener {
    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    AddPanel addpanel;

    PanelDisplayer panels;

    Border b1 = BorderFactory.createTitledBorder("Options");
    Border b2 = BorderFactory.createEmptyBorder(5,5,5,5);

    Dimension dim;
    public OptionPanel() {
        this.setLayout(new GridBagLayout());
        dim = new Dimension();
        dim.width =200;
        this.setPreferredSize(dim);
        this.setBorder(BorderFactory.createCompoundBorder(b1,b2));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 0.01;

        ////////////////////    First Button  ///////////////////
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 0.01;
        gc.gridx = 0;
        gc.gridy = 0;

        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.SOUTH;
        add(addButton,gc);

        ////////////////////     Second Button  ///////////////////
        gc.weightx = 1;
        gc.weighty = 0.01;
        gc.gridy = 1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.CENTER;
        add(editButton,gc);

        ////////////////////    Third Button  /////////////////////
        gc.weightx = 1;
        gc.weighty = 0.01;
        gc.insets = new Insets(5,0,0,0);
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridy = 2;
        add(deleteButton,gc);
        /**********************************************************/
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);

        /*addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });*/




    }

    public void setPanels(PanelDisplayer panels) {
        this.panels = panels;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if(clicked == addButton){
            if(panels != null)
                panels.displayAdd();
            System.out.println("AddButton\n");
        }
        else if(clicked == editButton){
            if(panels != null)
                panels.displayEdit();
            System.out.println("EditButton\n");
        }
        else if(clicked == deleteButton){
            if(panels != null)
                panels.displayDelete();
            System.out.println("Delete\n");
        }

    }
}
