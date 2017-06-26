package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class StatueData {

    /**
     * status : true
     * ErrorMsg :
     * Username : emp001
     * phone : null
     * IsGuiPei : false
     * token : mp33xqm5t50tatnbzzb0scbc
     */

    private boolean status;
    private String ErrorMsg;
    private String Username;
    private Object phone;
    private boolean IsGuiPei;
    private String token;

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
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
