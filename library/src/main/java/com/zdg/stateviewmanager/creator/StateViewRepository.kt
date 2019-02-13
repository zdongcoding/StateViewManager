package com.zdg.stateviewmanager.creator

import android.content.Context
import android.text.TextUtils
import android.view.View


import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty

import java.util.HashMap

/**
 * 状态仓库
 * @author zoudong
 */
class StateViewRepository(private var mContext: Context) : StateLoader<View> {

    /**
     * 用于映射State和具体State对象
     */
    var stateMap = HashMap<String, IStateView<StateProperty>>(5)
        protected set

    override fun   addState(iStateView: IStateView<StateProperty>): Boolean {
        if (!TextUtils.isEmpty(iStateView.state)) {
            stateMap[iStateView.state] = iStateView
            return true
        }

        return false
    }

    override fun removeState(state: String): Boolean {
        if (!TextUtils.isEmpty(state)) {
            val iStateView = get(state)
            iStateView?.onStateDestroy()
            stateMap.remove(state)
            return true
        }
        return false
    }


    operator  fun  get(state: String): IStateView<StateProperty>? {

        var istate: IStateView<StateProperty>? = stateMap[state]
        if (istate != null) {
            return istate
        }

        val clazz = stateClazzMap[state]
        if (clazz != null) {
            try {
                istate = clazz.newInstance() as IStateView<StateProperty>
                addState(istate)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }

        return istate
    }

    override fun getStateView(state: String): View? {
        val stateChanger = get(state)
        return stateChanger?.view
    }


    fun clear() {
        stateMap.clear()
    }

    companion  object Instance {
        /**
         * 注册 状态
         * @param state
         * @param clazz
         */
        fun registerState(state: String, clazz: Class<*>) {
            stateClazzMap[state] = clazz
        }

        /**
         * 注销 状态
         * @param state
         */
        fun unregisterState(state: String) {
            stateClazzMap.remove(state)
        }

        /**
         * 用于映射 State 和 Class对象
         */
        protected var stateClazzMap = HashMap<String, Class<*>>(5)
    }

}
