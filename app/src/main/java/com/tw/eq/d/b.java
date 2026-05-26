package com.tw.eq.d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import org.json.JSONObject;

public final class b {
    public Drawable a;
    public Drawable b;
    public Drawable c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public Drawable g;
    public Drawable h;
    public int i;

    public static b a(String str) {
        Log.e("EQPlugin", "parsingViewsConfig: " + str);
        b bVar = new b();
        try {
            Context contextA = g.a.a.a();
            contextA.getResources();
            JSONObject jSONObject = new JSONObject(str);
            bVar.a = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_car_bg"));
            bVar.b = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_bg_zuo"));
            bVar.c = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_bg_you"));
            bVar.d = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_seekbar_drawable_zuo"));
            bVar.e = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_seekbar_thumb_zuo"));
            bVar.f = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_seekbar_drawable_you"));
            bVar.g = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_seekbar_thumb_you"));
            bVar.h = com.tw.eq.theme.f.a(contextA, jSONObject.optString("boost_default_bg"));
            bVar.i = jSONObject.optInt("eq_seekbar_thumbOffset");
            return bVar;
        } catch (Exception e) {
            Log.e("EQPlugin", "parsingViewsConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return bVar;
        }
    }
}
