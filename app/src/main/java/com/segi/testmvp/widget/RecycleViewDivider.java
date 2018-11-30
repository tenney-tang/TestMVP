package com.segi.testmvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecycleView自定义分割线
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mWidth;
    private int mHeight;

    private int mOrientation;
    private int mDividerPadding = 0;//分割线Padding
    private int left, right, top, bottom;

    public RecycleViewDivider(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * 支持自定义dividerDrawable
     *
     * @param context
     * @param orientation
     * @param dividerDrawable
     * @param dividerPadding
     */
    public RecycleViewDivider(Context context, int orientation, Drawable dividerDrawable, int dividerPadding) {
        mDivider = dividerDrawable;
        setOrientation(orientation);
        mDividerPadding = dividerPadding;
    }

    private void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    /**
     * 支持手动为无高宽的drawable制定宽度
     *
     * @param width
     */
    public void setWidth(int width) {
        this.mWidth = width;
    }

    /**
     * 支持手动为无高宽的drawable制定高度
     *
     * @param height
     */
    public void setHeight(int height) {
        this.mHeight = height;
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent, mDividerPadding);
        } else {
            drawHorizontal(c, parent);
        }
    }

    /**
     * 列表纵向分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent, int padding) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == childCount - 1) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
            } else {
                left = parent.getPaddingLeft() + padding;
                right = parent.getWidth() - parent.getPaddingRight() - padding;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            bottom = top + getDividerHeight();
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    /**
     * 列表横向分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin + Math.round(ViewCompat.getTranslationX(child));
            final int right = left + getDividerWidth();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, getDividerHeight());
        } else {
            outRect.set(0, 0, getDividerWidth(), 0);
        }
    }

    private int getDividerWidth() {
        return mWidth > 0 ? mWidth : mDivider.getIntrinsicWidth();
    }

    private int getDividerHeight() {
        return mHeight > 0 ? mHeight : mDivider.getIntrinsicHeight();
    }

}
