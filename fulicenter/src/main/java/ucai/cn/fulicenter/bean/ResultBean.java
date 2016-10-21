package ucai.cn.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/21.
 */
public class ResultBean implements Serializable{
    private int retCode = -1;
    private boolean retMsg;
    private Object retData;


    public ResultBean() {
    }

    public ResultBean(boolean retMsg, int retCode){
        this.retMsg = retMsg;
        this.retCode = retCode;
    }
    public ResultBean(int retCode, boolean retMsg, Object retData) {
        super();
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retData = retData;
    }
    public int getRetCode() {
        return retCode;
    }
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }
    public boolean isRetMsg() {
        return retMsg;
    }
    public void setRetMsg(boolean retMsg) {
        this.retMsg = retMsg;
    }
    public Object getRetData() {
        return retData;
    }
    public void setRetData(Object retData) {
        this.retData = retData;
    }
    @Override
    public String toString() {
        return "ResultBeanBean [retCode=" + retCode + ", retMsg=" + retMsg + ", retData=" + retData + "]";
    }
}
