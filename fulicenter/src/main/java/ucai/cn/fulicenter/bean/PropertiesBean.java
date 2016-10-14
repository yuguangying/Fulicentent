package ucai.cn.fulicenter.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PropertiesBean {
    ArrayList<ColorBean> colorBeanlist;

    public ArrayList<ColorBean> getColorBeenlist() {
        return colorBeanlist;
    }

    public void setColorBeenlist(ArrayList<ColorBean> colorBeanlist) {
        this.colorBeanlist = colorBeanlist;
    }

    public PropertiesBean() {
    }

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "colorBeenlist=" + colorBeanlist +
                '}';
    }
}
