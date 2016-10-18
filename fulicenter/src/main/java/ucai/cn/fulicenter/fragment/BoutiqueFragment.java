package ucai.cn.fulicenter.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.BoutiqueAdapter;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.net.BoutiqueDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {

    @Bind(R.id.refresh)
    TextView refresh;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    MainActivity mcontext;
    ArrayList<BoutiqueBean> bouList;
    BoutiqueAdapter boutiqueAdaper;
    GridLayoutManager glm;
    int pagId=1;
    int action;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        ActionPullDown();
        ActionPullUp();
    }

    private void initData() {
        downloadBoutique();
    }

    private void downloadBoutique() {
        BoutiqueDao.downloadNewGoods(mcontext,pagId,action, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                if (result!=null&&result.length>0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    boutiqueAdaper.setMore(true);
                    if (list.size()<I.PAGE_SIZE_DEFAULT){
                        boutiqueAdaper.setMore(false);
                    }
                    switch (action){
                        case I.ACTION_DOWNLOAD:
                            boutiqueAdaper.initDataDown(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            boutiqueAdaper.initDataDown(list);
                            swipe.setRefreshing(false);
                            refresh.setVisibility(View.GONE);
                            ImageLoader.release();
                            break;
                        case I.ACTION_PULL_UP:
                            boutiqueAdaper.addData(list);
                            break;
                    }
                }else {
                    boutiqueAdaper.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                swipe.setEnabled(false);
                boutiqueAdaper.setMore(false);
                refresh.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void ActionPullUp() {
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastpostion;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastpostion = glm.findLastVisibleItemPosition();
                if (lastpostion>=boutiqueAdaper.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE&&boutiqueAdaper.isMore()){
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
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagId = 1;
                action = I.ACTION_PULL_DOWN;
                swipe.setEnabled(true);
                swipe.setRefreshing(true);
                refresh.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }
    private void initView() {
        mcontext = (MainActivity) getContext();
        bouList = new ArrayList<>();
        boutiqueAdaper = new BoutiqueAdapter(mcontext,bouList);
        //给刷新的圆圈设置渐变的颜色
        swipe.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mcontext, I.COLUM_NUM_ONE);
        recycler.setLayoutManager(glm);
        //适配
        recycler.setHasFixedSize(true);
        recycler.setAdapter(boutiqueAdaper);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
