package com.tw.eq.theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tw.eq.theme.g;
import java.io.File;

public final class f {
    public static int a(Context context, String str, String str2) {
        try {
            int identifier = context.getResources().getIdentifier(str, str2, context.getPackageName());
            if (identifier == 0) {
                Log.e("ThemeHelper", "getIdentifier: no such resource was found. resName = " + str + ", type = " + str2);
            }
            return identifier;
        } catch (Exception e) {
            Log.e("ThemeHelper", "getIdentifier error. resName = " + str);
            e.printStackTrace();
            return 0;
        }
    }

    public static Drawable a(Context context, String str) {
        int id = a(context, str, "drawable");
        if (id == 0) {
            return null;
        }
        return context.getResources().getDrawable(id);
    }

    public static i a(String str) {
        i iVar = new i();
        g gVar = g.a.a;
        File file = new File(str);
        if (gVar.a == null) {
            throw new IllegalStateException("must be call init(Context ctx).");
        }
        a.a("ThemeManager", "loadThemePlugin : %s", file);
        h themePlugin = null;
        if (file.exists()) {
            themePlugin = h.a(gVar, gVar.a, file);
            if (themePlugin != null) {
                gVar.b.put(file.getAbsolutePath(), themePlugin);
            }
            a.a("ThemeManager", "load theme plugin : %s", file.getAbsoluteFile());
        } else {
            a.a("ThemeManager", "plugin is not exists. path = %s", file.getAbsoluteFile());
        }
        g.a.a.a(themePlugin);
        Context context = themePlugin.b;
        
        String viewsConfig = b.a(context, "eq/eq_act_config.json");
        iVar.b = com.tw.eq.d.a.a(viewsConfig);
        
        String eqConfig = b.a(context, "eq/fragment_eq_config.json");
        iVar.c = com.tw.eq.d.d.a(eqConfig);
        
        String surroundConfig = b.a(context, "eq/fragment_sound_config.json");
        iVar.d = com.tw.eq.d.e.a(surroundConfig);
        
        String boostConfig = b.a(context, "eq/fragment_boost_config.json");
        iVar.e = com.tw.eq.d.b.a(boostConfig);
        
        String zoneConfig = b.a(context, "eq/fragment_zone_config.json");
        iVar.f = com.tw.eq.d.f.a(zoneConfig);
        
        String filterConfig = b.a(context, "eq/fragment_filter_config.json");
        iVar.g = com.tw.eq.d.c.a(filterConfig);
        
        iVar.a = themePlugin;
        return iVar;
    }

    public static ColorStateList b(Context context, String str) {
        int id = a(context, str, "color");
        if (id == 0) {
            return null;
        }
        return context.getResources().getColorStateList(id);
    }
}
