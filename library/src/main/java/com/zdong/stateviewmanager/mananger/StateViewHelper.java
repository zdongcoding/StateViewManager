package com.zdong.stateviewmanager.mananger;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zdong.stateviewmanager.state.IStateView;


/**
 * StateView控制器，控制StateView显示、隐藏、创建
 */
 class StateViewHelper {

    /**
     * 显示视图
     * 如果stateView为null，则需要创建
     *
     * @return
     */
    public static boolean showStateView(Context context, ViewGroup overallView, IStateView stater) {

        if (stater == null || overallView ==null) {
            return false;
        }
        View staterView = stater.getView();
        if (staterView == null) {
            stater.onStateCreate(context, overallView);
            staterView = stater.getView();
            if (staterView == null) {
                return false;
            }
        }
        if (overallView.indexOfChild(staterView) < 0 && staterView.getParent() == null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
            params.gravity = Gravity.CENTER;
            overallView.addView(staterView, params);
        }
        stater.getView().setVisibility(View.VISIBLE);
        stater.onStateResume();
        return true;
    }

    /**
     * 隐藏视图
     * 如果stateView没有创建，则不做处理
     *
     * @return
     */
    public static boolean hideStateView(IStateView stater) {

        if (stater == null || stater.getView() == null) {
            return false;
        }
        stater.getView().setVisibility(View.GONE);
        stater.onStatePause();
        return true;
    }

}
