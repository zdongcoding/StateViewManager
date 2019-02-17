package com.zdong.demo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zdg.stateviewmanager.StateManagerView;
import com.zdg.stateviewmanager.creator.StateViewStore;
import com.zdg.stateviewmanager.mananger.StateActionListener;
import com.zdg.stateviewmanager.mananger.StateViewChanger;
import com.zdg.stateviewmanager.state.CoreStateView;
import com.zdg.stateviewmanager.state.IStateView;
import com.zdg.stateviewmanager.state.StateProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private StateViewChanger mObserver;
    private TextView tv_show_state,tv_show_state_msg;
    private SwitchCompat sw;
    private IStateView.ShowState showState=IStateView.ShowState.ONLY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StateViewStore.registerState(LoadingStateView.STATE,LoadingStateView.class);
        StateViewStore.registerState(ExceptionStateView.STATE,ExceptionStateView.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show_state = findViewById(R.id.tv_show_state);
        tv_show_state_msg = findViewById(R.id.tv_show_state_msg);
        sw = findViewById(R.id.sw);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showState = isChecked ? IStateView.ShowState.STACK : IStateView.ShowState.ONLY;
                tv_show_state.setText(showState.name());
                tv_show_state_msg.setText(showState== IStateView.ShowState.ONLY?getString(R.string.show_state_only_str):getString(R.string.show_state_stack_str));
            }
        });
        mObserver = new StateManagerView.Builder(this).wrapper(findViewById(R.id.tv_hello)).builder();

        mObserver.setStateActionListener(new StateActionListener() {
            @Override
            public void onActionListener(@NotNull String state, @NotNull View view, @Nullable StateProperty stateProperty) {
                if (state == ExceptionStateView.STATE) {
                    if (view.getId()==R.id.btn_report) {
                        mObserver.showState(CoreStateView.STATE);
                        startActivity(new Intent(MainActivity.this,SimpleActivity.class));
                    }
                }
                Log.e("zoudong", "onActionListener====" + "state = [" + state + "], view = [" + view + "]");
            }
        });

        Field[] darkFlag = WindowManager.LayoutParams.class.getDeclaredFields();
        for (Field field : darkFlag) {
            Log.e("zoudong", "field=: "+field.toString());
        }
    }

    public void changeStateView(View view) {
//        mObserver.showState(LoadingStateView.STATE);
        mObserver.showState(LoadingStateView.STATE,showState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ExceptionData exceptionData=new ExceptionData("这是一条异常数据","点击重试");
                mObserver.showState(exceptionData);
            }
        },5000);
    }
}
