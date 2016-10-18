package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;

public class GoodsDatileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_datile);
        Intent intent = getIntent();
        int goodsid = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        Toast.makeText(GoodsDatileActivity.this, goodsid+"", Toast.LENGTH_SHORT).show();
    }
}
