package com.avinash.popularmoviesproject.extras;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

/**
 * Created by AVINASH on 23-05-2017.
 */

public class Utils {

    public static void colorise(Context context, @ColorRes int colorId, ImageView... ivs){
        int color = ContextCompat.getColor(context, colorId);
        for(ImageView iv:ivs){
            iv.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

    }
}
