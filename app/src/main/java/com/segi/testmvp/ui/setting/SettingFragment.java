package com.segi.testmvp.ui.setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.segi.testmvp.R;
import com.segi.testmvp.base.app.BaseFragment;
import com.segi.testmvp.widget.SimpleToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : tangjl
 * @date : 2018/11/12
 */
public class SettingFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.btn_about)
    Button btnAbout;
    Unbinder unbinder;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_zip_test)
    Button btnZipTest;
    private InteractionListener mListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AboutFragment.InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SettingFragment.InteractionListener");
        }
    }


    public interface InteractionListener {
        /**
         * 点击去关于界面
         */
        void onAboutItemClick();

        /**
         * 点击TestFragment
         */
        void onTestItemClick();

        /**
         * 点击去多接口组合一个请求
         */
        void onTestZipItemClick();
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

    @OnClick({R.id.toolbar, R.id.btn_about, R.id.btn_test, R.id.btn_zip_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                mActivity.finish();
                break;
            case R.id.btn_about:
                mListener.onAboutItemClick();
                break;
            case R.id.btn_test:
                mListener.onTestItemClick();
                break;
            case R.id.btn_zip_test:
                mListener.onTestZipItemClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
