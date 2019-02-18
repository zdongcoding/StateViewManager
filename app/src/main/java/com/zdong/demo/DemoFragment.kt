package com.zdong.demo

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.zdg.basestateui.StateFragment
import com.zdg.stateviewmanager.StateManagerView
import com.zdg.stateviewmanager.mananger.StateActionListener
import com.zdg.stateviewmanager.mananger.StateViewChanger
import com.zdg.stateviewmanager.state.CoreStateView
import com.zdg.stateviewmanager.state.IStateView
import com.zdg.stateviewmanager.state.StateProperty
import kotlinx.android.synthetic.main.demo_fragment_layout.*

class DemoFragment : StateFragment() {

    private var stateViewChanger: StateViewChanger? = null
    object Instance{
        fun newInstance(index:Int):DemoFragment{
            val demoFragment = DemoFragment()
           var bundle= Bundle()
            bundle.putInt("index", index)
            demoFragment.arguments=bundle;
            return demoFragment
        }
    }

    override val isEnableStateView: Boolean
        get() = true
    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.demo_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("zoudong", "onViewCreated    ==> " + "view = [${view}], ${arguments?.get("index")}");
        super.onViewCreated(view, savedInstanceState)
        stateViewChanger = StateManagerView.Builder(this.context!!).wrapper(container2).builder()

        tv_index.text="这是fragment  extends StateFragment ${arguments?.get("index")}"
        btn_fragment.setOnClickListener {
            showState(LoadingStateView.STATE, IStateView.ShowState.ONLY)
            Toast.makeText(this@DemoFragment.context, "5s 出现异常数据  IStateView.ShowState.ONLY", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                showState(ExceptionData("这是一条异常数据", "点击重试"))
            }, 5000)
        }

        btn_fragment2.setOnClickListener {
            stateViewChanger?.showState(LoadingStateView.STATE)
            Toast.makeText(this@DemoFragment.context, "5s 出现异常数据", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                stateViewChanger?.showState(ExceptionData("这是一条局部异常数据 ", "点击重试"))
            }, 5000)
        }

        setStateActionListener(object : StateActionListener {
            override fun onActionListener(state: String, view: View, stateProperty: StateProperty?) {
                if (state === ExceptionStateView.STATE) {
                    showState(CoreStateView.STATE)
                }
                Toast.makeText(this@DemoFragment.context, "$state 全局", Toast.LENGTH_SHORT).show()

            }

        })
        stateViewChanger?.setStateActionListener(object : StateActionListener {
            override fun onActionListener(state: String, view: View, stateProperty: StateProperty?) {
                if (state === ExceptionStateView.STATE) {
                    stateViewChanger?.showState(CoreStateView.STATE)
                }
                Toast.makeText(this@DemoFragment.context, "fragment $state 局部", Toast.LENGTH_SHORT).show()

            }

        })

    }


    override fun onDestroy() {
        stateViewChanger?.onDestroyView()
        super.onDestroy()
    }
}