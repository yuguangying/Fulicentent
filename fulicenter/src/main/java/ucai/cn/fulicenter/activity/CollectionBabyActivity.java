package ucai.cn.fulicenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.CollexctGoodsAdapter;
import ucai.cn.fulicenter.bean.CollectBean;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;

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
    ArrayList<CollectBean> goodslist;
    int pagId = 1;
    GridLayoutManager glm;
    int action = I.ACTION_DOWNLOAD;
    Context context;
    CollexctGoodsAdapter goodadapter;
    UserAvatar user;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_baby);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        ActionPullDown();
        ActionPullUp();
    }

    private void ActionPullUp() {
        recyclerBoutiques.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastpostion;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastpostion = glm.findLastVisibleItemPosition();
                if (lastpostion >= goodadapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && goodadapter.ismore()) {
                    pagId++;
                    action = I.ACTION_PULL_UP;
                    initData();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastpostion = glm.findLastVisibleItemPosition();
            }
        });
    }

    private void ActionPullDown() {
        swipeBoutiques.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagId = 1;
                action = I.ACTION_PULL_DOWN;
                swipeBoutiques.setEnabled(true);
                swipeBoutiques.setRefreshing(true);
                recyclerBoutiques.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        Log.i("m", "username: "+username+"pagId: "+pagId);
        GoodsDao.findCollects(context, username, pagId, action, I.PAGE_SIZE_DEFAULT, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    goodadapter.setIsmore(true);
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        goodadapter.setIsmore(false);
                    }
                    switch (action) {
                        case I.ACTION_DOWNLOAD:
                            goodadapter.initDataDown(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            goodadapter.initDataDown(list);
                            swipeBoutiques.setRefreshing(false);
                            recyclerBoutiques.setVisibility(View.GONE);
                            ImageLoader.release();
                            break;
                        case I.ACTION_PULL_UP:
                            goodadapter.addData(list);
                            break;
                    }
                } else {
                    swipeBoutiques.setRefreshing(false);
                    goodadapter.setIsmore(false);
                }
            }

            @Override
            public void onError(String error) {
                swipeBoutiques.setEnabled(false);
                goodadapter.setIsmore(false);
                recyclerBoutiques.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
            }
        });

    }

    @Override
    protected void initView() {
        context = this;
        user = FuLiCenterApplication.getUser();
        username = user.getMuserName();
        goodslist = new ArrayList<>();
        goodadapter = new CollexctGoodsAdapter(context, goodslist);
        //给刷新的圆圈设置渐变的颜色
        swipeBoutiques.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow)
        );
        //设置了两列
        glm = new GridLayoutManager(context, I.COLUM_NUM);
        recyclerBoutiques.setLayoutManager(glm);
        //适配
        recyclerBoutiques.setHasFixedSize(true);
        recyclerBoutiques.setAdapter(goodadapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.back)
    public void onClick() {
        MFGT.finish(this);
    }
}
