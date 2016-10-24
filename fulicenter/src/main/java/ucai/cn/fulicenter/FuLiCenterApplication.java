package ucai.cn.fulicenter;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import ucai.cn.fulicenter.bean.UserAvatar;


public class FuLiCenterApplication extends Application{
    public static FuLiCenterApplication applicationContext=null;
    private static FuLiCenterApplication instance;
    private static UserAvatar userAvatar;

    public static void setUserAvatar(UserAvatar userAvatar) {
        FuLiCenterApplication.userAvatar = userAvatar;
    }

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

    public static UserAvatar getUser() {
        return userAvatar;
    }
}
