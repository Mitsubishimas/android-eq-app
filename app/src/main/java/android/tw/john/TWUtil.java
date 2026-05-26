package android.tw.john;

import android.os.Handler;

/**
 * Системный класс для работы с DSP.
 * Реальная реализация находится в прошивке магнитолы.
 * Этот файл нужен ТОЛЬКО для компиляции.
 */
public class TWUtil {
    public int open(short[] channels) { return 0; }
    public void start() {}
    public void stop() {}
    public void close() {}
    public int write(int command, int value) { return 0; }
    public void write(int command, int index, int value) {}
    public void write(int command, int index, byte[] data) {}
    public void addHandler(String name, Handler handler) {}
    public void removeHandler(String name) {}
}
