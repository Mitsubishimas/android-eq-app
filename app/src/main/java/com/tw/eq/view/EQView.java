package com.tw.eq.view;

public interface EQView extends BaseView {
    void onEqMode(int i);
    void onEqValueChange(byte[] bArr);
    void onEqValueChange(int[] iArr);
    void onEqValueChange(int[] iArr, int[] iArr2);
}
