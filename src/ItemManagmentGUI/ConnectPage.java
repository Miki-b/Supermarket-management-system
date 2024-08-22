package ItemManagmentGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectPage extends JFrame implements ActionListener {
    JLabel username;
    JLabel password;
    JTextField usernameField;
    JTextField passwordField;
    JButton Login;
    //JCheckBox showpassword;
    FrameDisplayer displayMenu;

    public ConnectPage(String title) {
        super(title);

        this.username = new JLabel("User Name: ");
        this.password = new JLabel("Password: ");
        this.usernameField = new JTextField(15);
        this.passwordField = new JTextField(15);
        // this.showpassword = new JCheckBox("Show Password");
        this.Login = new JButton("Login");

        /////Login/////
        Login.addActionListener(this);


        /*showpassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showpassword.isSelected()){
//                    passwordField.setEchochar
                }
            }
        });*/

        setLayout(new GridBagLayout());
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gc = new GridBagConstraints();
        ////////////////////// Frist row ////////////////////
        gc.weightx = 1;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(username, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gc);
        ////////////////////// Second row ////////////////////
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(password, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(passwordField, gc);
        ////////////////////// Third row ////////////////////
        gc.weightx = 0.3;
        gc.weighty = 1;
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        add(Login, gc);


    }


    public boolean isValid(String user, String password) {
        if (user == "admin") {
            if (password == "admin")
                return true;
            else {
                return false;
            }
        } else
            return false;
    }

    public void setDisplayMenu(FrameDisplayer displayMenu) {
        this.displayMenu = displayMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputUser = usernameField.getText();
        String inputPassword = passwordField.getText();
        if (inputUser.equals("admin")) {
            if (inputPassword.equals("admin")) {
                if (displayMenu != null) {
                    displayMenu.displayPlayground();

                }
            }

        }

    }
}
