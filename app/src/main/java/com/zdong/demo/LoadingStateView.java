package com.zdong.demo;

import android.util.Log;
import android.view.View;

import com.zdong.stateviewmanager.state.BaseStateView;

public class LoadingStateView extends BaseStateView {
    public static final String STATE = "Loading_state";
    @Override
    protected int getLayoutId() {
        return R.layout.base_loading_layout;
    }

    @Override
    protected void onViewCreated(View stateView) {
        Log.e("zoudong", "onViewCreated====" + "stateView = [" + stateView + "]");
    }

    @Override
    public void onStateResume() {
        super.onStateResume();

        Log.e("zoudong", "onStateResume====" + getState());
    }

    @Override
    public String getState() {
        return STATE;
    }
}
