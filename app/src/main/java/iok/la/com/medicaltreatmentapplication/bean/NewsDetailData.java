package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by thinkpad on 2017/2/7.
 */

public class NewsDetailData {

    /**
     * status : true
     * ErrorMsg :
     * results : {"content":"http://www.baidu.com","msg":{"Details":"发年货了，赶快来领取。"}}
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
         * content : http://www.baidu.com
         * msg : {"Details":"发年货了，赶快来领取。"}
         */

        private String content;
        private MsgBean msg;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

        public static class MsgBean {
            /**
             * Details : 发年货了，赶快来领取。
             */

            private String Details;

            public String getDetails() {
                return Details;
            }

            public void setDetails(String Details) {
                this.Details = Details;
            }
        }
    }
}
