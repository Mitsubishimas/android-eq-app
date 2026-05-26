package com.tw.eq.theme;

import android.util.Log;
import java.util.Locale;

public class a {
    public static String a = "DFLog";
    public static boolean b = true;
    private static final String c = "com.tw.eq.theme.a";

    public static void a(String str, String str2, Object... objArr) {
        if (b) {
            String str3 = String.format(Locale.US, str2, objArr);
            StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
            String fileName = "<unknown>";
            for (int i = 2; i < stackTrace.length; i++) {
                if (!stackTrace[i].getClassName().equals(c)) {
                    fileName = "(" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber() + ")";
                    break;
                }
            }
            Log.e(str, String.format(Locale.US, "thread[%s, %d] %s: %s", 
                Thread.currentThread().getName(), Thread.currentThread().getId(), fileName, str3));
        }
    }
}
