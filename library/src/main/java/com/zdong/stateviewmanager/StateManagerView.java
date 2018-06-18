package com.zdong.stateviewmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.zdong.stateviewmanager.creator.StateObservable;
import com.zdong.stateviewmanager.creator.StateViewRepository;
import com.zdong.stateviewmanager.mananger.StateActionListener;
import com.zdong.stateviewmanager.mananger.StateManager;
import com.zdong.stateviewmanager.mananger.StateViewObserver;
import com.zdong.stateviewmanager.state.IStateView;
import com.zdong.stateviewmanager.state.StateProperty;

public class StateManagerView extends FrameLayout implements StateViewObserver, StateObservable<View> {

    StateManager mStateManager;

    public StateManagerView(Context context) {
        this(context, null);
    }

    public StateManagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateManagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StateManagerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mStateManager = StateManager.newInstance(getContext(), new StateViewRepository(getContext()), this);
        setBackgroundColor(Color.RED);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("zoudong", "onFinishInflate====" + "");
    }

    @Override
    public boolean addState(IStateView changger) {
        return mStateManager.addState(changger);
    }

    @Override
    public boolean removeState(String state) {
        return mStateManager.removeState(state);
    }

    @Override
    public View getStateView(String state) {
        return mStateManager.getStateView(state);
    }

    @Override
    public boolean showState(String state) {
        return mStateManager.showState(state);
    }

    @Override
    public boolean showState(StateProperty state) {
        return mStateManager.showState(state);
    }

    @Override
    public String getState() {
        return mStateManager.getState();
    }

    @Override
    public void setStateActionListener(StateActionListener listener) {
        mStateManager.setStateActionListener(listener);
    }

    @Override
    public void onDestoryView() {
        mStateManager.onDestoryView();
    }


    public static class Builder {
        StateManagerView mStateManagerView;

        public Builder(Context context) {
            mStateManagerView = new StateManagerView(context);
        }

        public Builder wrapper(View view) {
            mStateManagerView.wrapper(view);
            return this;
        }

        public StateViewObserver observer() {
            return mStateManagerView;
        }
    }

    private void wrapper(View view) {
        ViewParent parent = view.getParent();
        if (null == parent) {
            this.addView(view, new FrameLayout.LayoutParams(-1, -1));
        } else if (this != parent) {
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int index = viewGroup.indexOfChild(view);
                viewGroup.removeView(view);
                this.addView(view, new FrameLayout.LayoutParams(layoutParams.width, layoutParams.height));
                viewGroup.addView(this, index, layoutParams);
            }
        }
    }
}
