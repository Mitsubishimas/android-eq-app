package com.tw.eq.d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import org.json.JSONObject;

public final class e {
    public Drawable a;
    public String b;
    public Drawable c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public Drawable g;
    public Drawable h;
    public Drawable i;
    public Drawable j;
    public Drawable k;
    public Drawable l;
    public Drawable m;
    public Drawable n;
    public Drawable o;
    public Drawable p;
    public String q;
    public int r;
    public int s;

    public static e a(String str) {
        Log.e("RohmSurroundSoundFragmentPlugin", "parsingViewsConfig: " + str);
        e eVar = new e();
        try {
            Context contextA = g.a.a.a();
            contextA.getResources();
            JSONObject jSONObject = new JSONObject(str);
            eVar.a = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_area_mode_bg"));
            eVar.b = jSONObject.optString("surround_area_item_mode_bg");
            eVar.c = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_car_bg"));
            eVar.o = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_plus_minu_bg"));
            eVar.p = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_plus_minu_bg_2"));
            eVar.e = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_fr"));
            eVar.f = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_rl"));
            eVar.d = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_fl"));
            eVar.g = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_rr"));
            eVar.n = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_car_speakerDrawable"));
            eVar.h = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_buttom"));
            eVar.i = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_buttom_plus"));
            eVar.j = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_laba_buttom_minu"));
            eVar.k = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_space_mode_bg"));
            eVar.l = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_space_mode_shang_xia_bg"));
            String strOptString = jSONObject.optString("surround_space_mode_pop_bg");
            eVar.m = com.tw.eq.theme.f.a(contextA, strOptString);
            eVar.q = jSONObject.optString("surround_space_mode_pop_item_bg");
            eVar.r = jSONObject.optInt("surround_space_mode_pop_xoff");
            eVar.s = jSONObject.optInt("surround_space_mode_pop_yoff");
            return eVar;
        } catch (Exception e) {
            Log.e("RohmSurroundSoundFragmentPlugin", "parsingViewsConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return eVar;
        }
    }
}
