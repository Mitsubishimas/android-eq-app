package com.tw.eq.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class DounbleThumbSeekbar extends View {
    public Drawable a;
    public Drawable b;
    public Drawable c;
    public Drawable d;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(DounbleThumbSeekbar seekBar, double d, double d2, boolean z);
    }

    public DounbleThumbSeekbar(Context context) {
        super(context);
    }

    public DounbleThumbSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {}
    public void setProgressLow(int progress) {}
    public void setProgressHigh(int progress) {}
    public void setEditable(boolean editable) {}
    public void setTextColor(int color) {}
}
