package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class SceduleJiaoBanEditNoteData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"TransferDepartments":[{"Id":1106,"Name":"总值班室"}],"Stats":[{"ID":1,"Name":"编制"},{"ID":2,"Name":"交班"},{"ID":3,"Name":"接班"}],"Sentences":[{"ID":9,"Content":"谢谢","RotaSentenceCategoryName":"日常用语","Pinyin":"xiexie","Summary":"谢谢"},{"ID":10,"Content":"已完成","RotaSentenceCategoryName":"日常用语","Pinyin":"ywc","Summary":"已完成"},{"ID":11,"Content":"等待回复","RotaSentenceCategoryName":"日常用语","Pinyin":"ddhf","Summary":"等待回复"},{"ID":12,"Content":"aa","RotaSentenceCategoryName":"火灾等紧急处理常用语","Pinyin":"dd","Summary":"aa"}],"Transfer":{"ID":40,"DepartmentID":1106,"Name":"总值班室","FromEmployeeID":1213,"FromEmployeeName":"员工1","ToEmployeeID":2213,"ToEmployeeName":"员工2","TransferStatusID":1,"Summary":null,"Description":null,"TransferDate":"2017-01-19 03:19:00","ReceiveDate":"2017-01-19 03:20:59"}}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * TransferDepartments : [{"Id":1106,"Name":"总值班室"}]
     * Stats : [{"ID":1,"Name":"编制"},{"ID":2,"Name":"交班"},{"ID":3,"Name":"接班"}]
     * Sentences : [{"ID":9,"Content":"谢谢","RotaSentenceCategoryName":"日常用语","Pinyin":"xiexie","Summary":"谢谢"},{"ID":10,"Content":"已完成","RotaSentenceCategoryName":"日常用语","Pinyin":"ywc","Summary":"已完成"},{"ID":11,"Content":"等待回复","RotaSentenceCategoryName":"日常用语","Pinyin":"ddhf","Summary":"等待回复"},{"ID":12,"Content":"aa","RotaSentenceCategoryName":"火灾等紧急处理常用语","Pinyin":"dd","Summary":"aa"}]
     * Transfer : {"ID":40,"DepartmentID":1106,"Name":"总值班室","FromEmployeeID":1213,"FromEmployeeName":"员工1","ToEmployeeID":2213,"ToEmployeeName":"员工2","TransferStatusID":1,"Summary":null,"Description":null,"TransferDate":"2017-01-19 03:19:00","ReceiveDate":"2017-01-19 03:20:59"}
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
        /**
         * ID : 40
         * DepartmentID : 1106
         * Name : 总值班室
         * FromEmployeeID : 1213
         * FromEmployeeName : 员工1
         * ToEmployeeID : 2213
         * ToEmployeeName : 员工2
         * TransferStatusID : 1
         * Summary : null
         * Description : null
         * TransferDate : 2017-01-19 03:19:00
         * ReceiveDate : 2017-01-19 03:20:59
         */

        private TransferBean Transfer;
        /**
         * Id : 1106
         * Name : 总值班室
         */

        private List<TransferDepartmentsBean> TransferDepartments;
        /**
         * ID : 1
         * Name : 编制
         */

        private List<StatsBean> Stats;
        /**
         * ID : 9
         * Content : 谢谢
         * RotaSentenceCategoryName : 日常用语
         * Pinyin : xiexie
         * Summary : 谢谢
         */

        private List<SentencesBean> Sentences;

        public TransferBean getTransfer() {
            return Transfer;
        }

        public void setTransfer(TransferBean Transfer) {
            this.Transfer = Transfer;
        }

        public List<TransferDepartmentsBean> getTransferDepartments() {
            return TransferDepartments;
        }

        public void setTransferDepartments(List<TransferDepartmentsBean> TransferDepartments) {
            this.TransferDepartments = TransferDepartments;
        }

        public List<StatsBean> getStats() {
            return Stats;
        }

        public void setStats(List<StatsBean> Stats) {
            this.Stats = Stats;
        }

        public List<SentencesBean> getSentences() {
            return Sentences;
        }

        public void setSentences(List<SentencesBean> Sentences) {
            this.Sentences = Sentences;
        }

        public static class TransferBean {
            private int ID;
            private int DepartmentID;
            private String Name;
            private int FromEmployeeID;
            private String FromEmployeeName;
            private int ToEmployeeID;
            private String ToEmployeeName;
            private int TransferStatusID;
            private Object Summary;
            private Object Description;
            private String TransferDate;
            private String ReceiveDate;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getDepartmentID() {
                return DepartmentID;
            }

            public void setDepartmentID(int DepartmentID) {
                this.DepartmentID = DepartmentID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getFromEmployeeID() {
                return FromEmployeeID;
            }

            public void setFromEmployeeID(int FromEmployeeID) {
                this.FromEmployeeID = FromEmployeeID;
            }

            public String getFromEmployeeName() {
                return FromEmployeeName;
            }

            public void setFromEmployeeName(String FromEmployeeName) {
                this.FromEmployeeName = FromEmployeeName;
            }

            public int getToEmployeeID() {
                return ToEmployeeID;
            }

            public void setToEmployeeID(int ToEmployeeID) {
                this.ToEmployeeID = ToEmployeeID;
            }

            public String getToEmployeeName() {
                return ToEmployeeName;
            }

            public void setToEmployeeName(String ToEmployeeName) {
                this.ToEmployeeName = ToEmployeeName;
            }

            public int getTransferStatusID() {
                return TransferStatusID;
            }

            public void setTransferStatusID(int TransferStatusID) {
                this.TransferStatusID = TransferStatusID;
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

            public String getTransferDate() {
                return TransferDate;
            }

            public void setTransferDate(String TransferDate) {
                this.TransferDate = TransferDate;
            }

            public String getReceiveDate() {
                return ReceiveDate;
            }

            public void setReceiveDate(String ReceiveDate) {
                this.ReceiveDate = ReceiveDate;
            }
        }

        public static class TransferDepartmentsBean {
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

        public static class StatsBean {
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

        public static class SentencesBean {
            private int ID;
            private String Content;
            private String RotaSentenceCategoryName;
            private String Pinyin;
            private String Summary;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getRotaSentenceCategoryName() {
                return RotaSentenceCategoryName;
            }

            public void setRotaSentenceCategoryName(String RotaSentenceCategoryName) {
                this.RotaSentenceCategoryName = RotaSentenceCategoryName;
            }

            public String getPinyin() {
                return Pinyin;
            }

            public void setPinyin(String Pinyin) {
                this.Pinyin = Pinyin;
            }

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String Summary) {
                this.Summary = Summary;
            }
        }
    }
}
