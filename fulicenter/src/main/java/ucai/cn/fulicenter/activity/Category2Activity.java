package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class Category2Activity extends BaseActivity {

    @Bind(R.id.ye_miang_name)
    TextView yeMiang;
    @Bind(R.id.refresh_boutiques)
    TextView refreshCategory;
    @Bind(R.id.recycler_boutiques)
    RecyclerView recyclerCategory;
    @Bind(R.id.swipe_boutiques)
    SwipeRefreshLayout swipeCategory;

    ArrayList<NewGoodsBeanFive> goodslist;
    GoodsAdapter goodsadapter;
    GridLayoutManager glm;
    int carId;
    int action = I.ACTION_DOWNLOAD;
    int pagId = 1;
    @Bind(R.id.price_lei)
    RadioButton priceLei;
    @Bind(R.id.time_lei)
    RadioButton timeLei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category2);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        carId = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        yeMiang.setText(intent.getStringExtra("title"));
        super.onCreate(savedInstanceState);
    }

    private void ActionPullUp() {
        recyclerCategory.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastpostion;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastpostion = glm.findLastVisibleItemPosition();
                if (lastpostion >= goodsadapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && goodsadapter.ismore()) {
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
        swipeCategory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagId = 1;
                action = I.ACTION_PULL_DOWN;
                swipeCategory.setEnabled(true);
                swipeCategory.setRefreshing(true);
                refreshCategory.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }

    @Override
    protected void setListener() {
        ActionPullDown();
        ActionPullUp();
    }

    @Override
    protected void initData() {
        GoodsDao.downloadCategoryGoods(this, pagId, action, carId, new OkHttpUtils.OnCompleteListener<NewGoodsBeanFive[]>() {
            @Override
            public void onSuccess(NewGoodsBeanFive[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBeanFive> list = ConvertUtils.array2List(result);
                    goodsadapter.setIsmore(true);
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        goodsadapter.setIsmore(false);
                    }
                    switch (action) {
                        case I.ACTION_DOWNLOAD:
                            goodsadapter.initDataDown(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            Log.i("main", "onSuccess: " + action);
                            goodsadapter.initDataDown(list);
                            swipeCategory.setRefreshing(false);
                            refreshCategory.setVisibility(View.GONE);
                            Log.i("main", "onSuccess: " + 1);
                            ImageLoader.release();
                            break;
                        case I.ACTION_PULL_UP:
                            goodsadapter.addData(list);
                            break;
                    }
                } else {
                    swipeCategory.setRefreshing(false);
                    goodsadapter.setIsmore(false);
                }
            }

            @Override
            public void onError(String error) {
                swipeCategory.setEnabled(false);
                goodsadapter.setIsmore(false);
                refreshCategory.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void initView() {
        goodslist = new ArrayList<>();
        goodsadapter = new GoodsAdapter(this, goodslist);
        //设置了两列
        glm = new GridLayoutManager(this, I.COLUM_NUM);
        recyclerCategory.setLayoutManager(glm);
        //适配
        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.setAdapter(goodsadapter);
    }


    @OnClick({R.id.back, R.id.ye_miang_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ye_miang_name:
                break;
            case R.id.price_lei:
                break;
            case R.id.time_lei:
                break;
        }
    }

}
