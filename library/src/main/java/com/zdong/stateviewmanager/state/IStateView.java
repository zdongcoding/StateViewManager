package com.zdong.stateviewmanager.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zdong.stateviewmanager.mananger.StateActionListener;

/**
 * State生命周期管理
 */
public interface IStateView<T extends StateProperty> {

     String EMPTY = "empty_state_view";
     String EXCEPTION = "exception_state_view";
     String LAODING = "loading_state_view";
     String NETERROR = "net_error_state_view";

     String ERROR = "error_state";
    /**
     * StateView创建后，可以做一些操作
     * @param parent
     */
    void onStateCreate(Context context, ViewGroup parent);


    /**
     * StateView显示后，可以做一些操作
     */
    void onStateResume();

    /**
     * StateView隐藏后，可以做一些操作
     */
    void onStatePause();


    /**
     * 获取当前状态
     *
     * @return
     */
    String getState();

    /**
     * 设置当前状态下的一些按钮操作回调
     *
     * @param listener
     */
    void setStateActionListener(StateActionListener listener);


    /**
     * 获取状态机的View
     *
     * @return
     */
    View getView();


    /**
     * 定制View里面控件的内容
     */
    void setViewProperty(T stateProperty);
}
