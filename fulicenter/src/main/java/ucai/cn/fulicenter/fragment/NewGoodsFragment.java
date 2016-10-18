package ucai.cn.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    @Bind(R.id.refresh)
    TextView refresh;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    MainActivity mcontext;
    GoodsAdapter goodadapter;
    ArrayList<NewGoodsBeanFive> goodslist;
    int pagId = 1;
    GridLayoutManager glm;
    int action = I.ACTION_DOWNLOAD;


    public NewGoodsFragment() {
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

    private void ActionPullUp() {
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastpostion;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastpostion = glm.findLastVisibleItemPosition();
                if (lastpostion>=goodadapter.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE&&goodadapter.ismore()){
                    pagId++;
                    action = I.ACTION_PULL_UP;
                    initData();
                }

            }
        });
    }

    private void ActionPullDown() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagId = 1;
                action = I.ACTION_PULL_DOWN;
                initData();
            }
        });
    }


    private void initData() {
        GoodsDao.downloadNewGoods(mcontext, pagId,action, new OkHttpUtils.OnCompleteListener<NewGoodsBeanFive[]>() {
            @Override
            public void onSuccess(NewGoodsBeanFive[] result) {
                Log.i("main", "onSuccess: ");
                if (result!=null&&result.length>0){
                    ArrayList<NewGoodsBeanFive> list = ConvertUtils.array2List(result);
                    goodadapter.setIsmore(list!=null&&list.size()>0);
                    switch (action){
                        case I.ACTION_DOWNLOAD:
                            goodadapter.initDataDown(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            goodadapter.initDataDown(list);
                            swipe.setEnabled(false);
                            refresh.setVisibility(View.GONE);
                            ImageLoader.release();
                            break;
                        case I.ACTION_PULL_UP:
                            goodadapter.addData(list);
                            break;
                    }
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(mcontext, error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initView() {
        mcontext = (MainActivity) getContext();
        goodslist = new ArrayList<>();
        goodadapter = new GoodsAdapter(mcontext,goodslist);
        swipe.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        recycler.setLayoutManager(glm);
        //适配
        recycler.setHasFixedSize(true);
        recycler.setAdapter(goodadapter);
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
