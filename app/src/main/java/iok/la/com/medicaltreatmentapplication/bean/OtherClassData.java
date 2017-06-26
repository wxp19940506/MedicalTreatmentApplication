package iok.la.com.medicaltreatmentapplication.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/7 0007.
 */

public class OtherClassData implements Serializable {
    public List<Map> getData() {
        return data;
    }
    public List<Map> getData0() {
        return data0;
    }
    public List<Integer> getSelectid() {
        return selectid;
    }
    public List<Integer> selectid;
    public void setData(List<Map> data) {
        this.data = data;
    }
    public void setData0(List<Map> data0) {
        this.data0 = data0;
    }
    public void setSelectid(List<Integer> selectid) {
        this.selectid = selectid;
    }

    public List<Map> data;
    public List<Map> data0;

    public HashMap getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = (HashMap) map;
    }

    public HashMap map;

    public List<XunjianBean.ItemsBean.GesBean> getGes() {
        return ges;
    }

    public void setGes(List<XunjianBean.ItemsBean.GesBean> ges) {
        this.ges = ges;
    }

    public List<XunjianBean.ItemsBean.GesBean> ges;
}
