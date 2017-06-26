package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class PersonGuiPeiData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"obj":{"Id":1213,"departmentId":1054,"Name":"员工1","TitleID":0,"DutyID":0,"Pinyin":null,"IsRota":true,"WorkNo":"emp001","CellPhone":null,"officePhone":null,"Address":null,"IdentityNo":null,"OrderIndex":1},"depts":[{"Id":1054,"Name":"心内科"}],"ts":[{"ID":1,"Name":"主任医师"}],"ds":[{"ID":1,"Name":"aaa"}]}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * obj : {"Id":1213,"departmentId":1054,"Name":"员工1","TitleID":0,"DutyID":0,"Pinyin":null,"IsRota":true,"WorkNo":"emp001","CellPhone":null,"officePhone":null,"Address":null,"IdentityNo":null,"OrderIndex":1}
     * depts : [{"Id":1054,"Name":"心内科"}]
     * ts : [{"ID":1,"Name":"主任医师"}]
     * ds : [{"ID":1,"Name":"aaa"}]
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
         * departmentId : 1054
         * Name : 员工1
         * TitleID : 0
         * DutyID : 0
         * Pinyin : null
         * IsRota : true
         * WorkNo : emp001
         * CellPhone : null
         * officePhone : null
         * Address : null
         * IdentityNo : null
         * OrderIndex : 1
         */

        private ObjBean obj;
        /**
         * Id : 1054
         * Name : 心内科
         */

        private List<DeptsBean> depts;
        /**
         * ID : 1
         * Name : 主任医师
         */

        private List<TsBean> ts;
        /**
         * ID : 1
         * Name : aaa
         */

        private List<DsBean> ds;

        public ObjBean getObj() {
            return obj;
        }

        public void setObj(ObjBean obj) {
            this.obj = obj;
        }

        public List<DeptsBean> getDepts() {
            return depts;
        }

        public void setDepts(List<DeptsBean> depts) {
            this.depts = depts;
        }

        public List<TsBean> getTs() {
            return ts;
        }

        public void setTs(List<TsBean> ts) {
            this.ts = ts;
        }

        public List<DsBean> getDs() {
            return ds;
        }

        public void setDs(List<DsBean> ds) {
            this.ds = ds;
        }

        public static class ObjBean {
            private int Id;
            private int departmentId;
            private String Name;
            private int TitleID;
            private int DutyID;
            private Object Pinyin;
            private boolean IsRota;
            private String WorkNo;
            private Object CellPhone;
            private Object officePhone;
            private Object Address;
            private Object IdentityNo;
            private int OrderIndex;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getTitleID() {
                return TitleID;
            }

            public void setTitleID(int TitleID) {
                this.TitleID = TitleID;
            }

            public int getDutyID() {
                return DutyID;
            }

            public void setDutyID(int DutyID) {
                this.DutyID = DutyID;
            }

            public Object getPinyin() {
                return Pinyin;
            }

            public void setPinyin(Object Pinyin) {
                this.Pinyin = Pinyin;
            }

            public boolean isIsRota() {
                return IsRota;
            }

            public void setIsRota(boolean IsRota) {
                this.IsRota = IsRota;
            }

            public String getWorkNo() {
                return WorkNo;
            }

            public void setWorkNo(String WorkNo) {
                this.WorkNo = WorkNo;
            }

            public Object getCellPhone() {
                return CellPhone;
            }

            public void setCellPhone(Object CellPhone) {
                this.CellPhone = CellPhone;
            }

            public Object getOfficePhone() {
                return officePhone;
            }

            public void setOfficePhone(Object officePhone) {
                this.officePhone = officePhone;
            }

            public Object getAddress() {
                return Address;
            }

            public void setAddress(Object Address) {
                this.Address = Address;
            }

            public Object getIdentityNo() {
                return IdentityNo;
            }

            public void setIdentityNo(Object IdentityNo) {
                this.IdentityNo = IdentityNo;
            }

            public int getOrderIndex() {
                return OrderIndex;
            }

            public void setOrderIndex(int OrderIndex) {
                this.OrderIndex = OrderIndex;
            }
        }

        public static class DeptsBean {
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

        public static class TsBean {
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

        public static class DsBean {
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
