package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class ScheduleXunJianDetail {

    /**
     * status : true
     * ErrorMsg :
     * results : {"patrol":{"PatrolDate":"/Date(1430293452000)/","Description":"已完成,等待回复,aa","Name":"东部院区"},"images":[{"Path":"/UserUploads/PatrolImages/2/登录.png","Description":"aaaa"},{"Path":"/UserUploads/PatrolImages/2/验证手机号.png","Description":"bbbb"}],"records":[{"Name":"心内科","Description":"哈哈"},{"Name":"心电图","Description":"嘻嘻"}],"employees":[{"Name":"员工1"}],"categorys":[{"Name":"新入院及大手术病人情况"},{"Name":"值班护士掌握病情的程度"}],"departments":[{"Name":"心内科"},{"Name":"心电图"}]}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * patrol : {"PatrolDate":"/Date(1430293452000)/","Description":"已完成,等待回复,aa","Name":"东部院区"}
     * images : [{"Path":"/UserUploads/PatrolImages/2/登录.png","Description":"aaaa"},{"Path":"/UserUploads/PatrolImages/2/验证手机号.png","Description":"bbbb"}]
     * records : [{"Name":"心内科","Description":"哈哈"},{"Name":"心电图","Description":"嘻嘻"}]
     * employees : [{"Name":"员工1"}]
     * categorys : [{"Name":"新入院及大手术病人情况"},{"Name":"值班护士掌握病情的程度"}]
     * departments : [{"Name":"心内科"},{"Name":"心电图"}]
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
         * PatrolDate : /Date(1430293452000)/
         * Description : 已完成,等待回复,aa
         * Name : 东部院区
         */

        private PatrolBean patrol;
        /**
         * Path : /UserUploads/PatrolImages/2/登录.png
         * Description : aaaa
         */

        private List<ImagesBean> images;
        /**
         * Name : 心内科
         * Description : 哈哈
         */

        private List<RecordsBean> records;
        /**
         * Name : 员工1
         */

        private List<EmployeesBean> employees;
        /**
         * Name : 新入院及大手术病人情况
         */

        private List<CategorysBean> categorys;
        /**
         * Name : 心内科
         */

        private List<DepartmentsBean> departments;

        public PatrolBean getPatrol() {
            return patrol;
        }

        public void setPatrol(PatrolBean patrol) {
            this.patrol = patrol;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public List<EmployeesBean> getEmployees() {
            return employees;
        }

        public void setEmployees(List<EmployeesBean> employees) {
            this.employees = employees;
        }

        public List<CategorysBean> getCategorys() {
            return categorys;
        }

        public void setCategorys(List<CategorysBean> categorys) {
            this.categorys = categorys;
        }

        public List<DepartmentsBean> getDepartments() {
            return departments;
        }

        public void setDepartments(List<DepartmentsBean> departments) {
            this.departments = departments;
        }

        public static class PatrolBean {
            private String PatrolDate;
            private String Description;
            private String Name;

            public String getPatrolDate() {
                return PatrolDate;
            }

            public void setPatrolDate(String PatrolDate) {
                this.PatrolDate = PatrolDate;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }

        public static class ImagesBean {
            private String Path;
            private String Description;

            public String getPath() {
                return Path;
            }

            public void setPath(String Path) {
                this.Path = Path;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }
        }

        public static class RecordsBean {
            private String Name;
            private String Description;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }
        }

        public static class EmployeesBean {
            private String Name;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }

        public static class CategorysBean {
            private String Name;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }

        public static class DepartmentsBean {
            private String Name;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}
