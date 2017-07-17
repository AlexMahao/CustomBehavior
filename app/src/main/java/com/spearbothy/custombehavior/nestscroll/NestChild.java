package com.spearbothy.custombehavior.nestscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mahao on 17-7-17.
 */

public class NestChild extends View implements NestedScrollingChild {

    private static final String TAG = NestChild.class.getSimpleName();

    private final NestedScrollingChildHelper mChildHelper;

    private int[] mConsumed = new int[2];

    private int[] mOffset = new int[2];

    private int mOldY;

    public NestChild(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.i(TAG, "setNestedScrollingEnabled");
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.i(TAG, "startNestedScroll");
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.i(TAG, "stopNestedScroll");
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        Log.i(TAG, "dispatchNestedScroll");
        // 滚动之后将剩余滑动传给父类
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        // 子View滚动之前将滑动距离传给父类
        Log.i(TAG, "dispatchNestedPreScroll");
        return mChildHelper.dispatchNestedPreScroll(dx, dy,
                consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY,
                consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 启动滑动，传入方向
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                // 记录y值
                mOldY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                // 计算y值的偏移
                int offsetY = y - mOldY;
                Log.i(TAG, mConsumed[0] + ":" + mConsumed[1] + "--" + mOffset[0] + ":" + mOffset[1]);
                // 通知父类，如果返回true，表示父类消耗了触摸
                if (dispatchNestedPreScroll(0, offsetY, mConsumed, mOffset)) {
                    offsetY -= mConsumed[1];
                }
                int unConsumed = 0;
                float targetY = getTranslationY() + offsetY;
                if (targetY > -40 && targetY < 40) {
                    setTranslationY(targetY);
                } else {
                    unConsumed = offsetY;
                    offsetY = 0;
                }
                // 滚动完成之后，通知当前滑动的状态
                dispatchNestedScroll(0, offsetY, 0, unConsumed, mOffset);
                Log.i(TAG, mConsumed[0] + ":" + mConsumed[1] + "--" + mOffset[0] + ":" + mOffset[1]);
                mOldY = y;
                break;
            case MotionEvent.ACTION_UP:
                // 滑动结束
                stopNestedScroll();
                break;
            default:
                break;
        }
        return true;
    }
}
