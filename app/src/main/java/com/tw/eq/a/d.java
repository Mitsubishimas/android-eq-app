package com.tw.eq.a;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public final class d extends TWUtil {
    private static final d a = new d();
    private static int b;

    public static d a() {
        int i = b;
        b = i + 1;
        if (i == 0) {
            if (a.open(new short[]{257, 266}) != 0) {
                b--;
                return null;
            }
            a.start();
        }
        return a;
    }

    private static String a(File file) {
        StringBuffer stringBuffer = null;
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuffer stringBuffer2 = new StringBuffer();
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuffer2.append(line);
                    } catch (FileNotFoundException | IOException unused) {
                    }
                    stringBuffer = stringBuffer2;
                }
                bufferedReader.close();
                fileInputStream.close();
                stringBuffer = stringBuffer2;
            } catch (FileNotFoundException | IOException unused2) {
            }
        }
        return TextUtils.isEmpty(stringBuffer) ? "" : stringBuffer.toString();
    }

    public static String a(String str) {
        return a(new File("/sdcard/com.tw.eq/" + str));
    }

    public static void a(String str, String str2) {
        try {
            File file = new File("/sdcard/com.tw.eq/" + str);
            if (file.exists()) {
                file.delete();
            }
            File file2 = new File("/sdcard/com.tw.eq/" + str);
            if (!file2.exists()) {
                File parentFile = file2.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(str2 + "\n");
            outputStreamWriter.flush();
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String b() throws Throwable {
        String str = "";
        try {
            String str2 = SystemProperties.get("ro.tw.version");
            if (str2 != null && str2.length() > 0) {
                str = str2;
            }
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/system/etc/version"));
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null && line.length() > 0) {
                        str = line;
                    }
                    bufferedReader2.close();
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
        return TextUtils.isEmpty(str) ? str : str.substring(str.lastIndexOf("_") + 1);
    }

    public final void close() {
        if (b > 0) {
            int i = b - 1;
            b = i;
            if (i == 0) {
                stop();
                super.close();
            }
        }
    }
}
