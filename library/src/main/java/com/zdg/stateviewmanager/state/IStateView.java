package com.zdg.stateviewmanager.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zdg.stateviewmanager.mananger.StateActionListener;


/**
 * State生命周期管理
 */
public interface IStateView<T extends StateProperty> {

    /**
     * StateView创建后，可以做一些操作
     * @param context
     * @param parent
     */
    void onStateCreate(Context context, ViewGroup parent);


    /**
     * StateView显示后，可以做一些操作
     */
    void onStateResume();

    /**
     * StateView隐藏后，可以做一些操作
     */
    void onStatePause();

    /**
     * StateView#remove
     */
    void onStateDestroy();
    /**
     * 获取当前状态
     *
     * @return
     */
    String getState();

    /**
     * 设置当前状态下的一些按钮操作回调
     *
     * @param listener
     */
    void setStateActionListener(StateActionListener listener);


    /**
     * 获取状态机的View
     *
     * @return
     */
    View getView();

    ShowState getShowState();
    /**
     * 定制View里面控件的内容
     * @param stateProperty
     */
    void setViewProperty(T stateProperty);

    public class ShowState {
        /**
         *  只显示State显示的位置
         */
        public static ShowState ONLY=new ShowState(-1);
        /**
         *
         */
        public static ShowState STACK= new ShowState(0);

        /**
         *
         * @param index ViewGroup（mOverallView#addView(index）
         * @return ShowState
         */
        public static ShowState INDEX(int index){
            return new ShowState(index);
        }
        int index;

         private ShowState(int index) {
            this.index=index;
        }

        public int getIndex() {
            return index;
        }
    }
}
