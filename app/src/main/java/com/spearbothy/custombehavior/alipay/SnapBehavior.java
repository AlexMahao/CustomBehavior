package com.spearbothy.custombehavior.alipay;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.spearbothy.custombehavior.R;

import java.util.List;

/**
 * Created by mahao on 17-9-5.
 */

public class SnapBehavior extends VerticalBehavior<FrameLayout> {

    public static final String TAG = SnapBehavior.class.getSimpleName();

    private int mScrollHeight;

    private int mCurrentScrollHeight;

    public SnapBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollHeight = context.getResources().getDimensionPixelOffset(R.dimen.header_scroll_height);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FrameLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, FrameLayout child, MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, FrameLayout child, MotionEvent ev) {
        Log.i(TAG, "onTouchEvent");
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FrameLayout child, View target, int dx, int dy, int[] consumed) {
        // 手指向下移动， dy < 0 ; 手指向上,dy>0
        Log.i(TAG, "onNestedPreScroll");
        // 仅仅处理向上滑动
        if (mCurrentScrollHeight >= mScrollHeight && dy > 0) {
            // 向上已经滚动到最大高度
            return;
        }
        int scrollHeight;
        // 处理滑动
        if (dy > 0) {
            // dy > 0
            if (mCurrentScrollHeight + dy > mScrollHeight) {
                scrollHeight = mScrollHeight - mCurrentScrollHeight;
                mCurrentScrollHeight = mScrollHeight;
            } else {
                scrollHeight = dy;
                mCurrentScrollHeight = mCurrentScrollHeight + dy;
            }
            child.setTranslationY(-mCurrentScrollHeight / 3);
            consumed[1] = scrollHeight;
        }

        if (!ViewCompat.canScrollVertically(coordinatorLayout.findViewById(R.id.content), -1) && dy < 0 && mCurrentScrollHeight > 0) {
            if (dy < 0) {
                if (mCurrentScrollHeight + dy < 0) {
                    scrollHeight = 0 - mCurrentScrollHeight;
                    mCurrentScrollHeight = 0;
                } else {
                    scrollHeight = dy;
                    mCurrentScrollHeight = mCurrentScrollHeight + dy;
                }
                child.setTranslationY(-mCurrentScrollHeight / 3);
                consumed[1] = scrollHeight;
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FrameLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll");
       /* if (mCurrentScrollHeight <= 0 && dyUnconsumed < 0) {
            // 手指向下已经滑动到顶部，不在处理
            return;
        }
        if (dyUnconsumed < 0) {
            if (mCurrentScrollHeight + dyUnconsumed < 0) {
                mCurrentScrollHeight = 0;
            } else {
                mCurrentScrollHeight = mCurrentScrollHeight + dyUnconsumed;
            }
            child.setTranslationY(-mCurrentScrollHeight / 3);
        }*/
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return isDependOn(dependency);
    }

    public boolean isDependOn(View dependency) {
        return dependency.getId() == R.id.bar;
    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (isDependOn(view))
                return view;
        }
        return null;
    }
}
