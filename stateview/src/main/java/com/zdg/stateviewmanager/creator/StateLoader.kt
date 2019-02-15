package com.zdg.stateviewmanager.creator


import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty


/**
 * 状态加载器，加载各种状态
 *
 * @author zoudong
 */
interface StateLoader<T> {


    /**
     * 注册一个状态器，如果有重复的状态改变器，则不添加
     *
     * @param changger
     */
    fun   addState(stateView: IStateView<StateProperty>): Boolean

    /**
     * 如果对应的状态加载器
     *
     * @param state 状态
     */
    fun removeState(state: String): Boolean


    fun getStateView(state: String): T?
}
