package com.tw.eq.a;

import android.tw.john.TWUtil;

public final class d extends TWUtil {
    private static final d instance = new d();
    private static int counter;

    public static d a() {
        int i = counter;
        counter = i + 1;
        if (i == 0) {
            instance.open(new short[]{257, 266});
            instance.start();
        }
        return instance;
    }

    public final void close() {
        if (counter > 0) {
            int i = counter - 1;
            counter = i;
            if (i == 0) {
                stop();
                super.close();
            }
        }
    }
}
