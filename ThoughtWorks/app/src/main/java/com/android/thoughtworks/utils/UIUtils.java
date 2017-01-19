package com.android.thoughtworks.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by akanksha on 8/1/17.
 */
public class UIUtils {

    public  static Drawable tintImage(Drawable drawable, int color) {
        PorterDuff.Mode mode = PorterDuff.Mode.OVERLAY;
        drawable.mutate().setColorFilter(color, mode);
        return drawable;
    }

}
