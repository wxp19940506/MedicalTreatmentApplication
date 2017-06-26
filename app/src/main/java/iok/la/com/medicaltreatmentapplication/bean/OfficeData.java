package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class OfficeData {

    @Override
    public String toString() {
        return "OfficeData{" +
                "status=" + status +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", results=" + results +
                '}';
    }

    /**
     * status : true
     * ErrorMsg :
     * results : {"Departments":[{"Id":1054,"Name":"心内科"},{"Id":1055,"Name":"心电图"},{"Id":1056,"Name":"肾内科"},{"Id":1057,"Name":"呼吸内科"},{"Id":1058,"Name":"血液科"},{"Id":1059,"Name":"消化内科"},{"Id":1060,"Name":"内分泌科"},{"Id":1061,"Name":"感染科"},{"Id":1062,"Name":"神经内科"},{"Id":1063,"Name":"风湿免疫科"},{"Id":1064,"Name":"急诊科"},{"Id":1065,"Name":"ICU"},{"Id":1066,"Name":"影像科"},{"Id":1067,"Name":"肿瘤化疗科"},{"Id":1068,"Name":"肿瘤放疗科"},{"Id":1069,"Name":"超声科"},{"Id":1070,"Name":"老年科"},{"Id":1071,"Name":"精神科"},{"Id":1072,"Name":"神经外科"},{"Id":1073,"Name":"神经电生理"},{"Id":1074,"Name":"病理科"},{"Id":1075,"Name":"急诊内科"},{"Id":1076,"Name":"麻醉科"},{"Id":1077,"Name":"肝胆外科"},{"Id":1078,"Name":"耳鼻咽喉科"},{"Id":1079,"Name":"胃肠外科"},{"Id":1080,"Name":"甲乳外科"},{"Id":1081,"Name":"创伤骨科"},{"Id":1082,"Name":"心外科"},{"Id":1083,"Name":"胸外科"},{"Id":1084,"Name":"妇产科"},{"Id":1085,"Name":"放射科"},{"Id":1086,"Name":"口腔颌面外科门诊"},{"Id":1087,"Name":"牙体牙髓科"},{"Id":1088,"Name":"牙周科"},{"Id":1089,"Name":"口腔修复科"},{"Id":1090,"Name":"口腔预防科"},{"Id":1091,"Name":"口腔黏膜科"},{"Id":1092,"Name":"儿童口腔科"},{"Id":1093,"Name":"口腔颌面外科"},{"Id":1094,"Name":"口腔正畸科"},{"Id":1095,"Name":"口腔颌面影像科"},{"Id":1096,"Name":"口腔急诊"},{"Id":1097,"Name":"神经康复科"},{"Id":1098,"Name":"儿内科"},{"Id":1099,"Name":"康复科"},{"Id":1100,"Name":"烧伤整形科"},{"Id":1101,"Name":"皮肤科"},{"Id":1102,"Name":"眼科"},{"Id":1103,"Name":"耳鼻喉科"},{"Id":1104,"Name":"规培"},{"Id":1105,"Name":"口腔急诊科"},{"Id":1106,"Name":"总值班室"},{"Id":1107,"Name":"药学部"},{"Id":1108,"Name":"肿瘤科"}],"ShiftTypes":[{"Id":1,"Name":"医生班","DepartmentId":2},{"Id":2,"Name":"护士班","DepartmentId":3},{"Id":3,"Name":"行政班","DepartmentId":1006},{"Id":4,"Name":"两头班","DepartmentId":1006},{"Id":6,"Name":"特殊班","DepartmentId":1009},{"Id":10,"Name":"医生班","DepartmentId":1021},{"Id":11,"Name":"护士班","DepartmentId":1021},{"Id":13,"Name":"医生班","DepartmentId":1020},{"Id":15,"Name":"早班","DepartmentId":1043},{"Id":16,"Name":"神经科1","DepartmentId":1024},{"Id":17,"Name":"神经2","DepartmentId":1024},{"Id":18,"Name":"医生班","DepartmentId":1054},{"Id":19,"Name":"护士班","DepartmentId":1054},{"Id":20,"Name":"住院医师规培班","DepartmentId":1054},{"Id":21,"Name":"总值班医生班","DepartmentId":1106}]}
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
        @Override
        public String toString() {
            return "ResultsBean{" +
                    "Departments=" + Departments +
                    ", ShiftTypes=" + ShiftTypes +
                    '}';
        }

        /**
         * Id : 1054
         * Name : 心内科
         */

        private List<DepartmentsBean> Departments;
        /**
         * Id : 1
         * Name : 医生班
         * DepartmentId : 2
         */

        private List<ShiftTypesBean> ShiftTypes;

        public List<DepartmentsBean> getDepartments() {
            return Departments;
        }

        public void setDepartments(List<DepartmentsBean> Departments) {
            this.Departments = Departments;
        }

        public List<ShiftTypesBean> getShiftTypes() {
            return ShiftTypes;
        }

        public void setShiftTypes(List<ShiftTypesBean> ShiftTypes) {
            this.ShiftTypes = ShiftTypes;
        }

        public static class DepartmentsBean {
            private int Id;
            private String Name;

            public int getId() {
                return Id;
            }

            @Override
            public String toString() {
                return "DepartmentsBean{" +
                        "Id=" + Id +
                        ", Name='" + Name + '\'' +
                        '}';
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

        public static class ShiftTypesBean {
            private int Id;
            private String Name;
            private int DepartmentId;

            @Override
            public String toString() {
                return "ShiftTypesBean{" +
                        "Id=" + Id +
                        ", Name='" + Name + '\'' +
                        ", DepartmentId=" + DepartmentId +
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

            public int getDepartmentId() {
                return DepartmentId;
            }

            public void setDepartmentId(int DepartmentId) {
                this.DepartmentId = DepartmentId;
            }
        }
    }
}
