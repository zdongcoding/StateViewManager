package com.zdg.stateviewmanager.mananger


/**
 * View状态观察者
 */
interface StateViewChanger : StateChanger {
    /**
     * onDestoryView
     */
    fun onDestoryView()
}
