package com.tw.eq.b.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.SurroundSoundView;
import com.tw.eq.widget.EqSurroundView;

public final class f extends a<SurroundSoundView, com.tw.eq.e.a.d> implements View.OnClickListener, SurroundSoundView {
    private TextView A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private EqSurroundView G;
    private TextView d;
    private PopupWindow e;
    private View f;
    private ImageView h;
    private ImageView i;
    private View j;
    private Button k;
    private Button l;
    private Button m;
    private Button n;
    private Button o;
    private Button p;
    private RadioGroup q;
    private TextView r;
    private TextView s;
    private TextView t;
    private TextView u;
    private TextView v;
    private TextView w;
    private TextView x;
    private TextView y;
    private TextView z;
    private boolean c = true;
    private final int[] g = {2131427411, 2131427412, 2131427409, 2131427408, 2131427413, 2131427410};
    private long H = 0;
    private final Handler I = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 10088) {
                return;
            }
            f.e(f.this, ((Integer) message.obj).intValue());
        }
    };

    private static void a(TextView textView, TextView textView2, int i) {
        textView.setText(String.format("%s мс", Float.valueOf((float) (((double) (i * 10)) / 20.0d))));
        textView2.setText(String.format("%d см", Integer.valueOf(i * 17)));
    }

    private void b(i iVar) {
        com.tw.eq.d.e eVar = iVar.d;
        Context context = iVar.a.b;
        if (this.j != null) {
            this.j.setBackground(eVar.a);
        }
        if (this.k != null) {
            this.k.setBackground(com.tw.eq.theme.f.a(context, eVar.b));
            com.tw.eq.a.b.a(context, iVar.b.j, this.k);
        }
        if (this.l != null) {
            this.l.setBackground(com.tw.eq.theme.f.a(context, eVar.b));
            com.tw.eq.a.b.a(context, iVar.b.j, this.l);
        }
        if (this.m != null) {
            this.m.setBackground(com.tw.eq.theme.f.a(context, eVar.b));
            com.tw.eq.a.b.a(context, iVar.b.j, this.m);
        }
        if (this.n != null) {
            this.n.setBackground(com.tw.eq.theme.f.a(context, eVar.b));
            com.tw.eq.a.b.a(context, iVar.b.j, this.n);
        }
        if (this.o != null) {
            this.o.setBackground(com.tw.eq.theme.f.a(context, eVar.b));
            com.tw.eq.a.b.a(context, iVar.b.j, this.o);
        }
        if (this.p != null) {
            this.p.setBackground(com.tw.eq.theme.f.a(context, eVar.b));
            com.tw.eq.a.b.a(context, iVar.b.j, this.p);
        }
        if (this.G != null) {
            this.G.setBackground(eVar.c);
            this.G.setSpeakerDrawable(eVar.n);
            EqSurroundView eqSurroundView = this.G;
            Drawable drawable = eVar.d;
            Drawable drawable2 = eVar.e;
            Drawable drawable3 = eVar.f;
            Drawable drawable4 = eVar.h;
            Drawable drawable5 = eVar.g;
            eqSurroundView.b = EqSurroundView.a(drawable);
            eqSurroundView.c = EqSurroundView.a(drawable2);
            eqSurroundView.d = EqSurroundView.a(drawable3);
            eqSurroundView.f = EqSurroundView.a(drawable4);
            eqSurroundView.e = EqSurroundView.a(drawable5);
        }
        int color = iVar.b.i;
        if (this.r != null) {
            ((LinearLayout) this.r.getParent().getParent()).setBackground(eVar.p);
            ((LinearLayout) this.t.getParent().getParent()).setBackground(eVar.p);
            ((LinearLayout) this.v.getParent().getParent()).setBackground(eVar.p);
            ((LinearLayout) this.x.getParent().getParent()).setBackground(eVar.p);
            ((LinearLayout) this.z.getParent().getParent()).setBackground(eVar.o);
            if (color != 0) {
                this.r.setTextColor(color);
                this.s.setTextColor(color);
                this.v.setTextColor(color);
                this.w.setTextColor(color);
                this.t.setTextColor(color);
                this.u.setTextColor(color);
                this.x.setTextColor(color);
                this.y.setTextColor(color);
                this.z.setTextColor(color);
                this.A.setTextColor(color);
            }
        }
        if (this.h != null) {
            this.h.setImageDrawable(eVar.i);
        }
        if (this.i != null) {
            this.i.setImageDrawable(eVar.j);
        }
        if (this.d != null) {
            Drawable drawable6 = eVar.k;
            this.d.setBackground(drawable6);
            this.d.setCompoundDrawables(null, null, com.tw.eq.a.b.a(eVar.l), null);
            com.tw.eq.a.b.a(context, iVar.b.j, this.d);
            ViewGroup.LayoutParams layoutParams = this.d.getLayoutParams();
            if (drawable6 != null) {
                layoutParams.width = drawable6.getIntrinsicWidth();
            }
            this.d.setLayoutParams(layoutParams);
        }
        if (this.e != null) {
            this.e.setBackgroundDrawable(eVar.m);
        }
        if (this.q != null) {
            for (int i = 0; i < this.q.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) this.q.getChildAt(i);
                radioButton.setBackground(com.tw.eq.theme.f.a(context, eVar.q));
                com.tw.eq.a.b.a(context, iVar.b.j, radioButton);
            }
        }
    }

    private void c() {
        a(this.r, this.s, this.B);
        a(this.t, this.u, this.C);
        a(this.v, this.w, this.D);
        a(this.x, this.y, this.E);
        a(this.z, this.A, this.F);
        com.tw.eq.e.a.d.b(this.B);
        com.tw.eq.e.a.d.c(this.C);
        com.tw.eq.e.a.d.d(this.D);
        com.tw.eq.e.a.d.e(this.E);
        com.tw.eq.e.a.d.f(this.F);
    }

    static void e(f fVar, int i) {
        switch (i) {
            case 2131230806:
                fVar.B = 0;
                fVar.C = 0;
                fVar.D = 0;
                fVar.E = 0;
                fVar.F = 0;
                fVar.G.a(fVar.G.getZoneMax() / 2, fVar.G.getZoneMax() / 2);
                fVar.c();
                break;
            case 2131230807:
                if (Settings.System.getInt(fVar.getActivity().getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 1) {
                    fVar.B = 1;
                    fVar.C = 4;
                    fVar.D = 0;
                    fVar.E = 3;
                    fVar.G.a(fVar.G.getZoneMax() * 3 / 4, fVar.G.getZoneMax() / 4);
                } else {
                    fVar.B = 4;
                    fVar.C = 1;
                    fVar.D = 3;
                    fVar.E = 0;
                    fVar.G.a(fVar.G.getZoneMax() / 4, fVar.G.getZoneMax() / 4);
                }
                fVar.F = 0;
                fVar.c();
                break;
            case 2131230808:
                if (Settings.System.getInt(fVar.getActivity().getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 1) {
                    fVar.B = 4;
                    fVar.C = 1;
                    fVar.D = 3;
                    fVar.E = 0;
                    fVar.G.a(fVar.G.getZoneMax() / 4, fVar.G.getZoneMax() / 4);
                } else {
                    fVar.B = 1;
                    fVar.C = 4;
                    fVar.D = 0;
                    fVar.E = 3;
                    fVar.G.a(fVar.G.getZoneMax() * 3 / 4, fVar.G.getZoneMax() / 4);
                }
                fVar.F = 0;
                fVar.c();
                break;
            case 2131230810:
                fVar.B = 0;
                fVar.C = 0;
                fVar.D = 4;
                fVar.E = 4;
                fVar.F = 0;
                fVar.G.a(fVar.G.getZoneMax() / 2, (fVar.G.getZoneMax() * 3) / 4);
                fVar.c();
                break;
            case 2131230811:
                fVar.B = 2;
                fVar.C = 0;
                fVar.D = 9;
                fVar.E = 3;
                fVar.F = 0;
                fVar.G.a(fVar.G.getZoneMax() / 4, (fVar.G.getZoneMax() * 3) / 4);
                fVar.c();
                break;
            case 2131230812:
                fVar.B = 0;
                fVar.C = 2;
                fVar.D = 3;
                fVar.E = 9;
                fVar.F = 0;
                fVar.G.a((fVar.G.getZoneMax() * 3) / 4, (fVar.G.getZoneMax() * 3) / 4);
                fVar.c();
                break;
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
        return new com.tw.eq.e.a.d(getActivity());
    }

    @Override
    public final void onClick(View view) {
        switch (view.getId()) {
            case 2131230806:
            case 2131230807:
            case 2131230808:
            case 2131230810:
            case 2131230811:
            case 2131230812:
                Message message = new Message();
                message.what = 10088;
                message.obj = Integer.valueOf(view.getId());
                if (System.currentTimeMillis() - this.H > 500) {
                    this.I.removeMessages(message.what);
                    this.I.sendMessage(message);
                } else {
                    this.I.removeMessages(message.what);
                    this.I.sendMessageDelayed(message, 500L);
                }
                this.H = System.currentTimeMillis();
                break;
            case 2131230830:
                if (this.F > 0) {
                    this.F--;
                }
                c();
                break;
            case 2131230831:
                if (this.F < 40) {
                    this.F++;
                }
                c();
                break;
            case 2131230919:
                if (this.f.getParent() == null) {
                    if (this.d != null) {
                        this.d.getCompoundDrawables()[2].setLevel(1);
                    }
                    if (this.e == null) {
                        this.e = new PopupWindow(this.f, -2, -2);
                    }
                    if (this.b != null) {
                        this.e.setBackgroundDrawable(this.b.d.m);
                    } else {
                        this.e.setBackgroundDrawable(getResources().getDrawable(2131165327));
                    }
                    this.e.setOutsideTouchable(true);
                    this.e.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public final void onDismiss() {
                            f.this.d.getCompoundDrawables()[2].setLevel(0);
                        }
                    });
                    ((RadioButton) this.q.getChildAt(((com.tw.eq.e.a.d) this.a).g)).setChecked(true);
                    this.q.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int i2;
                            switch (i) {
                                case 2131230850:
                                    i2 = 1;
                                    break;
                                case 2131230851:
                                    i2 = 2;
                                    break;
                                case 2131230852:
                                    i2 = 3;
                                    break;
                                case 2131230853:
                                    i2 = 4;
                                    break;
                                case 2131230854:
                                    i2 = 5;
                                    break;
                                default:
                                    i2 = 0;
                                    break;
                            }
                            com.tw.eq.e.a.d.a(i2);
                            f.this.e.dismiss();
                            f.this.d.getCompoundDrawables()[2].setLevel(0);
                        }
                    });
                    this.e.setHeight(this.e.getBackground().getIntrinsicHeight());
                    this.e.setWidth(this.e.getBackground().getIntrinsicWidth());
                    if (this.b != null) {
                        this.e.showAsDropDown(view, this.b.d.r, this.b.d.s);
                    } else {
                        this.e.showAsDropDown(view, 0, 0);
                    }
                }
                break;
        }
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2131361832, (ViewGroup) null);
    }

    @Override
    public final void onPause() {
        super.onPause();
    }

    @Override
    public final void onResume() {
        super.onResume();
        this.G.setSpeakerDrawable(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public final void run() {
                String strA = com.tw.eq.a.d.a("SurroundSoundFragmentX");
                String strA2 = com.tw.eq.a.d.a("SurroundSoundFragmentY");
                if (TextUtils.isEmpty(strA)) {
                    strA = String.valueOf(f.this.G.getMax() / 2);
                }
                if (TextUtils.isEmpty(strA2)) {
                    strA2 = String.valueOf(f.this.G.getMax() / 2);
                }
                f.this.G.setSpeakerDrawable(f.this.getResources().getDrawable(2131165421));
                f.this.G.a(Integer.parseInt(strA), Integer.parseInt(strA2));
            }
        }, 100L);
    }

    @Override
    public final void onSurroundSpaceMode(int i) {
        if (i < this.g.length && this.d != null) {
            this.d.setText(this.g[i]);
        }
    }

    @Override
    public final void onSurroundSpaceValue(int i, int i2, int i3, int i4, int i5) {
        if (this.c) {
            this.c = false;
            this.B = i;
            this.C = i2;
            this.D = i3;
            this.E = i4;
            this.F = i5;
            c();
        }
    }

    @Override
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Log.d("HYH", "SurroundSoundFragment onViewCreated");
        this.r = (TextView) view.findViewById(2131230953);
        this.s = (TextView) view.findViewById(2131230952);
        this.t = (TextView) view.findViewById(2131230957);
        this.u = (TextView) view.findViewById(2131230956);
        this.v = (TextView) view.findViewById(2131230951);
        this.w = (TextView) view.findViewById(2131230950);
        this.x = (TextView) view.findViewById(2131230955);
        this.y = (TextView) view.findViewById(2131230954);
        this.z = (TextView) view.findViewById(2131230949);
        this.A = (TextView) view.findViewById(2131230948);
        this.h = (ImageView) view.findViewById(2131230831);
        this.h.setOnClickListener(this);
        this.i = (ImageView) view.findViewById(2131230830);
        this.i.setOnClickListener(this);
        this.j = view.findViewById(2131230809);
        this.k = (Button) view.findViewById(2131230807);
        this.k.setOnClickListener(this);
        this.l = (Button) view.findViewById(2131230808);
        this.l.setOnClickListener(this);
        this.m = (Button) view.findViewById(2131230811);
        this.m.setOnClickListener(this);
        this.n = (Button) view.findViewById(2131230810);
        this.n.setOnClickListener(this);
        this.o = (Button) view.findViewById(2131230812);
        this.o.setOnClickListener(this);
        this.p = (Button) view.findViewById(2131230806);
        this.p.setOnClickListener(this);
        this.G = (EqSurroundView) view.findViewById(2131230759);
        this.G.setOnFadingValueChangeListener(new EqSurroundView.OnValueChangeListener() {
            boolean a = true;

            @Override
            public final void onHornDelayChanged(int i, int i2, int i3, int i4, boolean z) {
                if (z) {
                    f.this.B = i;
                    f.this.C = i2;
                    f.this.D = i3;
                    f.this.E = i4;
                    f.this.c();
                }
            }

            @Override
            public final void onValueChanged(int i, int i2, boolean z) {
                if (this.a) {
                    this.a = false;
                } else {
                    com.tw.eq.a.d.a("SurroundSoundFragmentX", String.valueOf(i));
                    com.tw.eq.a.d.a("SurroundSoundFragmentY", String.valueOf(i2));
                }
            }
        });
        this.d = (TextView) view.findViewById(2131230919);
        if (this.d != null) {
            this.d.setOnClickListener(this);
            ViewGroup.LayoutParams layoutParams = this.d.getLayoutParams();
            layoutParams.width = this.d.getBackground().getIntrinsicWidth();
            this.d.setLayoutParams(layoutParams);
        }
        this.f = LayoutInflater.from(getActivity()).inflate(2131361842, (ViewGroup) null, false);
        this.q = (RadioGroup) this.f.findViewById(2131230858);
        if (this.b != null) {
            b(this.b);
        }
    }
}
