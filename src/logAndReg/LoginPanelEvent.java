package logAndReg;

import java.util.EventObject;

public class LoginPanelEvent extends EventObject {
    private String oldUsername;
    private String oldPassword;
    private String newUsername;
    private String newPassword;

    // Constructor for changing credentials
    public LoginPanelEvent(Object source, String oldUsername, String oldPassword, String newUsername, String newPassword) {
        super(source);
        this.oldUsername = oldUsername;
        this.oldPassword = oldPassword;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
    }

    // Constructor for registering new user
    public LoginPanelEvent(Object source, String newUserPassword) {
        super(source);
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
