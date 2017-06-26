package iok.la.com.medicaltreatmentapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/5 0005.
 */

public class SchedulePaiBanTodayBean {

    /**
     * status : true
     * ErrorMsg :
     * results : {"st":[{"Id":18,"Name":"医生班"}],"dt":{"Mode":0,"Title":null,"Date":"/Date(-62135596800000)/","DepartmentID":0,"EmployeeGroupID":0,"Columns":[{"Title":"周日 2/5","Date":"/Date(1486224000000)/","Tooltip":null,"WorkDay":false,"Holiday":false,"Available":true}],"Rows":[{"ID":1213,"Title":"员工1","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1323,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1337,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2223,"Title":"员工3","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":94,"ShiftID":1338,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":95,"ShiftID":1342,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"94,95,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2224,"Title":"员工4","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1339,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2225,"Title":"员工5","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":94,"ShiftID":1340,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2213,"Title":"员工2","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1341,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1343,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null}],"ShiftTypeID":0}}
     */

    private boolean status;
    private String ErrorMsg;
    /**
     * st : [{"Id":18,"Name":"医生班"}]
     * dt : {"Mode":0,"Title":null,"Date":"/Date(-62135596800000)/","DepartmentID":0,"EmployeeGroupID":0,"Columns":[{"Title":"周日 2/5","Date":"/Date(1486224000000)/","Tooltip":null,"WorkDay":false,"Holiday":false,"Available":true}],"Rows":[{"ID":1213,"Title":"员工1","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1323,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1337,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2223,"Title":"员工3","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":94,"ShiftID":1338,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":95,"ShiftID":1342,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"94,95,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2224,"Title":"员工4","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1339,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2225,"Title":"员工5","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":94,"ShiftID":1340,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2213,"Title":"员工2","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1341,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1343,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null}],"ShiftTypeID":0}
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
         * Mode : 0
         * Title : null
         * Date : /Date(-62135596800000)/
         * DepartmentID : 0
         * EmployeeGroupID : 0
         * Columns : [{"Title":"周日 2/5","Date":"/Date(1486224000000)/","Tooltip":null,"WorkDay":false,"Holiday":false,"Available":true}]
         * Rows : [{"ID":1213,"Title":"员工1","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1323,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1337,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2223,"Title":"员工3","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":94,"ShiftID":1338,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":95,"ShiftID":1342,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"94,95,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2224,"Title":"员工4","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1339,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2225,"Title":"员工5","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":94,"ShiftID":1340,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null},{"ID":2213,"Title":"员工2","Cells":[{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1341,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1343,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}],"Tooltip":null,"DepartmentName":"心内科","ShiftTypeName":"医生班","IsGuiPei":false,"grade":0,"Exclude":false,"ToolTip":null,"Available":false,"AppDateColumn":null}]
         * ShiftTypeID : 0
         */

        private DtBean dt;
        /**
         * Id : 18
         * Name : 医生班
         */

        private List<StBean> st;

        public DtBean getDt() {
            return dt;
        }

        public void setDt(DtBean dt) {
            this.dt = dt;
        }

        public List<StBean> getSt() {
            return st;
        }

        public void setSt(List<StBean> st) {
            this.st = st;
        }

        public static class DtBean {
            private int Mode;
            private Object Title;
            private String Date;
            private int DepartmentID;
            private int EmployeeGroupID;
            private int ShiftTypeID;
            /**
             * Title : 周日 2/5
             * Date : /Date(1486224000000)/
             * Tooltip : null
             * WorkDay : false
             * Holiday : false
             * Available : true
             */

            private List<ColumnsBean> Columns;
            /**
             * ID : 1213
             * Title : 员工1
             * Cells : [{"Date":"/Date(1486224000000)/","DayOfWeek":0,"Items":[{"ID":95,"ShiftID":1323,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1337,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}],"Color":null,"Mode":0,"Available":true,"Exclude":false,"IconBase64":null,"ToolTip":null,"Excludes":null,"Description":"","SelectedFrequency":0,"SelectedItems":"95,94,"}]
             * Tooltip : null
             * DepartmentName : 心内科
             * ShiftTypeName : 医生班
             * IsGuiPei : false
             * grade : 0
             * Exclude : false
             * ToolTip : null
             * Available : false
             * AppDateColumn : null
             */

            private List<RowsBean> Rows;

            public int getMode() {
                return Mode;
            }

            public void setMode(int Mode) {
                this.Mode = Mode;
            }

