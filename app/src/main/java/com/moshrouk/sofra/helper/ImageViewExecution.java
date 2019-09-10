package com.moshrouk.sofra.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class ImageViewExecution  {
    public static void LodeImage(Context context, String url, ImageView imageView,int placeholder ) {

        Glide.with(context)
                .load("imgPath")
                .centerCrop()
                .placeholder(placeholder)
                .error(placeholder)
                .fallback(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
