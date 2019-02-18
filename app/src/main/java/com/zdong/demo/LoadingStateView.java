package com.zdong.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zdg.stateviewmanager.state.BaseStateView;

import org.jetbrains.annotations.NotNull;


public class LoadingStateView extends BaseStateView {
    public static final String STATE = "loading_state";


    @Override
    public void onStateCreate(@NotNull Context context, @NotNull ViewGroup parent) {
        super.onStateCreate(context, parent);
    }

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



    @Override
    public void onStateDestroy() {
        super.onStateDestroy();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
    }

    @Override
    public void onStateResume() {
        super.onStateResume();
    }
}
