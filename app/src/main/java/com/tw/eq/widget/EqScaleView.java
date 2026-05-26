package com.tw.eq.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VerticalSeekBar;
import com.tw.eq.a;
import com.tw.eq.theme.f;
import com.tw.eq.theme.i;

public class EqScaleView extends LinearLayout implements VerticalSeekBar.OnSeekBarChangeListener {
    public TextView a;
    public boolean b;
    public int[] c;
    public int d;
    private SeekBar e;
    private View f;
    private i g;
    private int h;
    private TextView i;
    private VerticalSeekBar j;
    private int k;
    private int l;
    private int m;
    private OnValueChangeListener n;
    private int o;
    private int p;
    private TextView q;
    private final Context r;
    private int s;

    public interface FrequencyType {
        int eq1 = 1;
        int eq2 = 2;
        int eq3 = 3;
        int eq4 = 4;
        int eq5 = 5;
        int eq6 = 6;
        int eq7 = 7;
        int eq8 = 8;
        int eq9 = 9;
        int eq10 = 10;
        int eq11 = 11;
        int eq12 = 12;
        int eq13 = 13;
        int eq14 = 14;
        int eq15 = 15;
        int eq16 = 16;
        int SPDIF = 17;
        int CENTER = 18;
        int SUB_GAIN = 19;
        int SUB_FREQ = 20;
        int SUB_W = 21;
        int LOUND = 22;
    }

    public interface OnValueChangeListener {
        void onValueChanged(EqScaleView eqScaleView, int freqType, int progress, int index, int qValue, int frequency, boolean fromUser);
    }

    public EqScaleView(Context context) {
        this(context, null);
    }

