package com.zdong.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zdg.stateviewmanager.creator.StateViewStore;

public class ViewPageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StateViewStore.registerState(LoadingStateView.STATE,LoadingStateView.class);
        StateViewStore.registerState(ExceptionStateView.STATE,ExceptionStateView.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_layout);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_fragment, new ViewPageFragment())
                .commit();

    }
}
