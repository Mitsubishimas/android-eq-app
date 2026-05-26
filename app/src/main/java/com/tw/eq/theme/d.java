package com.tw.eq.theme;

import java.lang.reflect.Method;

public final class d {
    public static Object a(Class cls, Object obj, String str, Object... objArr) {
        Class<?>[] paramTypes = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            paramTypes[i] = objArr[i].getClass();
        }
        Method declaredMethod = cls.getDeclaredMethod(str, paramTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj, objArr);
    }
}
