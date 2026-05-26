package android.tw.john;

import android.os.Handler;
import java.util.HashMap;
import java.util.Map;

public class TWUtil {
    protected int mScrollX;
    protected int mScrollY;
    private Map<String, Handler> handlers = new HashMap<>();
    
    public int open(short[] channels) { return 0; }
    public void start() {}
    public void stop() {}
    public void close() {}
    public int write(int command, int value) { return 0; }
    public void write(int command, int index, int value) {}
    public void write(int command, int index, byte[] data) {}
    public void addHandler(String name, Handler handler) { handlers.put(name, handler); }
    public void removeHandler(String name) { handlers.remove(name); }
}
