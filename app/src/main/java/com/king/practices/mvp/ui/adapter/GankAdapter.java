package com.king.practices.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.practices.R;
import com.king.practices.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public class GankAdapter extends BaseMultiItemQuickAdapter<Gank, BaseViewHolder> {
    private static final int ANDROID = 0;
    private static final int IOS = 1;
    private static final int WEB = 2;
    private static final int FULI = 3;
    private static final int VIDEO = 4;
    private static final int EXTEND = 5;

    public GankAdapter(List<Gank> data) {
        super(data);
//        imageLoader = ((App) mContext).getAppComponent().imageLoader();
        addItemType(ANDROID, R.layout.item_for_gank);
        addItemType(IOS, R.layout.item_for_gank);
        addItemType(WEB, R.layout.item_for_gank);
        addItemType(FULI, R.layout.item_for_gank_image);
        addItemType(VIDEO, R.layout.item_for_gank);
    }

    @Override
    protected void convert(BaseViewHolder holder, Gank item) {
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
                Glide.with(mContext)
                        .asBitmap()
                        .load(item.getUrl())
                        .into((ImageView) holder.getView(R.id.iv_item));
                break;
            default:
        }
    }
}
