package com.tw.eq.e.a;

import android.content.Context;
import android.util.Log;
import com.tw.eq.view.BassFilterView;

public final class b extends com.tw.eq.e.a<BassFilterView, com.tw.eq.c.a> {
    public b(Context context) {
        super(context);
    }

    public static void a(int i) {
        if (d != null) {
            d.write(257, 40, i);
        }
    }

    public static void b(int i) {
        if (d != null) {
            d.write(257, 41, i);
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
                Log.d("BassFilterPresenter", "onBass:" + ((int) bArr[40]) + "," + ((int) bArr[41]));
            }
            if (e() == null) {
                return;
            }
            e().onBassFrontValueChange(bArr[40] & 0xFF);
            e().onBassRearValueChange(bArr[41] & 0xFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
