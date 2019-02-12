package com.zdg.stateviewmanager.mananger;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.zdg.stateviewmanager.state.IStateView;
import com.zdong.stateviewmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

        if (stater == null || overallView == null) {
            return false;
        }
        View staterView = stater.getView();
        if (staterView == null) {
            stater.onStateCreate(context, overallView);
            staterView = stater.getView();
            if (staterView == null) {
                return false;
            }
            staterView.setTag(R.id.state_view,stater);
            staterView.setTag(R.id.state_tag,stater.getState());
        }
        ViewGroup.LayoutParams layoutParams = overallView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        if (overallView.indexOfChild(staterView) < 0) {
            if (staterView.getParent() == null) {
                overallView.addView(staterView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (staterView.getParent() != overallView) {
                ViewGroup.LayoutParams params = staterView.getLayoutParams();
                if (staterView.getParent() instanceof ViewGroup) {
                    ViewGroup staterViewParent = (ViewGroup) staterView.getParent();
                    int ofChildIndex = staterViewParent.indexOfChild(staterView);
                    staterViewParent.removeViewInLayout(staterView);
                    overallView.addView(staterView, layoutParams);
                    staterViewParent.addView(overallView, ofChildIndex, params);
                }

            }
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
