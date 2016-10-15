package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.boutique)
    RadioButton boutique;
    @Bind(R.id.category)
    RadioButton category;
    @Bind(R.id.new_good)
    RadioButton newGood;
    @Bind(R.id.personal_center)
    RadioButton personalCenter;
    @Bind(R.id.car)
    RadioButton car;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.rl)
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.boutique, R.id.category, R.id.new_good, R.id.personal_center, R.id.car})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.boutique:
                break;
            case R.id.category:
                break;
            case R.id.new_good:
                break;
            case R.id.personal_center:
                break;
            case R.id.car:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
