package com.zdong.stateviewmanager.state;

import android.view.View;

/**
 * 核心UI界面
 */
public class CoreStateView extends BaseStateView {

    public static final String STATE = "CoreState";

    private String state = STATE;

    public CoreStateView(View coreView) {
        stateView = coreView;
    }

    /**
     * 支持创建多个CoreState时，需要指定不同的State，达到分离业务逻辑
     *
     * @param coreView
     * @param state
     */
    public CoreStateView(View coreView, String state) {
        stateView = coreView;
        this.state = state;
    }


    /**
     * 如果使用这个构造，需要重写{@link BaseStateView#getLayoutId()}方法
     */
    protected CoreStateView() { }


    @Override
    protected int getLayoutId() {

        try {
            throw new IllegalStateException(this + "没有返回布局文件Id");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    protected void onViewCreated(View stateView) {

    }


    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
