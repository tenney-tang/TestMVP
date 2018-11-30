package com.segi.testmvp.ui.test;

import com.segi.data.bean.TestBean;
import com.segi.testmvp.base.mvp.MVPPresenter;
import com.segi.testmvp.base.mvp.MVPView;

import java.util.List;

/**
 * 创建契约类，连接presenter和view
 *
 * @author : tangjl
 * @date : 2018/11/8
 */
public interface TestContract {
    interface View extends MVPView {
        void onFinishRefresh();

        void onFinishError();

    }

    interface Presenter extends MVPPresenter {
        void loadTestData(int menuVersion, int menuSid, int settingsId);

        List<TestBean.DataListBean> getTestList();

    }
}
