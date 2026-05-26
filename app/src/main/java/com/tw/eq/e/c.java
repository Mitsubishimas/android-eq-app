package com.tw.eq.e;

import android.content.Context;
import android.util.Log;
import com.tw.eq.view.NormalZoneSoundView;

public final class c extends a<NormalZoneSoundView, com.tw.eq.c.a> {
    public c(Context context) {
        super(context);
    }

    public static void a(int i) {
        if (d != null) {
            d.write(257, 5, i);
        }
    }

    public static void a(int i, int i2) {
        if (d != null) {
            d.write(257, 6, i2);
            d.write(257, 7, i);
        }
    }

    public static void b(int i) {
        if (d != null) {
            d.write(257, 4, i);
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
            e().onZoneXY(bArr[7] & 0xFF, bArr[6] & 0xFF);
            e().onLoundValueChange(bArr[5] & 0xFF);
            e().onSubWoofValueChange(bArr[4] & 0xFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
