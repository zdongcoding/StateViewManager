package com.zdong.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.zdg.basestateui.StateActivity;
import com.zdg.stateviewmanager.mananger.StateActionListener;
import com.zdg.stateviewmanager.state.CoreStateView;
import com.zdg.stateviewmanager.state.StateProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DemoStateUIActivity extends StateActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demostate_layout);

        setStateActionListener(new StateActionListener() {
            @Override
            public void onActionListener(@NotNull String state, @NotNull View view, @Nullable StateProperty stateProperty) {
                if (state == ExceptionStateView.STATE) {
                    if (view.getId()==R.id.btn_report) {
                        showState(CoreStateView.STATE);
                    }
                }
            }
        });
    }

    public void changeStateView(View view) {
         showState(LoadingStateView.STATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showState(new ExceptionData("这是一条异常数据","点击重试"));
            }
        },3000);
    }

}
