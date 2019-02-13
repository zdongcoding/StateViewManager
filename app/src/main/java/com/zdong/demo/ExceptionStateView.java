package com.zdong.demo;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zdg.stateviewmanager.state.BaseStateView;

public class ExceptionStateView extends BaseStateView<ExceptionData> {

    public static final String STATE = "exception_state";
    private TextView textView;
    private Button btn_report;

    @Override
    protected int getLayoutId() {
        return R.layout.base_exception_layout;
    }

    @Override
    protected void onViewCreated(View stateView) {
        textView = stateView.findViewById(R.id.tv_exception);
        btn_report = stateView.findViewById(R.id.btn_report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener(v);
            }
        });
    }

    @Override
    public void setViewProperty(ExceptionData stateProperty) {
        super.setViewProperty(stateProperty);
        textView.setText(stateProperty.title);
        btn_report.setText(stateProperty.msg);
    }

    @Override
    public ShowState getShowState() {
        return ShowState.ONLY;
    }

    @Override
    public String getState() {
        return STATE;
    }
}
