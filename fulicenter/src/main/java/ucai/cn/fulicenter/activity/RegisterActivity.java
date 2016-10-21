package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
               
                break;
        }
    }
}
