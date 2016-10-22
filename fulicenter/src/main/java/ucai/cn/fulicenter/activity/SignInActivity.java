package ucai.cn.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.ResultBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.MyResultBeanUtils;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class SignInActivity extends AppCompatActivity {

    @Bind(R.id.sign_in_back)
    ImageView signInBack;
    @Bind(R.id.sign_in)
    TextView signIn;
    @Bind(R.id.sign_in_name)
    EditText signInName;
    @Bind(R.id.sign_in_password)
    EditText signInPassword;
    @Bind(R.id.sign_in_button)
    Button signInButton;
    @Bind(R.id.sign_in_regiser)
    Button signInRegiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
//        Intent intent = getIntent();
//        if (intent != null) {
//            signInName.setText(intent.getStringExtra(I.User.USER_NAME));
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String name = data.getStringExtra(I.User.USER_NAME);
            signInName.setText(name);
        }
    }

    @OnClick({R.id.sign_in_back, R.id.sign_in_button, R.id.sign_in_regiser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_back:
                MFGT.finish(this);
                break;
            case R.id.sign_in_button:
                isEmpty();
                String name = signInName.getText().toString().trim();
                String password = signInPassword.getText().toString().trim();
                final ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("登录....");
                pd.show();
                GoodsDao.signIn(this, name, password, new OkHttpUtils.OnCompleteListener<ResultBean>() {

                    @Override
                    public void onSuccess(ResultBean result) {
                        pd.dismiss();
                        Log.i("main", "onSuccess: "+result.getRetCode());
                        Log.i("main", "onSuccess: "+result.isRetMsg());
                        if (result.getRetCode()==I.MSG_LOGIN_SUCCESS || result.getRetCode() ==0){
                            Log.i("main", "onSuccess: ");
                            String json = result.getRetData().toString();
                            Gson gson = new Gson();
                            UserAvatar user = gson.fromJson(json, UserAvatar.class);
                            Log.i("main", "onSuccess: "+user.toString());
                            CommonUtils.showLongToast("登录成功");
                            finish();
                        }

                    }

                    @Override
                    public void onError(String error) {
                        pd.dismiss();
                        CommonUtils.showLongToast("登录失败");
                        Log.i("main", "onError: "+error);
                    }
                });
                break;
            case R.id.sign_in_regiser:
                MFGT.gotoRegisterActivity(this,RegisterActivity.class);
                break;
        }
    }

    public void isEmpty() {
        if ("".equals(signInName.getText().toString().trim())) {
            signInName.setError("帐号不能为空");
            signInName.requestFocus();
            return;
        }
        if ("".equals(signInPassword.getText().toString().trim())) {
            signInPassword.setError("帐号不能为空");
            signInPassword.requestFocus();
            return;
        }

    }
}
