package com.tw.eq.widget;

import android.view.View;

public final class DounbleThumbHSeekbar extends View {
    public interface OnSeekBarChangeListener {
        void onProgressChanged(DounbleThumbHSeekbar seekBar, double d, double d2);
    }

    public DounbleThumbHSeekbar(android.content.Context context) {
        super(context);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {}
    public void setProgressLow(int progress) {}
    public void setProgressHigh(double progress) {}
    public void setEditable(boolean editable) {}
}
