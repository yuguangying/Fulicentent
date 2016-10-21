package ucai.cn.fulicenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.ResultBean;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class RegisterActivity extends AppCompatActivity {


    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_name)
    EditText registerName;
    @Bind(R.id.regiser_nick)
    EditText regiserNick;
    @Bind(R.id.regiter_password)
    EditText regiterPassword;
    @Bind(R.id.register_password_two)
    EditText registerPasswordTwo;
    @Bind(R.id.register_button)
    Button registerButton;

    RegisterActivity mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mcontext = this;

    }


    @OnClick({R.id.register_back, R.id.register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                MFGT.finish(this);
                break;
            case R.id.register_button:
                isEmpty();
                register();
                break;
        }
    }

    private void register() {
        final String name = registerName.getText().toString().trim();
        String nick = regiserNick.getText().toString().trim();
        String password = regiterPassword.getText().toString().trim();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("注册中…");
        pd.show();
        GoodsDao.register(mcontext, name, nick, password, new OkHttpUtils.OnCompleteListener<ResultBean>() {
            @Override
            public void onSuccess(ResultBean result) {
                pd.dismiss();
                if (result==null) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else if (result.getRetCode()== I.MSG_REGISTER_SUCCESS) {
                    CommonUtils.showLongToast(R.string.register_success);
                    MFGT.returnSignInActivity(mcontext, name);
                    finish();
                }else if (result.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS){
                    CommonUtils.showLongToast(R.string.register_fail_exists);
                }else if (result.getRetCode() == I.MSG_REGISTER_FAIL || result.getRetCode() == I.MSG_UNREGISTER_FAIL){
                    CommonUtils.showLongToast(R.string.register_fail);
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(R.string.register_fail);
                Log.i("main", "onError: "+error);
            }
        });
    }

    private void isEmpty() {
        if ("".equals(registerName.getText().toString().trim())) {
            registerName.setError("帐号不能为空");
            registerName.requestFocus();
            return;
        }
        //第一种
        //1.将给定的正则表达式编译到模式中。
        Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{1,9}$");
        //2.创建匹配给定输入与此模式的匹配器。
        Matcher m = p.matcher(registerName.getText().toString());
        //3.返回是否匹配
        boolean b = m.matches();
        //第二种(与第一种差不多)
        //告知此字符串是否匹配给定的正则表达式。
        // boolean b = registerName.getText().toString().matches("^[a-zA-Z][a-zA-Z0-9]{5,15}$");
        if (!b) {
            registerName.setError("非法帐号,6-16个数字和字母组合,请以字母开头");
            CommonUtils.showShortToast(R.string.illegal_user_name);
            registerName.requestFocus();
            return;
        }
        if ("".equals(regiserNick.getText().toString().trim())) {
            regiserNick.setError("昵称不能为空");
            regiserNick.requestFocus();
            return;
        }
        if ("".equals(regiterPassword.getText().toString().trim())) {
            regiserNick.setError("密码不能为空");
            regiterPassword.requestFocus();
            return;
        }
        if ("".equals(registerPasswordTwo.getText().toString().trim())) {
            regiserNick.setError("确认密码不能为空");
            return;
        }
        if (!(regiterPassword.getText().toString().equals(registerPasswordTwo.getText().toString()))) {
            CommonUtils.showShortToast(R.string.two_input_password);
            registerPasswordTwo.requestFocus();
            return;
        }
    }
}
