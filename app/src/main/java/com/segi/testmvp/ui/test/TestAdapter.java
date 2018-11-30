package com.segi.testmvp.ui.test;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.segi.data.bean.TestBean;
import com.segi.testmvp.R;

import java.util.List;

/**
 * @author : tangjl
 * @date : 2018/11/8
 */
public class TestAdapter extends BaseQuickAdapter<TestBean.DataListBean> {

    public TestAdapter(int layoutResId, List<TestBean.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TestBean.DataListBean dataListBean) {
        baseViewHolder.setText(R.id.txt_test, dataListBean.serviceDesc);
    }
}
