package com.library.flowlayout;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;

/**
 * Created by xiangcheng on 17/6/5.
 */

public class Utils {
    public static void trySetColorForEdgeEffect(EdgeEffect edgeEffect, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edgeEffect.setColor(color);
            return;
        }
        try {
            Field edgeField = EdgeEffect.class.getDeclaredField("mEdge");
            edgeField.setAccessible(true);
            Drawable mEdge = (Drawable) edgeField.get(edgeEffect);
            mEdge.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            mEdge.setCallback(null);
            Field glowField = EdgeEffect.class.getDeclaredField("mGlow");
            glowField.setAccessible(true);
            Drawable mGlow = (Drawable) glowField.get(edgeEffect);
            mGlow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            mGlow.setCallback(null);
        } catch (Exception ignored) {
        }
    }

}
