package iok.la.com.medicaltreatmentapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class HolidayDataBean implements Serializable {
    public List<HolidayDepartment.ResultsBean.DepartsAddBean> getDepartsAdd() {
        return departsAdd;
    }

    public void setDepartsAdd(List<HolidayDepartment.ResultsBean.DepartsAddBean> departsAdd) {
        this.departsAdd = departsAdd;
    }

    List<HolidayDepartment.ResultsBean.DepartsAddBean> departsAdd;

    public List<HolidayDepartment.ResultsBean.EetBean> getEetBeanList() {
        return eetBeanList;
    }

    public void setEetBeanList(List<HolidayDepartment.ResultsBean.EetBean> eetBeanList) {
        this.eetBeanList = eetBeanList;
    }

    List<HolidayDepartment.ResultsBean.EetBean> eetBeanList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
}
