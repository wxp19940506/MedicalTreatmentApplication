package iok.la.com.medicaltreatmentapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class EditBanBean implements Serializable{
    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    private List data;
}
