package com.zdong.stateviewmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.zdong.stateviewmanager.creator.StateLoader;
import com.zdong.stateviewmanager.creator.StateViewRepository;
import com.zdong.stateviewmanager.mananger.StateActionListener;
import com.zdong.stateviewmanager.mananger.StateManager;
import com.zdong.stateviewmanager.mananger.StateViewChanger;
import com.zdong.stateviewmanager.state.IStateView;
import com.zdong.stateviewmanager.state.StateProperty;

/**
 *
 * @author zoudong
 */
public class StateManagerView extends FrameLayout implements StateViewChanger, StateLoader<View> {

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
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            try {
                throw new IllegalStateException("StateManagerView can have only one direct child");

            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else if (getChildCount() == 1) {
            loadCoreView(getChildAt(0));
        }
    }
    private void loadCoreView(View child) {
        if (child != null) {
            mStateManager.setContentView(child);
        }
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
            mStateManagerView.mStateManager.setContentView(view);
            return this;
        }

        public StateViewChanger builder() {
            return mStateManagerView;
        }
    }

}
