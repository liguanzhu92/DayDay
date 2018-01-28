package com.guanzhuli.dayday.customized;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.utils.Constant;
import com.guanzhuli.dayday.utils.DisplayUtility;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/25/2018.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<String> imagesList;
    private int gridItemWidth;
    private OnImageClickListener onImageClickListener;


    public ImageGalleryAdapter(Context context, List<String> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
        int screenWidth = DisplayUtility.getScreenWidth(context);
        gridItemWidth = screenWidth / Constant.COLUMN_NUMBER;
    }

    // region Interfaces
    public interface OnImageClickListener {
        void onImageClick(int position);
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_gallery, parent, false);
        view.setLayoutParams(getGridItemLayoutParams(view));
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            final ImageViewHolder viewHolder = (ImageViewHolder)holder;
            String image = imagesList.get(position);
            viewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(image));
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = viewHolder.getAdapterPosition();
                    if(adapterPos != RecyclerView.NO_POSITION){
                        if (onImageClickListener != null) {
                            onImageClickListener.onImageClick(adapterPos);
                        }
                    }
                }
            });
        }
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public int getItemCount() {
        if (imagesList != null) {
            return imagesList.size();
        } else {
            return 0;
        }
    }

    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.width = gridItemWidth;
        layoutParams.height = gridItemWidth;

        return layoutParams;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_gallery_display);
        }
    }
}
