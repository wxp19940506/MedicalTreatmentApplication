package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class Schedule_fixedData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"DepartmentId":1054,"DepartmentName":"心内科","Date":"2016-01-01","EmployeeId":1213,"EmployeeName":"员工1","shiftPlans":[{"Id":94,"Name":"白班"},{"Id":95,"Name":"夜班"}],"selectedShiftPlans":[]}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * DepartmentId : 1054
     * DepartmentName : 心内科
     * Date : 2016-01-01
     * EmployeeId : 1213
     * EmployeeName : 员工1
     * shiftPlans : [{"Id":94,"Name":"白班"},{"Id":95,"Name":"夜班"}]
     * selectedShiftPlans : []
     */

    private ResultsBean results;

    @Override
    public String toString() {
        return "Schedule_fixedData{" +
                "status=" + status +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", results=" + results +
                '}';
    }

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
        private int DepartmentId;
        private String DepartmentName;
        private String Date;
        private int EmployeeId;
        private String EmployeeName;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "DepartmentId=" + DepartmentId +
                    ", DepartmentName='" + DepartmentName + '\'' +
                    ", Date='" + Date + '\'' +
                    ", EmployeeId=" + EmployeeId +
                    ", EmployeeName='" + EmployeeName + '\'' +
                    ", shiftPlans=" + shiftPlans +
                    ", selectedShiftPlans=" + selectedShiftPlans +
                    '}';
        }

        /**
         * Id : 94
         * Name : 白班
         */

        private List<ShiftPlansBean> shiftPlans;
        private List<?> selectedShiftPlans;

        public int getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(int DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public int getEmployeeId() {
            return EmployeeId;
        }

        public void setEmployeeId(int EmployeeId) {
            this.EmployeeId = EmployeeId;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public void setEmployeeName(String EmployeeName) {
            this.EmployeeName = EmployeeName;
        }

        public List<ShiftPlansBean> getShiftPlans() {
            return shiftPlans;
        }

        public void setShiftPlans(List<ShiftPlansBean> shiftPlans) {
            this.shiftPlans = shiftPlans;
        }

        public List<?> getSelectedShiftPlans() {
            return selectedShiftPlans;
        }

        public void setSelectedShiftPlans(List<?> selectedShiftPlans) {
            this.selectedShiftPlans = selectedShiftPlans;
        }

        public static class ShiftPlansBean {
            private int Id;
            private String Name;

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
    }
}
