package com.zdg.stateviewmanager.creator

import android.text.TextUtils
import android.view.View


import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty

import java.util.HashMap

/**
 * 状态仓库
 * @author zoudong
 */
class StateViewStore : StateLoader<View> {

    /**
     * 用于映射State和具体State对象
     */
    var stateMap = HashMap<String, IStateView<StateProperty>>(5)
        private set

    override fun   addState(stateView: IStateView<StateProperty>): Boolean {
        if (!TextUtils.isEmpty(stateView.state)) {
            stateMap[stateView.state] = stateView
            return true
        }

        return false
    }

    override fun removeState(state: String): Boolean {
        if (!TextUtils.isEmpty(state)) {
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
        @JvmStatic
        fun registerState(state: String, clazz: Class<*>) {
            stateClazzMap[state] = clazz
        }

        /**
         * 注销 状态
         * @param state
         */
        @JvmStatic
        fun unregisterState(state: String) {
            stateClazzMap.remove(state)
        }

        /**
         * 用于映射 State 和 Class对象
         */
        private var stateClazzMap = HashMap<String, Class<*>>(5)
    }

}
