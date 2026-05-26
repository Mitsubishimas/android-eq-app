package com.tw.eq.b.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VerticalSeekBar;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.EQView;
import com.tw.eq.widget.EqScaleView;
import com.tw.eq.widget.LineGraphicView;
import com.tw.eq.widget.Workspace;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"InflateParams"})
public final class d extends a<EQView, com.tw.eq.e.a.c> implements View.OnClickListener, EQView {
    private View e;
    private View g;
    private TextView h;
    private LineGraphicView i;
    private int j;
    private RadioGroup k;
    private Context l;
    private i m;
    private View n;
    private ImageView o;
    private ImageView[] p;
    private Workspace q;
    private final int[] d = {2131427429, 2131427395, 2131427384, 2131427396, 2131427376, 2131427405, 2131427407, 2131427430, 2131427383};
    private final List<EqScaleView> f = new ArrayList();
    long c = 0;
    private final Handler r = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 10086) {
                return;
            }
            com.tw.eq.e.a.c.a(((Integer) message.obj).intValue());
        }
    };

    private void a(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof EqScaleView) {
                EqScaleView eqScaleView = (EqScaleView) childAt;
                this.f.add(eqScaleView);
                eqScaleView.setValueChangeListener(new EqScaleView.OnValueChangeListener() {
                    @Override
                    public final void onValueChanged(EqScaleView eqScaleView2, int i2, int i3, int i4, int i5, int i6, boolean z) {
                        if (z) {
                            com.tw.eq.e.a.c cVar = (com.tw.eq.e.a.c) d.this.a;
                            cVar.k.removeMessages(65297);
                            Message messageObtain = Message.obtain();
                            messageObtain.what = 65297;
                            messageObtain.obj = new int[]{i2, i3 + 2, i5, i6, i4};
                            cVar.k.sendMessageDelayed(messageObtain, 100L);
                        }
                    }
                });
            }
        }
    }

    private void a(boolean z) {
        for (int i = 0; i < this.f.size(); i++) {
            this.f.get(i).getQView().setClickable(z);
        }
    }

    private void b(i iVar) {
        this.l = iVar.a.b;
        com.tw.eq.d.d dVar = iVar.c;
        if (this.n != null) {
            this.n.setBackground(dVar.a);
            if (dVar.a != null) {
                ViewGroup.LayoutParams layoutParams = this.n.getLayoutParams();
                layoutParams.width = dVar.a.getIntrinsicWidth();
                this.n.setLayoutParams(layoutParams);
            }
        }
        if (this.k != null) {
            for (int i = 0; i < this.k.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) this.k.getChildAt(i);
                radioButton.setBackground(com.tw.eq.theme.f.a(this.l, "btn_mode_item_bg"));
                ColorStateList colorStateListB = com.tw.eq.theme.f.b(this.l, iVar.b.j);
                if (colorStateListB != null) {
                    radioButton.setTextColor(colorStateListB);
                }
            }
        }
        if (this.h != null) {
            this.h.setCompoundDrawables(com.tw.eq.a.b.a(dVar.c), null, null, null);
            this.h.setBackground(dVar.b);
            ColorStateList colorStateListB2 = com.tw.eq.theme.f.b(this.l, iVar.b.j);
            if (colorStateListB2 != null) {
                this.h.setTextColor(colorStateListB2);
            }
        }
        if (this.i != null) {
            this.i.setBackground(dVar.e);
            this.i.setVisibility(dVar.m == 1 ? 0 : 4);
        }
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            EqScaleView eqScaleView = this.f.get(i2);
            VerticalSeekBar seekBar = eqScaleView.getSeekBar();
            seekBar.setThumb(com.tw.eq.theme.f.a(this.l, "thumb"));
            seekBar.setThumbOffset(dVar.l);
            seekBar.setProgressDrawableTiled(com.tw.eq.theme.f.a(this.l, "eq_progress"));
            seekBar.setBackground(dVar.f);
            int color = iVar.b.i;
            if (color != 0) {
                eqScaleView.getQView().setTextColor(color);
                eqScaleView.getFCView().setTextColor(color);
            }
            eqScaleView.getQView().setBackground(dVar.g);
            eqScaleView.getFCView().setBackground(dVar.g);
            eqScaleView.setThemeSwitchInfo(iVar);
        }
        if (this.o != null) {
            this.o.setImageDrawable(dVar.d);
        }
        if (this.p != null) {
            for (int i3 = 0; i3 < this.p.length; i3++) {
                this.p[i3].setImageDrawable(com.tw.eq.theme.f.a(this.l, dVar.n));
            }
            Drawable drawable = this.p[0].getDrawable();
            if (drawable != null) {
                drawable.setLevel(1);
            }
        }
    }

    @Override
    public final BaseView a() {
        return this;
    }

    @Override
    public final void a(i iVar) {
        super.a(iVar);
        this.m = iVar;
        b(iVar);
    }

    @Override
    public final com.tw.eq.e.a b() {
        return new com.tw.eq.e.a.c(getActivity());
    }

    @Override
    public final void onClick(View view) {
        if (view.getId() == 2131230799) {
            ((com.tw.eq.e.a.c) this.a).h();
            com.tw.eq.e.a.c.a(1);
        }
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.j = getActivity().getResources().getDisplayMetrics().widthPixels;
        this.g = layoutInflater.inflate(2131361831, (ViewGroup) null, false);
        return this.g;
    }

    @Override
    public final void onEqMode(int i) {
        int i2;
        if (i >= 6) {
            i2 = i - 5;
        } else {
            i2 = i - 1;
            if (i2 < 6) {
                i2 = 0;
            }
        }
        if (this.k != null && i2 >= 0 && i2 < this.k.getChildCount()) {
            ((RadioButton) this.k.getChildAt(i2)).setChecked(true);
        }
        a(i2 == 0);
    }

    @Override
    public final void onEqValueChange(byte[] bArr) {
    }

    @Override
    public final void onEqValueChange(int[] iArr) {
    }

    @Override
    public final void onEqValueChange(int[] iArr, int[] iArr2) {
        for (int i = 0; i < iArr.length; i++) {
            this.f.get(i).a(iArr[i] - 2, iArr2[i]);
            if (this.i != null) {
                this.i.getDate().set(i + 1, Integer.valueOf(((iArr[i] - 2) * this.i.getHeight()) / 24));
            }
        }
        if (this.i != null) {
            this.i.setItemChange(this.i.getDate());
        }
    }

    @Override
    public final void onPause() {
        super.onPause();
    }

    @Override
    public final void onResume() {
        super.onResume();
    }

    @Override
    public final void onViewCreated(final View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public final void run() {
                ((LinearLayout) d.this.getView()).addView(LayoutInflater.from(d.this.getActivity()).inflate(2131361827, (ViewGroup) null, false));
                
                d.this.e = LayoutInflater.from(d.this.getActivity()).inflate(2131361824, (ViewGroup) null, false);
                d.this.h = (TextView) view.findViewById(2131230799);
                d.this.h.setOnClickListener(d.this);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(2131230793);
                LinearLayout linearLayout2 = (LinearLayout) view.findViewById(2131230794);
                LinearLayout linearLayout3 = (LinearLayout) view.findViewById(2131230795);
                d.this.a(linearLayout);
                d.this.a(linearLayout2);
                d.this.a(linearLayout3);
                for (int i = 0; i < d.this.f.size(); i++) {
                    EqScaleView eqScaleView = d.this.f.get(i);
                    String str = com.tw.eq.a.a.k[i];
                    int freq = com.tw.eq.a.a.l[i];
                    if (eqScaleView.a != null) {
                        eqScaleView.a.setText(str);
                        eqScaleView.d = freq;
                    }
                }
                SharedPreferences.Editor editorEdit = d.this.getActivity().getSharedPreferences("iseq", 0).edit();
                editorEdit.putBoolean("isNorma2", true);
                editorEdit.commit();
                d.this.q = (Workspace) view.findViewById(2131230965);
                d.this.p = new ImageView[]{(ImageView) view.findViewById(2131230866), (ImageView) view.findViewById(2131230867), (ImageView) view.findViewById(2131230868)};
                d.this.q.setScreenSwitchListener(new Workspace.ScreenSwitchListener() {
                    @Override
                    public final void onScreenSwitch(View view2, int i3) {
                        if (d.this.p != null) {
                            int childCount = d.this.q.getChildCount();
                            for (int i4 = 0; i4 < childCount; i4++) {
                                Drawable drawable = d.this.p[i4].getDrawable();
                                if (drawable != null) {
                                    drawable.setLevel(i4 == i3 ? 1 : 0);
                                }
                            }
                        }
                    }
                });
                d.this.i = (LineGraphicView) view.findViewById(2131230801);
                int dimension = (int) d.this.getResources().getDimension(2131099793);
                LineGraphicView lineGraphicView = d.this.i;
                lineGraphicView.d.clear();
                for (int i2 = 0; i2 < 50; i2++) {
                    lineGraphicView.d.add(Integer.valueOf(dimension));
                }
                lineGraphicView.d.set(0, Integer.valueOf(dimension * 2));
                lineGraphicView.d.set(49, Integer.valueOf(dimension * 2));
                lineGraphicView.e.clear();
                int size = lineGraphicView.b / lineGraphicView.d.size();
                for (int i3 = 0; i3 < lineGraphicView.d.size(); i3++) {
                    lineGraphicView.e.add(Integer.valueOf(lineGraphicView.c + (size * i3)));
                }
                lineGraphicView.invalidate();
                d.this.n = view.findViewById(2131230803);
                d.this.k = (RadioGroup) view.findViewById(2131230858);
                if (d.this.k.getBackground() != null) {
                    ViewGroup.LayoutParams layoutParams = d.this.n.getLayoutParams();
                    layoutParams.width = d.this.k.getBackground().getIntrinsicWidth();
                    d.this.n.setLayoutParams(layoutParams);
                }
                if (d.this.k != null) {
                    int iG = ((com.tw.eq.e.a.c) d.this.a).g();
                    if (iG >= 0 && iG < d.this.k.getChildCount()) {
                        ((RadioButton) d.this.k.getChildAt(iG)).setChecked(true);
                    }
                    d.this.k.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public final void onCheckedChanged(RadioGroup radioGroup, int i5) {
                            int i6;
                            switch (i5) {
                                case 2131230849:
                                    i6 = 1;
                                    break;
                                case 2131230850:
                                    i6 = 6;
                                    break;
                                case 2131230851:
                                    i6 = 7;
                                    break;
                                case 2131230852:
                                    i6 = 8;
                                    break;
                                case 2131230853:
                                    i6 = 9;
                                    break;
                                case 2131230854:
                                    i6 = 10;
                                    break;
                                case 2131230855:
                                    i6 = 11;
                                    break;
                                case 2131230856:
                                    i6 = 12;
                                    break;
                                case 2131230857:
                                    i6 = 13;
                                    break;
                                default:
                                    i6 = 0;
                                    break;
                            }
                            Message message = new Message();
                            message.what = 10086;
                            message.obj = Integer.valueOf(i6);
                            if (System.currentTimeMillis() - d.this.c > 500) {
                                d.this.r.removeMessages(message.what);
                                d.this.r.sendMessage(message);
                            } else {
                                d.this.r.removeMessages(message.what);
                                d.this.r.sendMessageDelayed(message, 500L);
                            }
                            d.this.c = System.currentTimeMillis();
                        }
                    });
                }
                d.this.a(((com.tw.eq.e.a.c) d.this.a).g() == 0);
                d.this.o = (ImageView) view.findViewById(2131230802);
                ((com.tw.eq.e.a.c) d.this.a).b();
                if (d.this.m != null) {
                    d.this.b(d.this.m);
                }
            }
        }, 10L);
    }
}
