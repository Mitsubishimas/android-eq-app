package com.tw.eq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.tw.eq.a;

public class Workspace extends ViewGroup implements ViewGroup.OnHierarchyChangeListener {
    private static final float DECAY_RATE = (float) (0.016d / Math.log(0.75d));
    private final int defaultScreen;
    private boolean firstLayout;
    private int currentScreen;
    private int nextScreen;
    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private float lastMotionX;
    private float lastMotionY;
    private int touchSlop;
    private int maximumVelocity;
    private int scrollState;
    private int activePointerId;
    private float scrollX;
    private float lastScrollTime;
    private CustomInterpolator interpolator;
    private ScreenSwitchListener screenSwitchListener;

    public interface ScreenSwitchListener {
        void onScreenSwitch(View view, int screen);
    }

    private static class CustomInterpolator implements Interpolator {
        float tension = 1.3f;

        @Override
        public float getInterpolation(float t) {
            float t2 = t - 1.0f;
            return t2 * t2 * (((tension + 1.0f) * t2) + tension) + 1.0f;
        }
    }

    public Workspace(Context context) {
        this(context, null);
    }

    public Workspace(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Workspace(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.firstLayout = true;
        this.currentScreen = -1;
        this.nextScreen = -1;
        this.scrollState = 0;
        this.activePointerId = -1;
        
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, a.C0003a.Workspace, defStyle, 0);
        this.defaultScreen = typedArray.getInt(0, 0);
        typedArray.recycle();
        
        setHapticFeedbackEnabled(false);
        this.interpolator = new CustomInterpolator();
        this.scroller = new Scroller(context, this.interpolator);
        this.currentScreen = this.defaultScreen;
        
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.touchSlop = configuration.getScaledTouchSlop();
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    private void recycleVelocityTracker() {
        if (this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void snapToScreen(int whichScreen) {
        snapToScreen(whichScreen, 0, false);
    }

    private void snapToScreen(int whichScreen, int velocity, boolean fromFling) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        this.nextScreen = whichScreen;
        
        View focusedChild = getFocusedChild();
        if (focusedChild != null && whichScreen != this.currentScreen && focusedChild == getChildAt(this.currentScreen)) {
            focusedChild.clearFocus();
        }
        
        int distance = (whichScreen * getWidth()) - this.scrollX;
        int duration = Math.max(1, Math.abs(whichScreen - this.currentScreen));
        int baseDuration = (duration + 1) * 100;
        
        if (!this.scroller.isFinished()) {
            this.scroller.abortAnimation();
        }
        
        if (fromFling) {
            this.interpolator.tension = duration > 0 ? 1.3f / duration : 1.3f;
        } else {
            this.interpolator.tension = 0.0f;
        }
        
        int absVelocity = Math.abs(velocity);
        int actualDuration = baseDuration;
        if (absVelocity > 0) {
            actualDuration = (int) (baseDuration + ((baseDuration / (absVelocity / 2500.0f)) * 0.4f));
        } else {
            actualDuration = baseDuration + 100;
        }
        
        awakenScrollBars(actualDuration);
        this.scroller.startScroll(this.scrollX, 0, distance, 0, actualDuration);
        invalidate();
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> 8;
        int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == this.activePointerId) {
            int newIndex = pointerIndex == 0 ? 1 : 0;
            this.lastMotionX = ev.getX(newIndex);
            this.lastMotionY = ev.getY(newIndex);
            this.activePointerId = ev.getPointerId(newIndex);
            if (this.velocityTracker != null) {
                this.velocityTracker.clear();
            }
        }
    }

    private void notifyScreenSwitch() {
        if (this.screenSwitchListener != null) {
            this.screenSwitchListener.onScreenSwitch(getChildAt(this.currentScreen), this.currentScreen);
        }
    }

    private void initVelocityTracker(MotionEvent event) {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
    }

    @Override
    public void computeScroll() {
        if (this.scroller.computeScrollOffset()) {
            int oldX = this.scrollX;
            int x = this.scroller.getCurrX();
            this.scrollX = x;
            this.scrollX = x;
            this.lastScrollTime = System.nanoTime() / 1.0E9f;
            this.scrollY = this.scroller.getCurrY();
            postInvalidate();
        } else if (this.nextScreen != -1) {
            this.currentScreen = Math.max(0, Math.min(this.nextScreen, getChildCount() - 1));
            this.nextScreen = -1;
            notifyScreenSwitch();
        } else if (this.scrollState == 1) {
            float delta = this.scrollX - this.scrollX;
            float elapsed = (System.nanoTime() / 1.0E9f) - this.lastScrollTime;
            float factor = (float) Math.exp(elapsed / DECAY_RATE);
            this.scrollX = (int) (this.scrollX + (factor * delta));
            this.lastScrollTime = System.nanoTime() / 1.0E9f;
            if (Math.abs(delta) <= 1.0f) {
                return;
            }
            postInvalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        int currentScreen;
        if (direction == View.FOCUS_LEFT) {
            if (getCurrentScreen() > 0) {
                currentScreen = getCurrentScreen() - 1;
                snapToScreen(currentScreen);
                return true;
            }
        } else if (direction == View.FOCUS_RIGHT && getCurrentScreen() < getChildCount() - 1) {
            currentScreen = getCurrentScreen() + 1;
            snapToScreen(currentScreen);
            return true;
        }
        return super.dispatchUnhandledMove(focused, direction);
    }

    public int getCurrentScreen() {
        return this.currentScreen;
    }

    int getNextScreen() {
        return this.nextScreen != -1 ? this.nextScreen : this.currentScreen;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        computeScroll();
    }

    @Override
    public void onChildViewAdded(View parent, View child) {}

    @Override
    public void onChildViewRemoved(View parent, View child) {}

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE && this.scrollState != 0) {
            return true;
        }
        
        initVelocityTracker(ev);
        
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                float x = ev.getX();
                float y = ev.getY();
                this.lastMotionX = x;
                this.lastMotionY = y;
                this.activePointerId = ev.getPointerId(0);
                this.scrollState = !this.scroller.isFinished() ? 1 : 0;
                break;
                
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = ev.findPointerIndex(this.activePointerId);
                float x2 = ev.getX(pointerIndex);
                float y2 = ev.getY(pointerIndex);
                int xDiff = (int) Math.abs(x2 - this.lastMotionX);
                int yDiff = (int) Math.abs(y2 - this.lastMotionY);
                if (xDiff > this.touchSlop && xDiff > yDiff) {
                    this.scrollState = 1;
                    this.lastMotionX = x2;
                    this.scrollX = this.scrollX;
                    this.lastScrollTime = System.nanoTime() / 1.0E9f;
                }
                break;
                
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.scrollState = 0;
                this.activePointerId = -1;
                recycleVelocityTracker();
                break;
                
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }
        
