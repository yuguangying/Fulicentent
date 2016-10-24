package ucai.cn.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.activity.Boutiques2Activity;
import ucai.cn.fulicenter.activity.Category2Activity;
import ucai.cn.fulicenter.activity.CollectionBabyActivity;
import ucai.cn.fulicenter.activity.GoodsDatileActivity;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.RegisterActivity;
import ucai.cn.fulicenter.activity.SignInActivity;
import ucai.cn.fulicenter.bean.CategoryChildBean;


public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        //活动界面跳转时的动画
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void gotoRegisterActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivityForResult(intent, 110);
    }

    public static void gotoSignActivity(Activity context) {
        Intent in = new Intent(context,SignInActivity.class);
        context.startActivityForResult(in,111);
    }

    public static void signGotoMainActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.setResult(Activity.RESULT_OK, intent);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        startActivity(context, intent);

    }

    public static void gotoGoodsDatileActivity(Context context, int goodsid) {
        Intent intent = new Intent();
        intent.setClass(context, GoodsDatileActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsid);
        startActivity(context, intent);
    }

    public static void returnSignInActivity(Activity context, String name) {
        Intent intent = new Intent();
        intent.setClass(context, SignInActivity.class);
        intent.putExtra(I.User.USER_NAME, name);
        context.setResult(Activity.RESULT_OK, intent);
    }

    public static void gotoBoutiquesGoodsActivity(Context context, int goodsid, String title) {
        Intent intent = new Intent();
        intent.setClass(context, Boutiques2Activity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsid);
        intent.putExtra("title", title);
        startActivity(context, intent);
    }

    public static void gotoCategoryGoodsActivity(Context context, int carid, String title, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent();
        intent.setClass(context, Category2Activity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, carid);
        intent.putExtra("title", title);
        intent.putExtra("list", list);
        startActivity(context, intent);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        //活动界面跳转时的动画
        ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void gotoCollectionBaby(Context context){
        Intent intent = new Intent();
        intent.setClass(context,CollectionBabyActivity.class);
        startActivity(context,intent);
    }
}
