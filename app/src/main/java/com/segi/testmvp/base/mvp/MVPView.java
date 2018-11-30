package com.segi.testmvp.base.mvp;

/**
 * MVP 中的 View
 */
public interface MVPView {

    /**
     * 界面第一次加载数据统一写在该方法中
     */
    void onFirstLoadingData();
}
