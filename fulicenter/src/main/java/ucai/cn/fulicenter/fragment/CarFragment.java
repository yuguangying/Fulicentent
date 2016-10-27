package ucai.cn.fulicenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    UserAvatar user;
    LinearLayoutManager llm;
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
        return view;
    }

    private void initData() {
        FindCarts();
    }

    private void FindCarts() {
        GoodsDao.findCarts(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result.length>0){
                    ArrayList<CartBean> cartBeen = ConvertUtils.array2List(result);
                    carAdapter.initDataDown(cartBeen);
                }else {
                    Log.i(TAG, "onSuccess:result == null");
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
                Log.i(TAG, "onError: "+error);
            }
        });
    }

    private void initView() {
        user = FuLiCenterApplication.getUser();
        context = getContext();
        list = new ArrayList<>();
        carAdapter = new CarAdapter(context,list);
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
}
