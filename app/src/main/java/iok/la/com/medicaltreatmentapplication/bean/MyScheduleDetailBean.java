package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyScheduleDetailBean {

    /**
     * status : true
     * ErrorMsg :
     * results : {"date":"2017-02-02","sfts":[{"Name":"员工1","DepartmentName":"心内科","ShiftPlanName":"白班"}]}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * date : 2017-02-02
     * sfts : [{"Name":"员工1","DepartmentName":"心内科","ShiftPlanName":"白班"}]
     */

    private ResultsBean results;

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

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String date;
        /**
         * Name : 员工1
         * DepartmentName : 心内科
         * ShiftPlanName : 白班
         */

        private List<SftsBean> sfts;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<SftsBean> getSfts() {
            return sfts;
        }

        public void setSfts(List<SftsBean> sfts) {
            this.sfts = sfts;
        }

        public static class SftsBean {
            private String Name;
            private String DepartmentName;
            private String ShiftPlanName;

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

            public String getShiftPlanName() {
                return ShiftPlanName;
            }

            public void setShiftPlanName(String ShiftPlanName) {
                this.ShiftPlanName = ShiftPlanName;
            }
        }
    }
}
