package com.tw.eq.b.a;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VerticalSeekBar;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.BassBoostView;
import com.tw.eq.widget.PickerView;
import java.util.ArrayList;

public final class b extends a<BassBoostView, com.tw.eq.e.a.a> implements View.OnClickListener, BassBoostView {
    private Button e;
    private ImageView f;
    private View g;
    private View h;
    private TextView i;
    private TextView j;
    private VerticalSeekBar l;
    private VerticalSeekBar m;
    private PickerView n;
    private PickerView o;
    private final int[] d = {2131427386, 2131427387, 2131427388, 2131427389, 2131427390, 2131427391, 2131427392, 2131427393};
    ArrayList<String> c = new ArrayList<>();
    private final VerticalSeekBar.OnSeekBarChangeListener k = new VerticalSeekBar.OnSeekBarChangeListener() {
        public final void onProgressChanged(VerticalSeekBar verticalSeekBar, int i, boolean z) {
        }

        public final void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
        }

        public final void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
            int id = verticalSeekBar.getId();
            if (id == 2131230911) {
                com.tw.eq.e.a.a.a(verticalSeekBar.getProgress());
            } else if (id == 2131230913) {
                com.tw.eq.e.a.a.c(verticalSeekBar.getProgress());
            }
        }
    };

    private void b(i iVar) {
        com.tw.eq.d.b bVar = iVar.e;
        Context context = iVar.a.b;
        if (this.e != null) {
            this.e.setBackground(bVar.h);
            com.tw.eq.a.b.a(context, iVar.b.j, this.e);
        }
        if (this.f != null) {
            this.f.setImageDrawable(bVar.a);
        }
        if (this.g != null) {
            this.g.setBackground(bVar.b);
        }
        if (this.h != null) {
            this.h.setBackground(bVar.c);
        }
        if (this.l != null) {
            this.l.setThumb(bVar.e);
            this.l.setProgressDrawableTiled(bVar.d);
            this.l.setThumbOffset(bVar.i);
        }
        if (this.m != null) {
            this.m.setThumb(bVar.g);
            this.m.setProgressDrawableTiled(bVar.f);
            this.m.setThumbOffset(bVar.i);
        }
        int color = iVar.b.i;
        if (this.i != null) {
            this.i.setTextColor(color);
        }
        if (this.j != null) {
            this.j.setTextColor(color);
        }
        ColorStateList colorStateListB = com.tw.eq.theme.f.b(context, iVar.b.j);
        if (this.n != null) {
            this.n.a(color, colorStateListB.getChangingConfigurations());
        }
        if (this.o != null) {
            this.o.a(color, colorStateListB.getChangingConfigurations());
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
        return new com.tw.eq.e.a.a(getActivity());
    }

    @Override
    public final void onClick(View view) {
        if (view.getId() == 2131230751) {
            com.tw.eq.e.a.a.a(0);
            com.tw.eq.e.a.a.b(0);
            com.tw.eq.e.a.a.c(0);
            com.tw.eq.e.a.a.d(0);
        }
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(2131361828, (ViewGroup) null);
        this.e = (Button) viewInflate.findViewById(2131230751);
        this.e.setOnClickListener(this);
        this.l = viewInflate.findViewById(2131230911);
        this.l.setOnSeekBarChangeListener(this.k);
        this.m = viewInflate.findViewById(2131230913);
        this.m.setOnSeekBarChangeListener(this.k);
        this.n = (PickerView) viewInflate.findViewById(2131230752);
        this.o = (PickerView) viewInflate.findViewById(2131230753);
        this.f = (ImageView) viewInflate.findViewById(2131230759);
        this.g = viewInflate.findViewById(2131230976);
        this.h = viewInflate.findViewById(2131230967);
        this.i = (TextView) viewInflate.findViewById(2131230817);
        this.j = (TextView) viewInflate.findViewById(2131230883);
        for (int i = 0; i < this.d.length; i++) {
            this.c.add(getResources().getString(this.d[i]));
        }
        this.n.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public final void onSelect(PickerView pickerView, String str, int i2) {
                for (int i3 = 0; i3 < b.this.d.length; i3++) {
                    if (b.this.getActivity().getResources().getString(b.this.d[i3]).equals(str)) {
                        com.tw.eq.e.a.a.b(i3);
                    }
                }
            }
        });
        this.o.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public final void onSelect(PickerView pickerView, String str, int i2) {
                for (int i3 = 0; i3 < b.this.d.length; i3++) {
                    if (b.this.getActivity().getResources().getString(b.this.d[i3]).equals(str)) {
                        com.tw.eq.e.a.a.d(i3);
                    }
                }
            }
        });
        this.n.setData(this.c);
        this.o.setData(this.c);
        return viewInflate;
    }

    @Override
    public final void onFrontBassBoost(int i) {
        if (this.l != null && i <= this.l.getMax()) {
            this.l.setProgress(i);
        }
    }

    @Override
    public final void onFrontBassFreq(int i) {
        if (i >= 0 && i < this.c.size()) {
            this.n.setSelected(this.c.get(i));
        }
    }

    @Override
    public final void onRearBassBoost(int i) {
        if (this.m != null && i <= this.m.getMax()) {
            this.m.setProgress(i);
        }
    }

    @Override
    public final void onRearBassFreq(int i) {
        if (i >= 0 && i < this.c.size()) {
            this.o.setSelected(this.c.get(i));
        }
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
