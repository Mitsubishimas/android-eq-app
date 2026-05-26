package com.tw.eq.widget;
import android.view.View;
import android.view.MotionEvent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.tw.eq.a;

public class ZoneView extends View {
    private int maxX;
    private int maxY;
    private int currentX;
    private int currentY;
    private Bitmap speakerBitmap;
    private OnZoneChangeListener zoneChangeListener;
    private boolean isDragging;

    public interface OnZoneChangeListener {
        void onStartTrackingTouch(ZoneView zoneView);
        void onStopTrackingTouch(ZoneView zoneView, boolean changed);
    }

    public ZoneView(Context context) {
        this(context, null);
    }

    public ZoneView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoneView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.maxX = 9;
        this.maxY = 9;
        this.currentX = 9;
        this.currentY = 9;
        
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, a.C0003a.EqFadingView, defStyle, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == 8) {
                this.speakerBitmap = drawableToBitmap(typedArray.getDrawable(index));
            }
        }
        typedArray.recycle();
        setClickable(true);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
            drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public final void a(int x, int y) {
        this.isDragging = false;
        if (this.speakerBitmap == null) return;
        
        x = Math.max(0, x);
        y = Math.max(0, y);
        
        int newX = x - this.maxX;
        if (newX > this.maxX) newX = this.maxX;
        if (newX != (((this.currentX * 2) - getWidth()) * this.maxX) / (getWidth() - this.speakerBitmap.getWidth())) {
            this.currentX = (((newX * (getWidth() - this.speakerBitmap.getWidth())) / this.maxX) + getWidth()) / 2;
        }
        
        int newY = y - this.maxY;
        if (newY > this.maxY) newY = this.maxY;
        if (newY != (((this.currentY * 2) - getHeight()) * this.maxY) / (getHeight() - this.speakerBitmap.getHeight())) {
            this.currentY = (((newY * (getHeight() - this.speakerBitmap.getHeight())) / this.maxY) + getHeight()) / 2;
        }
        
        invalidate();
    }

    public int getMax() { return this.maxX; }

    public int getx() {
        return ((((this.currentX * 2) - getWidth()) * this.maxX) / (getWidth() - this.speakerBitmap.getWidth())) + this.maxX;
    }

    public int gety() {
        return ((((this.currentY * 2) - getHeight()) * this.maxY) / (getHeight() - this.speakerBitmap.getHeight())) + this.maxY;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.speakerBitmap != null) {
            this.speakerBitmap.recycle();
            this.speakerBitmap = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.speakerBitmap != null) {
            canvas.drawBitmap(this.speakerBitmap, 
                this.currentX - (this.speakerBitmap.getWidth() / 2),
                this.currentY - (this.speakerBitmap.getHeight() / 2), 
                new Paint());
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.isDragging = true;
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (this.zoneChangeListener != null) {
                    this.zoneChangeListener.onStopTrackingTouch(this, this.isDragging);
                }
                break;
                
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                
                x = Math.max(this.speakerBitmap.getWidth() / 2, 
                    Math.min(x, getWidth() - (this.speakerBitmap.getWidth() / 2)));
                y = Math.max(this.speakerBitmap.getHeight() / 2,
                    Math.min(y, getHeight() - (this.speakerBitmap.getHeight() / 2)));
                
                this.currentX = x;
                this.currentY = y;
                invalidate();
                
                if (this.zoneChangeListener != null) {
                    this.zoneChangeListener.onStartTrackingTouch(this);
                }
                break;
        }
        return true;
    }

    public void setOnZoneChangeListener(OnZoneChangeListener listener) {
        this.zoneChangeListener = listener;
    }

    public void setSpeakerDrawable(Drawable drawable) {
        this.speakerBitmap = drawableToBitmap(drawable);
    }
}
