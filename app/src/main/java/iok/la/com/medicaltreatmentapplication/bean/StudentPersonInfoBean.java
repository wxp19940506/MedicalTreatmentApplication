package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class StudentPersonInfoBean {

    /**
     * status : true
     * ErrorMsg :
     * results : {"emp":{"BaseName":"临床药师培训","Name":"王佳佳","WorkNo":"ly2016-010","IdCardNo":"320721198709092282","Phone":"18961320091","Grade":"2016","TrainingLimit":"6个月","EmployeeType":"住院医师（委托培养，单位人）","Email":"534570889@qq.com","QQ":"","Unit":"连云港市第一人民医院","TrainingStart":"2016-08-01","TrainingEnd":"2019-08-01","isXieTong":false,"XieTongUnitName":"","XieTongUnitLevel":"","IsGetZhiYeZhengShu":false,"ZiGeZhengShuNo":"","IsFirstOrAlterRegisterBase":false,"ZhiYeZhuCeNo":""}}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * emp : {"BaseName":"临床药师培训","Name":"王佳佳","WorkNo":"ly2016-010","IdCardNo":"320721198709092282","Phone":"18961320091","Grade":"2016","TrainingLimit":"6个月","EmployeeType":"住院医师（委托培养，单位人）","Email":"534570889@qq.com","QQ":"","Unit":"连云港市第一人民医院","TrainingStart":"2016-08-01","TrainingEnd":"2019-08-01","isXieTong":false,"XieTongUnitName":"","XieTongUnitLevel":"","IsGetZhiYeZhengShu":false,"ZiGeZhengShuNo":"","IsFirstOrAlterRegisterBase":false,"ZhiYeZhuCeNo":""}
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
         * BaseName : 临床药师培训
         * Name : 王佳佳
         * WorkNo : ly2016-010
         * IdCardNo : 320721198709092282
         * Phone : 18961320091
         * Grade : 2016
         * TrainingLimit : 6个月
         * EmployeeType : 住院医师（委托培养，单位人）
         * Email : 534570889@qq.com
         * QQ :
         * Unit : 连云港市第一人民医院
         * TrainingStart : 2016-08-01
         * TrainingEnd : 2019-08-01
         * isXieTong : false
         * XieTongUnitName :
         * XieTongUnitLevel :
         * IsGetZhiYeZhengShu : false
         * ZiGeZhengShuNo :
         * IsFirstOrAlterRegisterBase : false
         * ZhiYeZhuCeNo :
         */

        private EmpBean emp;

        public EmpBean getEmp() {
            return emp;
        }

        public void setEmp(EmpBean emp) {
            this.emp = emp;
        }

        public static class EmpBean {
            private String BaseName;
            private String Name;
            private String WorkNo;
            private String IdCardNo;
            private String Phone;
            private String Grade;
            private String TrainingLimit;
            private String EmployeeType;
            private String Email;
            private String QQ;
            private String Unit;
            private String TrainingStart;
            private String TrainingEnd;
            private boolean isXieTong;
            private String XieTongUnitName;
            private String XieTongUnitLevel;
            private boolean IsGetZhiYeZhengShu;
            private String ZiGeZhengShuNo;
            private boolean IsFirstOrAlterRegisterBase;
            private String ZhiYeZhuCeNo;

            public String getBaseName() {
                return BaseName;
            }

            public void setBaseName(String BaseName) {
                this.BaseName = BaseName;
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

            public String getIdCardNo() {
                return IdCardNo;
            }

            public void setIdCardNo(String IdCardNo) {
                this.IdCardNo = IdCardNo;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getGrade() {
                return Grade;
            }

            public void setGrade(String Grade) {
                this.Grade = Grade;
            }

            public String getTrainingLimit() {
                return TrainingLimit;
            }

            public void setTrainingLimit(String TrainingLimit) {
                this.TrainingLimit = TrainingLimit;
            }

            public String getEmployeeType() {
                return EmployeeType;
            }

            public void setEmployeeType(String EmployeeType) {
                this.EmployeeType = EmployeeType;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getQQ() {
                return QQ;
            }

            public void setQQ(String QQ) {
                this.QQ = QQ;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }

            public String getTrainingStart() {
                return TrainingStart;
            }

            public void setTrainingStart(String TrainingStart) {
                this.TrainingStart = TrainingStart;
            }

            public String getTrainingEnd() {
                return TrainingEnd;
            }

            public void setTrainingEnd(String TrainingEnd) {
                this.TrainingEnd = TrainingEnd;
            }

            public boolean isIsXieTong() {
                return isXieTong;
            }

            public void setIsXieTong(boolean isXieTong) {
                this.isXieTong = isXieTong;
            }

            public String getXieTongUnitName() {
                return XieTongUnitName;
            }

            public void setXieTongUnitName(String XieTongUnitName) {
                this.XieTongUnitName = XieTongUnitName;
            }

            public String getXieTongUnitLevel() {
                return XieTongUnitLevel;
            }

            public void setXieTongUnitLevel(String XieTongUnitLevel) {
                this.XieTongUnitLevel = XieTongUnitLevel;
            }

            public boolean isIsGetZhiYeZhengShu() {
                return IsGetZhiYeZhengShu;
            }

            public void setIsGetZhiYeZhengShu(boolean IsGetZhiYeZhengShu) {
                this.IsGetZhiYeZhengShu = IsGetZhiYeZhengShu;
            }

            public String getZiGeZhengShuNo() {
                return ZiGeZhengShuNo;
            }

            public void setZiGeZhengShuNo(String ZiGeZhengShuNo) {
                this.ZiGeZhengShuNo = ZiGeZhengShuNo;
            }

            public boolean isIsFirstOrAlterRegisterBase() {
                return IsFirstOrAlterRegisterBase;
            }

            public void setIsFirstOrAlterRegisterBase(boolean IsFirstOrAlterRegisterBase) {
                this.IsFirstOrAlterRegisterBase = IsFirstOrAlterRegisterBase;
            }

            public String getZhiYeZhuCeNo() {
                return ZhiYeZhuCeNo;
            }

            public void setZhiYeZhuCeNo(String ZhiYeZhuCeNo) {
                this.ZhiYeZhuCeNo = ZhiYeZhuCeNo;
            }
        }
    }
}
