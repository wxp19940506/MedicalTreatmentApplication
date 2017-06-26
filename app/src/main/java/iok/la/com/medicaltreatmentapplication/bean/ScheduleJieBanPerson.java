package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class ScheduleJieBanPerson {

    /**
     * status : true
     * ErrorMsg :
     * Id : 2213
     * Name : 员工2
     */

    private boolean status;
    private String ErrorMsg;
    private int Id;
    private String Name;

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

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
