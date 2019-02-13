package com.zdg.stateviewmanager.state

import android.view.View

/**
 * 核心UI界面
 */

class CoreStateView : BaseStateView<StateProperty> {

    override var state = "$STATE"


    override val layoutId: Int
        get() {

            try {
                throw IllegalStateException(this.toString() + "没有返回布局文件Id")
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                return -1
            }

        }

    constructor(coreView: View) {
        stateView = coreView
    }

    /**
     * 支持创建多个CoreState时，需要指定不同的State，达到分离业务逻辑
     *
     * @param coreView
     * @param state
     */
    constructor(coreView: View, state: String) {
        stateView = coreView
        this.state = state
    }


    override fun onViewCreated(stateView: View) {

    }

    override val showState: IStateView.ShowState
        get() = IStateView.ShowState.STACK


    companion object {
        const val STATE = "CoreState"
    }
}

