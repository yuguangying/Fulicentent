package ucai.cn.fulicenter;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application{
    public static FuLiCenterApplication applicationContext=null;
    private static FuLiCenterApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
    }


    public static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance = new FuLiCenterApplication();
        }
        return instance;
    }
}
