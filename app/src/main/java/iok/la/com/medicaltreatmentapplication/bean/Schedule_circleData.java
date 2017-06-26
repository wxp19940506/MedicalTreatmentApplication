package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class Schedule_circleData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"DepartmentID":1054,"DepartmentName":"心内科","Date":"2016-01-01","ShiftTypeID":18,"EmployeeId":1213,"EmployeeName":"员工1","Frequencies":[{"Id":19,"Name":"心内科医生班"}],"SelectedFrequencies":[]}
     */

    private boolean status;
    private String ErrorMsg;

    @Override
    public String toString() {
        return "Schedule_circleData{" +
                "status=" + status +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", results=" + results +
                '}';
    }

    /**
     * DepartmentID : 1054
     * DepartmentName : 心内科
     * Date : 2016-01-01
     * ShiftTypeID : 18
     * EmployeeId : 1213
     * EmployeeName : 员工1
     * Frequencies : [{"Id":19,"Name":"心内科医生班"}]
     * SelectedFrequencies : []
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
        private int DepartmentID;
        private String DepartmentName;
        private String Date;
        private int ShiftTypeID;
        private int EmployeeId;
        private String EmployeeName;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "DepartmentID=" + DepartmentID +
                    ", DepartmentName='" + DepartmentName + '\'' +
                    ", Date='" + Date + '\'' +
                    ", ShiftTypeID=" + ShiftTypeID +
                    ", EmployeeId=" + EmployeeId +
                    ", EmployeeName='" + EmployeeName + '\'' +
                    ", Frequencies=" + Frequencies +
                    ", SelectedFrequencies=" + SelectedFrequencies +
                    '}';
        }

        /**
         * Id : 19
         * Name : 心内科医生班
         */

        private List<FrequenciesBean> Frequencies;
        private List<?> SelectedFrequencies;

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
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

        public int getShiftTypeID() {
            return ShiftTypeID;
        }

        public void setShiftTypeID(int ShiftTypeID) {
            this.ShiftTypeID = ShiftTypeID;
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

        public List<FrequenciesBean> getFrequencies() {
            return Frequencies;
        }

        public void setFrequencies(List<FrequenciesBean> Frequencies) {
            this.Frequencies = Frequencies;
        }

        public List<?> getSelectedFrequencies() {
            return SelectedFrequencies;
        }

        public void setSelectedFrequencies(List<?> SelectedFrequencies) {
            this.SelectedFrequencies = SelectedFrequencies;
        }

        public static class FrequenciesBean {
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
