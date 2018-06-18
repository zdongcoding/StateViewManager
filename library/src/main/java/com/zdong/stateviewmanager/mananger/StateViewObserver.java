package com.zdong.stateviewmanager.mananger;



/**
 * View状态观察者
 */
public interface StateViewObserver  extends StateObserver{

    void onDestoryView();
}
