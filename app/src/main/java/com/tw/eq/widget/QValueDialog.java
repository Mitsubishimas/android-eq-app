package com.tw.eq.widget;

import android.view.View;

public final class QValueDialog extends View {
    private static final Object a = new Object();
    private boolean b;

    public interface onQValueListener {
        void setQValue(int i);
    }

    public QValueDialog(android.content.Context context) {
        super(context);
    }

    public final void setDisplay(boolean display) {
        this.b = display;
    }
}