    public EqScaleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EqScaleView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.h = 14;
        this.k = 1;
        this.l = -100;
        this.m = -12;
        this.o = 24;
        this.p = -1;
        this.c = new int[]{2131427397, 2131427398, 2131427399, 2131427400, 2131427401, 2131427402, 2131427403, 2131427404};
        this.s = 0;
        this.r = context;
        
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, a.C0003a.EqScaleView, defStyle, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case 1:
                    this.k = typedArray.getInt(index, 1);
                    break;
                case 2:
                    this.p = typedArray.getInt(index, -1);
                    break;
                case 3:
                    this.h = typedArray.getInt(index, 12);
                    break;
                case 4:
                    this.o = typedArray.getInt(index, 28);
                    break;
                case 5:
                    this.m = typedArray.getInt(index, -12);
                    break;
                case 6:
                    this.l = typedArray.getInt(index, -100);
                    break;
            }
        }
        typedArray.recycle();
    }

    private int getProgressValue() {
        return (int) (((this.l - this.m) / (this.h - this.m)) * this.j.getMax());
    }

    private void updateValueDisplay(int value) {
        if (this.q != null) {
            this.q.setText(value + "db");
            if (this.k == 17) {
                if (value == 0) this.q.setText("24bits");
                else if (value == 1) this.q.setText("20bits");
                else if (value == 2) this.q.setText("16bits");
                else if (value == 3) this.q.setText("32bits");
            }
        }
    }

    private void showQPopup(View anchor) {
        this.f = LayoutInflater.from(this.r).inflate(2131361841, (ViewGroup) null, false);
        PopupWindow popupWindow = new PopupWindow(this.f, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        this.e = (SeekBar) this.f.findViewById(2131230909);
        this.e.setProgress(this.s);
        this.e.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int qVal = progress + 7;
                    EqScaleView.this.n.onValueChanged(EqScaleView.this, EqScaleView.this.k, 
                        EqScaleView.this.j.getProgress(), EqScaleView.this.p, qVal, EqScaleView.this.d, true);
                    if (EqScaleView.this.i != null) {
                        EqScaleView.this.i.setText(String.valueOf(((double) qVal) / 10.0d));
                    }
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        popupWindow.showAtLocation(anchor, 17, 0, 0);
    }

    private void animateProgress(int targetProgress) {
        if (this.j == null) return;
        ValueAnimator animator = ValueAnimator.ofFloat(this.j.getProgress(), targetProgress);
        animator.setDuration(300L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                EqScaleView.this.j.setProgress((int) ((Float) animation.getAnimatedValue()).floatValue());
            }
        });
        animator.start();
    }

    private int calculateProgress(int progress) {
        int max = ((int) ((progress / this.j.getMax()) * (this.h - this.m))) + this.m;
        this.l = max;
        return max;
    }

    public final void a(int value, int qValue) {
        this.s = qValue;
        this.l = value;
        this.b = false;
        animateProgress(value);
        updateValueDisplay(calculateProgress(value));
        if (this.i != null) {
            this.i.setText(String.valueOf(((double) this.s) / 10.0d));
        }
    }

    public TextView getFCView() { return this.a; }
    public int getFrequencyType() { return this.k; }
    public int getIndex() { return this.p; }
    public int getMax() { return this.j.getMax(); }
    public TextView getQView() { return this.i; }
    public VerticalSeekBar getSeekBar() { return this.j; }
    public TextView getValueView() { return this.q; }

    @Override
    protected void onFinishInflate() {
        this.q = (TextView) findViewById(2131230960);
        this.a = (TextView) findViewById(2131230959);
        this.i = (TextView) findViewById(2131230958);
        this.j = findViewById(2131230908);
        this.j.setMax(this.o);
        this.j.setOnSeekBarChangeListener(this);
        this.j.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EqScaleView.this.b = true;
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        if (this.i != null) {
            this.i.setText(String.valueOf(((double) this.s) / 10.0d));
            this.i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EqScaleView.this.showQPopup(EqScaleView.this.i);
                }
            });
        }
        
        // Set frequency title based on type
        if (this.a != null) {
            String title = "";
            switch (this.k) {
                case 1: title = "20"; this.d = 20; break;
                case 2: title = "50"; this.d = 50; break;
                case 3: title = "80"; this.d = 80; break;
                case 4: title = "125"; this.d = 125; break;
                case 5: title = "200"; this.d = 200; break;
                case 6: title = "315"; this.d = 500; break;
                case 7: title = "500"; this.d = 500; break;
                case 8: title = "800"; this.d = 800; break;
                case 9: title = "1K"; this.d = 1000; break;
                case 10: title = "1.25K"; this.d = 1250; break;
                case 11: title = "2K"; this.d = 2000; break;
                case 12: title = "3.15K"; this.d = 3150; break;
                case 13: title = "5K"; this.d = 5000; break;
                case 14: title = "8K"; this.d = 8000; break;
                case 15: title = "12.5K"; this.d = 12500; break;
                case 16: title = "16K"; this.d = 16000; break;
                case 17: title = "SPDIF"; break;
                case 18: title = "CENTER"; break;
                case 19: title = "SUB GAIN"; break;
                case 20: title = "SUB FREQ"; break;
                case 21: title = "SUB W"; break;
                case 22: title = "LOUND"; break;
            }
            this.a.setText(title);
        }
    }

    @Override
    public void onProgressChanged(VerticalSeekBar seekBar, int progress, boolean fromUser) {
        if (this.n != null) {
            this.n.onValueChanged(this, this.k, progress, this.p, this.s, this.d, this.b);
        }
        updateValueDisplay(calculateProgress(progress));
    }

    @Override
    public void onStartTrackingTouch(VerticalSeekBar seekBar) {
        if (this.n != null) {
            this.n.onValueChanged(this, this.k, seekBar.getProgress(), this.p, this.s, this.d, this.b);
        }
        updateValueDisplay(calculateProgress(seekBar.getProgress()));
    }

    @Override
    public void onStopTrackingTouch(VerticalSeekBar seekBar) {
        if (this.n != null) {
            this.n.onValueChanged(this, this.k, seekBar.getProgress(), this.p, this.s, this.d, this.b);
        }
        updateValueDisplay(calculateProgress(seekBar.getProgress()));
    }

    public void setMinValue(int min) { this.m = min; }
    public void setThemeSwitchInfo(i themeInfo) { this.g = themeInfo; }
    public void setTitle(String title) { if (this.a != null) this.a.setText(title); }
    public void setValueChangeListener(OnValueChangeListener listener) { this.n = listener; }
}
