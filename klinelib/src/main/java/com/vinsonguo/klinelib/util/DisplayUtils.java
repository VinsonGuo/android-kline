package com.vinsonguo.klinelib.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by guoziwei
 */
public class DisplayUtils {

        public static int [] getWidthHeight(Context context){
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);

            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();

            return new int [] { width, height };
        }

        public static int dip2px(Context context, float dipValue){
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dipValue * scale + 0.5f);
        }

        public static int px2dip(Context context, float pxValue){
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int)(pxValue / scale + 0.5f);
    }



}
