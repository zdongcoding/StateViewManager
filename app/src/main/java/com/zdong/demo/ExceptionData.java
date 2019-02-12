package com.zdong.demo;

import com.zdg.stateviewmanager.state.StateProperty;

public class ExceptionData implements StateProperty {

    String title;
    String msg;

    public ExceptionData(String title, String msg) {
        this.title = title;
        this.msg = msg;
    }

    @Override
    public String getState() {
        return ExceptionStateView.STATE;
    }
}
