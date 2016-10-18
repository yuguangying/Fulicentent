package ucai.cn.fulicenter.net;

import android.content.Context;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsDao {
    public static void downloadNewGoods(Context context,int pagId,int action,OkHttpUtils.OnCompleteListener<NewGoodsBeanFive[]> listener){
        OkHttpUtils<NewGoodsBeanFive[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID+"")
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
}
