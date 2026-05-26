package com.tw.eq.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.tw.eq.a.d;
import com.tw.eq.e.a;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;

public abstract class a<V extends BaseView, P extends com.tw.eq.e.a> extends Activity {
    public static d b;
    public i a;
    private P c;
    private V d;

    private static void a(int i) {
        if (b != null) {
            b.write(40448, i);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.d == null) {
            this.d = null;
        }
        if (this.c == null) {
            this.c = null;
        }
        if (this.c != null && this.d != null) {
            this.c.a(this.d);
        }
        b = d.a();
        if (Build.VERSION.SDK_INT >= 24 && isInMultiWindowMode()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    Toast.makeText(a.this, a.this.getString(2131427378), Toast.LENGTH_SHORT).show();
                    a.this.finish();
                }
            }, 200L);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.c != null && this.d != null) {
            this.c.d();
        }
        if (b != null) {
            b.close();
            b = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        a(128);
    }

    @Override
    protected void onResume() {
        super.onResume();
        a(0);
    }
}
