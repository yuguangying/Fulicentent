package ucai.cn.fulicenter.net;

import android.content.Context;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.utils.OkHttpUtils;


public class GoodsDao {
    public static void downloadNewGoods(Context context, int pagId, int action,int cat_Id, OkHttpUtils.OnCompleteListener<NewGoodsBeanFive[]> listener){
        OkHttpUtils<NewGoodsBeanFive[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,cat_Id+"")
                .addParam(I.PAGE_ID,String.valueOf(pagId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBeanFive[].class)
                .execute(listener);
    }

    public static void downloadGoodsDataile(Context context,int goodsid,OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
    public static void downloadCategoryGroup(Context context,OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }
    public static void downloadCategoryChild(Context context,int parent_id,OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl("findCategoryChildren")
                .addParam("parent_id",parent_id+"")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

}
