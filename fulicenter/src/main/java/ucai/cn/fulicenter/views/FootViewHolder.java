package ucai.cn.fulicenter.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;

class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.foot)
        public TextView foot;

        public FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }