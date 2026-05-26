package com.tw.eq.e.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tw.eq.view.EQView;

@SuppressLint({"HandlerLeak"})
public final class c extends com.tw.eq.e.a<EQView, com.tw.eq.c.b> {
    protected byte g;
    public int[] h;
    public int[] i;
    public int[][] j;
    public final Handler k;
    private byte l;

    public c(Context context) {
        super(context);
        this.l = (byte) 0;
        this.h = new int[48];
        this.i = new int[48];
        this.j = new int[9][48];
        
        // Initialize default values
        for (int idx = 0; idx < 48; idx++) {
            this.h[idx] = 22;
            this.i[idx] = 14;
            for (int mode = 0; mode < 9; mode++) {
                this.j[mode][idx] = 14;
            }
        }
        
        // Load custom values from SharedPreferences
        SharedPreferences prefs = this.c.getSharedPreferences("iseq", 0);
        String customValues = prefs.getString("customValuess", "");
        if (!TextUtils.isEmpty(customValues)) {
            String[] parts = customValues.split(",");
            if (parts.length == this.j[0].length) {
                for (int i = 0; i < parts.length; i++) {
                    try {
                        this.j[0][i] = Integer.parseInt(parts[i]);
                    } catch (NumberFormatException e) {
                        // Use default
                    }
                }
            }
        }
        
        this.k = new Handler() {
            @Override
            public final void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 65297) {
                    return;
                }
                int[] data = (int[]) message.obj;
                int freqIndex = data[0];
                int gain = data[1];
                int qValue = data[2];
                int bandIndex = data[4];
                
                c.this.j[0][(freqIndex * 3) - (3 - bandIndex)] = gain;
                com.tw.eq.a.c.a(c.this.c, "iseq", "customValuess", c.this.j[0]);
                
                // Send to DSP
                int sampleRate = c.this.l == 0 ? 44100 : 48000;
                byte[] filterCoeffs = a(sampleRate, gain - 14, ((double) qValue) / 10.0d, bandIndex);
                if (d != null) {
                    d.write(257, freqIndex, filterCoeffs);
                    d.write(257, freqIndex + 16, qValue);
                }
            }
        };
    }

    public static void a(int i) {
        if (d != null) {
            d.write(257, 0, i);
        }
    }

    private static byte[] a(int sampleRate, int gain, double q, int band) {
        double dPow = Math.pow(10.0d, gain / 40.0d);
        double dPow2 = Math.pow(10.0d, Math.abs(gain) / 40.0d);
        double w = (2.0d * Math.PI * band) / sampleRate;
        double cosW = Math.cos(w);
        double sinW = Math.sin(w) / ((q * 2.0d) / dPow2);
        double a0 = sinW * dPow;
        double a1 = sinW / dPow;
        double b0 = (a0 + 1.0d) / (a1 + 1.0d);
        double b1 = (-2.0d * cosW) / (a1 + 1.0d);
        double b2 = (1.0d - a0) / (a1 + 1.0d);
        double a2 = (2.0d * cosW) / (a1 + 1.0d);
        double a3 = (-(1.0d - a1)) / (a1 + 1.0d);
        
        long[] coeffs = {
            (long) (b0 * 1.34217728E8d),
            (long) (b1 * 1.34217728E8d),
            (long) (b2 * 1.34217728E8d),
            (long) (a2 * 1.34217728E8d),
            (long) (a3 * 1.34217728E8d)
        };
        
        return new byte[]{
            (byte) ((coeffs[0] >> 24) & 0xFF), (byte) ((coeffs[0] >> 16) & 0xFF),
            (byte) ((coeffs[0] >> 8) & 0xFF), (byte) (coeffs[0] & 0xFF),
            (byte) ((coeffs[1] >> 24) & 0xFF), (byte) ((coeffs[1] >> 16) & 0xFF),
            (byte) ((coeffs[1] >> 8) & 0xFF), (byte) (coeffs[1] & 0xFF),
            (byte) ((coeffs[2] >> 24) & 0xFF), (byte) ((coeffs[2] >> 16) & 0xFF),
            (byte) ((coeffs[2] >> 8) & 0xFF), (byte) (coeffs[2] & 0xFF),
            (byte) ((coeffs[3] >> 24) & 0xFF), (byte) ((coeffs[3] >> 16) & 0xFF),
            (byte) ((coeffs[3] >> 8) & 0xFF), (byte) (coeffs[3] & 0xFF),
            (byte) ((coeffs[4] >> 24) & 0xFF), (byte) ((coeffs[4] >> 16) & 0xFF),
            (byte) ((coeffs[4] >> 8) & 0xFF), (byte) (coeffs[4] & 0xFF)
        };
    }

    @Override
    public final com.tw.eq.c.a a() {
        return new com.tw.eq.c.b(this.c);
    }

    @Override
    public final void a(byte[] bArr) {
        try {
            if (e() == null) {
                Log.d("EQPresenter", "getView() == null");
                return;
            }
            this.l = bArr[33];
            if (this.g != bArr[0]) {
                this.g = bArr[0];
                e().onEqMode(this.g);
            }
            int mode = this.g < 6 ? 0 : this.g - 5;
            int[] gains = new int[48];
            for (int i = 0; i < 48; i++) {
                gains[i] = bArr[(i / 3) + 1 + 16] & 0xFF;
            }
            e().onEqValueChange(this.j[mode], gains);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final int g() {
        return this.g >= 6 ? this.g - 5 : this.g - 1;
    }

    public final void h() {
        for (int i = 0; i < this.i.length; i++) {
            this.j[0][i] = this.i[i];
        }
        com.tw.eq.a.c.a(this.c, "iseq", "customValuess", this.j[0]);
        if (d != null) {
            d.write(257, 33);
        }
    }
}
