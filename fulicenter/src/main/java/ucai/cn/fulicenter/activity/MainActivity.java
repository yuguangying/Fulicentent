package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.fragment.BoutiqueFragment;
import ucai.cn.fulicenter.fragment.CategoryFragment;
import ucai.cn.fulicenter.fragment.NewGoodsFragment;
import ucai.cn.fulicenter.fragment.Personal_Fragment;
import ucai.cn.fulicenter.utils.MFGT;

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
        fragments[1] = new CategoryFragment();
        fragments[3] = new Personal_Fragment();
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
                if (FuLiCenterApplication.getUser()==null){
                    MFGT.gotoSignActivity(this);
                }else {
                    index=3;
                }
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
