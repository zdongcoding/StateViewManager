package com.zdg.basestateui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zdg.stateviewmanager.creator.StateViewStore
import com.zdg.stateviewmanager.mananger.StateActionListener
import com.zdg.stateviewmanager.mananger.StateManager
import com.zdg.stateviewmanager.mananger.StateViewChanger
import com.zdg.stateviewmanager.state.CoreStateView
import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty


/**
 * 状态设置 fragment
 * @author zouodng
 */
open class StateFragment : Fragment(), StateViewChanger {

    var mStateManager: StateManager?=null
    protected open val isEnableStateView:Boolean = true
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (isEnableStateView) {
             mStateManager= StateManager.newInstance(context!!, StateViewStore(context))
        }
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return mStateManager?.setContentView(getView(inflater,container)!!)
    }

    open fun getView(inflater: LayoutInflater, container: ViewGroup?):View?{
        return  null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        mStateManager?.setContentView(view)
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