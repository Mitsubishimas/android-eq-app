package com.tw.eq.d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import org.json.JSONObject;

public final class c {
    public Drawable a;
    public Drawable b;
    public Drawable c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public String g;
    public String h;
    public String i;
    public int j;
    public int k;

    public static c a(String str) {
        Log.e("EQPlugin", "parsingViewsConfig: " + str);
        c cVar = new c();
        try {
            Context contextA = g.a.a.a();
            contextA.getResources();
            JSONObject jSONObject = new JSONObject(str);
            cVar.a = com.tw.eq.theme.f.a(contextA, jSONObject.optString("filter_car_bg"));
            cVar.b = com.tw.eq.theme.f.a(contextA, jSONObject.optString("filter_default_drawableLeft"));
            cVar.c = com.tw.eq.theme.f.a(contextA, jSONObject.optString("filter_default_bg"));
            cVar.d = com.tw.eq.theme.f.a(contextA, jSONObject.optString("filter_seekbarView_bg"));
            cVar.e = com.tw.eq.theme.f.a(contextA, jSONObject.optString("filter_voice_bg"));
            cVar.f = com.tw.eq.theme.f.a(contextA, jSONObject.optString("filter_mute_bg"));
            cVar.g = jSONObject.optString("filter_seekbar_backgroud");
            cVar.h = jSONObject.optString("filter_seekbar_drawable");
            cVar.i = jSONObject.optString("filter_seekbar_thumb");
            cVar.j = jSONObject.optInt("filter_seekbar_thumbOffset");
            cVar.k = jSONObject.optInt("filter_front_rear_text_visibly");
            return cVar;
        } catch (Exception e) {
            Log.e("EQPlugin", "parsingViewsConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return cVar;
        }
    }
}
