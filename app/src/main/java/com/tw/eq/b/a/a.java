package com.tw.eq.b.a;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import com.tw.eq.e.a;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;

public abstract class a<V extends BaseView, P extends com.tw.eq.e.a> extends Fragment {
    public P a;
    protected i b;
    private V c;

    public abstract V a();

    public void a(i iVar) {
    }

    public abstract P b();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.a == null || this.c == null) {
            return;
        }
        this.a.d();
    }

    @Override
    public void onPause() {
        super.onPause();
        com.tw.eq.e.a.c();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.a.b();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.c == null) {
            this.c = a();
        }
        if (this.a == null) {
            this.a = b();
        }
        if (this.a != null && this.c != null) {
            this.a.a(this.c);
        }
        if (getActivity() instanceof com.tw.eq.activity.a) {
            this.b = ((com.tw.eq.activity.a) getActivity()).a;
            if (this.b != null) {
                a(this.b);
            }
        }
    }
}
