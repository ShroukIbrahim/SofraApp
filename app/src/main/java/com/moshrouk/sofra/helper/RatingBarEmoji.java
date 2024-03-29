package com.moshrouk.sofra.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.moshrouk.sofra.R;

public class RatingBarEmoji extends AppCompatRatingBar {

    private int[] iconArrayActive =  {
            R.drawable.ic_sentiment_very_dissatisfied_black_24dp,
            R.drawable.ic_sentiment_dissatisfied_black_24dp,
            R.drawable.ic_sentiment_neutral_black_24dp,
            R.drawable.ic_sentiment_satisfied_black_24dp,
            R.drawable.ic_sentiment_very_satisfied_black_24dp
    };

    private int[] iconArrayInactive =  {
            R.drawable.ic_sentiment_very_dissatisfied_black1_24dp,
            R.drawable.ic_sentiment_dissatisfied_black1_24dp,
            R.drawable.ic_sentiment_neutral_black1_24dp,
            R.drawable.ic_sentiment_satisfied_black1_24dp,
            R.drawable.ic_sentiment_very_satisfied_black1_24dp
    };


    public RatingBarEmoji(Context context) {
        super(context);
        init();
    }

    public RatingBarEmoji(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingBarEmoji(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setMax(5);
        this.setNumStars(5);
        this.setStepSize(1.0f);
        this.setRating(1.0f);
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int stars = getNumStars();
        float rating = getRating();
        float x = 0;

        Bitmap bitmap;
        Resources res = getResources();
        Paint paint = new Paint();

        int W = getWidth();
        int H = getHeight();
        int icon_size = (W/stars)-32;//21 //(H < W)?(H):(W); //72
        if (icon_size > H-16) {
            icon_size = H-16;
        }
        int emoji_y_pos = (H/2)-icon_size/2;

        int delta = ((H > W)?(H):(W))/(stars);
        int offset = (W-(icon_size+(stars-1)*delta))/2;

        for(int i = 0; i < stars; i++) {
            if ((int) rating == i) {
                bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayActive[i]);
            } else {
                bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayInactive[i]);
            }
            x = offset+(i*delta);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, icon_size, icon_size, true);
            canvas.drawBitmap(scaled, x, emoji_y_pos, paint);
            canvas.save();
        }
    }


}