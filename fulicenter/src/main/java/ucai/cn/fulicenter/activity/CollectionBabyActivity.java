package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.fragment.BaseFragment;

public class CollectionBabyActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ye_miang_name)
    TextView yeMiangName;
    @Bind(R.id.lltitle)
    RelativeLayout lltitle;
    @Bind(R.id.refresh_boutiques)
    TextView refreshBoutiques;
    @Bind(R.id.recycler_boutiques)
    RecyclerView recyclerBoutiques;
    @Bind(R.id.swipe_boutiques)
    SwipeRefreshLayout swipeBoutiques;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_collection_baby);
//        ButterKnife.bind(this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_baby);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.lltitle)
    public void onClick() {
    }
}
