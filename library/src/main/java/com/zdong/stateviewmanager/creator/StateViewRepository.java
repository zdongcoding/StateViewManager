package com.zdong.stateviewmanager.creator;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;


import com.zdong.stateviewmanager.state.IStateView;

import java.util.HashMap;

/**
 * 状态仓库
 * @author zoudong
 */
public class StateViewRepository implements StateLoader<View> {
    /**
     * 注册 状态
     * @param state
     * @param clazz
     */
    public static void registerState(String state, Class clazz) {
        stateClazzMap.put(state, clazz);
    }

    /**
     * 注销 状态
     * @param state
     */
    public static void unregisterState(String state) {
        stateClazzMap.remove(state);
    }

    /**
     * 用于映射State和具体State对象
     */
    protected HashMap<String, IStateView> stateMap = new HashMap<>(5);

    /**
     * 用于映射 State 和 Class对象
     */
    protected static HashMap<String, Class> stateClazzMap = new HashMap<>(5);


    protected Context mContext;

    public StateViewRepository(Context context) {
        mContext = context;
    }

    @Override
    public boolean addState(IStateView iStateView) {
        if (iStateView != null && !TextUtils.isEmpty(iStateView.getState())) {
            stateMap.put(iStateView.getState(), iStateView);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeState(String state) {
        if (!TextUtils.isEmpty(state)) {
            IStateView iStateView = get(state);
            if (iStateView!=null) {
                iStateView.onStateDestory();
            }
            stateMap.remove(state);
            return true;
        }
        return false;
    }


     public  IStateView get(String state) {

        IStateView istate = stateMap.get(state);
        if (istate != null) {
            return istate;
        }

        Class clazz = stateClazzMap.get(state);
        if (clazz != null) {
            try {
                istate = (IStateView) clazz.newInstance();
                addState(istate);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return istate;
    }

    @Override
    public View getStateView(String state) {
        IStateView stateChanger = get(state);
        if (stateChanger != null) {
            return stateChanger.getView();
        }
        return null;
    }


    public HashMap<String, IStateView> getStateMap() {
        return stateMap;
    }


    public void clear() {
        stateMap.clear();
    }

}