        return this.scrollState != 0;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childWidth = child.getMeasuredWidth();
                child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        if (this.firstLayout) {
            setHorizontalScrollBarEnabled(false);
            scrollTo(this.currentScreen * width, 0);
            setHorizontalScrollBarEnabled(true);
            this.firstLayout = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTracker(ev);
        
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (!this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                }
                this.lastMotionX = ev.getX();
                this.activePointerId = ev.getPointerId(0);
                break;
                
            case MotionEvent.ACTION_MOVE:
                if (this.scrollState == 1) {
                    int pointerIndex = ev.findPointerIndex(this.activePointerId);
                    float x = ev.getX(pointerIndex);
                    float delta = this.lastMotionX - x;
                    this.lastMotionX = x;
                    
                    if (delta < 0 && this.scrollX > 0) {
                        this.scrollX = Math.max(0, this.scrollX + delta);
                        this.lastScrollTime = System.nanoTime() / 1.0E9f;
                        invalidate();
                    } else if (delta > 0) {
                        float maxScroll = getChildAt(getChildCount() - 1).getRight() - this.scrollX - getWidth();
                        if (maxScroll > 0) {
                            this.scrollX += Math.min(maxScroll, delta);
                            this.lastScrollTime = System.nanoTime() / 1.0E9f;
                            invalidate();
                        }
                    } else if (this.scrollX > 0 && delta < 0) {
                        this.scrollX = Math.max(0, this.scrollX + delta);
                        this.lastScrollTime = System.nanoTime() / 1.0E9f;
                        invalidate();
                    }
                }
                break;
                
            case MotionEvent.ACTION_UP:
                if (this.scrollState == 1) {
                    this.velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
                    int velocityX = (int) this.velocityTracker.getXVelocity(this.activePointerId);
                    int width = getWidth();
                    int screen = (this.scrollX + (width / 2)) / width;
                    float screenPos = (float) this.scrollX / width;
                    
                    if (velocityX > 600 && this.currentScreen > 0) {
                        snapToScreen(Math.min(screen, screenPos < screen ? this.currentScreen - 1 : this.currentScreen), velocityX, true);
                    } else if (velocityX < -600 && this.currentScreen < getChildCount() - 1) {
                        snapToScreen(Math.max(screen, screenPos > screen ? this.currentScreen + 1 : this.currentScreen), velocityX, true);
                    } else {
                        snapToScreen(screen, velocityX, true);
                    }
                }
                this.scrollState = 0;
                this.activePointerId = -1;
                recycleVelocityTracker();
                break;
                
            case MotionEvent.ACTION_CANCEL:
                if (this.scrollState == 1) {
                    int width = getWidth();
                    snapToScreen((this.scrollX + (width / 2)) / width, 0, true);
                }
                this.scrollState = 0;
                this.activePointerId = -1;
                recycleVelocityTracker();
                break;
                
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }
        return true;
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        int screen = indexOfChild(child);
        if (screen == this.currentScreen && this.scroller.isFinished()) {
            return false;
        }
        snapToScreen(screen);
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        this.scrollX = x;
        this.lastScrollTime = System.nanoTime() / 1.0E9f;
    }

    public void setScreenSwitchListener(ScreenSwitchListener listener) {
        this.screenSwitchListener = listener;
        if (this.screenSwitchListener != null) {
            this.screenSwitchListener.onScreenSwitch(getChildAt(this.currentScreen), this.currentScreen);
        }
    }
}
