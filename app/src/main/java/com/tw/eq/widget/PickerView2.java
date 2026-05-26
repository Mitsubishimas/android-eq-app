package com.tw.eq.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public final class PickerView2 extends View {
    Handler a;
    private List<String> b;
    private int c;
    private Paint d;
    private Paint e;
    private float f;
    private int g;
    private int h;
    private float i;
    private float j;
    private boolean k;
    private onSelectListener l;
    private Timer m;
    private a n;
    private boolean o;
    private String p;
    private int q;

    class a extends TimerTask {
        Handler a;

        public a(Handler handler) {
            this.a = handler;
        }

        @Override
        public void run() {
            this.a.sendMessage(this.a.obtainMessage());
        }
    }

    public interface onSelectListener {
        void onSelect(PickerView2 pickerView2, String str, int i);
    }

    public PickerView2(android.content.Context context) {
        super(context);
        this.f = 25.0f;
        this.q = 10;
        this.a = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (Math.abs(PickerView2.this.j) < 10.0f) {
                    PickerView2.this.j = 0.0f;
                    if (PickerView2.this.n != null) {
                        PickerView2.this.n.cancel();
                        PickerView2.this.n = null;
                    }
                    if (PickerView2.this.l != null && PickerView2.this.b != null) {
                        PickerView2.this.l.onSelect(PickerView2.this, PickerView2.this.b.get(PickerView2.this.c), PickerView2.this.c);
                    }
                } else {
                    PickerView2.this.j -= (PickerView2.this.j / Math.abs(PickerView2.this.j)) * 10.0f;
                }
                PickerView2.this.invalidate();
            }
        };
    }

    private static float alphaFactor(float max, float offset) {
        float factor = (float) (1.0f - Math.pow(offset / max, 2.0f));
        return factor < 0.0f ? 0.0f : factor;
    }

    private void scrollUp() {
        String first = this.b.get(0);
        this.b.remove(0);
        this.b.add(first);
    }

    private void scrollDown() {
        String last = this.b.get(this.b.size() - 1);
        this.b.remove(this.b.size() - 1);
        this.b.add(0, last);
    }

    private void drawItem(Canvas canvas, int direction) {
        float offset = direction;
        float yOffset = (this.j * offset) + 20.0f;
        this.e.setTextSize(((this.f - 20.0f) * alphaFactor(this.g / 4.0f, yOffset)) + 20.0f);
        this.d.setColor(Color.parseColor("#FFFFFF"));
        this.e.setAlpha(225);
        float y = (float) (((double) this.g / 2.0d) + ((double) (offset * (yOffset + this.q))));
        Paint.FontMetricsInt fm = this.e.getFontMetricsInt();
        float textY = (float) (((double) y) - (((double) fm.bottom / 2.0d) + ((double) fm.top / 2.0d)));
        int index = this.c + direction;
        if (index < 0) index = this.b.size() - 1;
        else if (index >= this.b.size()) index = 0;
        canvas.drawText(this.b.get(index) + this.p, (float) ((double) this.h / 2.0d), textY, this.e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this.o) return super.dispatchTouchEvent(event);
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.k || this.b.size() <= 0) return;
        
        this.d.setTextSize(((this.f - 20.0f) * alphaFactor(this.g / 2.0f, this.j)) + 20.0f);
        this.e.setAlpha(225);
        this.d.setColor(Color.parseColor("#00BAFF"));
        float centerX = (float) ((double) this.h / 2.0d);
        float centerY = (float) (((double) this.g / 2.0d) + ((double) this.j));
        Paint.FontMetricsInt fm = this.d.getFontMetricsInt();
        float textY = (float) (((double) centerY) - (((double) fm.bottom / 2.0d) + ((double) fm.top / 2.0d)));
        
        StringBuilder sb = new StringBuilder();
        if (!this.b.get(this.c).equals("OFF")) sb.append("");
        sb.append(this.b.get(this.c));
        sb.append(this.p);
        canvas.drawText(sb.toString(), centerX, textY, this.d);
        drawItem(canvas, -1);
        drawItem(canvas, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.g = getMeasuredHeight();
        this.h = getMeasuredWidth();
        this.k = true;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (this.n != null) {
                    this.n.cancel();
                    this.n = null;
                }
                this.i = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(this.j) >= 0.0001f) {
                    if (this.n != null) {
                        this.n.cancel();
                        this.n = null;
                    }
                    this.n = new a(this.a);
                    if (this.m == null) this.m = new Timer();
                    this.m.schedule(this.n, 0, 10);
                } else {
                    this.j = 0.0f;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                this.j += event.getY() - this.i;
                if (this.j <= 10.0f) {
                    if (this.j < -10.0f) {
                        scrollUp();
                        this.j += 20.0f;
                    }
                } else {
                    scrollDown();
                    this.j -= 20.0f;
                }
                this.i = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    public void setCanScroll(boolean canScroll) { this.o = canScroll; }
    public void setData(List<String> data) { this.b = data; invalidate(); }
    public void setDateTag(String tag) { this.p = tag; }
    public void setOnSelectListener(onSelectListener listener) { this.l = listener; }
    
    public void setSelected(int position) {
        this.c = position;
        int diff = (this.b.size() / 2) - this.c;
        for (int i = 0; i < Math.abs(diff); i++) {
            if (diff < 0) scrollUp();
            else if (diff > 0) scrollDown();
        }
        invalidate();
    }
    
    public void setSelected(String text) {
        for (int i = 0; i < this.b.size(); i++) {
            if (this.b.get(i).equals(text)) {
                setSelected(i);
                return;
            }
        }
    }
}
