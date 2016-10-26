package ucai.cn.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.AlbumsBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.OkHttpUtils;
import ucai.cn.fulicenter.views.FlowIndicator;
import ucai.cn.fulicenter.views.SlideAutoLoopView;

public class GoodsDatileActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.share)
    ImageView share;

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
    @Bind(R.id.like)
    RadioButton like;
    @Bind(R.id.add_car)
    RadioButton addCar;
    GoodsDetailsBean goodsDetails;
    boolean flag;

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
        updateLike();
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
                goodsDetails = result;
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
                showShare();
                break;
            case R.id.like:
                isCollect();
                break;
            case R.id.add_car:
                break;
        }
    }

    private void addCollect() {
        GoodsDao.addCollect(context, goodsid, FuLiCenterApplication.getUser().getMuserName(),
                new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result.isSuccess()) {
                            CommonUtils.showLongToast(result.getMsg());
                            like.setChecked(true);
                        } else {
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        Log.e("main", "onError: " + error);
                    }
                });
    }

    public void isCollect() {
        GoodsDao.isCollect(context, goodsid, FuLiCenterApplication.getUser().getMuserName(),
                new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {

                        Log.i("main", "onSuccess: " + result.toString() + ":goodsid" + goodsDetails.getGoodsId() + ":name"
                                + FuLiCenterApplication.getUser().getMuserName());
                        if (result.isSuccess()) {
                            CommonUtils.showLongToast("已收藏");
                            //deleteCollect();
                        } else {
                            addCollect();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.i("main", "onError: " + error);
                    }
                });
    }

    private void deleteCollect() {
        GoodsDao.deleteCollect(context, goodsid, FuLiCenterApplication.getUser().getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result.isSuccess()) {
                    like.setChecked(false);
                } else {
                    CommonUtils.showLongToast("删除失败");
                }
            }

            @Override
            public void onError(String error) {
                Log.i("main", "onError: " + error);
                CommonUtils.showLongToast(error);
            }
        });
    }

    public void updateLike() {
        GoodsDao.isCollect(context, goodsid, FuLiCenterApplication.getUser().getMuserName(),
                new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result.isSuccess()) {
                            like.setChecked(true);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.i("main", "onError: " + error);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /* 创建菜单 */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "分享");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                // intent.setType("text/plain"); //纯文本
            /*
             * 图片分享 it.setType("image/png"); 　//添加图片 File f = new
             * File(Environment.getExternalStorageDirectory()+"/name.png");
             *
             * Uri uri = Uri.fromFile(f); intent.putExtra(Intent.EXTRA_STREAM,
             * uri); 　
             */
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                return true;
        }
        return false;
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
