package com.tw.eq.theme;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;

public final class h {
    final Context a;
    public final Context b = new c(this);
    final Resources c;
    final PackageInfo d;
    private final String e;
    private final g f;

    private h(g gVar, Context context, File file) {
        this.f = gVar;
        this.a = context;
        this.e = file.getAbsolutePath();
        Resources resources = context.getResources();
        AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
        d.a(AssetManager.class, assetManager, "addAssetPath", file.getAbsolutePath());
        this.c = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        this.d = context.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 64);
        Log.d("HYH", "ThemePlugin: " + this.d + "," + file.getAbsolutePath());
    }

    public static h a(g gVar, Context context, File file) {
        try {
            return new h(gVar, context, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
