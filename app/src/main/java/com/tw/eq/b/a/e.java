package com.tw.eq.b.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.ZoneSoundView;
import com.tw.eq.widget.DounbleThumbSeekbar;
import com.tw.eq.widget.EqScaleView;
import com.tw.eq.widget.ZoneView;

public final class e extends a<ZoneSoundView, com.tw.eq.e.a.e> implements View.OnClickListener, ZoneSoundView {
    private ZoneView c;
    private int d;
    private int e;
    private EqScaleView f;
    private EqScaleView g;
    private EqScaleView h;
    private DounbleThumbSeekbar i;
    private View k;
    private View l;
    private View m;
    private View n;
    private Button o;
    private Button p;
    private TextView q;
    private ImageView r;
    private boolean j = true;
    private final EqScaleView.OnValueChangeListener s = new EqScaleView.OnValueChangeListener() {
        @Override
        public final void onValueChanged(EqScaleView eqScaleView, int i, int i2, int i3, int i4, int i5, boolean z) {
            if (z) {
                int id = eqScaleView.getId();
                if (id == 2131230758) {
                    com.tw.eq.e.a.e.b(i2);
                } else if (id == 2131230921) {
                    com.tw.eq.e.a.e.a(i2);
                } else if (id == 2131230930) {
                    com.tw.eq.e.a.e.c(i2);
                }
            }
        }
    };

    private void b(i iVar) {
        com.tw.eq.d.f fVar = iVar.f;
        Context context = iVar.a.b;
        if (this.o != null) {
            this.o.setBackground(fVar.c);
            com.tw.eq.a.b.a(context, iVar.b.j, this.o);
        }
        if (this.p != null) {
            this.p.setBackground(fVar.d);
            com.tw.eq.a.b.a(context, iVar.b.j, this.p);
        }
        if (this.k != null) {
            this.k.setBackground(fVar.e);
        }
        if (this.l != null) {
            this.l.setBackground(fVar.f);
        }
        if (this.m != null) {
            this.m.setBackground(fVar.g);
        }
        if (this.n != null) {
            this.n.setBackground(fVar.h);
        }
        if (this.c != null) {
            this.c.setBackground(fVar.a);
            this.c.setSpeakerDrawable(fVar.b);
        }
        int color = iVar.b.i;
        if (this.f != null) {
            this.f.getSeekBar().setThumb(com.tw.eq.theme.f.a(context, fVar.j));
            this.f.getSeekBar().setProgressDrawableTiled(com.tw.eq.theme.f.a(context, fVar.i));
            this.f.getSeekBar().setThumbOffset(fVar.k);
            this.f.getFCView().setTextColor(color);
            this.f.getValueView().setTextColor(color);
        }
        if (this.g != null) {
            this.g.getSeekBar().setThumb(com.tw.eq.theme.f.a(context, fVar.j));
            this.g.getSeekBar().setProgressDrawableTiled(com.tw.eq.theme.f.a(context, fVar.i));
            this.g.getSeekBar().setThumbOffset(fVar.k);
            this.g.getFCView().setTextColor(color);
            this.g.getValueView().setTextColor(color);
        }
        if (this.h != null) {
            this.h.getSeekBar().setThumb(com.tw.eq.theme.f.a(context, fVar.j));
            this.h.getSeekBar().setProgressDrawableTiled(com.tw.eq.theme.f.a(context, fVar.i));
            this.h.getSeekBar().setThumbOffset(fVar.k);
            this.h.getFCView().setTextColor(color);
            this.h.getValueView().setTextColor(color);
        }
        if (this.i != null) {
            DounbleThumbSeekbar dounbleThumbSeekbar = this.i;
            Drawable drawableA = com.tw.eq.theme.f.a(context, fVar.j);
            Drawable drawableA2 = com.tw.eq.theme.f.a(context, fVar.j);
            dounbleThumbSeekbar.c = drawableA;
            dounbleThumbSeekbar.d = drawableA2;
            DounbleThumbSeekbar dounbleThumbSeekbar2 = this.i;
            Drawable drawable = fVar.l;
            Drawable drawable2 = fVar.m;
            dounbleThumbSeekbar2.b = drawable;
            dounbleThumbSeekbar2.a = drawable2;
            this.i.setTextColor(color);
            this.q.setTextColor(color);
        }
        if (this.r != null) {
            this.r.setImageDrawable(fVar.n);
        }
    }

    @Override
    public final BaseView a() {
        return this;
    }

    @Override
    public final void a(i iVar) {
        super.a(iVar);
        b(iVar);
    }

