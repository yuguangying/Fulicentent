package ucai.cn.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.activity.Boutiques2Activity;
import ucai.cn.fulicenter.activity.Category2Activity;
import ucai.cn.fulicenter.activity.GoodsDatileActivity;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.R;
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

    public static void gotoBoutiquesGoodsActivity(Context context, int goodsid) {
        Intent intent = new Intent();
        intent.setClass(context, Boutiques2Activity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsid);
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
        intent.putExtra("list",list);
        startActivity(context, intent);
    }
    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        //活动界面跳转时的动画
        ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
