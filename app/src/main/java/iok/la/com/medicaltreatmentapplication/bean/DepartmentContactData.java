package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class DepartmentContactData {

    /**
     * status : true
     * ErrorMsg :
     * results : [{"Name":"心内科","Address":"","Phone":"11111111"},{"Name":"心电图","Address":"","Phone":"22222222"},{"Name":"肾内科","Address":"","Phone":"33333333"},{"Name":"呼吸内科","Address":"","Phone":"44444444"},{"Name":"血液科","Address":"","Phone":"55555555"},{"Name":"消化内科","Address":"","Phone":"66666666"},{"Name":"内分泌科","Address":"","Phone":""},{"Name":"感染科","Address":"","Phone":""},{"Name":"神经内科","Address":"","Phone":""},{"Name":"风湿免疫科","Address":"","Phone":""}]
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * Name : 心内科
     * Address :
     * Phone : 11111111
     */

    private List<ResultsBean> results;

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

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String Name;
        private String Address;
        private String Phone;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }
    }
}
