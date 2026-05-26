package com.tw.eq.d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import org.json.JSONObject;

public final class d {
    public Drawable a;
    public Drawable b;
    Drawable c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public Drawable g;
    public String h;
    public String i;
    public String j;
    public String k;
    public int l;
    public int m;
    public String n;
    private Drawable o;
    private Drawable p;

    public static d a(String str) {
        Log.e("EQPlugin", "parsingViewsConfig: " + str);
        d dVar = new d();
        try {
            Context contextA = g.a.a.a();
            contextA.getResources();
            JSONObject jSONObject = new JSONObject(str);
            dVar.a = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_mode_bg"));
            dVar.b = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_mode_item_bg"));
            dVar.c = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_default_bg"));
            dVar.d = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_lines_bg"));
            dVar.e = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_curve_bg"));
            dVar.m = jSONObject.optInt("eq_curve_visibly");
            dVar.f = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_seekbar_0"));
            dVar.o = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_seekbar_drawable"));
            dVar.p = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_seekbar_thumb"));
            dVar.g = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_fc_q_bg"));
            dVar.h = jSONObject.optString("eq_qValue_pop_bg");
            dVar.k = jSONObject.optString("eq_qValue_pop_seekbar_backgroud");
            dVar.i = jSONObject.optString("eq_qValue_pop_seekbar_drawable");
            dVar.j = jSONObject.optString("eq_qValue_pop_seekbar_thumb");
            dVar.l = jSONObject.optInt("eq_seekbar_thumbOffset");
            dVar.n = jSONObject.optString("eq_level_page");
            return dVar;
        } catch (Exception e) {
            Log.e("EQPlugin", "parsingViewsConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return dVar;
        }
    }
}
