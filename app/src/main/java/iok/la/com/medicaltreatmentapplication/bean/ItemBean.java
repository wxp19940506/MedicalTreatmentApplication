package iok.la.com.medicaltreatmentapplication.bean;

/**
 * Created by Administrator on 2017/2/5 0005.
 */

public class ItemBean{

    /**
     * ID : 95
     * ShiftID : 1321
     * FrequencyID : 0
     * Name : 夜班
     * Mode : 0
     * Sequence : false
     * Description : null
     * ClassName : label label-primary
     */

    private int ID;
    private int ShiftID;
    private int FrequencyID;
    public String Name;
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

    @Override
    public String toString() {
        return "ItemBean{" +
                "ID=" + ID +
                ", ShiftID=" + ShiftID +
                ", FrequencyID=" + FrequencyID +
                ", Name='" + Name + '\'' +
                ", Mode=" + Mode +
                ", Sequence=" + Sequence +
                ", Description=" + Description +
                ", ClassName='" + ClassName + '\'' +
                '}';
    }
}
