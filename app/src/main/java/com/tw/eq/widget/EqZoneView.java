package com.tw.eq.widget;

import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public final class EqZoneView extends View {
    private static int c = 86;
    private static int d = 86;
    private int a;
    private int b;
    private int e;
    private int f;
    private double g;
    private double h;
    private double i;
    private double j;
    private int k;
    private Drawable l;
    private Drawable m;
    private Drawable n;
    private Drawable o;
    private Drawable p;
    private PaintFlagsDrawFilter q;
    private float r;
    private float s;
    private float t;
    private float u;
    private final float[] v = new float[18];
    private final float[] w = new float[18];
    private OnFadingValueChangeListener x;

    public interface OnFadingValueChangeListener {
        void onFadingValueChanged(int i, int i2, boolean z);
    }

    public EqZoneView(android.content.Context context) {
        super(context);
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.q);
        this.g = 45.0d;
        this.h = 180.0d;
        this.i = 0.0d;
        this.j = 180.0d;
        canvas.save();
        canvas.translate(5.0f, (-this.o.getIntrinsicHeight()) / 3);
        canvas.rotate((float) this.g, 15.0f, this.o.getIntrinsicHeight() / 2);
        this.m.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.rotate((float) this.h, this.b - (this.n.getIntrinsicWidth() * 0.8f), this.n.getIntrinsicHeight() / 2);
        canvas.translate(((-this.n.getIntrinsicWidth()) / 2) - 10, this.n.getIntrinsicHeight() / 3.0f);
        canvas.rotate(-45.0f, this.b - (this.n.getIntrinsicWidth() * 0.8f), this.n.getIntrinsicHeight() / 4.0f);
        this.n.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.rotate((float) this.i, 16.0f, this.k + (this.o.getIntrinsicHeight() / 2));
        this.o.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.rotate((float) this.j, this.b - (this.o.getIntrinsicHeight() / 2), this.k + (this.o.getIntrinsicHeight() / 2));
        this.p.draw(canvas);
        canvas.restore();
    }

    @Override
    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.a = h;
        this.b = w;
        this.t = (this.b - this.l.getIntrinsicWidth()) / 18.0f;
        this.u = (this.a - this.l.getIntrinsicHeight()) / 18.0f;
        for (int i = 0; i < this.v.length; i++) {
            this.v[i] = (this.l.getIntrinsicWidth() / 2.0f) + (i * this.t);
        }
        for (int i = 0; i < this.w.length; i++) {
            this.w[i] = (this.l.getIntrinsicHeight() / 2.0f) + (i * this.u);
        }
        this.l.setBounds((this.b / 2) - (this.l.getIntrinsicWidth() / 2), 
                        (this.a / 2) - (this.l.getIntrinsicHeight() / 2),
                        (this.l.getIntrinsicWidth() / 2) + (this.b / 2),
                        (this.a / 2) + (this.l.getIntrinsicHeight() / 2));
        this.m.setBounds(0, 0, this.m.getIntrinsicWidth(), this.m.getIntrinsicHeight());
        this.n.setBounds(this.b - this.n.getIntrinsicWidth(), 0, this.b, this.n.getIntrinsicHeight());
        this.o.setBounds(0, this.k, this.m.getIntrinsicWidth(), this.m.getIntrinsicHeight() + this.k);
        this.p.setBounds(this.b - this.n.getIntrinsicWidth(), this.k, this.b, this.m.getIntrinsicHeight() + this.k);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        this.r = event.getX();
        this.s = event.getY();
        if (event.getAction() == 2) {
            this.e = (int) this.s;
            this.f = (int) this.r;
            this.f = Math.max(this.l.getIntrinsicWidth() / 2, 
                             Math.min(this.f, this.b - (this.l.getIntrinsicWidth() / 2)));
            this.e = Math.max(this.l.getIntrinsicHeight() / 2,
                             Math.min(this.e, (this.a - (this.l.getIntrinsicHeight() / 2)) - 1));
            this.g = (Math.atan2(this.r, this.s) * 180.0d) / Math.PI;
            this.h = (Math.atan2(this.b - this.r, this.s) * 180.0d) / Math.PI;
            this.i = (Math.atan2(this.r, this.k - this.s) * 180.0d) / Math.PI;
            this.j = (Math.atan2(this.b - this.r, this.k - this.s) * 180.0d) / Math.PI;
            this.l.setBounds(this.f - (this.l.getIntrinsicWidth() / 2),
                            this.e - (this.l.getIntrinsicHeight() / 2),
                            this.f + (this.l.getIntrinsicWidth() / 2),
                            this.e + (this.l.getIntrinsicHeight() / 2));
        }
        return true;
    }

    public final void setOnFadingValueChangeListener(OnFadingValueChangeListener listener) {
        this.x = listener;
    }
}
