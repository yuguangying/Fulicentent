package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;


public class CarAdapter extends RecyclerView.Adapter {
    static final String TAG = "main";
    Context context;
    ArrayList<CartBean> cartList;

    public CarAdapter(Context context, ArrayList<CartBean> list) {
        this.context = context;
        this.cartList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: " + cartList.size());
        RecyclerView.ViewHolder hodler = new CarHolder(View.inflate(context, R.layout.item_car, null));
        return hodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CarHolder carHolder = (CarHolder) holder;
        Log.i("main", "onBindViewHolder: " + cartList.size());
        CartBean cartBean = cartList.get(position);
        carHolder.carCount.setText(String.valueOf(cartBean.getCount()));
        carHolder.carPrice.setText(cartBean.getGoods().getCurrencyPrice());
        carHolder.carGoodsName.setText(cartBean.getGoods().getGoodsName());
        ImageLoader.downloadImg(context, carHolder.carGoodsPicture,
                cartBean.getGoods().getGoodsThumb());
        carHolder.carCheckbox.setTag(cartBean);
        carHolder.addCarCount.setTag(position);
        carHolder.delCartCount.setTag(position);
    }

    @Override
    public int getItemCount() {
        return cartList == null ? 0 : cartList.size();
    }

    public void initDataDown(ArrayList<CartBean> list) {
        //cartList.clear();
        cartList = list;
        Log.i(TAG, "initDataDown: " + cartList.size());
        notifyDataSetChanged();
    }


    class CarHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.car_checkbox)
        RadioButton carCheckbox;
        @Bind(R.id.car_goods_picture)
        ImageView carGoodsPicture;
        @Bind(R.id.add_car_count)
        ImageView addCarCount;
        @Bind(R.id.car_count)
        TextView carCount;
        @Bind(R.id.del_cart_count)
        ImageView delCartCount;
        @Bind(R.id.car_price)
        TextView carPrice;
        @Bind(R.id.car_goods_name)
        TextView carGoodsName;

        CarHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.car_checkbox)
        public void checkboxOnClick() {
            CartBean c = (CartBean) carCheckbox.getTag();
            c.setChecked(true);
            context.sendBroadcast(new Intent("update"));
        }
        @OnClick(R.id.car_goods_picture)
        public void avatarOnClick() {
            CartBean c = (CartBean) carCheckbox.getTag();
            MFGT.gotoGoodsDatileActivity(context,c.getGoodsId());
        }
        @OnClick(R.id.del_cart_count)
        public void delCarCountOnClick() {
            Log.i(TAG, "delCarCountOnClick: ");
            final int delpostion = (int) delCartCount.getTag();
            CartBean c = cartList.get(delpostion);
            int count = cartList.get(delpostion).getCount()-1;
            if (cartList.get(delpostion).getCount() > 1) {
               updateCart(delpostion,count,c);
            }else {
                GoodsDao.deleteCart(context, c.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result.isSuccess()){
                            cartList.remove(delpostion);
                            context.sendBroadcast(new Intent("update"));
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        Log.i(TAG, "onError: " + error);
                    }
                });
            }

        }

        @OnClick(R.id.add_car_count)
        public void addCarCountOnClick() {
            addCarCount();
        }

        public void addCarCount() {
            Log.i(TAG, "addCarCountOnClick: ");
            final int addpostion = (int) addCarCount.getTag();
            int count = cartList.get(addpostion).getCount() + 1;
            CartBean c = cartList.get(addpostion);
            updateCart(addpostion,count, c);
        }

        private synchronized void updateCart(final int addpostion, final int count, CartBean c) {
            GoodsDao.updateCart(context, c.getId(), FuLiCenterApplication.getUser().getMuserName(),
                    count, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result.isSuccess()) {
                                cartList.get(addpostion).setCount(count);
                                context.sendBroadcast(new Intent("update"));
                                carCount.setText(cartList.get(addpostion).getCount() + "");
                                notifyDataSetChanged();
                            }else {
                                Log.i(TAG, "faile: ");
                            }
                        }
                        @Override
                        public void onError(String error) {
                            CommonUtils.showLongToast(error);
                            Log.i(TAG, "onError: " + error);
                        }
                    });
        }
    }

}
