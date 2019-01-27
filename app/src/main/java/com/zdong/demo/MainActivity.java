package com.zdong.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zdg.stateviewmanager.StateManagerView;
import com.zdg.stateviewmanager.creator.StateViewRepository;
import com.zdg.stateviewmanager.mananger.StateViewChanger;

public class MainActivity extends AppCompatActivity {

    private StateViewChanger mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StateViewRepository.registerState(LoadingStateView.STATE,LoadingStateView.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mObserver = new StateManagerView.Builder(this).wrapper(findViewById(R.id.tv_hello)).builder();
    }

    public void changeStateView(View view) {
        mObserver.showState(LoadingStateView.STATE);
    }
}
