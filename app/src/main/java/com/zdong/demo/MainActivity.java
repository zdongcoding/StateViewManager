package com.zdong.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zdg.stateviewmanager.StateManagerView;
import com.zdg.stateviewmanager.creator.StateViewRepository;
import com.zdg.stateviewmanager.mananger.StateActionListener;
import com.zdg.stateviewmanager.mananger.StateViewChanger;
import com.zdg.stateviewmanager.state.CoreStateView;

public class MainActivity extends AppCompatActivity {

    private StateViewChanger mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StateViewRepository.Instance.registerState(LoadingStateView.STATE,LoadingStateView.class);
        StateViewRepository.Instance.registerState(ExceptionStateView.STATE,ExceptionStateView.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mObserver = new StateManagerView.Builder(this).wrapper(findViewById(R.id.tv_hello)).builder();

        mObserver.setStateActionListener(new StateActionListener() {
            @Override
            public void onActionListener(String state, View view) {
                if (state == ExceptionStateView.STATE) {
                    if (view.getId()==R.id.btn_report) {
                        mObserver.showState(CoreStateView.STATE);
                        changeStateView(view);
                    }
                }
                Log.e("zoudong", "onActionListener====" + "state = [" + state + "], view = [" + view + "]");
            }
        });
    }

    public void changeStateView(View view) {
        mObserver.showState(LoadingStateView.STATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ExceptionData exceptionData=new ExceptionData("这是一条异常数据","点击重试");
                mObserver.showState(exceptionData);
            }
        },5000);
    }
}
