package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.utils.MFGT;

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
        Intent intent = getIntent();
        if (intent != null) {
            signInName.setText(intent.getStringExtra(I.User.USER_NAME));
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
                break;
            case R.id.sign_in_regiser:
                MFGT.gotoRegisterActivity(this);
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
