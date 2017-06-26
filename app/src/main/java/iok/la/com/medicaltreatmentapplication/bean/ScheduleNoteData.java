package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class ScheduleNoteData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"ManageDepartments":[{"Id":1054,"Name":"心内科"}],"Rotas":[{"ID":93,"RequestTime":"/Date(1486777284000)/","GeneralDepartmentID":1106,"GeneralDepartment":{"ID":1106,"Name":"总值班室"},"DepartmentID":1054,"Department":{"ID":1054,"Name":"心内科"},"RotaImportanceID":2,"RotaImportance":{"ID":2,"Name":"紧急"},"OccurrenceTime":"/Date(1486777284000)/","RotaStatusID":1,"RotaStatus":{"ID":1,"Name":"新建"},"Description":"afafafafafafafafafafafafafafafaf","CreatedDate":"/Date(1486777313000)/","Categories":"病人或家属伤医事件, 医疗设备使用协调, 病人间打架斗殴, 医生临时外出"}]}
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
         * Id : 1054
         * Name : 心内科
         */

        private List<ManageDepartmentsBean> ManageDepartments;
        /**
         * ID : 93
         * RequestTime : /Date(1486777284000)/
         * GeneralDepartmentID : 1106
         * GeneralDepartment : {"ID":1106,"Name":"总值班室"}
         * DepartmentID : 1054
         * Department : {"ID":1054,"Name":"心内科"}
         * RotaImportanceID : 2
         * RotaImportance : {"ID":2,"Name":"紧急"}
         * OccurrenceTime : /Date(1486777284000)/
         * RotaStatusID : 1
         * RotaStatus : {"ID":1,"Name":"新建"}
         * Description : afafafafafafafafafafafafafafafaf
         * CreatedDate : /Date(1486777313000)/
         * Categories : 病人或家属伤医事件, 医疗设备使用协调, 病人间打架斗殴, 医生临时外出
         */

        private List<RotasBean> Rotas;

        public List<ManageDepartmentsBean> getManageDepartments() {
            return ManageDepartments;
        }

        public void setManageDepartments(List<ManageDepartmentsBean> ManageDepartments) {
            this.ManageDepartments = ManageDepartments;
        }

        public List<RotasBean> getRotas() {
            return Rotas;
        }

        public void setRotas(List<RotasBean> Rotas) {
            this.Rotas = Rotas;
        }

        public static class ManageDepartmentsBean {
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

        public static class RotasBean {
            private int ID;
            private String RequestTime;
            private int GeneralDepartmentID;
            /**
             * ID : 1106
             * Name : 总值班室
             */

            private GeneralDepartmentBean GeneralDepartment;
            private int DepartmentID;
            /**
             * ID : 1054
             * Name : 心内科
             */

            private DepartmentBean Department;
            private int RotaImportanceID;
            /**
             * ID : 2
             * Name : 紧急
             */

            private RotaImportanceBean RotaImportance;
            private String OccurrenceTime;
            private int RotaStatusID;
            /**
             * ID : 1
             * Name : 新建
             */

            private RotaStatusBean RotaStatus;
            private String Description;
            private String CreatedDate;
            private String Categories;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getRequestTime() {
                return RequestTime;
            }

            public void setRequestTime(String RequestTime) {
                this.RequestTime = RequestTime;
            }

            public int getGeneralDepartmentID() {
                return GeneralDepartmentID;
            }

            public void setGeneralDepartmentID(int GeneralDepartmentID) {
                this.GeneralDepartmentID = GeneralDepartmentID;
            }

            public GeneralDepartmentBean getGeneralDepartment() {
                return GeneralDepartment;
            }

            public void setGeneralDepartment(GeneralDepartmentBean GeneralDepartment) {
                this.GeneralDepartment = GeneralDepartment;
            }

            public int getDepartmentID() {
                return DepartmentID;
            }

            public void setDepartmentID(int DepartmentID) {
                this.DepartmentID = DepartmentID;
            }

            public DepartmentBean getDepartment() {
                return Department;
            }

            public void setDepartment(DepartmentBean Department) {
                this.Department = Department;
            }

            public int getRotaImportanceID() {
                return RotaImportanceID;
            }

            public void setRotaImportanceID(int RotaImportanceID) {
                this.RotaImportanceID = RotaImportanceID;
            }

            public RotaImportanceBean getRotaImportance() {
                return RotaImportance;
            }

            public void setRotaImportance(RotaImportanceBean RotaImportance) {
                this.RotaImportance = RotaImportance;
            }

            public String getOccurrenceTime() {
                return OccurrenceTime;
            }

            public void setOccurrenceTime(String OccurrenceTime) {
                this.OccurrenceTime = OccurrenceTime;
            }

            public int getRotaStatusID() {
                return RotaStatusID;
            }

            public void setRotaStatusID(int RotaStatusID) {
                this.RotaStatusID = RotaStatusID;
            }

            public RotaStatusBean getRotaStatus() {
                return RotaStatus;
            }

            public void setRotaStatus(RotaStatusBean RotaStatus) {
                this.RotaStatus = RotaStatus;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getCreatedDate() {
                return CreatedDate;
            }

            public void setCreatedDate(String CreatedDate) {
                this.CreatedDate = CreatedDate;
            }

            public String getCategories() {
                return Categories;
            }

            public void setCategories(String Categories) {
                this.Categories = Categories;
            }

            public static class GeneralDepartmentBean {
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

            public static class DepartmentBean {
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

            public static class RotaImportanceBean {
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

            public static class RotaStatusBean {
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
        }
    }
}
