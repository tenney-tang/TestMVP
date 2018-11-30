package com.segi.testmvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.segi.testmvp.R;
import com.segi.testmvp.base.app.BaseFragment;
import com.segi.testmvp.ui.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : tangjl
 * @date : 2018/11/8
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.btn_hold)
    Button btnHold;
    Unbinder unbinder;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_desc, R.id.btn_hold})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_desc:
                break;
            case R.id.btn_hold:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
        }
    }
}
