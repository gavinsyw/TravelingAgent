package com.example.travelingagent.util.listener;


import com.example.travelingagent.util.model.DataBean;

/**
 * Created by hbh on 2017/4/20.
 * 父布局Item点击监听接口
 */

public interface ItemClickListener {
    /**
     * 展开子Item
     * @param bean
     */
    void onExpandChildren(DataBean bean);

    /**
     * 隐藏子Item
     * @param bean
     */
    void onHideChildren(DataBean bean);
}
