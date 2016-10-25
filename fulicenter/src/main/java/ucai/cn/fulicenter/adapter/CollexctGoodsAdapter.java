package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollexctGoodsAdapter extends RecyclerView.Adapter {
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

    public CollexctGoodsAdapter(Context context, ArrayList<NewGoodsBeanFive> goodlist) {
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
                holder = new GoodsViewHolder(View.inflate(context, R.layout.item_collect_goods, null));
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
        goodsholder.collectGoodsName.setText(goodfive.getGoodsName());
        goodsholder.collectGoodsPrice.setText(goodfive.getCurrencyPrice());
        ImageLoader.downloadImg(context, goodsholder.mivCollectGoods, goodfive.getGoodsThumb());
        goodsholder.goodsItem.setTag(goodfive.getGoodsId());
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


    class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.foot)
        TextView foot;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }




    class GoodsViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.miv_collect_goods)
        ImageView mivCollectGoods;
        @Bind(R.id.collect_goods_name)
        TextView collectGoodsName;
        @Bind(R.id.collect_goods_price)
        TextView collectGoodsPrice;
        @Bind(R.id.goods_item)
        RelativeLayout goodsItem;
        @Bind(R.id.delete)
        ImageView delete;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.goods_item)
        public void gooditem() {
            int goodsId = (int) goodsItem.getTag();
            MFGT.gotoGoodsDatileActivity(context, goodsId);
        }
        @OnClick(R.id.delete)
        public void delete(){

        }
    }
}
