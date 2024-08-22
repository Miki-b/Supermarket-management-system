package logAndReg;

import java.util.EventObject;

public class LoginEvent extends EventObject {

    private String userName;
    private String passWord;
    private int loginAttempt;
    private String authenticationType;

    public LoginEvent(Object source, String userName,
                      String passWord, int attempts, String authenticationType) {
        super(source);
        this.userName = userName;
        this.passWord = passWord;
        this.loginAttempt = attempts;
        this.authenticationType = authenticationType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return passWord;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }
}
