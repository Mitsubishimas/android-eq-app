package com.tw.eq.e.a;

import android.content.Context;
import android.util.Log;
import com.tw.eq.view.SurroundSoundView;

public final class d extends com.tw.eq.e.a<SurroundSoundView, com.tw.eq.c.a> {
    public byte g;

    public d(Context context) {
        super(context);
    }

    public static void a(int i) {
        if (d != null) {
            d.write(257, 44, i);
        }
    }

    public static void b(int i) {
        if (d != null) {
            d.write(257, 45, i);
        }
    }

    public static void c(int i) {
        if (d != null) {
            d.write(257, 46, i);
        }
    }

    public static void d(int i) {
        if (d != null) {
            d.write(257, 47, i);
        }
    }

    public static void e(int i) {
        if (d != null) {
            d.write(257, 48, i);
        }
    }

    public static void f(int i) {
        if (d != null) {
            d.write(257, 49, i);
        }
    }

    @Override
    public final com.tw.eq.c.a a() {
        return null;
    }

    @Override
    public final void a(byte[] bArr) {
        super.a(bArr);
        try {
            if (this.e) {
                Log.d("SurroundSoundPresenter", "SurroundSound:" + ((int) bArr[44]) + "," + ((int) bArr[45]));
            }
            this.g = bArr[44];
            if (e() == null) {
                return;
            }
            e().onSurroundSpaceMode(this.g);
            e().onSurroundSpaceValue(bArr[45] & 0xFF, bArr[46] & 0xFF, bArr[47] & 0xFF, bArr[48] & 0xFF, bArr[49] & 0xFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
