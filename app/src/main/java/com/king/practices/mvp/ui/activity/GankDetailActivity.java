package com.king.practices.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.app.utils.DBManager;
import com.king.practices.di.component.DaggerGankDetailComponent;
import com.king.practices.di.module.GankDetailModule;
import com.king.practices.mvp.contract.GankDetailContract;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.presenter.GankDetailPresenter;
import com.king.practices.mvp.ui.adapter.SwipeCardAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class GankDetailActivity extends BaseActivity<GankDetailPresenter> implements GankDetailContract.View {
    private RxPermissions mRxPermissions;

    @BindView(R.id.ll_gank_fuli)
    LinearLayout llGankFuli;
    @BindView(R.id.iv_left)
    ImageView ivLeft;//左边的按钮
    @BindView(R.id.iv_right)
    ImageView ivRight;//右边的按钮

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinator;//webView根视图
    @BindView(R.id.toolbar_ivback)
    ImageView ivBack;//关闭
    @BindView(R.id.toolbar_title)
    TextView tvTitle;//标题
    @BindView(R.id.toolbar_ivmore)
    ImageView ivMark;//收藏
    WebView webView;//webView
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.container)
    FrameLayout container;
    private SwipeCardAdapter adapter;


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
            llGankFuli.setVisibility(View.VISIBLE);
            updataFuli();
        } else {
            coordinator.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.GONE);
            ivBack.setImageResource(R.mipmap.ic_arrow_back_white_24dp);
            ivMark.setImageResource(R.drawable.ic_menu_camera);
            loadData(url);
        }
    }

    private void updataFuli() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Gank> fuliData = mPresenter.getFuliData();
        if (adapter == null) {
            adapter = new SwipeCardAdapter(R.layout.item_for_swipe_card, fuliData);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setNewData(fuliData);
        }

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), fuliData);
        cardCallback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                if (o instanceof Gank) {
                    Gank gank = (Gank) o;
                    if (direction == CardConfig.SWIPED_LEFT) {
                        //左滑 移除并 加入收藏
                        gank.setCollect(1);
                    } else {
                        //右滑 移除 取消收藏
                        gank.setCollect(0);
                    }
                    DBManager.getGankDao().insertOrReplace(gank);
                }
                Log.d(TAG, "onSwiped: " + o.toString());
                if (adapter.getItemCount() == 3) {
                    //加载更多
                    adapter.addData(mPresenter.getFuliData());
                    Log.d(TAG, "onSwiped: " + "加载更多" + adapter.getItemCount());
                }
            }

            @Override
            public void onSwipedClear() {
                if (adapter.getItemCount() == 0) {
                    Toast.makeText(GankDetailActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadData(String url) {
        webView = new WebView(getApplicationContext());
        container.addView(webView);
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//加载缓存否则网络
        }

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);//图片自动缩放 打开
        } else {
            webSettings.setLoadsImagesAutomatically(false);//图片自动缩放 关闭
        }

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码

        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本]
        webSettings.setSupportZoom(false);// 设置可以支持缩放
        webSettings.setBuiltInZoomControls(false);// 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false

        webSettings.setDisplayZoomControls(true);//隐藏缩放工具
        webSettings.setUseWideViewPort(true);// 扩大比例的缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDatabaseEnabled(true);//
        webSettings.setSavePassword(true);//保存密码
        webSettings.setDomStorageEnabled(false);//是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webView.setSaveEnabled(true);
        webView.setKeepScreenOn(true);

        //设置此方法可在WebView中打开链接，反之用浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(s));
                launchActivity(intent);
                killMyself();
            }
        });
        webView.loadUrl(url);
    }

    @OnClick({R.id.toolbar_more, R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_more:
                //收藏
                ArmsUtils.snackbarText("收藏");
                break;
            case R.id.iv_left:
                ArmsUtils.snackbarText("iv_left");
                break;
            case R.id.iv_right:
                ArmsUtils.snackbarText("iv_right");
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
        ArmsUtils.startActivity(intent);
    }


    @Override
    public void killMyself() {
        if (webView != null) {
            webView.destroy();
        }
        container.removeAllViews();
        finish();
    }


}
