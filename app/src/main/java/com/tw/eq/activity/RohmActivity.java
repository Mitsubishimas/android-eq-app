package com.tw.eq.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tw.eq.b.a.b;
import com.tw.eq.b.a.c;
import com.tw.eq.b.a.d;
import com.tw.eq.b.a.e;
import com.tw.eq.b.a.f;
import com.tw.eq.b.a.g;
import com.tw.eq.theme.IThemeSwitchStatus;
import com.tw.eq.theme.g;
import com.tw.eq.theme.i;
import java.io.File;
import java.util.ArrayList;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class RohmActivity extends a {
    public static boolean c = true;
    RadioButton d;
    RadioButton e;
    RadioButton f;
    RadioButton g;
    RadioButton h;
    RadioButton master;
    protected d i;
    protected f j;
    protected b k;
    protected e l;
    protected c m;
    protected g n;  // MasterMitsubishi фрагмент
    RadioGroup o;
    private FragmentManager p;
    private Fragment r;
    private final ArrayList<Fragment> q = new ArrayList<>();
    private final String s = "/data/tw/theme/default/EQTheme.apk";
    private final String t = "notify_theme_change";
    private final RadioGroup.OnCheckedChangeListener u = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public final void onCheckedChanged(RadioGroup radioGroup, int id) {
            switch (id) {
                case 2131230878:  // tab_bass
                    if (RohmActivity.this.k == null) {
                        RohmActivity.this.k = new b();
                    }
                    RohmActivity.this.a(RohmActivity.this.k);
                    RohmActivity.this.b(RohmActivity.this.k);
                    break;
                case 2131230879:  // tab_filter
                    if (RohmActivity.this.m == null) {
                        RohmActivity.this.m = new c();
                    }
                    RohmActivity.this.a(RohmActivity.this.m);
                    RohmActivity.this.b(RohmActivity.this.m);
                    break;
                case 2131230880:  // tab_eq
                    if (RohmActivity.this.i == null) {
                        RohmActivity.this.i = new d();
                    }
                    RohmActivity.this.a(RohmActivity.this.i);
                    RohmActivity.this.b(RohmActivity.this.i);
                    break;
                case 2131230881:  // tab_surround
                    if (RohmActivity.this.j == null) {
                        RohmActivity.this.j = new f();
                    }
                    RohmActivity.this.a(RohmActivity.this.j);
                    RohmActivity.this.b(RohmActivity.this.j);
                    break;
                case 2131230882:  // tab_zone
                    if (RohmActivity.this.l == null) {
                        RohmActivity.this.l = new e();
                    }
                    RohmActivity.this.a(RohmActivity.this.l);
                    RohmActivity.this.b(RohmActivity.this.l);
                    break;
                case 2131230883:  // tab_master
                    if (RohmActivity.this.n == null) {
                        RohmActivity.this.n = new g();
                    }
                    RohmActivity.this.a(RohmActivity.this.n);
                    RohmActivity.this.b(RohmActivity.this.n);
                    break;
            }
        }
    };
    private final IThemeSwitchStatus v = new IThemeSwitchStatus() {
        @Override
        public final int getOnFinishPriority() { return 0; }
        @Override
        public final int getOnStartPriority() { return 0; }
        @Override
        public final int getOnSwitchingPriority() { return 0; }
        @Override
        public final void onThemeSwitchFinish(i iVar, boolean z) {
            RohmActivity.a(RohmActivity.this, iVar);
        }
        @Override
        public final void onThemeSwitchStart(i iVar) {}
        @Override
        public final boolean onThemeSwitching(i iVar) {
            RohmActivity.this.a = iVar;
            com.tw.eq.d.a aVar = iVar.b;
            RohmActivity.this.findViewById(2131230893).setBackground(aVar.a);
            RohmActivity.this.o.setBackground(aVar.g);
            RohmActivity.a(RohmActivity.this.g, aVar.b, aVar.h);
            RohmActivity.a(RohmActivity.this.h, aVar.c, aVar.h);
            RohmActivity.a(RohmActivity.this.d, aVar.d, aVar.h);
            RohmActivity.a(RohmActivity.this.e, aVar.e, aVar.h);
            RohmActivity.a(RohmActivity.this.f, aVar.f, aVar.h);
            return false;
        }
    };
    public BroadcastReceiver w = new BroadcastReceiver() {
        @Override
        public final void onReceive(Context context, Intent intent) {
            if ("notify_theme_change".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra(IjkMediaMeta.IJKM_KEY_TYPE, 0);
                Log.d("RohmActivity", "onReceive: " + intExtra);
                if (intExtra != 0 && new File("/data/tw/theme/default/EQTheme.apk").exists()) {
                    g.a.a.a(com.tw.eq.theme.f.a("/data/tw/theme/default/EQTheme.apk"));
                    RohmActivity.this.finish();
                }
            }
        }
    };

    private void a(Fragment fragment) {
        if (fragment.isAdded()) return;
        this.p.beginTransaction().add(2131230846, fragment).commit();
        this.q.add(fragment);
    }

    static void a(RadioButton radioButton, Drawable drawable, int i) {
        if (i != 0) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            radioButton.setCompoundDrawables(null, null, null, drawable);
        } else {
            radioButton.setText("");
            radioButton.setCompoundDrawables(null, null, null, null);
            radioButton.setBackground(drawable);
        }
    }

    static void a(RohmActivity rohmActivity, i iVar) {
        if (rohmActivity.i != null) rohmActivity.i.a(iVar);
        if (rohmActivity.j != null) rohmActivity.j.a(iVar);
        if (rohmActivity.k != null) rohmActivity.k.a(iVar);
        if (rohmActivity.l != null) rohmActivity.l.a(iVar);
        if (rohmActivity.m != null) rohmActivity.m.a(iVar);
        if (rohmActivity.n != null) rohmActivity.n.a(iVar);
    }

    private void b(Fragment fragment) {
        if (fragment == null) {
            if (this.i == null) this.i = new d();
            a(this.i);
            fragment = this.i;
        }
        for (Fragment fragment2 : this.q) {
            if (fragment2 != fragment) {
                this.p.beginTransaction().hide(fragment2).commit();
            }
        }
        this.p.beginTransaction().show(fragment).commit();
        this.r = fragment;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFormat(-3);
        setContentView(2131361821);
        this.p = getFragmentManager();
        this.g = (RadioButton) findViewById(2131230880);
        this.h = (RadioButton) findViewById(2131230881);
        this.d = (RadioButton) findViewById(2131230878);
        this.e = (RadioButton) findViewById(2131230882);
        this.f = (RadioButton) findViewById(2131230879);
        this.master = (RadioButton) findViewById(2131230883);
        this.o = (RadioGroup) findViewById(2131230889);
        this.o.setOnCheckedChangeListener(this.u);
        this.g.setChecked(true);
        g gVar = g.a.a;
        if (!gVar.d.contains(this.v)) {
            gVar.d.add(this.v);
            com.tw.eq.theme.a.a("ThemeManager", "registerThemeSwitchStatus : %s, size : %s", this.v, Integer.valueOf(gVar.d.size()));
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("notify_theme_change");
        registerReceiver(this.w, intentFilter);
        Log.d("RohmActivity", "initThemePlugin: " + new File("/data/tw/theme/default/EQTheme.apk").exists());
        if (new File("/data/tw/theme/default/EQTheme.apk").exists()) {
            g.a.a.a(com.tw.eq.theme.f.a("/data/tw/theme/default/EQTheme.apk"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.w);
        g gVar = g.a.a;
        gVar.d.remove(this.v);
    }

    @Override
    protected void onResume() {
        RadioButton radioButton;
        super.onResume();
        if (this.i != null && this.i.isVisible()) {
            radioButton = this.g;
        } else if (this.j != null && this.j.isVisible()) {
            radioButton = this.h;
        } else if (this.k != null && this.k.isVisible()) {
            radioButton = this.d;
        } else if (this.l != null && this.l.isVisible()) {
            radioButton = this.e;
        } else if (this.m != null && this.m.isVisible()) {
            radioButton = this.f;
        } else if (this.n != null && this.n.isVisible()) {
            radioButton = this.master;
        } else {
            b(this.r);
            return;
        }
        radioButton.setChecked(true);
        b(this.r);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {}

    @Override
    public void onWindowFocusChanged(boolean z) {
        if (z) sendBroadcast(new Intent("com.notification"));
        super.onWindowFocusChanged(z);
    }
}
