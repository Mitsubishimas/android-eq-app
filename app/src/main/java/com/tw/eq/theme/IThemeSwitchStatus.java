package com.tw.eq.theme;

public interface IThemeSwitchStatus {
    int getOnFinishPriority();
    int getOnStartPriority();
    int getOnSwitchingPriority();
    void onThemeSwitchFinish(i iVar, boolean z);
    void onThemeSwitchStart(i iVar);
    boolean onThemeSwitching(i iVar);
}
