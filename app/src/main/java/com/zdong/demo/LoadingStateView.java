package com.zdong.demo;

import android.view.View;

import com.zdg.stateviewmanager.state.BaseStateView;



public class LoadingStateView extends BaseStateView {
    public static final String STATE = "loading_state";
    @Override
    protected int getLayoutId() {
        return R.layout.base_loading_layout;
    }

    @Override
    protected void onViewCreated(View stateView) {
        stateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener(v);
            }
        });
    }

    @Override
    public String getState() {
        return STATE;
    }


    @Override
    public ShowState getShowState() {
        return ShowState.STACK;
    }
}
