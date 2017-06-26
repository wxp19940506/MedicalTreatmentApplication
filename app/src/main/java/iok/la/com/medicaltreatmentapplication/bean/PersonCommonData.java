package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2017/1/21 0021.
 */

public class PersonCommonData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"Id":1044,"EBId":10,"EBName":"内科","WorkNo":"GP014","Name":"胡馨云","IdCardNo":"320721198801214852","Phone":"18961327978","Grade":"2016","TrainingLimit":"两年","EmployeeType":"住院医师（委托培养，单位人）","Email":"xiuchaoshang@163.com","QQ":null,"Unit":"连云港市第一人民医院","TrainingStart":"/Date(1469980800000)/","TrainingEnd":"/Date(1564588800000)/","isXieTong":false,"XieTongUnitName":null,"XieTongUnitLevel":null,"IsGetZhiYeZhengShu":false,"ZiGeZhengShuNo":null,"IsFirstOrAlterRegisterBase":false,"ZhiYeZhuCeNo":null,"Status":1}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * Id : 1044
     * EBId : 10
     * EBName : 内科
     * WorkNo : GP014
     * Name : 胡馨云
     * IdCardNo : 320721198801214852
     * Phone : 18961327978
     * Grade : 2016
     * TrainingLimit : 两年
     * EmployeeType : 住院医师（委托培养，单位人）
     * Email : xiuchaoshang@163.com
     * QQ : null
     * Unit : 连云港市第一人民医院
     * TrainingStart : /Date(1469980800000)/
     * TrainingEnd : /Date(1564588800000)/
     * isXieTong : false
     * XieTongUnitName : null
     * XieTongUnitLevel : null
     * IsGetZhiYeZhengShu : false
     * ZiGeZhengShuNo : null
     * IsFirstOrAlterRegisterBase : false
     * ZhiYeZhuCeNo : null
     * Status : 1
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
        private int Id;
        private int EBId;
        private String EBName;
        private String WorkNo;
        private String Name;
        private String IdCardNo;
        private String Phone;
        private String Grade;
        private String TrainingLimit;
        private String EmployeeType;
        private String Email;
        private Object QQ;
        private String Unit;
        private String TrainingStart;
        private String TrainingEnd;
        private boolean isXieTong;
        private Object XieTongUnitName;
        private Object XieTongUnitLevel;
        private boolean IsGetZhiYeZhengShu;
        private Object ZiGeZhengShuNo;
        private boolean IsFirstOrAlterRegisterBase;
        private Object ZhiYeZhuCeNo;
        private int Status;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getEBId() {
            return EBId;
        }

        public void setEBId(int EBId) {
            this.EBId = EBId;
        }

        public String getEBName() {
            return EBName;
        }

        public void setEBName(String EBName) {
            this.EBName = EBName;
        }

        public String getWorkNo() {
            return WorkNo;
        }

        public void setWorkNo(String WorkNo) {
            this.WorkNo = WorkNo;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
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

        public Object getQQ() {
            return QQ;
        }

        public void setQQ(Object QQ) {
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

        public Object getXieTongUnitName() {
            return XieTongUnitName;
        }

        public void setXieTongUnitName(Object XieTongUnitName) {
            this.XieTongUnitName = XieTongUnitName;
        }

        public Object getXieTongUnitLevel() {
            return XieTongUnitLevel;
        }

        public void setXieTongUnitLevel(Object XieTongUnitLevel) {
            this.XieTongUnitLevel = XieTongUnitLevel;
        }

        public boolean isIsGetZhiYeZhengShu() {
            return IsGetZhiYeZhengShu;
        }

        public void setIsGetZhiYeZhengShu(boolean IsGetZhiYeZhengShu) {
            this.IsGetZhiYeZhengShu = IsGetZhiYeZhengShu;
        }

        public Object getZiGeZhengShuNo() {
            return ZiGeZhengShuNo;
        }

        public void setZiGeZhengShuNo(Object ZiGeZhengShuNo) {
            this.ZiGeZhengShuNo = ZiGeZhengShuNo;
        }

        public boolean isIsFirstOrAlterRegisterBase() {
            return IsFirstOrAlterRegisterBase;
        }

        public void setIsFirstOrAlterRegisterBase(boolean IsFirstOrAlterRegisterBase) {
            this.IsFirstOrAlterRegisterBase = IsFirstOrAlterRegisterBase;
        }

        public Object getZhiYeZhuCeNo() {
            return ZhiYeZhuCeNo;
        }

        public void setZhiYeZhuCeNo(Object ZhiYeZhuCeNo) {
            this.ZhiYeZhuCeNo = ZhiYeZhuCeNo;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
