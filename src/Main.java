import ItemManagmentGUI.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConnectPage loginPage = new ConnectPage("Login");
                loginPage.setDisplayMenu(new FrameDisplayer() {
                    @Override
                    public void displayPlayground() {
                        loginPage.setVisible(false);
                        MenuFrame menu = new MenuFrame();
                        menu.setOptions(new OptionFrames() {
                            @Override
                            public void displayItem() {
                                menu.setVisible(false);
                                ItemManagmentFrame itemMenu = new ItemManagmentFrame();
                                itemMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                if(itemMenu.isActive() == false){
                                    menu.setVisible(true);
                                }
                            }

                            @Override
                            public void displayOrder() {
                                menu.setVisible(false);
                                //PlayGround orderMenu = new PlayGround();
//                                orderMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                                if(orderMenu.isActive() == false){
//                                    menu.setVisible(true);
//                                }
                            }

                             @Override
                            public void displayCustomer() {
                                menu.setVisible(false);
                                CustomerMenu custMenu = new CustomerMenu();
                                custMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                if(custMenu.isActive() == false){
                                    menu.setVisible(true);
                                }

                            }
                        });

                    }
                });
            }
        });

    }
}