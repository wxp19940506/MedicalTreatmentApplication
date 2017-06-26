package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class SceduleJiaoBanAddNoteData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"dps":[{"Id":1106,"Name":"总值班室"}],"stas":[{"ID":1,"Name":"编制"},{"ID":2,"Name":"交班"},{"ID":3,"Name":"接班"}],"sens":[{"ID":9,"Content":"谢谢","RotaSentenceCategoryName":"日常用语","Pinyin":"xiexie","Summary":"谢谢"},{"ID":10,"Content":"已完成","RotaSentenceCategoryName":"日常用语","Pinyin":"ywc","Summary":"已完成"},{"ID":11,"Content":"等待回复","RotaSentenceCategoryName":"日常用语","Pinyin":"ddhf","Summary":"等待回复"},{"ID":12,"Content":"aa","RotaSentenceCategoryName":"火灾等紧急处理常用语","Pinyin":"dd","Summary":"aa"}],"md":{"FromEmployeeID":1213,"Name":"员工1","TransferDate":"/Date(1484797792114)/","Summary":null,"Description":null}}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * dps : [{"Id":1106,"Name":"总值班室"}]
     * stas : [{"ID":1,"Name":"编制"},{"ID":2,"Name":"交班"},{"ID":3,"Name":"接班"}]
     * sens : [{"ID":9,"Content":"谢谢","RotaSentenceCategoryName":"日常用语","Pinyin":"xiexie","Summary":"谢谢"},{"ID":10,"Content":"已完成","RotaSentenceCategoryName":"日常用语","Pinyin":"ywc","Summary":"已完成"},{"ID":11,"Content":"等待回复","RotaSentenceCategoryName":"日常用语","Pinyin":"ddhf","Summary":"等待回复"},{"ID":12,"Content":"aa","RotaSentenceCategoryName":"火灾等紧急处理常用语","Pinyin":"dd","Summary":"aa"}]
     * md : {"FromEmployeeID":1213,"Name":"员工1","TransferDate":"/Date(1484797792114)/","Summary":null,"Description":null}
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
         * FromEmployeeID : 1213
         * Name : 员工1
         * TransferDate : /Date(1484797792114)/
         * Summary : null
         * Description : null
         */

        private MdBean md;
        /**
         * Id : 1106
         * Name : 总值班室
         */

        private List<DpsBean> dps;
        /**
         * ID : 1
         * Name : 编制
         */

        private List<StasBean> stas;
        /**
         * ID : 9
         * Content : 谢谢
         * RotaSentenceCategoryName : 日常用语
         * Pinyin : xiexie
         * Summary : 谢谢
         */

        private List<SensBean> sens;

        public MdBean getMd() {
            return md;
        }

        public void setMd(MdBean md) {
            this.md = md;
        }

        public List<DpsBean> getDps() {
            return dps;
        }

        public void setDps(List<DpsBean> dps) {
            this.dps = dps;
        }

        public List<StasBean> getStas() {
            return stas;
        }

        public void setStas(List<StasBean> stas) {
            this.stas = stas;
        }

        public List<SensBean> getSens() {
            return sens;
        }

        public void setSens(List<SensBean> sens) {
            this.sens = sens;
        }

        public static class MdBean {
            private int FromEmployeeID;
            private String Name;
            private String TransferDate;
            private Object Summary;
            private Object Description;

            public int getFromEmployeeID() {
                return FromEmployeeID;
            }

            public void setFromEmployeeID(int FromEmployeeID) {
                this.FromEmployeeID = FromEmployeeID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getTransferDate() {
                return TransferDate;
            }

            public void setTransferDate(String TransferDate) {
                this.TransferDate = TransferDate;
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

        public static class DpsBean {
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

        public static class StasBean {
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

        public static class SensBean {
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
