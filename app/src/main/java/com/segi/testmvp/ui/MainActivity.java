package com.segi.testmvp.ui;

import android.os.Bundle;

import com.segi.data.util.Logger;
import com.segi.testmvp.R;
import com.segi.testmvp.base.app.BaseActivity;
import com.segi.testmvp.ui.test.TestFragment;

/**
 * @author : tangjl
 * @date : 2018/11/7
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void addFragment(Bundle savedInstanceState) {
        checkAndAddFragment(R.id.main_fragment, TAG, MainFragment.class, null);
        Logger.d(TAG,"tjl测试》》》》》》》》》》>>>>>>>>>");
    }
}
