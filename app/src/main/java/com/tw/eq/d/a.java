package com.tw.eq.d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import org.json.JSONObject;

public final class a {
    public Drawable a;
    public Drawable b;
    public Drawable c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public Drawable g;
    public int h;
    public int i;
    public String j;

    public static a a(String str) {
        Log.e("EQPlugin", "parsingViewsConfig: " + str);
        a aVar = new a();
        try {
            Context contextA = g.a.a.a();
            contextA.getResources();
            JSONObject jSONObject = new JSONObject(str);
            aVar.a = com.tw.eq.theme.f.a(contextA, jSONObject.optString("bg"));
            aVar.b = com.tw.eq.theme.f.a(contextA, jSONObject.optString("eq_bg"));
            aVar.c = com.tw.eq.theme.f.a(contextA, jSONObject.optString("surround_sound_bg"));
            aVar.d = com.tw.eq.theme.f.a(contextA, jSONObject.optString("bass_boost_bg"));
            aVar.e = com.tw.eq.theme.f.a(contextA, jSONObject.optString("zone_bg"));
            aVar.f = com.tw.eq.theme.f.a(contextA, jSONObject.optString("bass_filter_bg"));
            aVar.g = com.tw.eq.theme.f.a(contextA, jSONObject.optString("tab_bg"));
            aVar.h = jSONObject.optInt("tab_text_visibly");
            int iA = com.tw.eq.theme.f.a(contextA, jSONObject.optString("text_color"), "color");
            if (iA != 0) {
                iA = contextA.getResources().getColor(iA);
            }
            aVar.i = iA;
            aVar.j = jSONObject.optString("text_color_select");
            return aVar;
        } catch (Exception e) {
            Log.e("EQPlugin", "parsingViewsConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return aVar;
        }
    }
}
