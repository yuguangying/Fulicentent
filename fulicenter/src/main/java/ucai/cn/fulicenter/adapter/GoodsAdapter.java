package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.GoodsDatileActivity;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<NewGoodsBeanFive> goodlist;
    boolean ismore;

    public boolean ismore() {
        return ismore;
    }

    public void setIsmore(boolean ismore) {
        this.ismore = ismore;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context context, ArrayList<NewGoodsBeanFive> goodlist) {
        this.context = context;
        this.goodlist = goodlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FootViewHolder(View.inflate(context, R.layout.item_foot, null));
                break;
            case I.TYPE_ITEM:
                holder = new GoodsViewHolder(View.inflate(context, R.layout.item_new_goods, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FootViewHolder footholder = (FootViewHolder) holder;
            footholder.foot.setText(getFootString());
            return;
        }
        GoodsViewHolder goodsholder = (GoodsViewHolder) holder;
        final NewGoodsBeanFive goodfive = goodlist.get(position);
        goodsholder.goodsName.setText(goodfive.getGoodsName());
        goodsholder.goodsPrice.setText(goodfive.getCurrencyPrice());
        ImageLoader.downloadImg(context, goodsholder.mivGoods, goodfive.getGoodsThumb());
        goodsholder.gooditem.setTag(goodfive.getGoodsId());
    }

    @Override
    public int getItemCount() {
        return goodlist == null ? 1 : goodlist.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initDataDown(ArrayList<NewGoodsBeanFive> list) {
        if (goodlist != null) {
            goodlist.clear();
        }
        goodlist.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBeanFive> list) {
        goodlist.addAll(list);
        notifyDataSetChanged();
    }

    public int getFootString() {
        return ismore ? R.string.load_more : R.string.no_more;
    }



    static class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.foot)
        TextView foot;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.miv_goods)
        ImageView mivGoods;
        @Bind(R.id.goods_name)
        TextView goodsName;
        @Bind(R.id.goods_price)
        TextView goodsPrice;
        @Bind(R.id.goods_item)
        LinearLayout gooditem;
        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.goods_item)
        public void goodOnclick(){
            int goodsId = (int) gooditem.getTag();
            context.startActivity(new Intent(context, GoodsDatileActivity.class)
                    .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));
        }
    }
}
