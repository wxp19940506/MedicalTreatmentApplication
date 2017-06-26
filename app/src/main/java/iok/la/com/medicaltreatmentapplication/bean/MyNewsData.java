package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by thinkpad on 2017/2/7.
 */

public class MyNewsData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"NotReadCount":0,"msgs":[{"ID":90,"IsRead":true,"SendDate":"2017-02-07","LevelName":"一般","Title":"注意了，发年货了","Description":"发年货了"}]}
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
         * NotReadCount : 0
         * msgs : [{"ID":90,"IsRead":true,"SendDate":"2017-02-07","LevelName":"一般","Title":"注意了，发年货了","Description":"发年货了"}]
         */

        private int NotReadCount;
        private List<MsgsBean> msgs;

        public int getNotReadCount() {
            return NotReadCount;
        }

        public void setNotReadCount(int NotReadCount) {
            this.NotReadCount = NotReadCount;
        }

        public List<MsgsBean> getMsgs() {
            return msgs;
        }

        public void setMsgs(List<MsgsBean> msgs) {
            this.msgs = msgs;
        }

        public static class MsgsBean {
            /**
             * ID : 90
             * IsRead : true
             * SendDate : 2017-02-07
             * LevelName : 一般
             * Title : 注意了，发年货了
             * Description : 发年货了
             */

            private int ID;
            private boolean IsRead;
            private String SendDate;
            private String LevelName;
            private String Title;
            private String Description;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public boolean isIsRead() {
                return IsRead;
            }

            public void setIsRead(boolean IsRead) {
                this.IsRead = IsRead;
            }

            public String getSendDate() {
                return SendDate;
            }

            public void setSendDate(String SendDate) {
                this.SendDate = SendDate;
            }

            public String getLevelName() {
                return LevelName;
            }

            public void setLevelName(String LevelName) {
                this.LevelName = LevelName;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
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
