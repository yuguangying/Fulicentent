package ucai.cn.fulicenter.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.fragment.NewGoodsFragment;

public class Boutiques2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutiques2);
        int intExtra = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        Log.i("main", "onCreate: "+intExtra);
        Fragment fragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.boutiques_goods, fragment).show(fragment).commit();
    }
}
