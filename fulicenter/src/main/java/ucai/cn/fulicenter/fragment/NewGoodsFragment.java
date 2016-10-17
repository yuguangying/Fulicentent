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
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.net.GoodsDao;
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
        return view;
    }

    private void initData() {
        GoodsDao.downloadNewGoods(mcontext, pagId, new OkHttpUtils.OnCompleteListener() {
            @Override
            public void onSuccess(Object result) {
                String json = result.toString();
                Log.i("main", "onSuccess: "+json);
                Gson gson = new Gson();


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
        GridLayoutManager glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
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
