package com.tw.eq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.tw.eq.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PickerView extends View {
    Handler a;
    private List<String> b;
    private int c;
    private Paint d;
    private Paint e;
    private float f;
    private final float g;
    private final float h;
    private final float i;
    private final int j;
    private final int k;
    private int l;
    private int m;
    private float n;
    private float o;
    private boolean p;
    private onSelectListener q;
    private Timer r;
    private a s;
    private boolean t;
    private String u;
    private int v;

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
        void onSelect(PickerView pickerView, String str, int i);
    }

    public PickerView(Context context) {
        this(context, null);
    }

    public PickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PickerView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.f = 25.0f;
        this.g = 20.0f;
        this.h = 255.0f;
        this.i = 120.0f;
        this.o = 0.0f;
        this.p = false;
        this.t = true;
        this.u = "";
        this.v = 10;
        
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, a.C0003a.PickerView, defStyle, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case 0:
                    this.f = typedArray.getInt(index, 25);
                    break;
                case 1:
                    this.v = typedArray.getInt(index, 10);
                    break;
            }
        }
        typedArray.recycle();
        
        this.a = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (Math.abs(PickerView.this.o) < 10.0f) {
                    PickerView.this.o = 0.0f;
                    if (PickerView.this.s != null) {
                        PickerView.this.s.cancel();
                        PickerView.this.s = null;
                    }
                    if (PickerView.this.q != null && PickerView.this.b != null && PickerView.this.c < PickerView.this.b.size()) {
                        PickerView.this.q.onSelect(PickerView.this, PickerView.this.b.get(PickerView.this.c), PickerView.this.c);
                    }
                } else {
                    PickerView.this.o -= (PickerView.this.o / Math.abs(PickerView.this.o)) * 10.0f;
                }
                PickerView.this.invalidate();
            }
        };
        
        this.r = new Timer();
        this.b = new ArrayList<>();
        this.d = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.d.setStyle(Paint.Style.FILL);
        this.d.setTextAlign(Paint.Align.CENTER);
        this.d.setColor(-1);
        this.e = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.e.setStyle(Paint.Style.FILL);
        this.e.setTextAlign(Paint.Align.CENTER);
        this.e.setColor(0xFF666666);
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
        float yOffset = (this.o * offset) + 20.0f;
        float alpha = alphaFactor(this.l / 4.0f, yOffset);
        this.e.setTextSize(((this.f - 20.0f) * alpha) + 20.0f);
        this.e.setAlpha((int) ((alpha * 135.0f) + 120.0f));
        float y = (float) (((double) this.l / 2.0d) + ((double) (offset * (yOffset + this.v))));
        Paint.FontMetricsInt fm = this.e.getFontMetricsInt();
        float textY = (float) (((double) y) - (((double) fm.bottom / 2.0d) + ((double) fm.top / 2.0d)));
        int index = this.c + direction;
        if (index < 0) index = this.b.size() - 1;
        else if (index >= this.b.size()) index = 0;
        canvas.drawText(this.b.get(index) + this.u, (float) ((double) this.m / 2.0d), textY, this.e);
    }

    public final void a(int color, int textColor) {
        if (this.d != null) this.d.setColor(textColor);
        if (this.e != null) this.e.setColor(color);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this.t) return super.dispatchTouchEvent(event);
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.p || this.b.size() <= 0) return;
        
        float alpha = alphaFactor(this.l / 2.0f, this.o);
        this.d.setTextSize(((this.f - 20.0f) * alpha) + 20.0f);
        this.d.setAlpha((int) ((alpha * 135.0f) + 120.0f));
        float centerX = (float) ((double) this.m / 2.0d);
        float centerY = (float) (((double) this.l / 2.0d) + ((double) this.o));
        Paint.FontMetricsInt fm = this.d.getFontMetricsInt();
        float textY = (float) (((double) centerY) - (((double) fm.bottom / 2.0d) + ((double) fm.top / 2.0d)));
        
        String displayText = this.b.get(this.c);
        if (!displayText.equals("OFF")) displayText = "<" + displayText;
        canvas.drawText(displayText + this.u, centerX, textY, this.d);
        drawItem(canvas, -1);
        drawItem(canvas, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.l = getMeasuredHeight();
        this.m = getMeasuredWidth();
        this.p = true;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (this.s != null) {
                    this.s.cancel();
                    this.s = null;
                }
                this.n = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(this.o) >= 0.0001f) {
                    if (this.s != null) {
                        this.s.cancel();
                        this.s = null;
                    }
                    this.s = new a(this.a);
                    this.r.schedule(this.s, 0, 10);
                } else {
                    this.o = 0.0f;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                this.o += event.getY() - this.n;
                if (this.o <= 10.0f) {
                    if (this.o < -10.0f) {
                        scrollUp();
                        this.o += 20.0f;
                    }
                } else {
                    scrollDown();
                    this.o -= 20.0f;
                }
                this.n = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    public void setCanScroll(boolean canScroll) { this.t = canScroll; }
    public void setData(List<String> data) { this.b.clear(); this.b.addAll(data); invalidate(); }
    public void setDateTag(String tag) { this.u = tag; }
    public void setOnSelectListener(onSelectListener listener) { this.q = listener; }
    
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
