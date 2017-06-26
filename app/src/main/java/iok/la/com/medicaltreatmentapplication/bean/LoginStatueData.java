package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class LoginStatueData {

    /**
     * status : true
     * ErrorMsg :
     * Username : emp001
     * phone : null
     * IsGuiPei : false
     * Token : mlz2qwp5yz4y2k3idr2jdb5m
     */

    private boolean status;
    private String ErrorMsg;
    private String Username;
    private Object phone;
    private boolean IsGuiPei;
    private String Token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public boolean isIsGuiPei() {
        return IsGuiPei;
    }

    public void setIsGuiPei(boolean IsGuiPei) {
        this.IsGuiPei = IsGuiPei;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
}
