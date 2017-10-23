package com.spearbothy.custombehavior.alipay;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.spearbothy.custombehavior.R;

import java.util.List;

/**
 * Created by mahao on 17-9-5.
 */

public class GridBehavior extends VerticalBehavior<FrameLayout> {

    public GridBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return isDependOn(dependency);
    }

    public int getScrollRange() {
        return mDependOnView.getMeasuredHeight();
    }

    public boolean isDependOn(View dependency) {
        return dependency.getId() == R.id.snap;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        child.setTranslationY(dependency.getTranslationY() * 3);
        return true;
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
