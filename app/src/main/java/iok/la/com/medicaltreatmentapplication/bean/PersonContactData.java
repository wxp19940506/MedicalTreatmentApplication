package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class PersonContactData {

    /**
     * status : true
     * ErrorMsg :
     * results : [{"Name":"孙洪涛","DepartmentName":"规培","CellPhone":"11111111111","officePhone":"11111111"},{"Name":"石师傅","DepartmentName":"规培","CellPhone":"22222222222","officePhone":"22222222"},{"Name":"胡馨云","DepartmentName":"规培","CellPhone":"33333333333","officePhone":"33333333"},{"Name":"颜虹杰","DepartmentName":"规培","CellPhone":"44444444444","officePhone":"44444444"},{"Name":"宋懿红","DepartmentName":"规培","CellPhone":"55555555555","officePhone":"55555555"},{"Name":"刘佳佳","DepartmentName":"规培","CellPhone":"66666666666","officePhone":"66666666"},{"Name":"林秋红","DepartmentName":"规培","CellPhone":"77777777777","officePhone":"77777777"},{"Name":"杨恕宁","DepartmentName":"规培","CellPhone":"88888888888","officePhone":"88888888"},{"Name":"周怡","DepartmentName":"规培","CellPhone":"","officePhone":""},{"Name":"侯  兵","DepartmentName":"规培","CellPhone":"","officePhone":""}]
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * Name : 孙洪涛
     * DepartmentName : 规培
     * CellPhone : 11111111111
     * officePhone : 11111111
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
        private String DepartmentName;
        private String CellPhone;
        private String officePhone;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getCellPhone() {
            return CellPhone;
        }

        public void setCellPhone(String CellPhone) {
            this.CellPhone = CellPhone;
        }

        public String getOfficePhone() {
            return officePhone;
        }

        public void setOfficePhone(String officePhone) {
            this.officePhone = officePhone;
        }
    }
}
