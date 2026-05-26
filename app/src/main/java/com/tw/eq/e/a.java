package com.tw.eq.e;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.tw.eq.a.d;
import com.tw.eq.c.a;
import com.tw.eq.view.BaseView;

public abstract class a<V extends BaseView, M extends com.tw.eq.c.a> {
    public static d d;
    protected V a;
    protected Context c;
    public boolean e = false;
    public Handler f = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            if (message.what != 257) {
                return;
            }
            a.this.a((byte[]) message.obj);
        }
    };
    protected M b;

    public a(Context context) {
        this.c = context;
        d = d.a();
        try {
            Thread.sleep(10L);
        } catch (InterruptedException unused) {
        }
    }

    public static void c() {
        if (d != null) {
            d.removeHandler("BasePresenter");
        }
    }

    public abstract M a();

    public final void a(V v) {
        this.a = v;
    }

    public void a(byte[] bArr) {
    }

    public void b() {
        if (d != null) {
            d.addHandler("BasePresenter", this.f);
            d.write(257, 255);
        }
    }

    public final void d() {
        this.a = null;
        this.c = null;
        if (d != null) {
            d.close();
            d = null;
        }
    }

    public final V e() {
        return this.a;
    }

    public final M f() {
        return this.b;
    }
}
