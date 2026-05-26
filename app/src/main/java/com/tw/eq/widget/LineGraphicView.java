package com.tw.eq.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;

public class LineGraphicView extends View {
    public Paint a;
    public int b;
    public int c;
    public ArrayList<Integer> d;
    public final ArrayList<Integer> e;
    private final Context f;
    private Resources g;
    private DisplayMetrics h;
    private final int i;
    private int j;
    private int k;
    private int l;
    private boolean m;
    private final int n;
    private Point[] o;

    private static final class a {
        public static final int a = 1;
        public static final int b = 2;
        private static final int[] c = {a, b};
    }

    public LineGraphicView(Context context) {
        this(context, null);
    }

    public LineGraphicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.i = a.b;
        this.l = 20;
        this.m = true;
        this.n = 100;
        this.d = new ArrayList<>();
        this.e = new ArrayList<>();
        this.f = context;
        this.g = this.f.getResources();
        this.a = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.a.setColor(-16776961);
        this.h = new DisplayMetrics();
        ((WindowManager) this.f.getSystemService("window")).getDefaultDisplay().getMetrics(this.h);
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i < this.o.length - 1; i++) {
            canvas.drawLine(this.o[i].x, this.o[i].y, this.o[i + 1].x, this.o[i + 1].y, this.a);
        }
    }

    private Point[] getPoints() {
        if (this.o == null) {
            this.o = new Point[this.d.size()];
            for (int i = 0; i < this.o.length; i++) {
                this.o[i] = new Point(this.e.get(i), this.l);
            }
        }
        if (this.o.length == 0) {
            this.o = new Point[this.d.size()];
            for (int i = 0; i < this.o.length; i++) {
                this.o[i] = new Point(this.e.get(i), this.l);
            }
        }
        for (int i = 0; i < this.d.size(); i++) {
            this.l = this.j - this.d.get(i);
            if (i < this.o.length && i < this.e.size()) {
                this.o[i].x = this.e.get(i);
                this.o[i].y = this.l;
            }
        }
        return this.o;
    }

    public ArrayList<Integer> getDate() {
        return this.d;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.o = getPoints();
        this.a.setStrokeWidth((int) ((this.h.density * 2.0f) + 0.5f));
        this.a.setStyle(Paint.Style.STROKE);
        if (this.i == a.b) {
            for (int i = 0; i < this.o.length - 1; i++) {
                Point p1 = this.o[i];
                Point p2 = this.o[i + 1];
                int midX = (p1.x + p2.x) / 2;
                Point cp1 = new Point(midX, p1.y);
                Point cp2 = new Point(midX, p2.y);
                Path path = new Path();
                path.moveTo(p1.x, p1.y);
                path.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, p2.x, p2.y);
                canvas.drawPath(path, this.a);
            }
        } else {
            drawLines(canvas);
        }
        this.a.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.b = getWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (this.m) {
            this.j = getHeight();
            this.k = getWidth();
            this.m = false;
        }
    }

    public void setItemChange(ArrayList<Integer> data) {
        this.d = data;
        invalidate();
    }
}
