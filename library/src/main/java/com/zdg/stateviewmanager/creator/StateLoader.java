package com.zdg.stateviewmanager.creator;


import com.zdg.stateviewmanager.state.IStateView;


/**
 * 状态加载器，加载各种状态
 *
 * @author zoudong
 */
public interface StateLoader<T> {


    /**
     * 注册一个状态器，如果有重复的状态改变器，则不添加
     *
     * @param changger
     */
    boolean addState(IStateView changger);

    /**
     * 如果对应的状态加载器
     *
     * @param state 状态
     */
    boolean removeState(String state);


     T getStateView(String state);
}
