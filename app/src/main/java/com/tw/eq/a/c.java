package com.tw.eq.a;

import android.content.Context;
import android.content.SharedPreferences;

public final class c {
    public static void a(Context context, String str, String str2, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
        editorEdit.putInt(str2, i);
        editorEdit.commit();
    }

    public static void a(Context context, String str, String str2, int[] iArr) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i : iArr) {
            stringBuffer.append(i);
            stringBuffer.append(",");
        }
        editorEdit.putString(str2, stringBuffer.toString());
        editorEdit.commit();
    }

    public static int b(Context context, String str, String str2, int i) {
        return context.getSharedPreferences(str, 0).getInt(str2, i);
    }
}
