package com.zdg.stateviewmanager

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

import com.zdg.stateviewmanager.creator.StateLoader
import com.zdg.stateviewmanager.creator.StateViewStore
import com.zdg.stateviewmanager.mananger.StateActionListener
import com.zdg.stateviewmanager.mananger.StateManager
import com.zdg.stateviewmanager.mananger.StateViewChanger
import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty

/**
 *
 * @author zoudong
 */
class StateManagerView : FrameLayout, StateViewChanger, StateLoader<View> {

    var mStateManager: StateManager = StateManager.newInstance(context, StateViewStore(context), this)

    override val state: String
        get() = mStateManager.state

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            try {
                throw IllegalStateException("StateManagerView can have only one direct child")
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        } else if (childCount == 1) {
            loadCoreView(getChildAt(0))
        }
    }

    private fun loadCoreView(child: View?) {
        if (child != null) {
            mStateManager.setContentView(child)
        }
    }
    override fun addState(stateView: IStateView<StateProperty>): Boolean {
        return mStateManager.addState(stateView)
    }

    override fun removeState(state: String): Boolean {
       return mStateManager.removeState(state)
    }

    override fun getStateView(state: String): View? {
        return mStateManager.getStateView(state)
    }
    override fun showState(state: String,showState: IStateView.ShowState?): Boolean {
        return mStateManager.showState(state)
    }
    override fun showState(state: StateProperty,showState: IStateView.ShowState?): Boolean {
        return mStateManager.showState(state)
    }
    override fun hideState(state: String): Boolean {
        return mStateManager.hideState(state)
    }
    override fun setStateActionListener(listener: StateActionListener) {
        mStateManager.setStateActionListener(listener)
    }

    override fun onDestroyView() {
        mStateManager.onDestroyView()
    }


    class Builder(context: Context) {
        internal var mStateManagerView: StateManagerView = StateManagerView(context)

        fun wrapper(view: View): Builder {
            mStateManagerView.mStateManager.setContentView(view)
            return this
        }

        fun builder(): StateViewChanger {
            return mStateManagerView
        }
    }

}
