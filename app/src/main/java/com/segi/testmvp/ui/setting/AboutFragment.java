package com.segi.testmvp.ui.setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
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
public class AboutFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.txt_about)
    TextView txtAbout;
    Unbinder unbinder;
    private InteractionListener mListener;
    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_about;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://www.jd.com/");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AboutFragment.InteractionListener");
        }
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

    @OnClick({R.id.toolbar, R.id.txt_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                mListener.onAboutBack();
                break;
            case R.id.txt_about:
                break;
        }
    }

    public interface InteractionListener {
        /**
         * 点击返回
         */
        void onAboutBack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
