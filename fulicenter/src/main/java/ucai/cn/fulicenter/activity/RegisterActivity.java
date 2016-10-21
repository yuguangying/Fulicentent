package ucai.cn.fulicenter.activity;

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
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.register_back, R.id.register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_button:
                if ("".equals(registerName.getText().toString().trim())) {
                    registerName.setError("用户名不能为空");
                    registerName.requestFocus();
                    return;
                }
//                第一种
//                //1.将给定的正则表达式编译到模式中。
//                Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{1,9}$");
//                //2.创建匹配给定输入与此模式的匹配器。
//                Matcher m = p.matcher(registerName.getText().toString());
//                //3.返回是否匹配
//                boolean b = m.matches();
//                第二种(与第一种差不多)
                boolean b = registerName.getText().toString().matches("^[a-zA-Z][a-zA-Z0-9]{1,9}$");
                if (b){
                    registerName.setError("用户名必须首位为字母，4-11位");
                    CommonUtils.showShortToast("用户名必须前三位为字母，4-10位");
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
                    regiserNick.setError("密码确认不能为空");
                    return;
                }
                if (!("".equals(registerName.getText().toString().trim()))&&regiterPassword.getText().toString().equals(registerPasswordTwo.getText().toString())) {
                    CommonUtils.showShortToast("确认密码不一致");
                    registerPasswordTwo.requestFocus();
                    return;
                }
                break;
        }
    }
}
