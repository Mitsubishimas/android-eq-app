package com.tw.eq.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public final class VerticalSeekBar2 extends View {
    private Paint a;
    private Paint b;
    private Drawable c;
    private PaintFlagsDrawFilter d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private final float[] k = new float[25];
    private float l;
    private float m;
    private int n;
    private int o;
    private OnSeekBarChangeListener p;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(VerticalSeekBar2 seekBar, int progress, boolean fromUser);
        void onStartTrackingTouch(VerticalSeekBar2 seekBar, int progress);
        void onStopTrackingTouch(VerticalSeekBar2 seekBar, int progress);
    }

    public VerticalSeekBar2(android.content.Context context) {
        super(context);
    }

    private int getProgressFromY(float y) {
        for (int i = 0; i < this.k.length; i++) {
            if (i == this.k.length - 2) {
                this.k[i] = this.c.getIntrinsicHeight() + 6 + ((((this.h - this.c.getIntrinsicHeight()) - 6) * i) / 24) + ((((this.h - this.c.getIntrinsicHeight()) - 6) / 24) * 0.8f);
            }
            if (y <= this.k[i]) {
                return (this.k.length - 1) - i;
            }
        }
        return 0;
    }

    private int getThumbY() {
        return ((((this.k.length - 1) - this.o) * ((this.h - this.c.getIntrinsicHeight()) - 6)) / 24) + this.c.getIntrinsicHeight() + 6;
    }

    public final int getProgress() { return this.o; }

    @Override
    protected final void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.d);
        RectF bgRect = new RectF(this.e, this.f, this.g, this.h);
        RectF progressRect = new RectF(this.e, getThumbY() - (this.c.getIntrinsicHeight() / 2), this.g, this.h);
        canvas.drawRoundRect(bgRect, 10.0f, 10.0f, this.a);
        canvas.drawRoundRect(progressRect, 10.0f, 10.0f, this.b);
        this.c.draw(canvas);
    }

    @Override
    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.i = h;
        this.j = w;
        this.e = (w - 6) / 2;
        this.f = 6;
        this.g = this.e + 6;
        this.h = this.i - 4;
        this.c.setBounds(0, this.h - this.c.getIntrinsicHeight(), this.c.getIntrinsicWidth(), this.h);
        for (int i = 0; i < this.k.length; i++) {
            this.k[i] = this.c.getIntrinsicHeight() + 6 + ((((this.h - this.c.getIntrinsicHeight()) - 6) * i) / 24);
        }
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        this.l = event.getX();
        this.m = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (this.c.getBounds().contains((int) event.getX(), (int) event.getY()) && this.p != null) {
                    this.p.onStopTrackingTouch(this, this.o);
                }
                this.n = this.n >= 203 ? 236 : this.n >= 134 ? 170 : this.n >= 65 ? 98 : 33;
                this.c.setBounds(0, this.n - this.c.getIntrinsicHeight(), this.c.getIntrinsicWidth(), this.n);
                this.o = getProgressFromY(this.n);
                break;
            case MotionEvent.ACTION_MOVE:
                this.n = (int) this.m;
                this.n = Math.max(this.c.getIntrinsicHeight() + 6, Math.min(this.n, this.i - 4));
                this.o = getProgressFromY(this.n);
                this.c.setBounds(0, this.n - this.c.getIntrinsicHeight(), this.c.getIntrinsicWidth(), this.n);
                break;
        }
        invalidate();
        return true;
    }

    public final void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) { this.p = listener; }
    public final void setProgress(int progress) { this.o = progress; invalidate(); }
    public void setThumb(Drawable thumb) { this.c = thumb; }
    public void setProgressDrawableTiled(Drawable drawable) {}
    public void setBackground(Drawable background) { super.setBackground(background); }
    public void setThumbOffset(int offset) {}
}
