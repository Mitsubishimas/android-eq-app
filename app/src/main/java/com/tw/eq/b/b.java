package com.tw.eq.b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tw.eq.e.c;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.NormalZoneSoundView;
import com.tw.eq.widget.EqScaleView;
import com.tw.eq.widget.ZoneView;

public final class b extends com.tw.eq.b.a.a<NormalZoneSoundView, c> implements View.OnClickListener, NormalZoneSoundView {
    private ZoneView c;
    private int d;
    private int e;
    private EqScaleView g;
    private EqScaleView h;
    private boolean f = true;
    private final EqScaleView.OnValueChangeListener i = new EqScaleView.OnValueChangeListener() {
        @Override
        public final void onValueChanged(EqScaleView eqScaleView, int i, int i2, int i3, int i4, int i5, boolean z) {
            if (z) {
                switch (i) {
                    case 21:
                        c.b(i2);
                        break;
                    case 22:
                        c.a(i2);
                        break;
                }
            }
        }
    };

    private void c() {
        if (this.c != null) {
            this.c.a(this.d, this.e);
        }
    }

    @Override
    public final BaseView a() {
        return this;
    }

    @Override
    public final com.tw.eq.e.a b() {
        return new c(getActivity());
    }

    @Override
    public final void onClick(View view) {
        switch (view.getId()) {
            case 2131230750:
                this.e = 9;
                this.d = 9;
                c();
                c.a(this.d, this.e);
                return;
            case 2131230969:
                this.e++;
                if (this.e > 18) {
                    this.e = 18;
                }
                break;
            case 2131230970:
                this.d--;
                if (this.d < 0) {
                    this.d = 0;
                }
                break;
            case 2131230972:
                this.d++;
                if (this.d > 18) {
                    this.d = 18;
                }
                break;
            case 2131230975:
                this.e--;
                if (this.e < 0) {
                    this.e = 0;
                }
                break;
            default:
                return;
        }
        c();
        c.a(this.d, this.e);
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(2131361833, (ViewGroup) null);
        viewInflate.findViewById(2131230970).setOnClickListener(this);
        viewInflate.findViewById(2131230972).setOnClickListener(this);
        viewInflate.findViewById(2131230975).setOnClickListener(this);
        viewInflate.findViewById(2131230969).setOnClickListener(this);
        viewInflate.findViewById(2131230750).setOnClickListener(this);
        this.g = (EqScaleView) viewInflate.findViewById(2131230974);
        this.g.setValueChangeListener(this.i);
        this.h = (EqScaleView) viewInflate.findViewById(2131230971);
        this.h.setValueChangeListener(this.i);
        this.c = (ZoneView) viewInflate.findViewById(2131230968);
        this.c.setOnZoneChangeListener(new ZoneView.OnZoneChangeListener() {
            @Override
            public final void onStartTrackingTouch(ZoneView zoneView) {
            }

            @Override
            public final void onStopTrackingTouch(ZoneView zoneView, boolean z) {
                if (z) {
                    b.this.d = zoneView.getx();
                    b.this.e = zoneView.gety();
                    c.a(b.this.d, b.this.e);
                }
            }
        });
        return viewInflate;
    }

    @Override
    public final void onLoundValueChange(int i) {
        if (this.h == null || this.h.b) {
            return;
        }
        this.h.a(i, 0);
    }

    @Override
    public final void onResume() {
        super.onResume();
        ((c) this.a).b();
    }

    @Override
    public final void onSubWoofValueChange(int i) {
        if (this.g == null || this.g.b) {
            return;
        }
        this.g.a(i, 0);
    }

    @Override
    public final void onZoneXY(int i, int i2) {
        this.d = i;
        this.e = i2;
        if (this.f) {
            this.f = false;
            c();
        }
    }
}
