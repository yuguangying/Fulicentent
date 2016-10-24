package ucai.cn.fulicenter.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ucai.cn.fulicenter.Dao.SharePrefrenceUtils;
import ucai.cn.fulicenter.Dao.UserDao;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    static final long SLEEPTIME = 2000;
    SplashActivity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //闪屏
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserAvatar user = FuLiCenterApplication.getUser();
                String username = SharePrefrenceUtils.getInstance(context).getUser();
                Log.i("main", "run: username:"+username);
                if (user == null && username!=null){
                    UserDao dao = new UserDao(context);
                    user = dao.getUser(username);
                    Log.i("main", "database,run: user"+user);
                    if(user!=null){
                        FuLiCenterApplication.setUserAvatar(user);
                    }
                }
                UserDao ud = new UserDao(context);
                //活动界面跳转时的动画
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        },SLEEPTIME);
    }
}
