package com.renkun.mnpic.util;

import android.util.Log;

import com.renkun.mnpic.R;

/**
 * Created by rk on 2015/10/20.
 */
public class ColorUtil {
    public static final int[] colors = {
            R.color.color_0, R.color.color_1,
            R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5,
            R.color.color_6, R.color.color_7,
            R.color.color_8, R.color.color_9,
            R.color.color_10, R.color.color_11,
            R.color.color_12, R.color.color_13,
            R.color.color_14, R.color.color_15};
    public static int getColor(){
         int i= (int) (Math.random() * 16);

         return colors[i];

    }
}
