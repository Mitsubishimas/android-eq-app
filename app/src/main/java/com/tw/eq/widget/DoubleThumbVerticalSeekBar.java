package com.tw.eq.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public final class DoubleThumbVerticalSeekBar extends View {
    public interface OnSeekBarChangeListener {
        void onStopTrackingTouch(DoubleThumbVerticalSeekBar seekBar, int i, int i2, boolean z);
        void onStopTrackingTouch(DoubleThumbVerticalSeekBar seekBar, int i, boolean z);
    }

    // Simplified implementation - full version would be very large
    // This is a placeholder for compilation
    public DoubleThumbVerticalSeekBar(android.content.Context context) {
        super(context);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {}
    public void setThumb1Progress(int progress) {}
    public void setThumb2Progress(int progress) {}
    public void setThumbProgress(int progress) {}
}
