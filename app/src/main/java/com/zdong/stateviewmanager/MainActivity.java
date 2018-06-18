package com.zdong.stateviewmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zdong.stateviewmanager.creator.StateViewRepository;
import com.zdong.stateviewmanager.mananger.StateViewObserver;

public class MainActivity extends AppCompatActivity {

    private StateViewObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StateViewRepository.registerState(LoadingStateView.STATE,LoadingStateView.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mObserver = new StateManagerView.Builder(this)
                .wrapper(findViewById(R.id.tv_hello)).observer();
    }

    public void changeStateView(View view) {
        mObserver.showState(LoadingStateView.STATE);
    }
}
