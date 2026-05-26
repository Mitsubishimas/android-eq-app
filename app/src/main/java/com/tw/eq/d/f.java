package com.tw.eq.d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import org.json.JSONObject;

public final class f {
    public Drawable a;
    public Drawable b;
    public Drawable c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public Drawable g;
    public Drawable h;
    public String i;
    public String j;
    public int k;
    public Drawable l;
    public Drawable m;
    public Drawable n;
    private Drawable o;

    public static f a(String str) {
        Log.e("EQPlugin", "parsingViewsConfig: " + str);
        f fVar = new f();
        try {
            Context contextA = g.a.a.a();
            contextA.getResources();
            JSONObject jSONObject = new JSONObject(str);
            fVar.a = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_bg"));
            fVar.b = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_speakerDrawable"));
            fVar.c = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_balance_bg"));
            fVar.d = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_default_bg"));
            fVar.e = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_left_bg"));
            fVar.f = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_right_bg"));
            fVar.g = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_up_bg"));
            fVar.h = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_down_bg"));
            fVar.o = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_seekbar_double_thumb"));
            fVar.l = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_seekbar_double_thumb_0"));
            fVar.m = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_seekbar_double_thumb_1"));
            fVar.n = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_line_bg"));
            fVar.i = jSONObject.optString("zone_seekbar_bg");
            fVar.j = jSONObject.optString("zone_seekbar_thumb");
            fVar.k = jSONObject.optInt("zone_seekbar_thumbOffset");
            return fVar;
        } catch (Exception e) {
            Log.e("EQPlugin", "parsingViewsConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return fVar;
        }
    }
}
