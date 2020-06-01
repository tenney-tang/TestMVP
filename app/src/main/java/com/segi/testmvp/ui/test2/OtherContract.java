package com.segi.testmvp.ui.test2;

import com.segi.data.bean.TestBean;
import com.segi.testmvp.base.mvp.MVPPresenter;
import com.segi.testmvp.base.mvp.MVPView;

import java.util.List;

public class OtherContract {

    interface View extends MVPView {
        void onFinishRefresh();
        void onFinishError();
    }

    interface Presenter extends MVPPresenter {
        void loadData(int menuVersion, int menuSid, int settingsId);
        List<TestBean.DataListBean> getDataList();

    }

}
