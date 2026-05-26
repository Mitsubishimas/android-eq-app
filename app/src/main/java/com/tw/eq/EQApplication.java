package com.tw.eq;

import android.app.Application;
import android.content.Intent;
import com.tw.eq.theme.g;

public class EQApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            g.a.a.a = this;
            startService(new Intent(this, EQService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
