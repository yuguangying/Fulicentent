package ucai.cn.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.AlbumsBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.OkHttpUtils;
import ucai.cn.fulicenter.views.FlowIndicator;
import ucai.cn.fulicenter.views.SlideAutoLoopView;

public class GoodsDatileActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.like)
    ImageView like;
    @Bind(R.id.add_car)
    ImageView addCar;
    @Bind(R.id.EnglishName)
    TextView EnglishName;
    @Bind(R.id.ChinaName)
    TextView ChinaName;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.rlname)
    RelativeLayout rlname;
    @Bind(R.id.goods_iv)
    SlideAutoLoopView goodsIv;
    @Bind(R.id.flowIndicator)
    FlowIndicator flowIndicator;
    @Bind(R.id.brief_introduction)
    WebView briefIntroduction;
    Context context;
    int goodsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_datile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        goodsid = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsid == 0) {
            finish();
        }
        context = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        GoodsDao.downloadGoodsDataile(context, goodsid, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result == null) {
                    finish();
                }
                showGoodsDatile(result);
            }

            @Override
            public void onError(String error) {
                finish();
            }
        });

    }

    private void showGoodsDatile(GoodsDetailsBean details) {
        EnglishName.setText(details.getGoodsEnglishName());
        ChinaName.setText(details.getGoodsName());
        price.setText(details.getCurrencyPrice());
        goodsIv.startPlayLoop(flowIndicator, getAlbumImagUrl(details), getAlbumImagCount(details));
        briefIntroduction.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImagCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImagUrl(GoodsDetailsBean details) {
        String[] util = null;
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            util = new String[albums.length];
            for (int i = 0; i < util.length; i++) {
                util[i] = albums[i].getImgUrl();
            }
        }
        return util;
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.back, R.id.share, R.id.like, R.id.add_car})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                break;
            case R.id.like:
                break;
            case R.id.add_car:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
