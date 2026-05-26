package com.tw.eq.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tw.eq.b.b;
import java.util.ArrayList;

public class NormalActivity extends a {
    private RadioButton c;
    private RadioButton d;
    private int e;
    private com.tw.eq.b.a g;
    private b h;
    private RadioGroup i;
    private FragmentManager j;
    private Fragment l;
    private final ArrayList<Fragment> f = new ArrayList<>();
    private final Handler k = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            if (message.what != 257) {
                return;
            }
            byte[] bArr = (byte[]) message.obj;
            NormalActivity.this.e = bArr[0] - 1;
        }
    };
    private final RadioGroup.OnCheckedChangeListener m = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public final void onCheckedChanged(RadioGroup radioGroup, int i) {
            Log.d("HYH", "eqZoneListener:");
            if (i == 2131230880) {
                if (NormalActivity.this.g == null) {
                    NormalActivity.this.g = new com.tw.eq.b.a();
                }
                NormalActivity.this.a(NormalActivity.this.g);
                NormalActivity.b(NormalActivity.this, NormalActivity.this.g);
            } else if (i == 2131230882) {
                if (NormalActivity.this.h == null) {
                    NormalActivity.this.h = new b();
                }
                NormalActivity.this.a(NormalActivity.this.h);
                NormalActivity.b(NormalActivity.this, NormalActivity.this.h);
            }
        }
    };
    private final RadioGroup.OnCheckedChangeListener n = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public final void onCheckedChanged(RadioGroup radioGroup, int i) {
            Log.d("HYH", "eqModeListener:" + NormalActivity.this.findViewById(i).isPressed());
            if (NormalActivity.this.findViewById(i).isPressed()) {
                switch (i) {
                    case 2131230850:
                        NormalActivity.a(1);
                        break;
                    case 2131230851:
                        NormalActivity.a(2);
                        break;
                    case 2131230852:
                        NormalActivity.a(3);
                        break;
                    case 2131230853:
                        NormalActivity.a(4);
                        break;
                    case 2131230854:
                        NormalActivity.a(5);
                        break;
                    case 2131230855:
                        NormalActivity.a(6);
                        break;
                    case 2131230856:
                        NormalActivity.a(7);
                        break;
                    case 2131230857:
                        NormalActivity.a(8);
                        break;
                }
            }
        }
    };

    public static void a(int i) {
        if (b != null) {
            b.write(257, 0, i);
        }
    }

    private void a(Fragment fragment) {
        if (fragment.isAdded()) {
            return;
        }
        this.j.beginTransaction().add(2131230846, fragment).commit();
        this.f.add(fragment);
    }

    static void b(NormalActivity normalActivity, Fragment fragment) {
        if (fragment == null) {
            if (normalActivity.g == null) {
                normalActivity.g = new com.tw.eq.b.a();
            }
            normalActivity.a(normalActivity.g);
            fragment = normalActivity.g;
        }
        for (Fragment fragment2 : normalActivity.f) {
            if (fragment2 != fragment) {
                normalActivity.j.beginTransaction().hide(fragment2).commit();
            }
        }
        normalActivity.j.beginTransaction().show(fragment).commit();
        normalActivity.l = fragment;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131361820);
        this.j = getFragmentManager();
        this.c = (RadioButton) findViewById(2131230880);
        this.d = (RadioButton) findViewById(2131230882);
        ((RadioGroup) findViewById(2131230889)).setOnCheckedChangeListener(this.m);
        this.c.setChecked(true);
        this.i = (RadioGroup) findViewById(2131230858);
        this.i.setOnCheckedChangeListener(this.n);
        if (b != null) {
            b.addHandler("NormalActivity", this.k);
            b.write(257, 255);
            b.write(266, 255);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.k.postDelayed(new Runnable() {
            @Override
            public final void run() {
                if (NormalActivity.this.i != null && NormalActivity.this.e >= 0 && NormalActivity.this.e < NormalActivity.this.i.getChildCount()) {
                    ((RadioButton) NormalActivity.this.i.getChildAt(NormalActivity.this.e)).setChecked(true);
                }
            }
        }, 500L);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
    }
}
