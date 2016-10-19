package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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


    int index;int currIndex=8;
    Fragment[] fragments = new Fragment[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragments[2] = new NewGoodsFragment();
        fragments[0] = new BoutiqueFragment();
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
                index=0;
                break;
            case R.id.mcategory:
                index=1;
                break;
            case R.id.mnew_good:
                index=2;
                break;
            case R.id.mpersonal_center:
                index=3;
                break;
            case R.id.mcar:
                index=4;
                break;
        }
        setFragment();
    }
    public void setFragment(){
        if (index!=currIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (currIndex<5){
                ft.hide(fragments[currIndex]);
            }
            if (!fragments[index].isAdded()){
                ft.add(R.id.mfragment_main,fragments[index]);
            }
            ft.show(fragments[index]).commit();
        }
        currIndex = index;
    }
}
