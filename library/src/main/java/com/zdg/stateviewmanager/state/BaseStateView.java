package com.zdg.stateviewmanager.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdg.stateviewmanager.mananger.StateActionListener;


/**
 * 基础状态器，加载一些界面
 * 所有的状态机都要实现这个类
 * @author zoudong
 */
public abstract class BaseStateView<T extends StateProperty> implements IStateView<T> {

    public static final String STATE = "NONE";

    protected Context context;

    /**
     * 状态View
     */
    protected View stateView;

    /**
     * 状态属性：动态文案
     */
    protected T stateProperty;

    /**
     * 视图状态里面的按钮监听
     */
    protected StateActionListener stateActionListener;
    protected ViewGroup mOverallView;

    public BaseStateView() {
    }

    @Override
    public void onStateCreate(Context context, ViewGroup parent) {
        this.context = context;
        this.mOverallView = parent;
        stateView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        onViewCreated(stateView);

    }

    @Override
    public View getView() {
        return stateView;
    }

    protected abstract int getLayoutId();


    @Override
    public void setViewProperty(T stateProperty) {
        this.stateProperty = stateProperty;
    }

    /**
     * 获取当前状态view
     *
     * @param stateView
     * @return
     */
    protected abstract void onViewCreated(View stateView);

    @Override
    public void onStateResume() {
    }

    @Override
    public void onStatePause() {

    }

    @Override
    public void onStateDestroy() {

    }

    @Override
    public ShowState getShowState() {
        return ShowState.ONLY;
    }

    @Override
    public void setStateActionListener(StateActionListener listener) {
        this.stateActionListener = listener;
    }

    protected void actionListener(View view){
        if (this.stateActionListener!=null) {
            stateActionListener.onActionListener(getState(),view);
        }
    }

    public Context getContext() {
        return context;
    }
}
