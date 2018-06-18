package com.zdong.stateviewmanager.mananger;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zdong.stateviewmanager.creator.StateViewRepository;
import com.zdong.stateviewmanager.state.BaseStateView;
import com.zdong.stateviewmanager.state.CoreStateView;
import com.zdong.stateviewmanager.state.IStateView;
import com.zdong.stateviewmanager.state.StateProperty;

import java.util.Iterator;

/**
 * 状态管理类
 */
public class StateManager implements IStateViewManager {

    private Context context;
    private StateViewRepository stateRepository; //所有 状态管理的配置

    private IStateView currentState;  //当前显示的状态

    private StateActionListener listener;
    private ViewGroup overallView;

    protected StateManager(Context context) {
        this.context = context;

    }

    public static StateManager newInstance(Context context, StateViewRepository repository) {
        StateManager stateManager = new StateManager(context);
        stateManager.stateRepository = repository;
        return stateManager;
    }

    public static StateManager newInstance(Context context, StateViewRepository repository, ViewGroup overallView) {
        StateManager stateManager = new StateManager(context);
        stateManager.stateRepository = repository;
        stateManager.overallView = overallView;
        return stateManager;
    }

    /*------------------------StateObserver------------------------------*/
    @Override
    public boolean showState(String state) {
        if (overallView == null || TextUtils.isEmpty(state)) {
            return false;
        }

        IStateView iState = stateRepository.get(state);
        if (iState == null) {
            Log.e("StateManager", "没有找到对应的" + state + "State，需要调用addState()进行注册");
            return false;
        }
        iState.setStateActionListener(listener);
        boolean isSuccess = StateViewHelper.showStateView(context, overallView, iState);
        if (!isSuccess) {
            return false;
        }
        if (currentState != null) {
            if (currentState.getState().equals(state)) {
                return true;
            }
            StateViewHelper.hideStateView(currentState);
        }

        currentState = iState;
        return true;
    }

    @Override
    public boolean showState(StateProperty state) {
        boolean result = showState(state.getState());
        if (result) {
            IStateView baseStater = stateRepository.get(state.getState());
            baseStater.setViewProperty(state);
        }
        return result;
    }

    @Override
    public String getState() {
        return currentState == null ? BaseStateView.STATE : currentState.getState();
    }

    @Override
    public void setStateActionListener(StateActionListener listener) {
        this.listener = listener;
        Iterator<IStateView> iterator = stateRepository.getStateMap().values().iterator();
        while (iterator.hasNext()) {
            IStateView stateChanger = iterator.next();
            stateChanger.setStateActionListener(listener);
        }
    }


    /*------------------------StateObservable------------------------------*/

    /**
     * 注册一个状态改变器，如果有重复的状态改变器，则不添加
     *
     * @param stater
     */
    @Override
    public boolean addState(IStateView stater) {
        if (stater != null) {
            stater.setStateActionListener(listener);
            //如果存在替换流程，需要将之前的StateView移除
            if (!TextUtils.isEmpty(stater.getState())) {
                removeState(stater.getState());
            }
            return stateRepository.addState(stater);
        }

        return false;
    }


    /**
     * 移除对应的状态加载器
     */
    @Override
    public boolean removeState(String state) {
        if (stateRepository == null) {
            return false;
        }
        View stateView = getStateView(state);
        //移除对应状态的同时，也需要移除对应的View
        if (stateView != null) {
            overallView.removeView(stateView);
        }
        return stateRepository.removeState(state);
    }

    @Override
    public View getStateView(String state) {

        IStateView stateChanger = stateRepository.get(state);
        if (null != stateChanger) {
            return stateChanger.getView();
        }

        return null;
    }

    public void removeAllState() {
        if (stateRepository != null) {
            stateRepository.clear();
        }
        overallView.removeAllViews();
    }

    public IStateView getCurrentState() {
        return currentState;
    }

    public StateViewRepository getStateRepository() {
        return stateRepository;
    }

    public void setStateRepository(StateViewRepository stateRepository) {
        if (stateRepository == null) {
            return;
        }
        stateRepository.addState(this.stateRepository.get(CoreStateView.STATE));
        this.stateRepository = stateRepository;
    }
    @Override
    public void onDestoryView() {
        overallView = null;
        currentState = null;
        if (stateRepository != null) {
            stateRepository.clear();
        }
    }

}
