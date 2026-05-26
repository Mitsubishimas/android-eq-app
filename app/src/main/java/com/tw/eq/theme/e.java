package com.tw.eq.theme;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.util.concurrent.CountDownLatch;

public class e {
    private static Handler a;

    private static class a extends Handler {
        public a() {
            super(Looper.getMainLooper());
        }

        @Override
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                Pair<Runnable, CountDownLatch> pair = (Pair) message.obj;
                pair.first.run();
                if (pair.second != null) {
                    pair.second.countDown();
                }
            }
        }
    }

    private static Handler a() {
        synchronized (e.class) {
            if (a == null) {
                a = new a();
            }
        }
        return a;
    }

    public static void a(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            a().obtainMessage(1, new Pair(runnable, null)).sendToTarget();
        }
    }
}
