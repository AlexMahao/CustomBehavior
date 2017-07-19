package com.spearbothy.custombehavior.recycler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * Created by mahao on 17-7-19.
 */

public class RecyclerNestedLinearLayout extends LinearLayout implements NestedScrollingParent {

    NestedScrollingParentHelper mParentHelper;
    private View mFirstView;
    private int mFirstViewHeight;
    private int mScrollTop;
    private View mThirdView;
    private View mSecond;

    public RecyclerNestedLinearLayout(Context context) {
        this(context, null);
    }

    public RecyclerNestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerNestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mFirstView = getChildAt(0);
        mFirstViewHeight = mFirstView.getMeasuredHeight();

        mSecond = getChildAt(1);
        mThirdView = getChildAt(2);
        ViewGroup.LayoutParams params = mThirdView.getLayoutParams();
        params.height = getMeasuredHeight() - mSecond.getMeasuredHeight();
        //
        setMeasuredDimension(getMeasuredWidth(), mFirstView.getMeasuredHeight() + mSecond.getMeasuredHeight() + mThirdView.getMeasuredHeight());
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // 准备滑动之前
        //  consumed[1]=
        if (dy > 0) {
            // 手指向上滑动
            int offsetY = 0;
            int currentTop = mScrollTop;
            if (currentTop <= 0 && Math.abs(currentTop) < mFirstViewHeight) {
                // 当前tab1还在显示，需要向上偏移
                if (Math.abs(currentTop) + dy > mFirstViewHeight) {
                    offsetY = (int) (mFirstViewHeight - Math.abs(currentTop));
                } else {
                    offsetY = dy;
                }
            }
            Log.i("info", "dy:" + dy + "mScrollTop:" + mScrollTop + "offsetY:" + offsetY);
            mScrollTop = currentTop - offsetY;
            scrollTo(0, -mScrollTop);
            // offset
            consumed[1] = offsetY;
        }
    }


    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // recycler 滑动之后  父控件
        if (dyUnconsumed < 0) {
            // 手指向下滑动
            int offsetY = 0;
            int currentTop = mScrollTop;
            if (currentTop <= 0) {
                if (currentTop - dyUnconsumed > 0) {
                    offsetY = -currentTop;
                } else {
                    offsetY = -dyUnconsumed;
                }
            }
            mScrollTop = currentTop + offsetY;
            Log.i("info", "dy:" + dyUnconsumed + "mScrollTop:" + mScrollTop);
            scrollTo(0, -mScrollTop);
            invalidate();
        }
    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i("info", "velocityY:" + velocityY + "consumed" + consumed);
        // 如果已经消费了
        if (consumed) {
            if (velocityY > 0 && -mScrollTop != mFirstViewHeight) {
                ValueAnimator mOffsetAnimator = new ValueAnimator();
                mOffsetAnimator.setInterpolator(new LinearInterpolator());
                mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (animation.getAnimatedValue() instanceof Integer) {
                            Log.i("info", "animation:" + (Integer) animation.getAnimatedValue());
                            scrollTo(0, (Integer) animation.getAnimatedValue());
                        }
                    }
                });
                mOffsetAnimator.setDuration(300);
                mOffsetAnimator.setIntValues(-mScrollTop, mFirstViewHeight);
                mOffsetAnimator.start();
            }
        }
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i("info", "velocityY:" + velocityY);
        // 滑动滚动
        return false;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public int getNestedScrollAxes() {
        // 垂直滚动
        return mParentHelper.getNestedScrollAxes();
    }
}
