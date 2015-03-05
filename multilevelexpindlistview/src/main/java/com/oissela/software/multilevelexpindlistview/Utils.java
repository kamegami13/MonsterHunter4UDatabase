package com.oissela.software.multilevelexpindlistview;

import android.content.Context;

public class Utils {
    /**
     * Converting dp units to pixel units
     * http://developer.android.com/guide/practices/screens_support.html#dips-pels
     */
    public static int getPaddingPixels(Context context, int dpValue) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dpValue * scale + 0.5f);
    }
}
