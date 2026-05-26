package com.tw.eq.a;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.tw.eq.theme.f;

public final class b {
    public static Drawable a(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public static void a(Context context, String str, TextView textView) {
        ColorStateList colorStateListB = f.b(context, str);
        if (colorStateListB != null) {
            textView.setTextColor(colorStateListB);
        }
    }
}
