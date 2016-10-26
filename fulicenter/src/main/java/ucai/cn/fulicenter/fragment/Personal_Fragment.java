package ucai.cn.fulicenter.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import ucai.cn.fulicenter.Dao.SharePrefrenceUtils;
import ucai.cn.fulicenter.Dao.UserDao;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.activity.PersonalDataActivity;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.bean.ResultBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Personal_Fragment extends BaseFragment {


    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;

    MainActivity context;
    @Bind(R.id.collection_of_baby)
    TextView collectionOfBaby;
    @Bind(R.id.collection_of_shop)
    TextView collectionOfShop;
    @Bind(R.id.collection_of_footprint)
    TextView collectionOfFootprint;

    UserAvatar user;
    public Personal_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user!=null) {
            tvName.setText(user.getMuserNick());
//            if (user.getMavatarSuffix() != null) {
//                ImageLoader.downloadAvatar(ImageLoader.getAvatar(user), context, ivAvatar);
//            }
        }
        if (user != null) {
            update();
            updateGoodsCount();
        }

    }

    private void updateGoodsCount() {
        GoodsDao.findCollectCount(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                collectionOfBaby.setText(result.getMsg());
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
                Log.i("main", "onError: "+error);
            }
        });
    }

    private void update() {
        GoodsDao.findUserByUserName(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<ResultBean>() {
            @Override
            public void onSuccess(ResultBean result) {
                if (result.isRetMsg()){
                    String json = result.getRetData().toString();
                    Gson gson = new Gson();
                    UserAvatar userAvatar = gson.fromJson(json, UserAvatar.class);
                    user = userAvatar;
                    Log.i("main", "onSuccess: user"+user.getMavatarLastUpdateTime());
                    Log.i("main", "onSuccess: userAvatar"+userAvatar.getMavatarLastUpdateTime());
                    //if (!user.equals(userAvatar)){
                        SharePrefrenceUtils.getInstance(context).saveUser(userAvatar.getMuserName());
                        FuLiCenterApplication.setUserAvatar(userAvatar);
                        tvName.setText(userAvatar.getMuserNick());
                        if (userAvatar.getMavatarSuffix() != null) {
                            ImageLoader.downloadAvatar(ImageLoader.getAvatar(userAvatar), context, ivAvatar);
                        }

                  // }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
        context = (MainActivity) getContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.collection1, R.id.collection2, R.id.collection3,R.id.tv_she})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collection1:
                MFGT.gotoCollectionBaby(context);
                break;
            case R.id.collection2:
                break;
            case R.id.collection3:
                break;
            case R.id.tv_she:
                Intent intent = new Intent();
                intent.setClass(context, PersonalDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

}
