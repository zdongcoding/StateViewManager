package com.zdong.stateviewmanager.mananger;

import android.view.View;

import com.zdong.stateviewmanager.creator.StateObservable;

public interface IStateViewManager extends  StateViewObserver, StateObservable<View> {
}
