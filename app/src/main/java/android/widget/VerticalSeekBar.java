package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VerticalSeekBar extends View {
    private int mMax = 100;
    private int mProgress = 0;
    private OnSeekBarChangeListener mListener;
    
    public VerticalSeekBar(Context context) { super(context); }
    public VerticalSeekBar(Context context, AttributeSet attrs) { super(context, attrs); }
    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }
    
    public void setMax(int max) { mMax = max; }
    public int getMax() { return mMax; }
    public void setProgress(int progress) { mProgress = progress; invalidate(); }
    public int getProgress() { return mProgress; }
    public void setThumb(android.graphics.drawable.Drawable d) {}
    public void setProgressDrawableTiled(android.graphics.drawable.Drawable d) {}
    public void setThumbOffset(int offset) {}
    
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) { mListener = listener; }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            int progress = (int) ((event.getY() / getHeight()) * mMax);
            if (progress < 0) progress = 0;
            if (progress > mMax) progress = mMax;
            mProgress = progress;
            invalidate();
            if (mListener != null) mListener.onProgressChanged(this, progress, true);
        }
        return true;
    }
    
    public interface OnSeekBarChangeListener {
        void onProgressChanged(VerticalSeekBar seekBar, int progress, boolean fromUser);
        void onStartTrackingTouch(VerticalSeekBar seekBar);
        void onStopTrackingTouch(VerticalSeekBar seekBar);
    }
}
