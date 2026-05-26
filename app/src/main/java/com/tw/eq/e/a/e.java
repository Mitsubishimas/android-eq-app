package com.tw.eq.e.a;

import android.content.Context;
import android.util.Log;
import com.tw.eq.view.ZoneSoundView;

public final class e extends com.tw.eq.e.a<ZoneSoundView, com.tw.eq.c.a> {
    public e(Context context) {
        super(context);
    }

    public static void a(int i) {
        if (d != null) {
            d.write(257, 50, i);
        }
    }

    public static void a(int i, int i2) {
        if (d != null) {
            d.write(257, 42, i2);
            d.write(257, 43, i);
        }
    }

    public static void b(int i) {
        if (d != null) {
            d.write(257, 51, i);
        }
    }

    public static void b(int i, int i2) {
        if (d != null) {
            d.write(257, 35, (i & 0xFF) | ((i2 & 0xFF) << 4));
        }
    }

    public static void c(int i) {
        if (d != null) {
            d.write(257, 34, i);
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
            if (e() == null) {
                return;
            }
            e().onSpdifValueChange(bArr[50] & 0xFF);
            e().onCenterValueChange(bArr[51] & 0xFF);
            e().onSubGainValueChange(bArr[34] & 0xFF);
            e().onSubFreqValueChange(bArr[35] & 0x0F, (bArr[35] & 0xF0) >> 4);
            e().onZoneXY(bArr[43] & 0xFF, bArr[42] & 0xFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
