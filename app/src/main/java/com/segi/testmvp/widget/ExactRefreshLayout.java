package com.segi.testmvp.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.segi.testmvp.R;

/**
 * 准确度高的下拉刷新,手势只有在往下的时候才出现下拉的球
 */
public class ExactRefreshLayout extends SwipeRefreshLayout {

    private final static String TAG = "ExactRefreshLayout";

    private int mTouchSlop;
    private float mPrevX;

    public ExactRefreshLayout(Context context) {
        this(context, null);
    }

    public ExactRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
//                L.d("refresh", "move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(event);
    }
}
