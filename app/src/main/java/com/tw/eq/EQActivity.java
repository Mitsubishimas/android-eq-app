package com.tw.eq;
import android.content.Intent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.Toast;
import com.tw.eq.a.d;
import com.tw.eq.activity.NormalActivity;
import com.tw.eq.activity.RohmActivity;

public class EQActivity extends Activity {
    private d a;
    private PackageManager c;
    private ComponentName d;
    private ComponentName e;

    @SuppressLint({"HandlerLeak"})
    private final Handler b = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            if (message.what == 266 && message.arg1 == 0) {
                try {
                    String str = (String) message.obj;
                    String[] parts = str.split("-");
                    if (parts.length > 3 && parts[3].length() > 3) {
                        int i = Integer.parseInt(parts[3].substring(3, 4), 16);
                        com.tw.eq.a.a.a = i;
                        EQActivity.this.a(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void a(int eqType) {
        b(this.d);
        a(this.e);
        Intent intent = new Intent();
        
        if (eqType == 6) {
            intent.setClass(this, RohmActivity.class);
        } else if (eqType == 460) {
            if (this.c != null) {
                Intent pkgIntent = this.c.getLaunchIntentForPackage("com.chs.mt.hh_dbs460_carplay");
                if (pkgIntent != null) {
                    intent = pkgIntent;
                } else {
                    Toast.makeText(this, "DSP apk не установлено!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (eqType == 810) {
            if (this.c != null) {
                Intent pkgIntent = this.c.getLaunchIntentForPackage("com.chs.mthh.hh_dbp810_carplay_usb");
                if (pkgIntent != null) {
                    intent = pkgIntent;
                }
            }
        } else {
            b(this.e);
            a(this.d);
            intent.setClass(this, NormalActivity.class);
        }
        
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    private void a(ComponentName componentName) {
        if (this.c != null && componentName != null) {
            this.c.setComponentEnabledSetting(componentName, 
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 
                PackageManager.DONT_KILL_APP);
        }
    }

    private void b(ComponentName componentName) {
        if (this.c != null && componentName != null) {
            this.c.setComponentEnabledSetting(componentName, 
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 
                PackageManager.DONT_KILL_APP);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = getPackageManager();
        this.d = new ComponentName(getBaseContext(), "com.tw.eq.EQChoiceActivity");
        this.e = new ComponentName(getBaseContext(), "com.tw.eq.DSPActivity");
        this.c = getApplicationContext().getPackageManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.a != null) {
            this.a.removeHandler("EQActivity");
            this.a = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int settings = Settings.System.getInt(getContentResolver(), "EQ_SETTINGS", 0);
        this.a = d.a();
        
        boolean needHandler = true;
        if (this.a != null && this.a.write(65521) == 35) {
            if (settings != 1) {
                int eqType = settings == 2 ? 810 : 460;
                a(eqType);
                needHandler = false;
            }
        } else {
            needHandler = false;
        }
        
        if (needHandler && this.a != null) {
            this.a.addHandler("EQActivity", this.b);
            this.a.write(266, 255);
        }
    }
}
