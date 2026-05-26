package com.tw.eq.a;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.TextView;

public final class e {
    private static TextView d = null;
    private static e e = null;
    private static String f = "";
    private static int g = -16777216;
    private static int h = -589505316;
    private static float i = 20.0f;
    private static int j = 15;
    private static int k = 0;
    private static int l = 35;
    private static int m = 80;
    private final WindowManager b;
    private final WindowManager.LayoutParams c;
    int a = 2000;
    private final Handler n = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                e.a(e.this);
            }
        }
    };

    private e() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private e(Context context) {
        d = new TextView(context);
        this.b = (WindowManager) context.getSystemService("window");
        this.c = new WindowManager.LayoutParams(-2, -2, 0, 0, 2002, 56, 1);
        if (Build.VERSION.SDK_INT >= 26) {
            this.c.type = 2038;
        } else {
            this.c.type = 2002;
        }
    }

    public static e a(Context context) {
        if (e != null) {
            return e;
        }
        e eVar = new e(context);
        e = eVar;
        return eVar;
    }

    public static e a(String str) {
        f = str;
        return e;
    }

    static void a(e eVar) {
        if (d.getParent() != null) {
            eVar.b.removeView(d);
        }
    }

    public final void a() {
        if (d != null) {
            d.setText(f);
            d.setTextColor(g);
            d.setPadding(8, 8, 8, 8);
            d.setBackgroundColor(h);
            final int i2 = j;
            if (i2 > 0 && Build.VERSION.SDK_INT >= 21) {
                d.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public final void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), i2);
                    }
                });
                d.setClipToOutline(true);
            }
            d.setTextSize(i);
        }
        this.c.gravity = m;
        this.c.x = k;
        this.c.y = l;
        if (d.getParent() == null) {
            this.b.addView(d, this.c);
        }
        this.n.removeMessages(1);
        this.n.sendEmptyMessageDelayed(1, this.a);
    }
}
