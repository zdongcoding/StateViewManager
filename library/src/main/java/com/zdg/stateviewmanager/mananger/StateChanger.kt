package com.zdg.stateviewmanager.mananger


import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty

/**
 * 状态观察者
 */
interface StateChanger {

    /**
     * 获取当前状态
     *
     * @return
     */
    val state: String

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    fun showState(state: String): Boolean
    fun showState(state: String,showState: IStateView.ShowState?): Boolean
    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    fun hideState(state: String): Boolean
    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    fun showState(state: StateProperty): Boolean
    fun showState(state: StateProperty,showState: IStateView.ShowState?): Boolean

    /**
     * 设置当前状态下的一些按钮操作回调
     *
     * @param listener
     */
    fun setStateActionListener(listener: StateActionListener)

}
