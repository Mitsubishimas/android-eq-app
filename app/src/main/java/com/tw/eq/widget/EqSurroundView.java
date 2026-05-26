package com.tw.eq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.tw.eq.a;
import java.math.BigDecimal;

public class EqSurroundView extends View {
    public Bitmap b;
    public Bitmap c;
    public Bitmap d;
    public Bitmap e;
    public Bitmap f;
    private Drawable i;
    private Drawable j;
    private Drawable k;
    private OnValueChangeListener n;
    private final Paint o;
    private final PaintFlagsDrawFilter p;
    private int q;
    private int r;
    private int t;
    private int v;
    private float w;
    private float x;
    private float y;
    private float z;
    private int A;
    private float B;
    private float C;
    private int E;
    private int F;
    public int[] a = new int[5];
    private int g = 0;
    private int h = 0;
    private int l = 0;
    private int m = 0;
    private int s = 100;
    private int u = 100;

    public interface OnValueChangeListener {
        void onHornDelayChanged(int i, int i2, int i3, int i4, boolean z);
        void onValueChanged(int i, int i2, boolean z);
    }

    public EqSurroundView(Context context) {
        this(context, null);
    }

    public EqSurroundView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EqSurroundView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, a.C0003a.EqFadingView, defStyle, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArray.getIndex(i2);
            switch (index) {
                case 0: this.b = a(typedArray.getDrawable(index)); break;
                case 1: this.c = a(typedArray.getDrawable(index)); break;
                case 2: this.f = a(typedArray.getDrawable(index)); break;
                case 3: this.d = a(typedArray.getDrawable(index)); break;
                case 4: this.e = a(typedArray.getDrawable(index)); break;
                case 5: this.j = typedArray.getDrawable(index); break;
                case 6: this.g = typedArray.getInt(index, 0); break;
                case 7: this.A = 10; this.C = 8.0f; this.B = 10.0f; break;
                case 8: this.i = typedArray.getDrawable(index); break;
                case 9: this.k = typedArray.getDrawable(index); break;
                case 10: this.h = typedArray.getInt(index, 0); break;
            }
        }
        typedArray.recycle();
        
        setClickable(true);
        this.o = new Paint();
        this.o.setAntiAlias(true);
        this.o.setColor(-1);
        this.o.setStyle(Paint.Style.STROKE);
        this.p = new PaintFlagsDrawFilter(0, 2);
        this.s = this.i != null ? this.i.getIntrinsicWidth() : 100;
        this.u = this.i != null ? this.i.getIntrinsicHeight() : 100;
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable == null) return null;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, 
            drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private void updateAngles() {
        this.w = calculateAngle(this.F, this.E);
        this.x = calculateAngle(100 - this.F, this.E);
        this.y = calculateAngle(this.F, Math.abs(66) - this.E);
        this.z = calculateAngle(100 - this.F, Math.abs(66) - this.E);
    }

    private float calculateAngle(int x, int y) {
        double angle = Math.atan2(y, x);
        if (angle < 0) angle += 6.283185307179586d;
        return Math.round(Math.toDegrees(angle));
    }

    private void updatePosition(MotionEvent event) {
        int width = getWidth();
        int height = getHeight();
        int maxX = ((width - getPaddingLeft()) - getPaddingRight()) - this.s;
        int maxY = ((height - getPaddingTop()) - getPaddingBottom()) - this.u;
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        float ratioX = x <= getPaddingLeft() + (this.s / 2) ? 0.0f : 
                       x >= (width - getPaddingRight()) - (this.s / 2) ? 1.0f : 
                       ((x - getPaddingLeft()) - (this.u / 2)) / maxX;
        float ratioY = y <= getPaddingTop() + (this.u / 2) ? 0.0f : 
                       y >= (height - getPaddingBottom()) - (this.u / 2) ? 1.0f : 
                       ((y - getPaddingTop()) - (this.u / 2)) / maxY;
        
        float newX = (float) new BigDecimal(Float.toString(ratioX)).setScale(3, 4).doubleValue();
        float newY = (float) new BigDecimal(Float.toString(ratioY)).setScale(3, 4).doubleValue();
        
        this.g = (int) (this.C * newX);
        this.h = (int) (this.B * newY);
        this.F = (int) (newX * 100.0f);
        this.E = (int) (newY * 100.0f);
        
        updateAngles();
        invalidate();
        
        if ((this.l != this.g || this.m != this.h) && this.n != null) {
            this.n.onValueChanged(this.F, this.E, true);
        }
        this.l = this.g;
        this.m = this.h;
    }

    public final void a(int x, int y) {
        float ratioX = x / 100.0f;
        float ratioY = y / 100.0f;
        int maxX = ((this.q - getPaddingLeft()) - getPaddingRight()) - this.s;
        int maxY = ((this.r - getPaddingTop()) - getPaddingBottom()) - this.u;
        this.t = getPaddingLeft() + (int)(maxX * ratioX) + (this.s / 2);
        this.v = getPaddingTop() + (int)(maxY * ratioY) + (this.u / 2);
        this.F = x;
        this.E = y;
        this.g = (int)(ratioX * this.C);
        this.h = (int)(ratioY * this.B);
        updateAngles();
        postInvalidate();
        if (this.n != null) {
            this.n.onValueChanged(x, y, false);
        }
    }

    public int getMax() { return this.A; }
    public int getZoneMax() { return 100; }
    public int getZoneX() { return 100; }
    public int getZoneY() { return 100; }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.p);
        if (this.b != null) {
            canvas.save();
            canvas.rotate(this.w - 45.0f, this.b.getWidth() / 2, this.b.getHeight() / 2);
            canvas.drawBitmap(this.b, 0, 0, this.o);
            canvas.restore();
        }
        if (this.c != null) {
            canvas.save();
            canvas.rotate(45.0f - this.x, this.q - (this.c.getWidth() / 2), this.c.getHeight() / 2);
            canvas.drawBitmap(this.c, this.q - this.c.getWidth(), 0, this.o);
            canvas.restore();
        }
        if (this.d != null) {
            canvas.save();
            canvas.rotate(45.0f - this.y, this.d.getWidth() / 2, (this.r * 2) / 3);
            canvas.drawBitmap(this.d, 0, ((this.r * 2) / 3) - (this.d.getWidth() / 2), this.o);
            canvas.restore();
        }
        if (this.e != null) {
            canvas.save();
            canvas.rotate(this.z - 45.0f, this.q - (this.e.getWidth() / 2), (this.r * 2) / 3);
            canvas.drawBitmap(this.e, this.q - this.c.getWidth(), ((this.r * 2) / 3) - (this.d.getWidth() / 2), this.o);
            canvas.restore();
        }
        if (this.f != null) {
            canvas.save();
            canvas.drawBitmap(this.f, (this.q / 2) - (this.f.getWidth() / 2), this.r - this.f.getWidth(), this.o);
            canvas.restore();
        }
        if (this.i != null) {
            int left = this.t - (this.s / 2);
            int top = this.v - (this.u / 2);
            this.i.setBounds(left, top, this.s + left, this.u + top);
            this.i.draw(canvas);
            if (this.j != null) {
                int centerY = top + (this.u / 2);
                this.j.setBounds(getPaddingLeft(), centerY, getPaddingLeft() + this.j.getIntrinsicWidth(), centerY + this.j.getIntrinsicHeight());
                this.j.draw(canvas);
            }
            if (this.k != null) {
                int centerX = left + (this.s / 2);
                this.k.setBounds(centerX, getPaddingTop(), centerX + this.k.getIntrinsicWidth(), getPaddingTop() + this.k.getIntrinsicHeight());
                this.k.draw(canvas);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.q = getPaddingLeft() + getPaddingRight();
        this.r = getPaddingTop() + getPaddingBottom();
        this.q = Math.max(this.q, getSuggestedMinimumWidth());
        this.r = Math.max(this.r, getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSizeAndState(this.q, widthMeasureSpec, 0), 
                            resolveSizeAndState(this.r, heightMeasureSpec, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        a(this.F, this.E);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        x = Math.max(getPaddingLeft() + (this.s / 2), Math.min(x, (getWidth() - getPaddingRight()) - (this.s / 2)));
        y = Math.max(getPaddingTop() + (this.u / 2), Math.min(y, (getHeight() - getPaddingBottom()) - (this.u / 2)));
        this.t = x;
        this.v = y;
        
        switch (event.getAction() & 0xFF) {
            case 0:
                this.l = this.g;
                this.m = this.h;
                break;
            case 1:
                updatePosition(event);
                int distance = (int) Math.sqrt((this.g * this.g) + (this.h * this.h));
                int distance2 = (int) Math.sqrt(((this.C - this.g) * (this.C - this.g)) + (this.h * this.h));
                int distance3 = (int) Math.sqrt((this.g * this.g) + ((((this.B * 2.0f) / 3.0f) - this.h) * (((this.B * 2.0f) / 3.0f) - this.h)));
                int distance4 = (int) Math.sqrt(((this.C - this.g) * (this.C - this.g)) + ((((this.B * 2.0f) / 3.0f) - this.h) * (((this.B * 2.0f) / 3.0f) - this.h)));
                int max = Math.max(Math.max(distance, distance2), Math.max(distance3, distance4));
                if (this.n != null) {
                    this.n.onHornDelayChanged(max - distance, max - distance2, max - distance3, max - distance4, true);
                }
                return true;
            case 2:
                updatePosition(event);
                return true;
            case 3:
                this.g = this.l;
                this.h = this.m;
                break;
        }
        invalidate();
        return true;
    }

    public void setOnFadingValueChangeListener(OnValueChangeListener listener) { this.n = listener; }
    public void setSpeakerDrawable(Drawable drawable) { this.i = drawable; }
    public void setBackground(Drawable drawable) { super.setBackground(drawable); }
}
