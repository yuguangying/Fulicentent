package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.fragment.BoutiqueFragment;
import ucai.cn.fulicenter.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.mboutique)
    RadioButton mboutique;
    @Bind(R.id.mcategory)
    RadioButton mcategory;
    @Bind(R.id.mnew_good)
    RadioButton mnewGood;
    @Bind(R.id.mpersonal_center)
    RadioButton mpersonalCenter;
    @Bind(R.id.mcar)
    RadioButton mcar;
    @Bind(R.id.mcount)
    TextView mcount;

    Fragment fragment;
    NewGoodsFragment newGoodsFragment;
    BoutiqueFragment boutiqueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.mboutique, R.id.mcategory, R.id.mnew_good, R.id.mpersonal_center, R.id.mcar, R.id.mcount})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mboutique:
                    fragment = new BoutiqueFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mfragment_main, fragment).show(fragment).commit();
                break;
            case R.id.mcategory:
                break;
            case R.id.mnew_good:
                    fragment = new NewGoodsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mfragment_main, fragment).show(fragment).commit();
                break;
            case R.id.mpersonal_center:
                break;
            case R.id.mcar:
                break;
        }
    }
}
