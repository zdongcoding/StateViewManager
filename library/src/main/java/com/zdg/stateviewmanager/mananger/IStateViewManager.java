package com.zdg.stateviewmanager.mananger;

import android.view.View;
import android.view.ViewGroup;

import com.zdg.stateviewmanager.creator.StateLoader;

public interface IStateViewManager extends StateViewChanger, StateLoader<View> {


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