            public Object getTitle() {
                return Title;
            }

            public void setTitle(Object Title) {
                this.Title = Title;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public int getDepartmentID() {
                return DepartmentID;
            }

            public void setDepartmentID(int DepartmentID) {
                this.DepartmentID = DepartmentID;
            }

            public int getEmployeeGroupID() {
                return EmployeeGroupID;
            }

            public void setEmployeeGroupID(int EmployeeGroupID) {
                this.EmployeeGroupID = EmployeeGroupID;
            }

            public int getShiftTypeID() {
                return ShiftTypeID;
            }

            public void setShiftTypeID(int ShiftTypeID) {
                this.ShiftTypeID = ShiftTypeID;
            }

            public List<ColumnsBean> getColumns() {
                return Columns;
            }

            public void setColumns(List<ColumnsBean> Columns) {
                this.Columns = Columns;
            }

            public List<RowsBean> getRows() {
                return Rows;
            }

            public void setRows(List<RowsBean> Rows) {
                this.Rows = Rows;
            }

            public static class ColumnsBean {
                private String Title;
                private String Date;
                private Object Tooltip;
                private boolean WorkDay;
                private boolean Holiday;
                private boolean Available;

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public String getDate() {
                    return Date;
                }

                public void setDate(String Date) {
                    this.Date = Date;
                }

                public Object getTooltip() {
                    return Tooltip;
                }

                public void setTooltip(Object Tooltip) {
                    this.Tooltip = Tooltip;
                }

                public boolean isWorkDay() {
                    return WorkDay;
                }

                public void setWorkDay(boolean WorkDay) {
                    this.WorkDay = WorkDay;
                }

                public boolean isHoliday() {
                    return Holiday;
                }

                public void setHoliday(boolean Holiday) {
                    this.Holiday = Holiday;
                }

                public boolean isAvailable() {
                    return Available;
                }

                public void setAvailable(boolean Available) {
                    this.Available = Available;
                }
            }

            public static class RowsBean {
                private int ID;
                private String Title;
                private Object Tooltip;
                private String DepartmentName;
                private String ShiftTypeName;
                private boolean IsGuiPei;
                private int grade;
                private boolean Exclude;
                private Object ToolTip;
                private boolean Available;
                private Object AppDateColumn;
                /**
                 * Date : /Date(1486224000000)/
                 * DayOfWeek : 0
                 * Items : [{"ID":95,"ShiftID":1323,"FrequencyID":0,"Name":"夜班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"},{"ID":94,"ShiftID":1337,"FrequencyID":0,"Name":"白班","Mode":0,"Sequence":false,"Description":null,"ClassName":"label label-primary"}]
                 * Color : null
                 * Mode : 0
                 * Available : true
                 * Exclude : false
                 * IconBase64 : null
                 * ToolTip : null
                 * Excludes : null
                 * Description :
                 * SelectedFrequency : 0
                 * SelectedItems : 95,94,
                 */

                private List<CellsBean> Cells;

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public Object getTooltip() {
                    return Tooltip;
                }

                public void setTooltip(Object Tooltip) {
                    this.Tooltip = Tooltip;
                }

                public String getDepartmentName() {
                    return DepartmentName;
                }

                public void setDepartmentName(String DepartmentName) {
                    this.DepartmentName = DepartmentName;
                }

                public String getShiftTypeName() {
                    return ShiftTypeName;
                }

                public void setShiftTypeName(String ShiftTypeName) {
                    this.ShiftTypeName = ShiftTypeName;
                }

                public boolean isIsGuiPei() {
                    return IsGuiPei;
                }

                public void setIsGuiPei(boolean IsGuiPei) {
                    this.IsGuiPei = IsGuiPei;
                }

                public int getGrade() {
                    return grade;
                }

                public void setGrade(int grade) {
                    this.grade = grade;
                }

                public boolean isExclude() {
                    return Exclude;
                }

                public void setExclude(boolean Exclude) {
                    this.Exclude = Exclude;
                }

                public Object getToolTip() {
                    return ToolTip;
                }

                public void setToolTip(Object ToolTip) {
                    this.ToolTip = ToolTip;
                }

                public boolean isAvailable() {
                    return Available;
                }

                public void setAvailable(boolean Available) {
                    this.Available = Available;
                }

                public Object getAppDateColumn() {
                    return AppDateColumn;
                }

                public void setAppDateColumn(Object AppDateColumn) {
                    this.AppDateColumn = AppDateColumn;
                }

                public List<CellsBean> getCells() {
                    return Cells;
                }

                public void setCells(List<CellsBean> Cells) {
                    this.Cells = Cells;
                }

                public static class CellsBean {
                    private String Date;
                    private int DayOfWeek;
                    private Object Color;
                    private int Mode;
                    private boolean Available;
                    private boolean Exclude;
                    private Object IconBase64;
                    private Object ToolTip;
                    private Object Excludes;
                    private String Description;
                    private int SelectedFrequency;
                    private String SelectedItems;
                    /**
                     * ID : 95
                     * ShiftID : 1323
                     * FrequencyID : 0
                     * Name : 夜班
                     * Mode : 0
                     * Sequence : false
                     * Description : null
                     * ClassName : label label-primary
                     */

                    private List<ItemsBean> Items;

                    public String getDate() {
                        return Date;
                    }

                    public void setDate(String Date) {
                        this.Date = Date;
                    }

                    public int getDayOfWeek() {
                        return DayOfWeek;
                    }

                    public void setDayOfWeek(int DayOfWeek) {
                        this.DayOfWeek = DayOfWeek;
                    }

                    public Object getColor() {
                        return Color;
                    }

                    public void setColor(Object Color) {
                        this.Color = Color;
                    }

                    public int getMode() {
                        return Mode;
                    }

                    public void setMode(int Mode) {
                        this.Mode = Mode;
                    }

                    public boolean isAvailable() {
                        return Available;
                    }

                    public void setAvailable(boolean Available) {
                        this.Available = Available;
                    }

                    public boolean isExclude() {
                        return Exclude;
                    }

                    public void setExclude(boolean Exclude) {
                        this.Exclude = Exclude;
                    }

                    public Object getIconBase64() {
                        return IconBase64;
                    }

                    public void setIconBase64(Object IconBase64) {
                        this.IconBase64 = IconBase64;
                    }

                    public Object getToolTip() {
                        return ToolTip;
                    }

                    public void setToolTip(Object ToolTip) {
                        this.ToolTip = ToolTip;
                    }

                    public Object getExcludes() {
                        return Excludes;
                    }

                    public void setExcludes(Object Excludes) {
                        this.Excludes = Excludes;
                    }

                    public String getDescription() {
                        return Description;
                    }

                    public void setDescription(String Description) {
                        this.Description = Description;
                    }

                    public int getSelectedFrequency() {
                        return SelectedFrequency;
                    }

                    public void setSelectedFrequency(int SelectedFrequency) {
                        this.SelectedFrequency = SelectedFrequency;
                    }

                    public String getSelectedItems() {
                        return SelectedItems;
                    }

                    public void setSelectedItems(String SelectedItems) {
                        this.SelectedItems = SelectedItems;
                    }

                    public List<ItemsBean> getItems() {
                        return Items;
                    }

                    public void setItems(List<ItemsBean> Items) {
                        this.Items = Items;
                    }

                    public static class ItemsBean {
                        private int ID;
                        private int ShiftID;
                        private int FrequencyID;
                        private String Name;
                        private int Mode;
                        private boolean Sequence;
                        private Object Description;
                        private String ClassName;

                        public int getID() {
                            return ID;
                        }

                        public void setID(int ID) {
                            this.ID = ID;
                        }

                        public int getShiftID() {
                            return ShiftID;
                        }

                        public void setShiftID(int ShiftID) {
                            this.ShiftID = ShiftID;
                        }

                        public int getFrequencyID() {
                            return FrequencyID;
                        }

                        public void setFrequencyID(int FrequencyID) {
                            this.FrequencyID = FrequencyID;
                        }

                        public String getName() {
                            return Name;
                        }

                        public void setName(String Name) {
                            this.Name = Name;
                        }

                        public int getMode() {
                            return Mode;
                        }

                        public void setMode(int Mode) {
                            this.Mode = Mode;
                        }

                        public boolean isSequence() {
                            return Sequence;
                        }

                        public void setSequence(boolean Sequence) {
                            this.Sequence = Sequence;
                        }

                        public Object getDescription() {
                            return Description;
                        }

                        public void setDescription(Object Description) {
                            this.Description = Description;
                        }

                        public String getClassName() {
                            return ClassName;
                        }

                        public void setClassName(String ClassName) {
                            this.ClassName = ClassName;
                        }
                    }
                }
            }
        }

        public static class StBean {
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
    }
}
