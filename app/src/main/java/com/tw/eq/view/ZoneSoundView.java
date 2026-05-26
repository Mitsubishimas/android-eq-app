package com.tw.eq.view;

public interface ZoneSoundView extends BaseView {
    void onCenterValueChange(int i);
    void onSpdifValueChange(int i);
    void onSubFreqValueChange(int i, int i2);
    void onSubGainValueChange(int i);
    void onZoneXY(int i, int i2);
}
