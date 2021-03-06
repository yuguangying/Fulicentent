package ucai.cn.fulicenter.net;

import android.content.Context;

import java.io.File;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.bean.CollectBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.bean.ResultBean;
import ucai.cn.fulicenter.utils.MD5;
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
    public static void downloadCategoryGoods(Context context, int pagId, int action,int cat_Id, OkHttpUtils.OnCompleteListener<NewGoodsBeanFive[]> listener){
        OkHttpUtils<NewGoodsBeanFive[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_GOODS_DETAILS)
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
    public static void register(Context context, String name, String nick,String password, OkHttpUtils.OnCompleteListener<ResultBean> listener){
        OkHttpUtils<ResultBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,name)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD, password)
                .targetClass(ResultBean.class)
                .post()
                .execute(listener);
    }
    public static void signIn(Context context, String name,String password, OkHttpUtils.OnCompleteListener<ResultBean> listener){
        OkHttpUtils<ResultBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,name)
                .addParam(I.User.PASSWORD,password)
                .targetClass(ResultBean.class)
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/addCollect?goods_id=348&userName=GueiHuen
    public static void addCollect(Context context, int goodsid, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_ADD_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    public static void deleteCollect(Context context, int goodsid, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_DELETE_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/isCollect?goods_id=347&userName=GueiHuen
    public static void isCollect(Context context, int goodsid, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/updateNick?m_user_name=SDFA&m_user_nick=SDFA
    public static void modifiedNick(Context context,String username,String newNick, OkHttpUtils.OnCompleteListener<ResultBean> listener){
        OkHttpUtils<ResultBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.NICK,newNick)
                .addParam(I.User.USER_NAME,username)
                .targetClass(ResultBean.class)
                .execute(listener);

    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/updateAvatar?name_or_hxid=SDFA&avatarType=user_avatar
    public static void modifiedAvatar(Context context, String name_or_hxid ,File file,OkHttpUtils.OnCompleteListener<ResultBean> listener){
        OkHttpUtils<ResultBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,name_or_hxid)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(ResultBean.class)
                .post()
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findCollectCount?userName=GueiHuen
    public static void findCollectCount(Context context,String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findCollects?userName=GueiHuen&page_id=1&page_size=5
    public static void findCollects(Context context,String username, int pagId, int action,int pagesize, OkHttpUtils.OnCompleteListener<CollectBean[]> listener){
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_COLLECTS)
                .addParam(I.PAGE_ID,String.valueOf(pagId))
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findUserByUserName?m_user_name=GueiHuen
    public static void findUserByUserName(Context context, String name, OkHttpUtils.OnCompleteListener<ResultBean> listener){
        OkHttpUtils<ResultBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,name)
                .targetClass(ResultBean.class)
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findCarts?userName=yeshen
    public static void findCarts(Context context, String name, OkHttpUtils.OnCompleteListener<CartBean[]> listener){
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,name)
                .targetClass(CartBean[].class)
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/addCart?goods_id=7671&userName=yeshen&count=1&isChecked=0
    public static void addCart(Context context, int goodsid, String username,int count, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_ADD_CART)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .addParam(I.Collect.USER_NAME,username)
                .addParam("count",String.valueOf(count))
                .addParam("isChecked",String.valueOf(0))
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/updateCart?id=1&count=1&isChecked=0
    public static void updateCart(Context context, int id, String username,int count, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_UPDATE_CART)
                .addParam(I.Boutique.ID,String.valueOf(id))
                .addParam("count",String.valueOf(count))
                .addParam("isChecked",String.valueOf(0))
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/deleteCart?id=1
    public static void deleteCart(Context context, int id, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils =new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_DELETE_CART)
                .addParam(I.Boutique.ID,String.valueOf(id))
                .targetClass(MessageBean.class)
                .execute(listener);

    }
}
