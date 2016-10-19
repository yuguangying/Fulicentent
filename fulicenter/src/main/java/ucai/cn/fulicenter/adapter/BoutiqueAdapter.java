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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/18.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    ArrayList<BoutiqueBean> boutiqueList;
    boolean isMore;
    Context context;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> boutiqueList) {
        this.context = context;
        this.boutiqueList = boutiqueList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FootViewHolder(View.inflate(context, R.layout.item_foot, null));
                break;
            case I.TYPE_ITEM:
                holder = new BoutiqueViewHolder(View.inflate(context, R.layout.item_boutique, null));
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
        BoutiqueViewHolder boutiqueholder = (BoutiqueViewHolder) holder;
        BoutiqueBean boutiquebean = boutiqueList.get(position);
        boutiqueholder.mtitle.setText(boutiquebean.getTitle());
        boutiqueholder.mname.setText(boutiquebean.getName());
        boutiqueholder.mdescription.setText(boutiquebean.getDescription());
        ImageLoader.downloadImg(context, boutiqueholder.mivBoutiques, boutiquebean.getImageurl());
        boutiqueholder.boutiques_goods.setTag(boutiquebean.getId());
    }

    @Override
    public int getItemCount() {
        return boutiqueList == null ? 1 : boutiqueList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public int getFootString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void initDataDown(ArrayList<BoutiqueBean> list) {
        if (list != null) {
            boutiqueList.clear();
        }
        boutiqueList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        boutiqueList.addAll(list);
        notifyDataSetChanged();
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.foot)
        TextView foot;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.miv_boutiques)
        ImageView mivBoutiques;
        @Bind(R.id.mtitle)
        TextView mtitle;
        @Bind(R.id.mdescription)
        TextView mdescription;
        @Bind(R.id.mname)
        TextView mname;
        @Bind(R.id.item_boutiques_goods)
        RelativeLayout boutiques_goods;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_boutiques_goods)
        public void boutiquesLei() {
            Log.i("main", "boutiquesLei: ");
            int goodsId = (int) boutiques_goods.getTag();
            MFGT.gotoBoutiquesGoodsActivity(context, goodsId);
        }

    }

}
