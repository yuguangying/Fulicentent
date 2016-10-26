package ucai.cn.fulicenter.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.Dao.DBMenager;
import ucai.cn.fulicenter.Dao.SharePrefrenceUtils;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.ResultBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;
import ucai.cn.fulicenter.utils.OnSetAvatarListener;

public class PersonalDataActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_modify_avatar)
    ImageView ivModifyAvatar;
    @Bind(R.id.tv_data_name)
    TextView tvDataName;
    @Bind(R.id.tv_modify_nick)
    TextView tvModifyNick;
    @Bind(R.id.btn_sign_out)
    Button btnSignOut;

    PersonalDataActivity context;
    @Bind(R.id.tv_name)
    LinearLayout tvName;
    @Bind(R.id.personal_data)
    LinearLayout personaldata;
    UserAvatar user;
    String oldNick;
    boolean isSuccess;
    OnSetAvatarListener osal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    protected void initData() {
        if (user != null) {
            oldNick = user.getMuserNick();
            tvModifyNick.setText(oldNick);
            tvDataName.setText(user.getMuserName());
            if (user.getMavatarSuffix() != null) {
                ImageLoader.downloadAvatar(ImageLoader.getAvatar(user), context, ivModifyAvatar);
            }

        }
    }

    protected void initView() {
        context = this;
        user = FuLiCenterApplication.getUser();


    }

    @OnClick({R.id.back, R.id.iv_modify_avatar, R.id.tv_modify_nick, R.id.btn_sign_out, R.id.tv_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                MFGT.finish(this);
                break;
            case R.id.iv_modify_avatar:
                osal = new OnSetAvatarListener(this, R.id.personal_data, user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;

            case R.id.tv_modify_nick:
                MFGT.gotoModifiledActivity(this);
                break;
            case R.id.btn_sign_out:
                if (user != null) {
                    SharePrefrenceUtils.getInstance(this).removeUser();
                    FuLiCenterApplication.setUserAvatar(null);
                    Log.i("main", "onClick: ");
                }
                MFGT.finish(this);
                break;
            case R.id.tv_name:
                CommonUtils.showLongToast("用户名不能修改");
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("main", "onActivityResult: requestCode:"+requestCode+"--resultCode:"+resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode!=112) {
            osal.setAvatar(requestCode, data, ivModifyAvatar,this);
        }
        switch (requestCode) {
            case 112:
                String newNick = data.getStringExtra("newNick");
                tvModifyNick.setText(newNick);
                user.setMuserNick(newNick);
                FuLiCenterApplication.setUserAvatar(user);
                isSuccess = DBMenager.getInstance().updateUser(user);
                if (isSuccess) {
                    modifiedNickYuan();
                } else {
                    tvModifyNick.setText(oldNick);
                    user.setMuserNick(oldNick);
                    CommonUtils.showLongToast("修改失败");
                }
                break;
            case 2:
                updateAvatar();
                break;
        }
    }

    ///storage/emulated/0/Android/data/ucai.cn.fulicenter/files/Pictures/user_avatar/GueiHuen.jpg
    private void updateAvatar() {
        String avatarPath = OnSetAvatarListener.getAvatarPath(this, user.getMavatarPath()+"/"+user.getMuserName()+I.AVATAR_SUFFIX_JPG);
        Log.i("main", "updateAvatar: "+avatarPath);
        File file = new File(avatarPath);
        GoodsDao.modifiedAvatar(context, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<ResultBean>() {
            @Override
            public void onSuccess(ResultBean result) {
                Log.i("main", "onSuccess: "+result.toString());
                if (result.isRetMsg()){
                    String json = result.getRetData().toString();
                    Gson gson = new Gson();
                    UserAvatar u = gson.fromJson(json, UserAvatar.class);
                    Log.i("main", "onSuccess: user"+json);
                    FuLiCenterApplication.setUserAvatar(u);
                    Log.i("main", "onSuccess: PersonalDataActivity"+u.getMavatarLastUpdateTime());
                    ImageLoader.setAvatar(ImageLoader.getAvatar(u), context, ivModifyAvatar);
                }else {
                    CommonUtils.showLongToast("上传头像失败");
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
                Log.i("main", "onError: "+error);
            }
        });
    }

    private void modifiedNickYuan() {
        GoodsDao.modifiedNick(context, user.getMuserName(), user.getMuserNick(), new OkHttpUtils.OnCompleteListener<ResultBean>() {
            @Override
            public void onSuccess(ResultBean result) {
                if (result.isRetMsg()) {
                    CommonUtils.showLongToast("修改成功");
                } else {
                    tvModifyNick.setText(oldNick);
                    user.setMuserNick(oldNick);
                    CommonUtils.showLongToast("修改失败");
                }
            }

            @Override
            public void onError(String error) {
                tvModifyNick.setText(oldNick);
                user.setMuserNick(oldNick);
                CommonUtils.showLongToast(error);
                Log.i("main", "onError: " + error);
            }
        });
    }
}
