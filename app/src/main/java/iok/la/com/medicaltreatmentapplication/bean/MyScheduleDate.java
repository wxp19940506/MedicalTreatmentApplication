package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyScheduleDate {

    /**
     * status : true
     * ErrorMsg :
     * results : {"title":"2017/02/01-2017/02/28","columns":[{"title":"日","DayOfWeek":0},{"title":"一","DayOfWeek":1},{"title":"二","DayOfWeek":2},{"title":"三","DayOfWeek":3},{"title":"四","DayOfWeek":4},{"title":"五","DayOfWeek":5},{"title":"六","DayOfWeek":6}],"items":[{"day":1,"DayOfWeek":3,"date":"2017-02-01"},{"day":2,"DayOfWeek":4,"date":"2017-02-02"},{"day":3,"DayOfWeek":5,"date":"2017-02-03"},{"day":4,"DayOfWeek":6,"date":"2017-02-04"},{"day":5,"DayOfWeek":0,"date":"2017-02-05"},{"day":6,"DayOfWeek":1,"date":"2017-02-06"},{"day":7,"DayOfWeek":2,"date":"2017-02-07"},{"day":8,"DayOfWeek":3,"date":"2017-02-08"},{"day":9,"DayOfWeek":4,"date":"2017-02-09"},{"day":10,"DayOfWeek":5,"date":"2017-02-10"},{"day":11,"DayOfWeek":6,"date":"2017-02-11"},{"day":12,"DayOfWeek":0,"date":"2017-02-12"},{"day":13,"DayOfWeek":1,"date":"2017-02-13"},{"day":14,"DayOfWeek":2,"date":"2017-02-14"},{"day":15,"DayOfWeek":3,"date":"2017-02-15"},{"day":16,"DayOfWeek":4,"date":"2017-02-16"},{"day":17,"DayOfWeek":5,"date":"2017-02-17"},{"day":18,"DayOfWeek":6,"date":"2017-02-18"},{"day":19,"DayOfWeek":0,"date":"2017-02-19"},{"day":20,"DayOfWeek":1,"date":"2017-02-20"},{"day":21,"DayOfWeek":2,"date":"2017-02-21"},{"day":22,"DayOfWeek":3,"date":"2017-02-22"},{"day":23,"DayOfWeek":4,"date":"2017-02-23"},{"day":24,"DayOfWeek":5,"date":"2017-02-24"},{"day":25,"DayOfWeek":6,"date":"2017-02-25"},{"day":26,"DayOfWeek":0,"date":"2017-02-26"},{"day":27,"DayOfWeek":1,"date":"2017-02-27"},{"day":28,"DayOfWeek":2,"date":"2017-02-28"}]}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * title : 2017/02/01-2017/02/28
     * columns : [{"title":"日","DayOfWeek":0},{"title":"一","DayOfWeek":1},{"title":"二","DayOfWeek":2},{"title":"三","DayOfWeek":3},{"title":"四","DayOfWeek":4},{"title":"五","DayOfWeek":5},{"title":"六","DayOfWeek":6}]
     * items : [{"day":1,"DayOfWeek":3,"date":"2017-02-01"},{"day":2,"DayOfWeek":4,"date":"2017-02-02"},{"day":3,"DayOfWeek":5,"date":"2017-02-03"},{"day":4,"DayOfWeek":6,"date":"2017-02-04"},{"day":5,"DayOfWeek":0,"date":"2017-02-05"},{"day":6,"DayOfWeek":1,"date":"2017-02-06"},{"day":7,"DayOfWeek":2,"date":"2017-02-07"},{"day":8,"DayOfWeek":3,"date":"2017-02-08"},{"day":9,"DayOfWeek":4,"date":"2017-02-09"},{"day":10,"DayOfWeek":5,"date":"2017-02-10"},{"day":11,"DayOfWeek":6,"date":"2017-02-11"},{"day":12,"DayOfWeek":0,"date":"2017-02-12"},{"day":13,"DayOfWeek":1,"date":"2017-02-13"},{"day":14,"DayOfWeek":2,"date":"2017-02-14"},{"day":15,"DayOfWeek":3,"date":"2017-02-15"},{"day":16,"DayOfWeek":4,"date":"2017-02-16"},{"day":17,"DayOfWeek":5,"date":"2017-02-17"},{"day":18,"DayOfWeek":6,"date":"2017-02-18"},{"day":19,"DayOfWeek":0,"date":"2017-02-19"},{"day":20,"DayOfWeek":1,"date":"2017-02-20"},{"day":21,"DayOfWeek":2,"date":"2017-02-21"},{"day":22,"DayOfWeek":3,"date":"2017-02-22"},{"day":23,"DayOfWeek":4,"date":"2017-02-23"},{"day":24,"DayOfWeek":5,"date":"2017-02-24"},{"day":25,"DayOfWeek":6,"date":"2017-02-25"},{"day":26,"DayOfWeek":0,"date":"2017-02-26"},{"day":27,"DayOfWeek":1,"date":"2017-02-27"},{"day":28,"DayOfWeek":2,"date":"2017-02-28"}]
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
        private String title;
        /**
         * title : 日
         * DayOfWeek : 0
         */

        private List<ColumnsBean> columns;
        /**
         * day : 1
         * DayOfWeek : 3
         * date : 2017-02-01
         */

        private List<ItemsBean> items;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ColumnsBean> getColumns() {
            return columns;
        }

        public void setColumns(List<ColumnsBean> columns) {
            this.columns = columns;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ColumnsBean {
            private String title;
            private int DayOfWeek;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getDayOfWeek() {
                return DayOfWeek;
            }

            public void setDayOfWeek(int DayOfWeek) {
                this.DayOfWeek = DayOfWeek;
            }
        }

        public static class ItemsBean {
            private int day;
            private int DayOfWeek;
            private String date;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getDayOfWeek() {
                return DayOfWeek;
            }

            public void setDayOfWeek(int DayOfWeek) {
                this.DayOfWeek = DayOfWeek;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
