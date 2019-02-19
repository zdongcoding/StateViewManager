package com.zdg.basestateui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zdg.stateviewmanager.creator.StateViewStore
import com.zdg.stateviewmanager.mananger.StateActionListener
import com.zdg.stateviewmanager.mananger.StateManager
import com.zdg.stateviewmanager.mananger.StateViewChanger
import com.zdg.stateviewmanager.state.CoreStateView
import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty

/**
 * 状态设置 Activity
 * @author zouodng
 */
open class StateActivity:AppCompatActivity(), StateViewChanger {

    var mStateManager:StateManager?=null
    protected open val isEnableStateView:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        if(isEnableStateView){
            mStateManager = StateManager.newInstance(this, StateViewStore())
        }
        super.onCreate(savedInstanceState)
    }
    override fun setContentView(layoutResID: Int) {
        if (mStateManager != null) {
            super.setContentView(mStateManager?.setContentView(layoutResID))
        } else {
            super.setContentView(layoutResID)
        }

    }
    override fun setContentView(view: View?) {
        super.setContentView(mStateManager?.setContentView(view!!)?:view)
    }
    override val state: String
        get() = mStateManager?.state ?: CoreStateView.STATE

    override fun hideState(state: String): Boolean {
        return mStateManager?.hideState(state) ?: false
    }

    override fun setStateActionListener(listener: StateActionListener) {
        mStateManager?.setStateActionListener(listener)
    }

    override fun showState(state: StateProperty, showState: IStateView.ShowState?): Boolean {
        return mStateManager?.showState(state, showState) ?: false
    }

    override fun showState(state: String, showState: IStateView.ShowState?): Boolean {
        return mStateManager?.showState(state, showState) ?: false
    }
    override fun onDestroyView() {
        mStateManager?.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyView()
    }
}