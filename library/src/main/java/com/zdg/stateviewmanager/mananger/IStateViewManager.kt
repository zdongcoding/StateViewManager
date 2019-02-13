package com.zdg.stateviewmanager.mananger

import android.view.View
import android.view.ViewGroup

import com.zdg.stateviewmanager.creator.StateLoader

interface IStateViewManager : StateViewChanger, StateLoader<View> {


    val overallView: ViewGroup?


    val contentView: View?


    /**
     * 设置核心布局
     *
     * @param resId
     * @return
     */
    fun setContentView(resId: Int): View

    fun setContentView(view: View): View
}
