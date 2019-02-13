package com.zdg.stateviewmanager.mananger

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty
import com.zdong.stateviewmanager.R

import java.util.ArrayList


/**
 * StateView控制器，控制StateView显示、隐藏、创建
 */
internal object StateViewHelper {

    /**
     * 显示视图
     * 如果stateView为null，则需要创建
     *
     * @return
     */
    fun showStateView(context: Context, overallView: ViewGroup?, stater: IStateView<*>?): Boolean {

        if (stater == null || overallView == null) {
            return false
        }
        var staterView: View? = stater.view
        if (staterView == null) {
            stater.onStateCreate(context, overallView)
            staterView = stater.view
            if (staterView==null) {
                return  false
            }
            staterView.setTag(R.id.state_view, stater)
            staterView.setTag(R.id.state_tag, stater.state)
        }
        var layoutParams: ViewGroup.LayoutParams? = overallView.layoutParams
        if (layoutParams == null) {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        if (overallView.indexOfChild(staterView) < 0) {
            if (staterView.parent == null) {
                overallView.addView(staterView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            } else if (staterView.parent !== overallView) {
                val params = staterView.layoutParams
                if (staterView.parent is ViewGroup) {
                    val staterViewParent = staterView.parent as ViewGroup
                    val ofChildIndex = staterViewParent.indexOfChild(staterView)
                    staterViewParent.removeViewInLayout(staterView)
                    overallView.addView(staterView, layoutParams)
                    staterViewParent.addView(overallView, ofChildIndex, params)
                }

            }
        }
        stater.view?.let {
            it.visibility = View.VISIBLE
            stater.onStateResume()
        }
        return true
    }

    /**
     * 隐藏视图
     * 如果stateView没有创建，则不做处理
     *
     * @return
     */
    fun hideStateView(stater: IStateView<StateProperty>?): Boolean {

        if (stater?.view == null) {
            return false
        }
        stater.view?.let {
            it.visibility = View.GONE
            stater.onStatePause()
        }

        return true
    }

}
