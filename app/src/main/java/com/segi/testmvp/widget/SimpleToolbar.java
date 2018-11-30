package com.segi.testmvp.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.segi.testmvp.R;


/**
 * 简单的 Toolbar
 */
public class SimpleToolbar extends FrameLayout {

    public static final int DEF_TEXTSIZE = 18;
    protected ImageView mNavigationImageView;
    protected TextView mTitleTextView;
    protected TextView mRightTV;
    protected ImageView mRightFirstIconImageView;
    protected ImageView mRightSecondIconImageView;

    public SimpleToolbar(Context context) {
        super(context);
        init();
    }

    public SimpleToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(context, attrs);
    }

    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(context, attrs);
    }

    /**
     * 初始化布局
     */
    private void init() {
        View view = inflate(getContext(), R.layout.widget_simple_toolbar, this);
        mNavigationImageView = view.findViewById(R.id.simple_toolbar_navigation_icon);
        mTitleTextView = view.findViewById(R.id.simple_toolbar_title);
        mRightTV = view.findViewById(R.id.tv_right);
        mRightFirstIconImageView = view.findViewById(R.id.simple_toolbar_right_icon1);
        mRightSecondIconImageView = view.findViewById(R.id.simple_toolbar_right_icon2);
    }

    /**
     * 初始化属性
     *
     * @param context context
     * @param attrs   attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar);
        for (int i = 0; i < typedArray.length(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.SimpleToolbar_stb_navigation_icon:
                    int resourceId = typedArray.getResourceId(index, 0);
                    if (resourceId != 0) {
                        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
                        mNavigationImageView.setImageDrawable(drawable);
                    }
                    break;
                case R.styleable.SimpleToolbar_stb_title_text:
                    mTitleTextView.setText(typedArray.getString(index));
                    break;
                case R.styleable.SimpleToolbar_stb_title_text_color:
                    mTitleTextView.setTextColor(typedArray.getColor(index,
                            ContextCompat.getColor(context, R.color.simple_toolbar_title_default_color)));
                    break;
                case R.styleable.SimpleToolbar_stb_title_text_size:
                    mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            typedArray.getDimension(R.styleable.SimpleToolbar_stb_title_text_size, DEF_TEXTSIZE));
                    break;
                case R.styleable.SimpleToolbar_stb_right_text:
                    mRightTV.setVisibility(View.VISIBLE);
                    mRightTV.setText(typedArray.getString(index));
                    break;
                case R.styleable.SimpleToolbar_stb_right_text_size:
                    mRightTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.SimpleToolbar_stb_right_text_size, DEF_TEXTSIZE));
                    break;
                case R.styleable.SimpleToolbar_stb_right_text_color:
                    mRightTV.setTextColor(typedArray.getColor(index,
                            ContextCompat.getColor(context, R.color.simple_toolbar_right_default_color)));
                    break;
                default:
                    break;

            }
        }
        typedArray.recycle();
    }

    /**
     * 设置title右边图标
     *
     * @param drawableRight
     */
    public void setTitleDrawableRight(int drawableRight) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableRight);
        drawable.setBounds(5, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        mTitleTextView.setCompoundDrawables(null, null, drawable, null);
        mTitleTextView.setPadding(0, 0, 10, 0);
    }

    /**
     * 设置左侧图标点击监听
     *
     * @param listener View.OnClickListener
     */
    public void setOnNavigationClickListener(OnClickListener listener) {
        if (mNavigationImageView != null) {
            mNavigationImageView.setOnClickListener(listener);
        }
    }

    /**
     * 设置标题栏点击监听
     *
     * @param listener View.OnClickListener
     */
    public void setOnTitleClickListener(OnClickListener listener) {
        if (mTitleTextView != null) {
            mTitleTextView.setOnClickListener(listener);
        }
    }

    /**
     * 设置最右侧 Icon 的点击事件
     *
     * @param listener View.onClickListener
     */
    public void setOnRightFirstIconClickListener(OnClickListener listener) {
        if (mRightFirstIconImageView != null) {
            mRightFirstIconImageView.setOnClickListener(listener);
        }
    }

    /**
     * 设置右侧第二个 Icon 的点击事件
     *
     * @param listener View.onClickListener
     */
    public void setOnRightSecondIconClickListener(OnClickListener listener) {
        if (mRightSecondIconImageView != null) {
            mRightSecondIconImageView.setOnClickListener(listener);
        }
    }

    /**
     * 设置右侧第二个 Icon 的点击事件
     *
     * @param listener View.onClickListener
     */
    public void setOnRightTextClickListener(OnClickListener listener) {
        if (mRightTV != null) {
            mRightTV.setOnClickListener(listener);
        }
    }

    /**
     * 设置左侧图标资源
     *
     * @param drawableRes drawableRes
     */
    public void setNavigationIcon(@DrawableRes int drawableRes) {
        mNavigationImageView.setImageResource(drawableRes);
    }

    /**
     * 设置左侧图标
     *
     * @param drawable drawable
     */
    public void setNavigationIconDrawable(Drawable drawable) {
        mNavigationImageView.setImageDrawable(drawable);
    }

    /**
     * 设置右侧第一个图标的资源
     *
     * @param drawableRes drawableRes
     */
    public void setRightFirstIcon(@DrawableRes int drawableRes) {
        mRightFirstIconImageView.setImageResource(drawableRes);
    }

    /**
     * 设置右侧第一个图标
     *
     * @param drawable drawable
     */
    public void setRightFirstIconDrawable(Drawable drawable) {
        mRightFirstIconImageView.setImageDrawable(drawable);
    }

    /**
     * 设置右侧第二个图标的资源
     *
     * @param drawableRes drawableRes
     */
    public void setRightSecondIcon(@DrawableRes int drawableRes) {
        mRightSecondIconImageView.setImageResource(drawableRes);
    }

    /**
     * 设置右侧第二个图标
     *
     * @param drawable drawable
     */
    public void setRightSecondIconDrawable(Drawable drawable) {
        mRightSecondIconImageView.setImageDrawable(drawable);
    }

    /**
     * 设置标题资源
     *
     * @param stringRes stringRes
     */
    public void setTitleText(@StringRes int stringRes) {
        mTitleTextView.setText(stringRes);
    }

    /**
     * 设置标题内容
     *
     * @param string string
     */
    public void setTitleText(String string) {
        mTitleTextView.setText(string);
    }

    /**
     * 给字体设置样式
     *
     * @param styleRes styleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("deprecated")
    public void setTitleStyle(@StyleRes int styleRes) {
        mTitleTextView.setTextAppearance(styleRes);
    }

    /**
     * 设置右边TextView的点击事件
     *
     * @param listener
     */
    public void setRightTextClickListener(OnClickListener listener) {
        if (mRightTV != null) {
            mRightTV.setOnClickListener(listener);
        }
    }

    /**
     * 设置标题资源
     *
     * @param stringRes stringRes
     */
    public void setRightText(@StringRes int stringRes) {
        mRightTV.setText(stringRes);
    }

    /**
     * 设置标题内容
     *
     * @param string string
     */
    public void setRightText(String string) {
        mRightTV.setText(string);
    }
}
