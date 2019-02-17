package com.zdg.basestateui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.zdg.stateviewmanager.creator.StateViewStore
import com.zdg.stateviewmanager.mananger.StateActionListener
import com.zdg.stateviewmanager.mananger.StateManager
import com.zdg.stateviewmanager.mananger.StateViewChanger
import com.zdg.stateviewmanager.state.CoreStateView
import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty

open class StateFragment : Fragment(), StateViewChanger {

    var mStateManager: StateManager?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mStateManager= StateManager.newInstance(context!!, StateViewStore(context))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mStateManager?.setContentView(view)
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
        super.onDestroyView()
        mStateManager?.onDestroyView()
    }
}