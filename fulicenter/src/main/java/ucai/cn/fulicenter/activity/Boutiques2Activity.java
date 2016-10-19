package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBeanFive;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class Boutiques2Activity extends AppCompatActivity {

    @Bind(R.id.ye_miang_name)
    TextView yeMiangName;
    GoodsAdapter goodsadapter;
    ArrayList<NewGoodsBeanFive> goodslist;
    GridLayoutManager glm;
    @Bind(R.id.boutiques_goods)
    RecyclerView boutiquesGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutiques2);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        Log.i("main", "onCreate: " + intExtra);
        initView();
        initData();
        setLisetener();
    }

    private void setLisetener() {
    }

    private void initView() {
        goodslist = new ArrayList<>();
        goodsadapter = new GoodsAdapter(this, goodslist);

        //设置了两列
        glm = new GridLayoutManager(this, I.COLUM_NUM);
        boutiquesGoods.setLayoutManager(glm);
        //适配
        boutiquesGoods.setHasFixedSize(true);
        boutiquesGoods.setAdapter(goodsadapter);
    }

    private void initData() {

    }
}
