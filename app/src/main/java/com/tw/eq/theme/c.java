package com.tw.eq.theme;

import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Process;

final class c extends ContextWrapper {
    private final h a;

    public c(h hVar) {
        super(hVar.a);
        this.a = hVar;
    }

    @Override
    public final ApplicationInfo getApplicationInfo() {
        return this.a.d.applicationInfo;
    }

    @Override
    public final AssetManager getAssets() {
        return this.a.c.getAssets();
    }

    @Override
    public final String getPackageCodePath() {
        return this.a.d.applicationInfo.sourceDir;
    }

    @Override
    public final String getPackageName() {
        return this.a.d.packageName;
    }

    @Override
    public final String getPackageResourcePath() {
        h hVar = this.a;
        int myUid = Process.myUid();
        ApplicationInfo applicationInfo = hVar.d.applicationInfo;
        return applicationInfo.uid == myUid ? applicationInfo.sourceDir : applicationInfo.publicSourceDir;
    }

    @Override
    public final Resources getResources() {
        return this.a.c;
    }
}
