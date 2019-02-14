package com.zdong.demo

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.zdg.stateviewmanager.StateManagerView
import com.zdg.stateviewmanager.mananger.StateActionListener
import com.zdg.stateviewmanager.mananger.StateViewChanger
import com.zdg.stateviewmanager.state.CoreStateView
import com.zdg.stateviewmanager.state.IStateView
import kotlinx.android.synthetic.main.simple_activity_layout.*

class SimpleActivity : AppCompatActivity() {
    private var stateViewChanger: StateViewChanger? = null
    private var stateViewChanger2: StateViewChanger? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_activity_layout)
        stateViewChanger = StateManagerView.Builder(this).wrapper(container).builder()
        stateViewChanger2 = StateManagerView.Builder(this).wrapper(container2).builder()

        stateViewChanger?.setStateActionListener( object : StateActionListener {
            override fun onActionListener(state: String, view: View) {
                if (state === ExceptionStateView.STATE) {
                    stateViewChanger?.showState(CoreStateView.STATE)
                }
                Toast.makeText(this@SimpleActivity,"$state 全局",Toast.LENGTH_SHORT).show()

            }

        })
        stateViewChanger2?.setStateActionListener( object : StateActionListener {
            override fun onActionListener(state: String, view: View) {
                if (state === ExceptionStateView.STATE) {
                    stateViewChanger2?.showState(CoreStateView.STATE)
                }
                Toast.makeText(this@SimpleActivity,"$state 局部",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun changeStateView(view: View) {
        stateViewChanger?.showState(LoadingStateView.STATE,IStateView.ShowState.ONLY)
        Handler().postDelayed({
            val exceptionData = ExceptionData("这是一条异常数据", "点击重试")
            stateViewChanger?.showState(exceptionData)
        }, 5000)
    }

    fun changeStateView2(view: View) {
        stateViewChanger2?.showState(LoadingStateView.STATE)
        Handler().postDelayed({
            val exceptionData = ExceptionData("这是一条局部异常数据", "点击重试")
            stateViewChanger2?.showState(exceptionData)
        }, 5000)
    }

    override fun onDestroy() {
        Log.e("zoudong", "onDestroy    ==> " + "");
        stateViewChanger?.onDestroyView()
        super.onDestroy()
    }
}