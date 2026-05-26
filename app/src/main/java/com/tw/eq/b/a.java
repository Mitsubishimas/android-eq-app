package com.tw.eq.b;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.tw.eq.a.c;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.EQView;
import com.tw.eq.widget.EqScaleView;
import java.util.ArrayList;
import java.util.List;

public final class a extends com.tw.eq.b.a.a<EQView, com.tw.eq.e.b> implements View.OnClickListener, EQView {
    private View c;
    private View e;
    private ImageView f;
    private ImageView g;
    private ImageView h;
    private ImageView i;
    private HorizontalScrollView j;
    private final List<EqScaleView> d = new ArrayList();
    private final View.OnScrollChangeListener k = new View.OnScrollChangeListener() {
        private int b;

        @Override
        public final void onScrollChange(View view, int scrollX, int i, int i3, int i4) {
            this.b = scrollX;
            if (scrollX >= 666 && scrollX < 900) {
                a.this.f.setImageResource(2131165324);
                a.this.g.setImageResource(2131165323);
                a.this.h.setImageResource(2131165324);
                a.this.i.setImageResource(2131165324);
            } else if (scrollX >= 900 && scrollX < 1500) {
                a.this.f.setImageResource(2131165324);
                a.this.g.setImageResource(2131165324);
                a.this.h.setImageResource(2131165323);
                a.this.i.setImageResource(2131165324);
            } else if (scrollX >= 1500) {
                a.this.f.setImageResource(2131165324);
                a.this.g.setImageResource(2131165324);
                a.this.h.setImageResource(2131165324);
                a.this.i.setImageResource(2131165323);
            } else {
                a.this.f.setImageResource(2131165323);
                a.this.g.setImageResource(2131165324);
                a.this.h.setImageResource(2131165324);
                a.this.i.setImageResource(2131165324);
            }
        }
    };

    @Override
    public final BaseView a() {
        return this;
    }

    @Override
    public final com.tw.eq.e.a b() {
        return new com.tw.eq.e.b(getActivity());
    }

    @Override
    public final void onClick(View view) {
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.e = layoutInflater.inflate(2131361830, (ViewGroup) null, false);
        return this.e;
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
    }

    @Override
    public final void onEqMode(int i) {
    }

    @Override
    public final void onEqValueChange(byte[] bArr) {
    }

    @Override
    public final void onEqValueChange(int[] iArr) {
        for (int i = 0; i < this.d.size(); i++) {
            EqScaleView eqScaleView = this.d.get(i);
            if (!eqScaleView.b) {
                eqScaleView.a(iArr[i], 0);
            }
        }
    }

    @Override
    public final void onEqValueChange(int[] iArr, int[] iArr2) {
    }

    @Override
    public final void onPause() {
        super.onPause();
        com.tw.eq.c.b bVarF = ((com.tw.eq.e.b) this.a).f();
        for (int i = 0; i < bVarF.d.length; i++) {
            c.a(bVarF.a, "EQ_VALUE_USER", "eqmode_value" + i, bVarF.d[i]);
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
    }

    @Override
    public final void onViewCreated(final View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.c = LayoutInflater.from(getActivity()).inflate(2131361824, (ViewGroup) null, false);
        final View viewFindViewById = view.findViewById(2131230870);
        this.f = (ImageView) view.findViewById(2131230866);
        this.g = (ImageView) view.findViewById(2131230867);
        this.h = (ImageView) view.findViewById(2131230868);
        this.i = (ImageView) view.findViewById(2131230869);
        new Handler().postDelayed(new Runnable() {
            @Override
            public final void run() {
                if (com.tw.eq.a.a.b == 12) {
                    View viewInflate = LayoutInflater.from(a.this.getActivity()).inflate(2131361825, (ViewGroup) null, false);
                    if (view instanceof RelativeLayout) {
                        ((RelativeLayout) view).addView(viewInflate);
                    } else if (view instanceof LinearLayout) {
                        ((LinearLayout) view).addView(viewInflate);
                    }
                    ViewGroup group = (ViewGroup) view.findViewById(2131230800);
                    int childCount = group.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = group.getChildAt(i);
                        if (childAt instanceof EqScaleView) {
                            EqScaleView eqScaleView = (EqScaleView) childAt;
                            a.this.d.add(eqScaleView);
                            eqScaleView.setValueChangeListener(new EqScaleView.OnValueChangeListener() {
                                @Override
                                public final void onValueChanged(EqScaleView eqScaleView2, int i2, int i3, int i4, int i5, int i6, boolean z) {
                                    if (z) {
                                        com.tw.eq.e.b bVar = (com.tw.eq.e.b) a.this.a;
                                        if (com.tw.eq.a.a.b == 12) {
                                            com.tw.eq.e.b.d.write(257, i2 + 7, i4);
                                        } else {
                                            com.tw.eq.e.b.d.write(257, i2 + 7, i4 / 4);
                                        }
                                        com.tw.eq.e.b.d.write(257, i2, i3);
                                    }
                                }
                            });
                        }
                    }
                    for (int i2 = 0; i2 < a.this.d.size(); i2++) {
                        ((EqScaleView) a.this.d.get(i2)).setTitle(com.tw.eq.a.a.i[i2]);
                    }
                    viewFindViewById.setVisibility(8);
                } else {
                    View viewInflate2 = LayoutInflater.from(a.this.getActivity()).inflate(2131361826, (ViewGroup) null, false);
                    if (view instanceof RelativeLayout) {
                        ((RelativeLayout) view).addView(viewInflate2);
                    } else if (view instanceof LinearLayout) {
                        ((LinearLayout) view).addView(viewInflate2);
                    }
                    ViewGroup group2 = (ViewGroup) view.findViewById(2131230800);
                    int childCount2 = group2.getChildCount();
                    for (int i3 = 0; i3 < childCount2; i3++) {
                        View childAt2 = group2.getChildAt(i3);
                        if (childAt2 instanceof EqScaleView) {
                            EqScaleView eqScaleView2 = (EqScaleView) childAt2;
                            a.this.d.add(eqScaleView2);
                            eqScaleView2.setValueChangeListener(new EqScaleView.OnValueChangeListener() {
                                @Override
                                public final void onValueChanged(EqScaleView eqScaleView2, int i2, int i3, int i4, int i5, int i6, boolean z) {
                                    if (z) {
                                        com.tw.eq.e.b bVar = (com.tw.eq.e.b) a.this.a;
                                        if (com.tw.eq.a.a.b == 12) {
                                            com.tw.eq.e.b.d.write(257, i2 + 7, i4);
                                        } else {
                                            com.tw.eq.e.b.d.write(257, i2 + 7, i4 / 4);
                                        }
                                        com.tw.eq.e.b.d.write(257, i2, i3);
                                    }
                                }
                            });
                        }
                    }
                    for (int i4 = 0; i4 < a.this.d.size(); i4++) {
                        ((EqScaleView) a.this.d.get(i4)).setTitle(com.tw.eq.a.a.h[i4]);
                    }
                    viewFindViewById.setVisibility(0);
                    a.this.j = (HorizontalScrollView) view.findViewById(2131230798);
                    a.this.j.setOnScrollChangeListener(a.this.k);
                }
                ((com.tw.eq.e.b) a.this.a).b();
            }
        }, 200L);
    }
}
