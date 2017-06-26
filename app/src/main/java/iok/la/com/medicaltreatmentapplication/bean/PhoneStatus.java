package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class PhoneStatus {

    /**
     * status : true
     * ErrorMsg :
     */

    private boolean status;
    private String ErrorMsg;

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
}
