package com.king.practices.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.di.component.DaggerGankDetailComponent;
import com.king.practices.di.module.GankDetailModule;
import com.king.practices.mvp.contract.GankDetailContract;
import com.king.practices.mvp.presenter.GankDetailPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;

public class GankDetailActivity extends BaseActivity<GankDetailPresenter> implements GankDetailContract.View {
    private RxPermissions mRxPermissions;

    @BindView(R.id.rl_gank_fuli)
    RelativeLayout rlGankFuli;
    @BindView(R.id.iv_fuli)
    ImageView ivFuli;//福利图

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinator;//webView根视图
    @BindView(R.id.toolbar_ivback)
    ImageView ivBack;//关闭
    @BindView(R.id.toolbar_title)
    TextView tvTitle;//标题
    @BindView(R.id.toolbar_ivmore)
    ImageView ivMark;//收藏
    @BindView(R.id.webview)
    WebView webvew;//webView


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        mRxPermissions = new RxPermissions(this);
        DaggerGankDetailComponent.builder()
                .appComponent(appComponent)
                .gankDetailModule(new GankDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_gank_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getData(getIntent());
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void updataView(int viewType, String url) {
        if (viewType == 0) {
            rlGankFuli.setVisibility(View.VISIBLE);
            mPresenter.getImageLoader().
                    loadImage(this,
                            ImageConfigImpl
                                    .builder()
                                    .imageView(ivFuli)
                                    .url(url)
                                    .cacheStrategy(0)
                                    .build()
                    );
        } else {
            coordinator.setVisibility(View.VISIBLE);
//            loadData(url);
            webvew.loadUrl(url);
            tvTitle.setVisibility(View.GONE);
            ivBack.setImageResource(R.mipmap.ic_arrow_back_white_24dp);
            ivMark.setImageResource(R.drawable.ic_menu_camera);
        }
    }

    private void loadData(String url) {
        WebSettings webSettings = webvew.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//加载缓存否则网络
        }

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);//图片自动缩放 打开
        } else {
            webSettings.setLoadsImagesAutomatically(false);//图片自动缩放 关闭
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webvew.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//软件解码
        }
        webvew.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码

//        webSettings.setAllowContentAccess(true);
//        webSettings.setAllowFileAccessFromFileURLs(true);
//        webSettings.setAppCacheEnabled(true);
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }*/


        // setMediaPlaybackRequiresUserGesture(boolean require) //是否需要用户手势来播放Media，默认true

        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportZoom(true);// 设置可以支持缩放
        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false

        webSettings.setDisplayZoomControls(false);//隐藏缩放工具
        webSettings.setUseWideViewPort(true);// 扩大比例的缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDatabaseEnabled(true);//
        webSettings.setSavePassword(true);//保存密码
        webSettings.setDomStorageEnabled(true);//是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webvew.setSaveEnabled(true);
        webvew.setKeepScreenOn(true);
        // 设置setWebChromeClient对象
        webvew.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
            }
        });
        //设置此方法可在WebView中打开链接，反之用浏览器打开
        webvew.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!webvew.getSettings().getLoadsImagesAutomatically()) {
                    webvew.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });
        webvew.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(paramAnonymousString1));
                startActivity(intent);
                killMyself();
            }
        });
        webvew.loadUrl(url);
    }

    @OnClick({R.id.toolbar_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_more:
                //收藏
                ArmsUtils.snackbarText("收藏");
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }


    @Override
    public void killMyself() {
        if (webvew != null) {
            webvew.destroy();
        }
        mPresenter.getImageLoader().clear(this, ImageConfigImpl.builder().imageView(ivFuli).build());
        finish();
    }
}
