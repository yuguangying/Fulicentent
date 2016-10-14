package ucai.cn.fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.R;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        //活动界面跳转时的动画
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        //活动界面跳转时的动画
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
