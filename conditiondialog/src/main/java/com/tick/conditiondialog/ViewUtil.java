package com.tick.conditiondialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author FengPeng
 * @date 2016/10/18
 */
public class ViewUtil {

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((px * 160) / scale + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dp * (scale / 160) + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param val     sp值
     * @return px值
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param fontScale 上下文
     * @param pxVal     px值
     * @return sp值
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int getStateBarHeight(Context context) {
        int statusBarHeight = dp2px(context, 15);
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取屏幕尺寸信息
     *
     * @param context 上下文
     * @return 屏幕尺寸信息metrics，屏幕宽为metrics.widthPixels, 屏幕高为metrics.heightPixels
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 将视图View直接转化成Bitmap
     *
     * @param view   视图View
     * @param square 是否为正方形
     * @return Bitmap
     */
    public static Bitmap view2Bitmap(View view, boolean square) {
        final int originW = view.getWidth();
        final int originH = view.getHeight();
        int w = originW;
        int h = originH;
        if (square) {
            w = w > h ? w : h;
            h = w;
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        if (square) {
            canvas.drawRGB(255, 255, 255);
            int count = canvas.save();
            float dx = (w - originW) / 2.0f;
            float dy = (h - originH) / 2.0f;
            canvas.translate(dx, dy);
            view.draw(canvas);
            canvas.restoreToCount(count);
        } else {
            view.draw(canvas);
        }
        return bitmap;
    }

    /**
     * 关闭Popwindow
     *
     * @param window Popwindow
     */
    public static void dismiss(PopupWindow window) {
        if (null != window && window.isShowing()) {
            window.dismiss();
        }
    }
}
