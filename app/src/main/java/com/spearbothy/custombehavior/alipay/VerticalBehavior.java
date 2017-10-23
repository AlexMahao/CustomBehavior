package com.spearbothy.custombehavior.alipay;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import java.util.List;

/**
 * Created by mahao on 17-9-5.
 */

public abstract class VerticalBehavior<T extends View> extends CoordinatorLayout.Behavior<T> {

    protected View mDependOnView;
    protected Context mContext;

    public VerticalBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, T child, int layoutDirection) {
        // getDependencies  获取child 依赖的view
        List<View> dependencies = parent.getDependencies(child);

        View header = findFirstDependency(dependencies);

        if (header != null) {
            mDependOnView = header;
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            Rect available = new Rect();
            available.set(parent.getPaddingLeft() + lp.leftMargin,
                    header.getBottom() + lp.topMargin,
                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                    parent.getHeight() - parent.getPaddingBottom() - lp.bottomMargin);
            final Rect out = new Rect();
            GravityCompat.apply(resolveGravity(getFinalGravity(lp.gravity)), child.getMeasuredWidth(),
                    child.getMeasuredHeight(), available, out, layoutDirection);

            child.layout(out.left, out.top, out.right, out.bottom);
        } else {
            super.onLayoutChild(parent, child, layoutDirection);
        }
        Log.i("info", "onLayoutChild");
        return true;
    }

    public int getFinalGravity(int gravity) {
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
            gravity = gravity | Gravity.TOP;
        }
        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == 0) {
            gravity = gravity | Gravity.LEFT;
        }
        return gravity;
    }

    private static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? GravityCompat.START | Gravity.TOP : gravity;
    }

    protected abstract View findFirstDependency(List<View> views);

}
