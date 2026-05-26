package com.tw.eq.e.a;

import android.content.Context;
import android.util.Log;
import com.tw.eq.view.BassBoostView;

public final class a extends com.tw.eq.e.a<BassBoostView, com.tw.eq.c.a> {
    public a(Context context) {
        super(context);
    }

    public static void a(int i) {
        if (d != null) {
            d.write(257, 36, i);
        }
    }

    public static void b(int i) {
        if (d != null) {
            d.write(257, 37, i);
        }
    }

    public static void c(int i) {
        if (d != null) {
            d.write(257, 38, i);
        }
    }

    public static void d(int i) {
        if (d != null) {
            d.write(257, 39, i);
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
                Log.d("BassBoostPresenter", "bassBoostView ==null");
                return;
            }
            e().onFrontBassBoost(bArr[36] & 0xFF);
            e().onFrontBassFreq(bArr[37] & 0xFF);
            e().onRearBassBoost(bArr[38] & 0xFF);
            e().onRearBassFreq(bArr[39] & 0xFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
