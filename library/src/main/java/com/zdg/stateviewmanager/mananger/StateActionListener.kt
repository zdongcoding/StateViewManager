package com.zdg.stateviewmanager.mananger

import android.view.View

/**
 * 状态界面按钮点击事件监听
 */
interface StateActionListener {
    fun onActionListener(state: String, view: View)
}