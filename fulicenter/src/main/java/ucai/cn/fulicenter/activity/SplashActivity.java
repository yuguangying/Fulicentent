package ucai.cn.fulicenter.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    static final long SLEEPTIME = 2000;
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
                //活动界面跳转时的动画
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        },SLEEPTIME);
    }
}
