package com.zdong.stateviewmanager.mananger;


import com.zdong.stateviewmanager.state.StateProperty;

/**
 * 状态观察者
 */
public interface StateObserver {

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    boolean showState(String state);

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    boolean showState(StateProperty state);

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

}
