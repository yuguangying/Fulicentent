package ucai.cn.fulicenter.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.CarAdapter;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.OkHttpUtils;


public class CarFragment extends Fragment {
    static final String TAG = "main";

    @Bind(R.id.car_recycler)
    RecyclerView carRecycler;
    @Bind(R.id.car_total)
    TextView carTotal;
    @Bind(R.id.car_save)
    TextView carSave;
    CarAdapter carAdapter;
    Context context;
    ArrayList<CartBean> list;
    LinearLayoutManager llm;
    @Bind(R.id.refresh_boutiques)
    TextView refreshBoutiques;
    @Bind(R.id.swipe_boutiques)
    SwipeRefreshLayout swipeBoutiques;
    @Bind(R.id.car_buy_ll)
    LinearLayout carBuyLl;
    @Bind(R.id.car_ko)
    TextView carKo;

    MyBroadcast myReceiver;
    public CarFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        setListener();
        return view;
    }

    private void initData() {
        FindCarts();

    }

    private void setListener(){
        swipeBoutiques.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBoutiques.setVisibility(View.VISIBLE);
                swipeBoutiques.setRefreshing(true);

                FindCarts();
            }
        });
    }

    private void UserHas(boolean has) {
        carBuyLl.setVisibility(has?View.VISIBLE:View.GONE);
        carKo.setVisibility(has?View.GONE:View.VISIBLE);
        carBuyLl.setVisibility(has?View.VISIBLE:View.GONE);
        sumPrice();
    }

    private void FindCarts() {
        GoodsDao.findCarts(context, FuLiCenterApplication.getUser().getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                refreshBoutiques.setVisibility(View.GONE);
                swipeBoutiques.setRefreshing(false);
                if (result.length > 0) {
                    ArrayList<CartBean> cartBeen = ConvertUtils.array2List(result);
                    list=cartBeen;
                    carAdapter.initDataDown(list);
                    UserHas(true);
                } else {
                    Log.i(TAG, "onSuccess:result == null");
                    UserHas(false);
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
                Log.i(TAG, "onError: " + error);
            }
        });
    }

    private void initView() {
        context = getContext();
        list = new ArrayList<>();
        carAdapter = new CarAdapter(context, list);
        llm = new LinearLayoutManager(context);
        carRecycler.setLayoutManager(llm);
        carRecycler.setAdapter(carAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.car_total, R.id.car_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_total:
                break;
            case R.id.car_save:
                break;
        }
    }

    private void sumPrice() {
        int sumPrice = 0;
        int rankPrice = 0;
        if (list.size() > 0) {
            for (CartBean c : list) {
                if (c.isChecked()) {
                    Log.i(TAG, "sumPrice: "+c.getGoodsId());
                    sumPrice += money(c.getGoods().getCurrencyPrice()) * c.getCount();
                    rankPrice += money(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            carTotal.setText("合计：￥ " + Double.valueOf(sumPrice));
            carSave.setText("节省：￥ " + Double.valueOf(sumPrice - rankPrice));
        } else {
            UserHas(false);
            carTotal.setText("合计：￥ 0");
            carSave.setText("节省：￥ 0");
        }
    }

    private int money(String pice) {
        pice = pice.substring(pice.indexOf("￥") + 1);
        int jaige = Integer.valueOf(pice);
        return jaige;
    }
    public class MyBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            sumPrice();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        myReceiver = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        getContext().registerReceiver(myReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(myReceiver);
    }
}
