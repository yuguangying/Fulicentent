package ucai.cn.fulicenter;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application{
    public static FuLiCenterApplication applicationContext=Holde.INSTANCE;

    @Override
    public Looper getMainLooper() {
        return super.getMainLooper();
    }
    private static class Holde{
        private static final FuLiCenterApplication INSTANCE = new FuLiCenterApplication();
    }
    public static FuLiCenterApplication getInstance(){
        return Holde.INSTANCE;
    }
}
