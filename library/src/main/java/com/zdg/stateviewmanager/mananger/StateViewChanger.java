package com.zdg.stateviewmanager.mananger;



/**
 * View状态观察者
 */
public interface StateViewChanger extends StateChanger {
    /**
     * onDestoryView
     */
    void onDestoryView();
}
