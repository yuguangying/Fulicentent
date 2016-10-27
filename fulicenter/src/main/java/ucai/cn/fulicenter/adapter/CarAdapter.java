package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.utils.ImageLoader;


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
        ImageLoader.downloadImg(context, carHolder.carGoodsPicture,
                cartBean.getGoods().getGoodsThumb());
        carHolder.carCheckbox.setTag(cartBean);
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


    class CarHolder extends RecyclerView.ViewHolder{
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

        CarHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.car_checkbox)
        public void checkboxOnClick(){
            CartBean c = (CartBean) carCheckbox.getTag();
            c.setChecked(true);
            context.sendBroadcast(new Intent("update"));
        }
    }

}
