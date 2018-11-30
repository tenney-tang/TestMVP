package com.segi.testmvp.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.segi.data.util.Logger;
import com.segi.testmvp.R;

import javax.inject.Inject;

/**
 * Toast 工具
 */
public class ToastUtils {

    private Context context;
    private Toast mToast;
    private TextView mTvToastMessage;
    private View mView;

    @Inject
    public ToastUtils(Context context) {
        this.context = context;
        Logger.d("ToastUtils");
        mView = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
        mTvToastMessage = (TextView) mView.findViewById(R.id.tv_toast_message);
        mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(mView);
    }

    public void makeText(String msg, int duration) {
        Logger.d("makeText");
        mTvToastMessage.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }

    public void makeText(int resId, int duration) {
        makeText(context.getResources().getString(resId), duration);
    }

    public void makeText(String msg) {
        makeText(msg, Toast.LENGTH_SHORT);
    }

    public void makeText(int resId) {
        makeText(context.getResources().getString(resId), Toast.LENGTH_SHORT);
    }
}
