package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class XunjianBean {

    /**
     * status : true
     * ErrorMsg :
     * items : {"pco":[{"ID":1,"Name":"卫生","Pinyin":null},{"ID":2,"Name":"设备","Pinyin":null}],"pct":[{"ID":6,"Name":"急危重症病人","Pinyin":null},{"ID":7,"Name":"新入院及大手术病人情况","Pinyin":null},{"ID":8,"Name":"病区管理情况","Pinyin":null},{"ID":9,"Name":"急救物品管理情况","Pinyin":null},{"ID":10,"Name":"值班护士掌握病情的程度","Pinyin":null},{"ID":11,"Name":"护士在岗情况、各班职责完成情况","Pinyin":null},{"ID":12,"Name":"环境卫生情况","Pinyin":null},{"ID":13,"Name":"满意度测评","Pinyin":null},{"ID":14,"Name":"无菌药品、医疗废弃物管理","Pinyin":null},{"ID":15,"Name":"手卫生依从性调查","Pinyin":null}],"ges":[{"Id":1213,"Name":"员工1","Pinyin":null}],"dps":[{"Id":1054,"Name":"心内科","Pinyin":"xinneike"},{"Id":1055,"Name":"心电图","Pinyin":"xindiantu"},{"Id":1056,"Name":"肾内科","Pinyin":"shenneike"},{"Id":1057,"Name":"呼吸内科","Pinyin":"huxineike"},{"Id":1058,"Name":"血液科","Pinyin":"xueyeke"},{"Id":1059,"Name":"消化内科","Pinyin":"xiaohuaneike"},{"Id":1060,"Name":"内分泌科","Pinyin":"neifenmike"},{"Id":1061,"Name":"感染科","Pinyin":"ganranke"},{"Id":1062,"Name":"神经内科","Pinyin":"shenjingneike"},{"Id":1063,"Name":"风湿免疫科","Pinyin":"fengshimianyike"},{"Id":1064,"Name":"急诊科","Pinyin":"jizhenke"},{"Id":1065,"Name":"ICU","Pinyin":"ICU"},{"Id":1066,"Name":"影像科","Pinyin":"yingxiangke"},{"Id":1067,"Name":"肿瘤化疗科","Pinyin":"zhongliuhualiaoke"},{"Id":1068,"Name":"肿瘤放疗科","Pinyin":"zhongliufangliaoke"},{"Id":1069,"Name":"超声科","Pinyin":"chaoshengke"},{"Id":1070,"Name":"老年科","Pinyin":"laonianke"},{"Id":1071,"Name":"精神科","Pinyin":"shenjingke"},{"Id":1072,"Name":"神经外科","Pinyin":"shenjingwaike"},{"Id":1073,"Name":"神经电生理","Pinyin":"shenjingdianshengli"},{"Id":1074,"Name":"病理科","Pinyin":"binglike"},{"Id":1075,"Name":"急诊内科","Pinyin":"jizhenneike"},{"Id":1076,"Name":"麻醉科","Pinyin":"mazuike"},{"Id":1077,"Name":"肝胆外科","Pinyin":"gandanwaike"},{"Id":1078,"Name":"耳鼻咽喉科","Pinyin":"erbiyanhouke"},{"Id":1079,"Name":"胃肠外科","Pinyin":"weichangwaike"},{"Id":1080,"Name":"甲乳外科","Pinyin":"jiaruwaike"},{"Id":1081,"Name":"创伤骨科","Pinyin":"chuangshangguke"},{"Id":1082,"Name":"心外科","Pinyin":"xinwaike"},{"Id":1083,"Name":"胸外科","Pinyin":"xiongwaike"},{"Id":1084,"Name":"妇产科","Pinyin":"fuchanke"},{"Id":1085,"Name":"放射科","Pinyin":"fangsheke"},{"Id":1086,"Name":"口腔颌面外科门诊","Pinyin":null},{"Id":1087,"Name":"牙体牙髓科","Pinyin":null},{"Id":1088,"Name":"牙周科","Pinyin":null},{"Id":1089,"Name":"口腔修复科","Pinyin":null},{"Id":1090,"Name":"口腔预防科","Pinyin":null},{"Id":1091,"Name":"口腔黏膜科","Pinyin":null},{"Id":1092,"Name":"儿童口腔科","Pinyin":null},{"Id":1093,"Name":"口腔颌面外科","Pinyin":null},{"Id":1094,"Name":"口腔正畸科","Pinyin":null},{"Id":1095,"Name":"口腔颌面影像科","Pinyin":null},{"Id":1096,"Name":"口腔急诊","Pinyin":null},{"Id":1097,"Name":"神经康复科","Pinyin":null},{"Id":1098,"Name":"儿内科","Pinyin":null},{"Id":1099,"Name":"康复科","Pinyin":null},{"Id":1100,"Name":"烧伤整形科","Pinyin":null},{"Id":1101,"Name":"皮肤科","Pinyin":null},{"Id":1102,"Name":"眼科","Pinyin":null},{"Id":1103,"Name":"耳鼻喉科","Pinyin":null},{"Id":1104,"Name":"规培","Pinyin":null},{"Id":1105,"Name":"口腔急诊科","Pinyin":null},{"Id":1106,"Name":"总值班室","Pinyin":null},{"Id":1107,"Name":"药学部","Pinyin":null},{"Id":1108,"Name":"肿瘤科","Pinyin":null}],"area":[{"Id":1,"Name":"东部院区"},{"Id":2,"Name":"西部院区"}],"sens":[{"ID":9,"Content":"谢谢","RotaSentenceCategoryName":"日常用语","Pinyin":"xiexie","Summary":"谢谢"},{"ID":10,"Content":"已完成","RotaSentenceCategoryName":"日常用语","Pinyin":"ywc","Summary":"已完成"},{"ID":11,"Content":"等待回复","RotaSentenceCategoryName":"日常用语","Pinyin":"ddhf","Summary":"等待回复"},{"ID":12,"Content":"aa","RotaSentenceCategoryName":"火灾等紧急处理常用语","Pinyin":"dd","Summary":"aa"}]}
     */

    private boolean status;
    private String ErrorMsg;
    private ItemsBean items;

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

    public ItemsBean getItems() {
        return items;
    }

    public void setItems(ItemsBean items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * ID : 1
         * Name : 卫生
         * Pinyin : null
         */

        private List<PcoBean> pco;
        /**
         * ID : 6
         * Name : 急危重症病人
         * Pinyin : null
         */

        private List<PctBean> pct;
        /**
         * Id : 1213
         * Name : 员工1
         * Pinyin : null
         */

        private List<GesBean> ges;
        /**
         * Id : 1054
         * Name : 心内科
         * Pinyin : xinneike
         */

        private List<DpsBean> dps;
        /**
         * Id : 1
         * Name : 东部院区
         */

        private List<AreaBean> area;
        /**
         * ID : 9
         * Content : 谢谢
         * RotaSentenceCategoryName : 日常用语
         * Pinyin : xiexie
         * Summary : 谢谢
         */

        private List<SensBean> sens;

        public List<PcoBean> getPco() {
            return pco;
        }

        public void setPco(List<PcoBean> pco) {
            this.pco = pco;
        }

        public List<PctBean> getPct() {
            return pct;
        }

        public void setPct(List<PctBean> pct) {
            this.pct = pct;
        }

        public List<GesBean> getGes() {
            return ges;
        }

        public void setGes(List<GesBean> ges) {
            this.ges = ges;
        }

        public List<DpsBean> getDps() {
            return dps;
        }

        public void setDps(List<DpsBean> dps) {
            this.dps = dps;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public List<SensBean> getSens() {
            return sens;
        }

        public void setSens(List<SensBean> sens) {
            this.sens = sens;
        }

        public static class PcoBean {
            private int ID;
            private String Name;
            private Object Pinyin;

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

            public Object getPinyin() {
                return Pinyin;
            }

            public void setPinyin(Object Pinyin) {
                this.Pinyin = Pinyin;
            }
        }

        public static class PctBean {
            private int ID;
            private String Name;
            private Object Pinyin;

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

            public Object getPinyin() {
                return Pinyin;
            }

            public void setPinyin(Object Pinyin) {
                this.Pinyin = Pinyin;
            }
        }

        public static class GesBean {
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

        public static class DpsBean {
            private int Id;
            private String Name;
            private String Pinyin;

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

            public String getPinyin() {
                return Pinyin;
            }

            public void setPinyin(String Pinyin) {
                this.Pinyin = Pinyin;
            }
        }

        public static class AreaBean {
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

        public static class SensBean {
            private int ID;
            private String Content;
            private String RotaSentenceCategoryName;
            private String Pinyin;
            private String Summary;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getRotaSentenceCategoryName() {
                return RotaSentenceCategoryName;
            }

            public void setRotaSentenceCategoryName(String RotaSentenceCategoryName) {
                this.RotaSentenceCategoryName = RotaSentenceCategoryName;
            }

            public String getPinyin() {
                return Pinyin;
            }

            public void setPinyin(String Pinyin) {
                this.Pinyin = Pinyin;
            }

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String Summary) {
                this.Summary = Summary;
            }
        }
    }
}
