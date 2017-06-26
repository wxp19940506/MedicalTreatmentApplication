package iok.la.com.medicaltreatmentapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class HolidayDepartment {

    /**
     * status : true
     * ErrorMsg :
     * results : {"dpts":[{"Id":1054,"Name":"心内科"}],"excludes":[{"DepartmentName":"心内科","EmployeeName":"员工1","Reason":"aaa","BeginDate":"2017-01-20","EndDate":"2017-01-21","BeginTime":"01:00:00","EndTime":"15:00:00","CreateDate":"2017-01-20","EmployeeExcludeTypeName":"婚假","Path":"/UserUploads/EmployeeExcludeImages/32/e8f262f4-fa1f-44e2-bf36-b486456e2365.jpg"}],"eet":[{"ID":1,"Name":"婚假"}],"emp":{"Id":1213,"Name":"员工1"},"departsAdd":[{"Id":1054,"Name":"心内科"}]}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * dpts : [{"Id":1054,"Name":"心内科"}]
     * excludes : [{"DepartmentName":"心内科","EmployeeName":"员工1","Reason":"aaa","BeginDate":"2017-01-20","EndDate":"2017-01-21","BeginTime":"01:00:00","EndTime":"15:00:00","CreateDate":"2017-01-20","EmployeeExcludeTypeName":"婚假","Path":"/UserUploads/EmployeeExcludeImages/32/e8f262f4-fa1f-44e2-bf36-b486456e2365.jpg"}]
     * eet : [{"ID":1,"Name":"婚假"}]
     * emp : {"Id":1213,"Name":"员工1"}
     * departsAdd : [{"Id":1054,"Name":"心内科"}]
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
         * Id : 1213
         * Name : 员工1
         */

        private EmpBean emp;
        /**
         * Id : 1054
         * Name : 心内科
         */

        private List<DptsBean> dpts;
        /**
         * DepartmentName : 心内科
         * EmployeeName : 员工1
         * Reason : aaa
         * BeginDate : 2017-01-20
         * EndDate : 2017-01-21
         * BeginTime : 01:00:00
         * EndTime : 15:00:00
         * CreateDate : 2017-01-20
         * EmployeeExcludeTypeName : 婚假
         * Path : /UserUploads/EmployeeExcludeImages/32/e8f262f4-fa1f-44e2-bf36-b486456e2365.jpg
         */

        private List<ExcludesBean> excludes;
        /**
         * ID : 1
         * Name : 婚假
         */

        private List<EetBean> eet;
        /**
         * Id : 1054
         * Name : 心内科
         */

        private List<DepartsAddBean> departsAdd;

        public EmpBean getEmp() {
            return emp;
        }

        public void setEmp(EmpBean emp) {
            this.emp = emp;
        }

        public List<DptsBean> getDpts() {
            return dpts;
        }

        public void setDpts(List<DptsBean> dpts) {
            this.dpts = dpts;
        }

        public List<ExcludesBean> getExcludes() {
            return excludes;
        }

        public void setExcludes(List<ExcludesBean> excludes) {
            this.excludes = excludes;
        }

        public List<EetBean> getEet() {
            return eet;
        }

        public void setEet(List<EetBean> eet) {
            this.eet = eet;
        }

        public List<DepartsAddBean> getDepartsAdd() {
            return departsAdd;
        }

        public void setDepartsAdd(List<DepartsAddBean> departsAdd) {
            this.departsAdd = departsAdd;
        }

        public static class EmpBean {
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

        public static class DptsBean {
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

        public static class ExcludesBean {
            private String DepartmentName;
            private String EmployeeName;
            private String Reason;
            private String BeginDate;
            private String EndDate;
            private String BeginTime;
            private String EndTime;
            private String CreateDate;
            private String EmployeeExcludeTypeName;
            private String Path;

            public String getDepartmentName() {
                return DepartmentName;
            }

            public void setDepartmentName(String DepartmentName) {
                this.DepartmentName = DepartmentName;
            }

            public String getEmployeeName() {
                return EmployeeName;
            }

            public void setEmployeeName(String EmployeeName) {
                this.EmployeeName = EmployeeName;
            }

            public String getReason() {
                return Reason;
            }

            public void setReason(String Reason) {
                this.Reason = Reason;
            }

            public String getBeginDate() {
                return BeginDate;
            }

            public void setBeginDate(String BeginDate) {
                this.BeginDate = BeginDate;
            }

            public String getEndDate() {
                return EndDate;
            }

            public void setEndDate(String EndDate) {
                this.EndDate = EndDate;
            }

            public String getBeginTime() {
                return BeginTime;
            }

            public void setBeginTime(String BeginTime) {
                this.BeginTime = BeginTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getEmployeeExcludeTypeName() {
                return EmployeeExcludeTypeName;
            }

            public void setEmployeeExcludeTypeName(String EmployeeExcludeTypeName) {
                this.EmployeeExcludeTypeName = EmployeeExcludeTypeName;
            }

            public String getPath() {
                return Path;
            }

            public void setPath(String Path) {
                this.Path = Path;
            }
        }

        public static class EetBean implements Serializable{
            private int ID;

            @Override
            public String toString() {
                return "EetBean{" +
                        "ID=" + ID +
                        ", Name='" + Name + '\'' +
                        '}';
            }

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

        public static class DepartsAddBean implements Serializable{
            private int Id;
            private String Name;

            @Override
            public String toString() {
                return "DepartsAddBean{" +
                        "Id=" + Id +
                        ", Name='" + Name + '\'' +
                        '}';
            }

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
