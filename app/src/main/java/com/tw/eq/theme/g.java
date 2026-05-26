package com.tw.eq.theme;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class g {
    public Context a;
    final Map<String, h> b;
    final List<IThemeDownloadStatus> c;
    public final List<IThemeSwitchStatus> d;
    private volatile h e;

    public static final class a {
        public static g a = new g((byte) 0);
    }

    private static class b implements Comparator<IThemeSwitchStatus> {
        private b() {}

        @Override
        public final int compare(IThemeSwitchStatus o1, IThemeSwitchStatus o2) {
            return o2.getOnFinishPriority() - o1.getOnFinishPriority();
        }
    }

    private static class c implements Comparator<IThemeSwitchStatus> {
        private c() {}

        @Override
        public final int compare(IThemeSwitchStatus o1, IThemeSwitchStatus o2) {
            return o2.getOnStartPriority() - o1.getOnStartPriority();
        }
    }

    private static class d implements Comparator<IThemeSwitchStatus> {
        private d() {}

        @Override
        public final int compare(IThemeSwitchStatus o1, IThemeSwitchStatus o2) {
            return o2.getOnSwitchingPriority() - o1.getOnSwitchingPriority();
        }
    }

    private g() {
        this.b = new ConcurrentHashMap();
        this.c = new CopyOnWriteArrayList();
        this.d = new ArrayList();
    }

    private g(byte b2) {
        this();
    }

    static boolean a(List<IThemeSwitchStatus> statuses, i themeInfo) {
        try {
            for (IThemeSwitchStatus status : statuses) {
                if (!status.onThemeSwitching(themeInfo)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final synchronized Context a() {
        if (this.e == null) {
            return null;
        }
        return this.e.b;
    }

    public final synchronized void a(h themePlugin) {
        this.e = themePlugin;
        Log.e("ThemeManager", "updateCurThemePlugin: " + themePlugin);
    }

    public final void a(final i themeInfo) {
        e.a(new Runnable() {
            @Override
            public final void run() {
                a.a("ThemeManager", "theme switch start. size = %s", Integer.valueOf(g.this.d.size()));
                List<IThemeSwitchStatus> list = g.this.d;
                Collections.sort(list, new c());
                for (IThemeSwitchStatus status : list) {
                    status.onThemeSwitchStart(themeInfo);
                }
                Collections.sort(list, new d());
                boolean success = g.a(list, themeInfo);
                Collections.sort(list, new b());
                for (IThemeSwitchStatus status : list) {
                    status.onThemeSwitchFinish(themeInfo, !success);
                }
                a.a("ThemeManager", "theme switch finished. size = %s", Integer.valueOf(g.this.d.size()));
            }
        });
    }
}
