package com.tw.eq.widget;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.HorizontalScrollView;

public final class CustomHorizontalScrollView extends HorizontalScrollView {
    private boolean a;
    private ScrollViewListener b;

    public interface ScrollViewListener {
        void onScrollChanged(int i, boolean z);
    }

    @Override
    public final void fling(int i) {
        super.fling(i / 1000);
    }

    @Override
    protected final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    @Override
    public final ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    @Override
    protected final void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.b != null) {
            this.b.onScrollChanged(i, this.a);
        }
    }

    @Override
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.a = true;
                break;
            case 1:
                this.a = false;
                break;
            default:
                return super.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.b = scrollViewListener;
    }
}
