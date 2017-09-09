package com.king.practices.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.king.practices.R;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.ui.holder.GankViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public class GankAdapter extends BaseMultiItemQuickAdapter<Gank, GankViewHolder> {
    private static final int ANDROID = 0;
    private static final int IOS = 1;
    private static final int WEB = 2;
    private static final int FULI = 3;
    private static final int VIDEO = 4;
    private static final int EXTEND = 5;


    public GankAdapter(List<Gank> data) {
        super(data);
        addItemType(ANDROID, R.layout.item_for_gank);
        addItemType(IOS, R.layout.item_for_gank);
        addItemType(WEB, R.layout.item_for_gank);
        addItemType(FULI, R.layout.item_for_gank_image);
        addItemType(VIDEO, R.layout.item_for_gank);
        addItemType(EXTEND, R.layout.item_for_gank);
    }

    @Override
    protected void convert(GankViewHolder holder, Gank item) {
        switch (item.getItemType()) {
            case ANDROID:
                holder.setImageResource(R.id.iv_item, R.drawable.ic_item_android);
                holder.setText(R.id.tv_item, item.getDesc());
                break;
            case IOS:
                holder.setImageResource(R.id.iv_item, R.drawable.ic_item_ios);
                holder.setText(R.id.tv_item, item.getDesc());
                break;
            case WEB:
                holder.setImageResource(R.id.iv_item, R.drawable.ic_item_web);
                holder.setText(R.id.tv_item, item.getDesc());
                break;
            case VIDEO:
                holder.setImageResource(R.id.iv_item, R.drawable.ic_item_video);
                holder.setText(R.id.tv_item, item.getDesc());
                break;
            case EXTEND:
                holder.setImageResource(R.id.iv_item, R.drawable.ic_item_web);
                holder.setText(R.id.tv_item, item.getDesc());
                break;
            case FULI:
                holder.mImageLoader.loadImage(holder.appManager.getCurrentActivity() == null ? holder.mAppComponent.application() : holder.appManager.getCurrentActivity(),
                        ImageConfigImpl
                                .builder()
                                .url(item.getUrl())
                                .cacheStrategy(3)
                                .imageView((ImageView) holder.getView(R.id.iv_item))
                                .build()
                );
                break;
            default:
        }
    }
}
