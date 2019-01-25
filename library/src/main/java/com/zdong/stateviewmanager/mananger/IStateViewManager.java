package com.zdong.stateviewmanager.mananger;

import android.view.View;
import android.view.ViewGroup;

import com.zdong.stateviewmanager.creator.StateObservable;

public interface IStateViewManager extends  StateViewObserver, StateObservable<View> {


    ViewGroup getOverallView();


    /**
     * 设置核心布局
     *
     * @param resId
     * @return
     */
    View setContentView(int resId);

    View setContentView(View view);


    View getContentView();
}
