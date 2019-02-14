package com.zdg.stateviewmanager.mananger

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.zdg.stateviewmanager.creator.StateViewRepository
import com.zdg.stateviewmanager.state.BaseStateView
import com.zdg.stateviewmanager.state.CoreStateView
import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty
import com.zdong.stateviewmanager.R

/**
 * 状态管理类
 */
class StateManager private constructor(private val context: Context) : IStateViewManager {
    /**
     * 所有 状态管理的配置
     */
    private lateinit var stateRepository: StateViewRepository

    /**
     * 当前显示的状态
     */
    private var currentState: IStateView<StateProperty>? = null

    private var listener: StateActionListener? = null
    override var overallView: ViewGroup? = null

    override val state: String
        get() = if (currentState == null) BaseStateView.STATE else currentState!!.state

    override val contentView: View
        get() = getStateView(CoreStateView.STATE)!!

    /*------------------------StateObserver------------------------------*/
    override fun hideState(state: String): Boolean {
        val iState = stateRepository[state]
        if (iState == null) {
            Log.e("StateManager", "没有找到对应的" + state + "State，需要调用addState()进行注册")
            return false
        }
        return StateViewHelper.hideStateView(iState)
    }
    override fun showState(state: String): Boolean {
       return showState(state,null)
    }

    override fun showState(state: String, showState: IStateView.ShowState?): Boolean {
        if (overallView == null || TextUtils.isEmpty(state)) {
            return false
        }

        val iState = stateRepository[state]
        if (iState == null) {
            Log.e("StateManager", "没有找到对应的" + state + "State，需要调用addState()进行注册")
            return false
        }
        iState.showState = showState ?: iState.showState
        iState.setStateActionListener(listener)
        val isSuccess = StateViewHelper.showStateView(context, overallView, iState)
        if (!isSuccess) {
            return false
        }
        if (state == currentState?.state) {
            return true
        }
        if (iState.showState === IStateView.ShowState.ONLY) {
//            StateViewHelper.hideStateView(currentState)
            hideOtherStateView(iState)
        }
        currentState = iState
        return true
    }

    override fun showState(state: StateProperty): Boolean {
        return showState(state,null)
    }

    override fun showState(state: StateProperty, showState: IStateView.ShowState?): Boolean {
        val result = showState(state.state, showState)
        if (result) {
            val baseStater = stateRepository[state.state]
            baseStater?.setViewProperty(state)
        }
        return result
    }

    private fun hideOtherStateView(stater: IStateView<StateProperty>?) {
        if (stater == null) {
            return
        }
        val view = stater.view
        overallView?.let {
            val index = it.indexOfChild(view)
            for (childCount in it.childCount - 1 downTo 0) {
                //Object tag = parent.getChildAt(childCount).getTag(R.id.state_tag);
                val other = it.getChildAt(childCount).getTag(R.id.state_view)
                if (childCount != index && other is IStateView<*> /*&& other.state != CoreStateView.STATE*/) {
                    StateViewHelper.hideStateView(other as? IStateView<StateProperty>)
                }
            }
        }

    }


    override fun setStateActionListener(listener: StateActionListener) {
        this.listener = listener
        val iterator = stateRepository.stateMap.values.iterator()
        while (iterator.hasNext()) {
            val stateChanger = iterator.next()
            stateChanger.setStateActionListener(listener)
        }
    }

    override fun setContentView(resId: Int): View {

        if (overallView == null) {
            overallView = FrameLayout(context)
            overallView!!.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        val view = LayoutInflater.from(context).inflate(resId, overallView, false)
        //注册核心view的State
        addState(CoreStateView(view))
        showState(CoreStateView.STATE)

        return overallView!!
    }

    override fun setContentView(view: View): View {
        if (overallView == null) {

            overallView = FrameLayout(context)
            overallView!!.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        //注册核心view的State
        addState(CoreStateView(view))
        showState(CoreStateView.STATE)
        return overallView!!
    }


    /*------------------------StateObservable------------------------------*/

    /**
     * 注册一个状态改变器，如果有重复的状态改变器，则不添加
     *
     * @param stateView
     */
    override fun addState(stateView: IStateView<StateProperty>): Boolean {
        stateView.setStateActionListener(listener)
        //如果存在替换流程，需要将之前的StateView移除
        if (!TextUtils.isEmpty(stateView.state)) {
            removeState(stateView.state)
        }
        return stateRepository.addState(stateView)
    }


    /**
     * 移除对应的状态加载器
     */
    override fun removeState(state: String): Boolean {
        val stateView = getStateView(state)
        //移除对应状态的同时，也需要移除对应的View
        if (stateView != null) {
            overallView!!.removeView(stateView)
            val stateChanger = stateRepository[state]
            stateChanger?.onStateDestroy()
        }
        return stateRepository.removeState(state)
    }

    override fun getStateView(state: String): View? {

        val stateChanger = stateRepository[state]
        return stateChanger?.view

    }

    fun getStateRepository(): StateViewRepository {
        return stateRepository
    }

    fun setStateRepository(stateRepository: StateViewRepository) {
        val iStateView = this.stateRepository[CoreStateView.STATE]
        iStateView?.let {
            stateRepository.addState(it)
        }
        this.stateRepository = stateRepository
    }


    override fun onDestroyView() {
        overallView?.let {
            val childCount = it.childCount
            var arrayview= mutableListOf<View>()
            for (index in 0 until childCount){
                val childAt = it.getChildAt(index)
                val other = childAt.getTag(R.id.state_view)
                if (other is IStateView<*>) {
                    if (other.state != CoreStateView.STATE) {
                        arrayview.add(childAt)
                        val stateChanger = stateRepository[other.state]
                        stateChanger?.onStateDestroy()
                    }
                }
            }
            for (view in arrayview) {
                it.removeView(view)
            }

        }
        stateRepository.clear()
    }

    companion object INSTACE {

        fun newInstance(context: Context, repository: StateViewRepository): StateManager {
            val stateManager = StateManager(context)
            stateManager.stateRepository = repository
            return stateManager
        }

        fun newInstance(context: Context, repository: StateViewRepository, overallView: ViewGroup): StateManager {
            val stateManager = StateManager(context)
            stateManager.stateRepository = repository
            stateManager.overallView = overallView
            return stateManager
        }
    }

}
