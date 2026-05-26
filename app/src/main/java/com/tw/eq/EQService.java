package com.tw.eq;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.tw.eq.a.d;
import com.tw.eq.a.e;
import java.util.Arrays;

public class EQService extends Service {
    private int b;
    private int[] c;
    private d a = null;
    private boolean e = true;

    @SuppressLint({"HandlerLeak"})
    private final Handler d = new Handler() {
        @Override
        public final void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 257) {
                    try {
                        byte[] bArr = (byte[]) message.obj;
                        if (EQService.this.e) {
                            EQService.this.b = bArr[0] - 1;
                            EQService.this.e = false;
                        }
                        if (com.tw.eq.a.a.a == 6) {
                            if (EQService.this.b != bArr[0] - 1) {
                                EQService.this.b = bArr[0] - 1;
                                e.a(EQService.this.getApplicationContext());
                                int index = EQService.this.b >= 6 ? EQService.this.b - 4 : EQService.this.b;
                                e.a(EQService.this.getString(EQService.this.c[index])).a();
                            }
                        } else {
                            if (EQService.this.b != bArr[0] - 1) {
                                EQService.this.b = bArr[0] - 1;
                                e.a(EQService.this.getApplicationContext());
                                int index = EQService.this.b >= 0 ? EQService.this.b : 0;
                                e.a(EQService.this.getString(EQService.this.c[index])).a();
                            }
                        }
                    } catch (Exception unused) {
                    }
                } else if (i == 266 && message.arg1 == 0) {
                    try {
                        String str = (String) message.obj;
                        String[] parts = str.split("-");
                        if (parts.length > 3 && parts[3].length() > 3) {
                            int i2 = Integer.parseInt(parts[3].substring(3, 4), 16);
                            com.tw.eq.a.a.a = i2;
                            if (i2 == 6) {
                                EQService.this.c = Arrays.copyOf(com.tw.eq.a.a.d, com.tw.eq.a.a.d.length);
                            } else {
                                EQService.this.c = Arrays.copyOf(com.tw.eq.a.a.c, com.tw.eq.a.a.c.length);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                Log.e("EQService", Log.getStackTraceString(e2));
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.a = d.a();
        if (this.a != null) {
            this.a.addHandler("EQService", this.d);
            this.a.write(257, 255);
            this.a.write(266, 255);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
