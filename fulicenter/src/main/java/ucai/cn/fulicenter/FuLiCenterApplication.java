package ucai.cn.fulicenter;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application{
    private static FuLiCenterApplication applicationContext=null;


    public FuLiCenterApplication() {
        applicationContext = this;
    }

    public static FuLiCenterApplication getInstance(){
        if (applicationContext==null){
            applicationContext = new FuLiCenterApplication();
        }
        return applicationContext;
    }
}
