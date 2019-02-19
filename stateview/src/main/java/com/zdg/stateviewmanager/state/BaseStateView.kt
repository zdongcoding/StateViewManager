package com.zdg.stateviewmanager.state

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zdg.stateviewmanager.mananger.StateActionListener


/**
 * 基础状态器，加载一些界面
 * 所有的状态机都要实现这个类
 * @author zoudong
 */
abstract class BaseStateView<T : StateProperty> : IStateView<T> {

    lateinit var context: Context
        protected set

    /**
     * 状态View
     */
    protected  var stateView: View? =null
    /**
     * 状态属性：动态文案
     */
    private  var stateProperty: T?=null

    override  var showState: IStateView.ShowState=IStateView.ShowState.ONLY

    /**
     * 视图状态里面的按钮监听
     */
    var listener: StateActionListener? = null

    lateinit var mOverallView: ViewGroup

    protected abstract val layoutId: Int

    override fun onStateCreate(context: Context, parent: ViewGroup) {
        this.context = context
        this.mOverallView = parent
        stateView = LayoutInflater.from(context).inflate(layoutId, parent, false)
        onViewCreated(stateView!!)

    }

    override val view: View?
        get() = stateView

    override fun setViewProperty(stateProperty: T) {
        this.stateProperty=stateProperty
    }

    override fun setStateActionListener(listener: StateActionListener?) {
        this.listener = listener
    }
    /**
     * 获取当前状态view
     *
     * @param stateView
     * @return
     */
    protected abstract fun onViewCreated(stateView: View)

    override fun onStateResume() {
        if(DEBUG)Log.d(TAG, "onStateResume====$state")
    }

    override fun onStatePause() {
        if(DEBUG)Log.d(TAG, "onStatePause====$state")
    }

    override fun onStateDestroy() {
        if(DEBUG)Log.d(TAG, "onStateDestroy====$state")
    }




    protected fun actionListener(view: View) {
         listener?.onActionListener(state, view,stateProperty)
    }

    companion object {
        const val TAG = "BaseStateView"
        const val STATE = "NONE"
        var DEBUG=true
    }
}
