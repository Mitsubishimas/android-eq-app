package com.tw.eq.e;

import android.content.Context;
import com.tw.eq.view.EQView;
import java.util.Arrays;

public final class b extends a<EQView, com.tw.eq.c.b> {
    private int g;

    public b(Context context) {
        super(context);
        com.tw.eq.c.b bVarF = f();
        if (com.tw.eq.a.a.b == 12) {
            bVarF.d = Arrays.copyOf(bVarF.b, bVarF.b.length);
        } else {
            bVarF.d = Arrays.copyOf(bVarF.c, bVarF.c.length);
        }
    }

    @Override
    public final com.tw.eq.c.a a() {
        return new com.tw.eq.c.b(this.c);
    }

    @Override
    public final void a(byte[] bArr) {
        try {
            if (e() == null) {
                return;
            }
            int mode = bArr[0] & 0xFF;
            int start;
            int length;
            if (com.tw.eq.a.a.b == 12) {
                start = 17;
                length = 12;
            } else {
                start = 1;
                length = 36;
            }
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = bArr[start + i] & 0xFF;
            }
            e().onEqMode(mode);
            e().onEqValueChange(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void b() {
        super.b();
        com.tw.eq.c.b bVarF = f();
        for (int i = 0; i < bVarF.d.length; i++) {
            bVarF.d[i] = com.tw.eq.a.c.b(bVarF.a, "EQ_VALUE_USER", "eqmode_value" + i, bVarF.d[i]);
        }
    }
}
