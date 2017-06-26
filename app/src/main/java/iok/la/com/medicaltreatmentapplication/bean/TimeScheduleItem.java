package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class TimeScheduleItem {

    /**
     * ID : 95
     * ShiftID : 1173
     * FrequencyID : 0
     * Name : 夜班
     * Mode : 0
     * Sequence : false
     * Description : afaf
     * ClassName : label label-primary
     */

    private int ID;
    private int ShiftID;
    private int FrequencyID;
    private String Name;
    private int Mode;
    private boolean Sequence;
    private String Description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }
}
