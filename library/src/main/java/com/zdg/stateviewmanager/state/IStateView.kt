package com.zdg.stateviewmanager.state

import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.zdg.stateviewmanager.mananger.StateActionListener


/**
 * State生命周期管理
 */
interface IStateView<T : StateProperty> {
    val TAG: String
        get() = "StateView"
    /**
     *
     * 获取当前状态
     *
     * @return
     */
    val state: String


    /**
     * 获取状态机的View
     *
     * @return
     */
    val view: View?

    val showState: ShowState

    /**
     * StateView创建后，可以做一些操作
     * @param context
     * @param parent
     */
    fun onStateCreate(context: Context, parent: ViewGroup)


    /**
     * StateView显示后，可以做一些操作
     */
    fun onStateResume()

    /**
     * StateView隐藏后，可以做一些操作
     */
    fun onStatePause()

    /**
     * StateView#remove
     */
    fun onStateDestroy()

    /**
     * 设置当前状态下的一些按钮操作回调
     *
     * @param listener
     */
    fun setStateActionListener(listener: StateActionListener?)

    /**
     * 定制View里面控件的内容
     * @param stateProperty
     */
    fun setViewProperty(stateProperty: T)

    enum class ShowState(var index:Int) {
        ONLY(-1),STACK(0);
    }

}
