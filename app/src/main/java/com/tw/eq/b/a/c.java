package com.tw.eq.b.a;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.BassFilterView;

public final class c extends a<BassFilterView, com.tw.eq.e.a.b> implements View.OnClickListener, BassFilterView {
    private TextView d;
    private Button e;
    private ImageView f;
    private View g;
    private View h;
    private ImageView i;
    private ImageView j;
    private ImageView k;
    private ImageView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private TextView p;
    private SeekBar r;
    private SeekBar s;
    private TextView t;
    private final String[] c = {"PASS", "25Hz", "31.5Hz", "40Hz", "50Hz", "63Hz", "80Hz", "100Hz", "125Hz", "160Hz", "200Hz", "250Hz"};
    private final SeekBar.OnSeekBarChangeListener q = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                int id = seekBar.getId();
                if (id == 2131230910) {
                    com.tw.eq.e.a.b.a(i);
                } else if (id == 2131230912) {
                    com.tw.eq.e.a.b.b(i);
                }
            }
        }

        @Override
        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public final void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void b(i iVar) {
        com.tw.eq.d.c cVar = iVar.g;
        Context context = iVar.a.b;
        int color = iVar.b.i;
        if (this.e != null) {
            this.e.setBackground(cVar.c);
            this.e.setCompoundDrawables(com.tw.eq.a.b.a(cVar.b), null, null, null);
            com.tw.eq.a.b.a(context, iVar.b.j, this.e);
        }
        if (this.f != null) {
            this.f.setImageDrawable(cVar.a);
        }
        if (this.g != null) {
            this.g.setBackground(cVar.d);
        }
        if (this.h != null) {
            this.h.setBackground(cVar.d);
        }
        if (this.r != null) {
            this.r.setThumb(com.tw.eq.theme.f.a(context, cVar.i));
            this.r.setBackground(com.tw.eq.theme.f.a(context, cVar.g));
            this.r.setProgressDrawableTiled(com.tw.eq.theme.f.a(context, cVar.h));
            this.r.setThumbOffset(cVar.j);
        }
        if (this.s != null) {
            this.s.setThumb(com.tw.eq.theme.f.a(context, cVar.i));
            this.s.setBackground(com.tw.eq.theme.f.a(context, cVar.g));
            this.s.setProgressDrawableTiled(com.tw.eq.theme.f.a(context, cVar.h));
            this.s.setThumbOffset(cVar.j);
        }
        if (this.i != null) {
            this.i.setImageDrawable(cVar.f);
        }
        if (this.j != null) {
            this.j.setImageDrawable(cVar.e);
        }
        if (this.k != null) {
            this.k.setImageDrawable(cVar.f);
        }
        if (this.l != null) {
            this.l.setImageDrawable(cVar.e);
        }
        if (this.m != null && this.n != null) {
            this.m.setVisibility(cVar.k == 0 ? 8 : 0);
            this.m.setTextColor(color);
            this.n.setVisibility(cVar.k == 0 ? 8 : 0);
            this.n.setTextColor(color);
        }
        if (this.o != null && this.p != null) {
            this.o.setTextColor(color);
            this.p.setTextColor(color);
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
        return new com.tw.eq.e.a.b(getActivity());
    }

    @Override
    public final void onBassFrontValueChange(int i) {
        if (i >= 0 && i <= this.r.getMax()) {
            this.r.setProgress(i);
        }
        if (this.t != null) {
            this.t.setText(this.c[i]);
        }
    }

    @Override
    public final void onBassRearValueChange(int i) {
        if (i >= 0 && i <= this.s.getMax()) {
            this.s.setProgress(i);
        }
        if (this.d != null) {
            this.d.setText(this.c[i]);
        }
    }

    @Override
    public final void onClick(View view) {
        if (view.getId() == 2131230751) {
            com.tw.eq.e.a.b.a(0);
            com.tw.eq.e.a.b.b(0);
        }
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(2131361829, (ViewGroup) null);
        this.e = (Button) viewInflate.findViewById(2131230751);
        this.e.setOnClickListener(this);
        this.t = (TextView) viewInflate.findViewById(2131230821);
        this.d = (TextView) viewInflate.findViewById(2131230887);
        this.r = (SeekBar) viewInflate.findViewById(2131230910);
        this.r.setOnSeekBarChangeListener(this.q);
        this.s = (SeekBar) viewInflate.findViewById(2131230912);
        this.s.setOnSeekBarChangeListener(this.q);
        this.f = (ImageView) viewInflate.findViewById(2131230759);
        this.g = viewInflate.findViewById(2131230819);
        this.h = viewInflate.findViewById(2131230885);
        this.i = (ImageView) viewInflate.findViewById(2131230820);
        this.j = (ImageView) viewInflate.findViewById(2131230822);
        this.k = (ImageView) viewInflate.findViewById(2131230886);
        this.l = (ImageView) viewInflate.findViewById(2131230888);
        this.m = (TextView) viewInflate.findViewById(2131230817);
        this.n = (TextView) viewInflate.findViewById(2131230883);
        this.o = (TextView) viewInflate.findViewById(2131230818);
        this.p = (TextView) viewInflate.findViewById(2131230884);
        return viewInflate;
    }

    @Override
    public final void onResume() {
        super.onResume();
    }

    @Override
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.b != null) {
            b(this.b);
        }
    }
}