    @Override
    public final com.tw.eq.e.a b() {
        return new com.tw.eq.e.a.e(getActivity());
    }

    @Override
    public final void onCenterValueChange(int i) {
        Log.d("RohmZoneFragment", "onCenterValueChange: " + i);
        if (this.g != null && i <= this.g.getMax()) {
            this.g.a(i, 0);
        }
    }

    @Override
    public final void onClick(View view) {
        switch (view.getId()) {
            case 2131230750:
                this.e = 9;
                this.d = 9;
                break;
            case 2131230927:
                com.tw.eq.e.a.e.a(0);
                com.tw.eq.e.a.e.b(7);
                com.tw.eq.e.a.e.c(7);
                com.tw.eq.e.a.e.b(10, 0);
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
        this.c.a(this.d, this.e);
        com.tw.eq.e.a.e.a(this.d, this.e);
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(2131361834, (ViewGroup) null);
        this.r = (ImageView) viewInflate.findViewById(2131230973);
        this.f = (EqScaleView) viewInflate.findViewById(2131230921);
        this.f.setValueChangeListener(this.s);
        this.g = (EqScaleView) viewInflate.findViewById(2131230758);
        this.g.setValueChangeListener(this.s);
        this.h = (EqScaleView) viewInflate.findViewById(2131230930);
        this.h.setValueChangeListener(this.s);
        this.i = (DounbleThumbSeekbar) viewInflate.findViewById(2131230928);
        this.q = (TextView) viewInflate.findViewById(2131230929);
        this.i.setOnSeekBarChangeListener(new DounbleThumbSeekbar.OnSeekBarChangeListener() {
            @Override
            public final void onProgressChanged(DounbleThumbSeekbar dounbleThumbSeekbar, double d, double d2, boolean z) {
                if (z) {
                    com.tw.eq.e.a.e.b((int) (11.0d - d), (int) (11.0d - d2));
                }
            }
        });
        this.k = viewInflate.findViewById(2131230970);
        this.k.setOnClickListener(this);
        this.l = viewInflate.findViewById(2131230972);
        this.l.setOnClickListener(this);
        this.m = viewInflate.findViewById(2131230975);
        this.m.setOnClickListener(this);
        this.n = viewInflate.findViewById(2131230969);
        this.n.setOnClickListener(this);
        this.o = (Button) viewInflate.findViewById(2131230750);
        this.o.setOnClickListener(this);
        this.p = (Button) viewInflate.findViewById(2131230927);
        if (this.p != null) {
            this.p.setOnClickListener(this);
        }
        this.c = (ZoneView) viewInflate.findViewById(2131230968);
        this.c.setOnZoneChangeListener(new ZoneView.OnZoneChangeListener() {
            @Override
            public final void onStartTrackingTouch(ZoneView zoneView) {
            }

            @Override
            public final void onStopTrackingTouch(ZoneView zoneView, boolean z) {
                if (z) {
                    e.this.d = zoneView.getx();
                    e.this.e = zoneView.gety();
                    com.tw.eq.e.a.e.a(e.this.d, e.this.e);
                }
            }
        });
        if (com.tw.eq.a.d.b().contains("ACX28")) {
            this.f.setVisibility(4);
        } else {
            this.f.setVisibility(0);
        }
        this.g.setVisibility(8);
        this.h.setTitle(getResources().getString(2131427418));
        return viewInflate;
    }

    @Override
    public final void onResume() {
        super.onResume();
        ((com.tw.eq.e.a.e) this.a).b();
    }

    @Override
    public final void onSpdifValueChange(int i) {
        Log.d("RohmZoneFragment", "onSpdifValueChange: " + i);
        if (this.f != null && i <= this.f.getMax()) {
            this.f.a(i, 0);
        }
    }

    @Override
    public final void onSubFreqValueChange(int i, int i2) {
        if (this.i != null) {
            this.i.setProgressLow(11 - i);
            this.i.setProgressHigh(11 - i2);
        }
    }

    @Override
    public final void onSubGainValueChange(int i) {
        Log.d("RohmZoneFragment", "onSubGainValueChange: " + i);
        if (this.h != null && i <= this.h.getMax()) {
            this.h.a(i, 0);
        }
    }

    @Override
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.b != null) {
            b(this.b);
        }
    }

    @Override
    public final void onZoneXY(int i, int i2) {
        this.d = i;
        this.e = i2;
        if (this.j) {
            this.j = false;
            if (this.c != null) {
                this.c.a(this.d, this.e);
            }
        }
    }
}
