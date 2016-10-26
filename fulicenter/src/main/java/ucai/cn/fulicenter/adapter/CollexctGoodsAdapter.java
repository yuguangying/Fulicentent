package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.CollectBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollexctGoodsAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<CollectBean> goodlist;
    boolean ismore;


    public boolean ismore() {
        return ismore;
    }

    public void setIsmore(boolean ismore) {
        this.ismore = ismore;
        notifyDataSetChanged();
    }

    public CollexctGoodsAdapter(Context context, ArrayList<CollectBean> goodlist) {
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
        final CollectBean goodfive = goodlist.get(position);
        goodsholder.collectGoodsName.setText(goodfive.getGoodsName());
        ImageLoader.downloadImg(context, goodsholder.mivCollectGoods, goodfive.getGoodsThumb());
        goodsholder.goodsItem.setTag(goodfive);
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

    public void initDataDown(ArrayList<CollectBean> list) {
        if (goodlist != null) {
            goodlist.clear();
        }
        goodlist.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
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
            CollectBean goods = (CollectBean) goodsItem.getTag();
            MFGT.gotoGoodsDatileActivity(context, goods.getGoodsId());
        }
        @OnClick(R.id.delete)
        public void delete(){
            final CollectBean goods = (CollectBean) goodsItem.getTag();
            GoodsDao.deleteCollect(context, goods.getGoodsId(), FuLiCenterApplication.getUser().getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result.isSuccess()){
                        goodlist.remove(goods);
                        notifyDataSetChanged();
                        CommonUtils.showLongToast("删除成功");
                    }else {
                        CommonUtils.showLongToast("删除失败");
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showLongToast(error);
                    Log.i("main", "onError: "+error);
                }
            });
        }
    }
}
