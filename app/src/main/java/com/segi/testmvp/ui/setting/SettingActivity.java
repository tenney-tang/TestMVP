package com.segi.testmvp.ui.setting;

import android.os.Bundle;

import com.segi.testmvp.R;
import com.segi.testmvp.base.app.BaseActivity;
import com.segi.testmvp.ui.all.ZipFragment;
import com.segi.testmvp.ui.test.TestFragment;
import com.segi.testmvp.utils.FragmentUtils;
import com.segi.testmvp.utils.RxBus;

/**
 * @author : tangjl
 * @date : 2018/11/12
 */
public class SettingActivity extends BaseActivity implements AboutFragment.InteractionListener
                                                                ,SettingFragment.InteractionListener {
    private SettingFragment settingFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            settingFragment = SettingFragment.newInstance();
            replaceFragment(R.id.fl_content, settingFragment);
        }
    }


    @Override
    public void onAboutBack() {
        //关于里面点击返回
        onBackPressed();
    }

    @Override
    public void onAboutItemClick() {
        //关于界面
        Bundle b = new Bundle();
        b.putString("key", "value");
        FragmentUtils.switchFragment(getSupportFragmentManager(),
                R.id.fl_content,
                settingFragment,
                AboutFragment.class,
                b,
                true,
                null,
                true);
    }

    @Override
    public void onTestItemClick() {
        //TestFragment界面
        FragmentUtils.switchFragment(getSupportFragmentManager(),
                R.id.fl_content,
                settingFragment,
                TestFragment.class,
                null,
                true,
                null,
                true);
    }

    @Override
    public void onTestZipItemClick() {
        //TestFragment界面
        FragmentUtils.switchFragment(getSupportFragmentManager(),
                R.id.fl_content,
                settingFragment,
                ZipFragment.class,
                null,
                true,
                null,
                true);
    }
}
