package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class MyStudentListBean {

    /**
     * status : true
     * ErrorMsg :
     * results : {"grades":[2017,2016,2015,2014],"departs":[{"Id":1054,"Name":"心内科"}],"emps":[{"GuiPeiEmployeeId":1209,"Name":"王佳佳","WorkNo":"ly2016-010","Phone":"18961320091","departName":"心内科"},{"GuiPeiEmployeeId":1210,"Name":"乔雪丽","WorkNo":"ly2016-005","Phone":"18961320580","departName":"心内科"}]}
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
        private List<Integer> grades;
        /**
         * Id : 1054
         * Name : 心内科
         */

        private List<DepartsBean> departs;
        /**
         * GuiPeiEmployeeId : 1209
         * Name : 王佳佳
         * WorkNo : ly2016-010
         * Phone : 18961320091
         * departName : 心内科
         */

        private List<EmpsBean> emps;

        public List<Integer> getGrades() {
            return grades;
        }

        public void setGrades(List<Integer> grades) {
            this.grades = grades;
        }

        public List<DepartsBean> getDeparts() {
            return departs;
        }

        public void setDeparts(List<DepartsBean> departs) {
            this.departs = departs;
        }

        public List<EmpsBean> getEmps() {
            return emps;
        }

        public void setEmps(List<EmpsBean> emps) {
            this.emps = emps;
        }

        public static class DepartsBean {
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

        public static class EmpsBean {
            private int GuiPeiEmployeeId;
            private String Name;
            private String WorkNo;
            private String Phone;
            private String departName;

            public int getGuiPeiEmployeeId() {
                return GuiPeiEmployeeId;
            }

            public void setGuiPeiEmployeeId(int GuiPeiEmployeeId) {
                this.GuiPeiEmployeeId = GuiPeiEmployeeId;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getWorkNo() {
                return WorkNo;
            }

            public void setWorkNo(String WorkNo) {
                this.WorkNo = WorkNo;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }
        }
    }
}
