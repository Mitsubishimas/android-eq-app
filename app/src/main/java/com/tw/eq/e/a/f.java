package com.tw.eq.e.a;

import android.content.Context;
import com.tw.eq.c.a;
import com.tw.eq.view.MasterMitsubishiView;

public final class f extends com.tw.eq.e.a<MasterMitsubishiView, a> {
    
    public f(Context context) {
        super(context);
    }
    
    @Override
    public final com.tw.eq.c.a a() {
        return null;
    }
    
    @Override
    public final void a(byte[] bArr) {
        super.a(bArr);
        // Обработка входящих данных от DSP для эквалайзера MasterMitsubishi
        try {
            if (e() == null) return;
            // Данные для 5-полосного EQ (адреса 52-56)
            // Данные для 3-полосного EQ (адреса 57-59)
            // Обновление UI при необходимости
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
