package com.spearbothy.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by mahao on 17-7-20.
 */

public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {
    // 记录手指触摸的位置
    private int mLastY = 0;

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        // 手指触摸的回调
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getRawY();
                child.setTranslationY(child.getTranslationY() + y - mLastY);
                mLastY = y;
                break;
        }
        return true;
    }
}
