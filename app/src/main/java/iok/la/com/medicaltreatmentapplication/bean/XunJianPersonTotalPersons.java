package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class XunJianPersonTotalPersons {

    /**
     * status : true
     * ErrorMsg :
     * results : [{"Id":1213,"Name":"员工1","Pinyin":null},{"Id":2213,"Name":"员工2","Pinyin":null},{"Id":2223,"Name":"员工3","Pinyin":null}]
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * Id : 1213
     * Name : 员工1
     * Pinyin : null
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
        private int Id;
        private String Name;
        private Object Pinyin;

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

        public Object getPinyin() {
            return Pinyin;
        }

        public void setPinyin(Object Pinyin) {
            this.Pinyin = Pinyin;
        }
    }
}
