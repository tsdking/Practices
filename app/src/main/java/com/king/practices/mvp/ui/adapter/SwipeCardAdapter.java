package com.king.practices.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.king.practices.R;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.ui.holder.SwipeCardViewHolder;

import java.util.List;


public class SwipeCardAdapter extends BaseQuickAdapter<Gank, SwipeCardViewHolder> {
    public SwipeCardAdapter(@LayoutRes int layoutResId, @Nullable List<Gank> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SwipeCardViewHolder holder, Gank item) {
        holder.mImageLoader.loadImage(holder.appManager.getCurrentActivity() == null ? holder.mAppComponent.application() : holder.appManager.getCurrentActivity(),
                ImageConfigImpl
                        .builder()
                        .url(item.getUrl())
                        .cacheStrategy(3)
                        .imageView((ImageView) holder.getView(R.id.iv_item))
                        .build()
        );
    }
}
