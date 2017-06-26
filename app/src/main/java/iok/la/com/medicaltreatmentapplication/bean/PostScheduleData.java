package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18 0018.
 */

public class PostScheduleData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"Departments":[{"Id":1106,"Name":"总值班室"}],"status":[{"ID":1,"Name":"编制"},{"ID":2,"Name":"交班"},{"ID":3,"Name":"接班"}],"Transfers":[{"ID":40,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1484810340000)/","TransferStatusName":"编制","ReceiveDate":"2017-01-19","Summary":null,"Description":null},{"ID":41,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1484968500000)/","TransferStatusName":"交班","ReceiveDate":"2017-01-21","Summary":null,"Description":null},{"ID":42,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1486396800000)/","TransferStatusName":"编制","ReceiveDate":"2017-02-08","Summary":"Trying","Description":"Return"},{"ID":43,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1486656000000)/","TransferStatusName":"交班","ReceiveDate":"2017-02-08","Summary":null,"Description":null},{"ID":44,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1486656000000)/","TransferStatusName":"编制","ReceiveDate":"2017-02-08","Summary":"Kl","Description":"谢谢 已完成 等待回复 aa "},{"ID":45,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1487865600000)/","TransferStatusName":"编制","ReceiveDate":"2017-02-08","Summary":null,"Description":null},{"ID":46,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1488211200000)/","TransferStatusName":"交班","ReceiveDate":"2017-02-08","Summary":"Rryyyyyyyyyy","Description":"谢谢 "},{"ID":47,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1487088000000)/","TransferStatusName":"编制","ReceiveDate":"2017-02-08","Summary":null,"Description":null},{"ID":48,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1486483200000)/","TransferStatusName":"编制","ReceiveDate":"2017-02-08","Summary":"Qwerty hill","Description":"已完成 "},{"ID":49,"dapartmentName":"总值班室","FromEmployeeName":"员工1","ToEmployeeName":"员工2","TransferDate":"/Date(1485878400000)/","TransferStatusName":"编制","ReceiveDate":"2017-02-08","Summary":null,"Description":"等待回复 "}]}
     */

    private boolean status;
    private String ErrorMsg;
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
        /**
         * Id : 1106
         * Name : 总值班室
         */

        private List<DepartmentsBean> Departments;
        /**
         * ID : 1
         * Name : 编制
         */

        private List<StatusBean> status;
        /**
         * ID : 40
         * dapartmentName : 总值班室
         * FromEmployeeName : 员工1
         * ToEmployeeName : 员工2
         * TransferDate : /Date(1484810340000)/
         * TransferStatusName : 编制
         * ReceiveDate : 2017-01-19
         * Summary : null
         * Description : null
         */

        private List<TransfersBean> Transfers;

        public List<DepartmentsBean> getDepartments() {
            return Departments;
        }

        public void setDepartments(List<DepartmentsBean> Departments) {
            this.Departments = Departments;
        }

        public List<StatusBean> getStatus() {
            return status;
        }

        public void setStatus(List<StatusBean> status) {
            this.status = status;
        }

        public List<TransfersBean> getTransfers() {
            return Transfers;
        }

        public void setTransfers(List<TransfersBean> Transfers) {
            this.Transfers = Transfers;
        }

        public static class DepartmentsBean {
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

        public static class StatusBean {
            private int ID;
            private String Name;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }

        public static class TransfersBean {
            private int ID;
            private String dapartmentName;
            private String FromEmployeeName;
            private String ToEmployeeName;
            private String TransferDate;
            private String TransferStatusName;
            private String ReceiveDate;
            private Object Summary;
            private Object Description;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getDapartmentName() {
                return dapartmentName;
            }

            public void setDapartmentName(String dapartmentName) {
                this.dapartmentName = dapartmentName;
            }

            public String getFromEmployeeName() {
                return FromEmployeeName;
            }

            public void setFromEmployeeName(String FromEmployeeName) {
                this.FromEmployeeName = FromEmployeeName;
            }

            public String getToEmployeeName() {
                return ToEmployeeName;
            }

            public void setToEmployeeName(String ToEmployeeName) {
                this.ToEmployeeName = ToEmployeeName;
            }

            public String getTransferDate() {
                return TransferDate;
            }

            public void setTransferDate(String TransferDate) {
                this.TransferDate = TransferDate;
            }

            public String getTransferStatusName() {
                return TransferStatusName;
            }

            public void setTransferStatusName(String TransferStatusName) {
                this.TransferStatusName = TransferStatusName;
            }

            public String getReceiveDate() {
                return ReceiveDate;
            }

            public void setReceiveDate(String ReceiveDate) {
                this.ReceiveDate = ReceiveDate;
            }

            public Object getSummary() {
                return Summary;
            }

            public void setSummary(Object Summary) {
                this.Summary = Summary;
            }

            public Object getDescription() {
                return Description;
            }

            public void setDescription(Object Description) {
                this.Description = Description;
            }
        }
    }
}
