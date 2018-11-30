package com.segi.testmvp.ui.all;

import com.segi.data.bean.TestBean;
import com.segi.data.bean.ZipBean;
import com.segi.testmvp.base.mvp.MVPPresenter;
import com.segi.testmvp.base.mvp.MVPView;

import java.util.List;

/**
 * @author : tangjl
 * @date : 2018/11/12
 */
public interface ZipContract {
    interface View extends MVPView {
        void onFinishRefresh();
        void onFinishError();
    }

    interface Presenter extends MVPPresenter {
     void refresh(int menuVersion,
                  int menuSid,
                  int settingsId,
                  int cityId,
                  int provinceId);
     List<ZipBean.DataListBean> getZipList();
     List<TestBean.DataListBean> getTestList();
    }
}
