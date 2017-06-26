package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class GuiPeiItemBean {

    /**
     * ID : 103
     * ShiftID : 0
     * FrequencyID : 0
     * Name : 规培白班
     * Mode : 0
     * Sequence : false
     * Description : null
     * ClassName : label label-primary
     */

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
