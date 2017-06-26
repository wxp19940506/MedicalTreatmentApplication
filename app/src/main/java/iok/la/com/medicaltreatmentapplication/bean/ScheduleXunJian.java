package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class ScheduleXunJian {


    /**
     * status : true
     * ErrorMsg :
     * results : {"Patrols":[{"ID":2,"PatrolDate":"/Date(1430293452000)/","CreatedDate":"/Date(1430293452000)/","Description":"已完成,等待回复,aa"},{"ID":10,"PatrolDate":"/Date(1433497570000)/","CreatedDate":"/Date(1433497582000)/","Description":"hhfff"},{"ID":15,"PatrolDate":"/Date(1433900274000)/","CreatedDate":"/Date(1433900290047)/","Description":"eee"},{"ID":17,"PatrolDate":"/Date(1433900488000)/","CreatedDate":"/Date(1433900493207)/","Description":null},{"ID":19,"PatrolDate":"/Date(1433900711000)/","CreatedDate":"/Date(1433900735023)/","Description":null},{"ID":20,"PatrolDate":"/Date(1433900741000)/","CreatedDate":"/Date(1433900753367)/","Description":null},{"ID":23,"PatrolDate":"/Date(1433916697000)/","CreatedDate":"/Date(1433916718310)/","Description":"232 d"},{"ID":24,"PatrolDate":"/Date(1434352808000)/","CreatedDate":"/Date(1434352809760)/","Description":null},{"ID":25,"PatrolDate":"/Date(1434352814000)/","CreatedDate":"/Date(1434352816573)/","Description":null},{"ID":26,"PatrolDate":"/Date(1434352915000)/","CreatedDate":"/Date(1434352918573)/","Description":null}]}
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
         * ID : 2
         * PatrolDate : /Date(1430293452000)/
         * CreatedDate : /Date(1430293452000)/
         * Description : 已完成,等待回复,aa
         */

        private List<PatrolsBean> Patrols;

        public List<PatrolsBean> getPatrols() {
            return Patrols;
        }

        public void setPatrols(List<PatrolsBean> Patrols) {
            this.Patrols = Patrols;
        }

        public static class PatrolsBean {
            private int ID;
            private String PatrolDate;
            private String CreatedDate;
            private String Description;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getPatrolDate() {
                return PatrolDate;
            }

            public void setPatrolDate(String PatrolDate) {
                this.PatrolDate = PatrolDate;
            }

            public String getCreatedDate() {
                return CreatedDate;
            }

            public void setCreatedDate(String CreatedDate) {
                this.CreatedDate = CreatedDate;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }
        }
    }
}
