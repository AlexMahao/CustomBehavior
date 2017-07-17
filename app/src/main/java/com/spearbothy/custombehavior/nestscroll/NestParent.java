package com.spearbothy.custombehavior.nestscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 调用流程  child:startNestedScroll - > parent: onStartNestedScroll -> child:dispatchNestedPreScroll
 * -> parent:onNestedPreScroll -> child: dispatchNestedScroll -> parent:onNestedScrollNestChild
 * -> child:stopNestedScroll -> parent : onStopNestedScroll
 * Created by mahao on 17-7-17.
 */

public class NestParent extends LinearLayout implements NestedScrollingParent {

    private static final String TAG = NestParent.class.getSimpleName();

    NestedScrollingParentHelper mParentHelper;

    public NestParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        // 滑动的child  , 目标child , 两者唯一
        // child  嵌套滑动的子控件(当前控件的子控件) ， target ， 手指触摸的控件
        Log.i(TAG, "onStartNestedScroll:" + child.getClass().getSimpleName() + ":" + target.getClass().getSimpleName());
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i(TAG, "onStopNestedScroll" + target.getClass().getSimpleName());
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll" + target.getClass().getSimpleName());
        Log.i(TAG, "dxUnconsumed:" + dxUnconsumed + "dyUnconsumed:" + dyUnconsumed);

        getChildAt(0).setTranslationY(getChildAt(0).getTranslationY() + dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.i(TAG, "onNestedPreScroll" + target.getClass().getSimpleName());
        // 开始滑动之前
        Log.i(TAG, consumed[0] + ":" + consumed[1]);
//        consumed[1] = 10;// 消费10px

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        // 惯性滑动
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        // 垂直滚动
        return mParentHelper.getNestedScrollAxes();
    }
}
