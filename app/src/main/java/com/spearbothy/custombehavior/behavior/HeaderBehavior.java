package com.spearbothy.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by mahao on 17-7-20.
 */

public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = HeaderBehavior.class.getSimpleName();

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        Log.i(TAG, "onNestedPreScroll");
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        Log.i(TAG, "onLayoutChild");
        // getDependents 获取依赖child
        List<View> dependents = parent.getDependents(child);

        for (View dependent : dependents) {
            logClass(dependent.getClass());
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "layoutDependsOn");
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "onDependentViewChanged");
        return super.onDependentViewChanged(parent, child, dependency);
    }

    public void logClass(Class clazz) {
        Log.i(TAG, clazz.getSimpleName());
    }
}
