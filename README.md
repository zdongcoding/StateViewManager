
## StateViewManager  
> 此项目kotlin 编写  完美适配Java项目

## 依赖

```

implementation 'com.zdg.android:stateview:0.0.2'

```
or
```

implementation 'com.zdg.android:basestateui:0.0.2'

```
###  stateview
> 只需要关注一个  StateManagerView
#### 创建状态器
```java
public class LoadingStateView extends BaseStateView {
    public static final String STATE = "loading_state";
    @Override
    protected int getLayoutId() {
        return R.layout.base_loading_layout;
    }

    @Override
    protected void onViewCreated(View stateView) {
        stateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener(v);
            }
        });
    }
     /**
     *  该状态的
     * @return 
     */
    @Override
    public String getState() {
        return STATE;
    }

    /**
    *   ShowState.STACK   叠加模式   顾名思义 
    *   ShowState.ONLY    单显模式   显示该状态 会将其他状态和原View gone
    * @return 
    */
    @Override
    public ShowState getShowState() {
        return ShowState.STACK;
    }
}

```

`[关键点]`  StateView的点击事件 处理方法：在StateViewView 点击事件只需要调用 `actionListener(v)`就能把点击事件传到上层 `StateManagerView#onActionListener`
#### 注册状态
```
StateViewStore.registerState(LoadingStateView.STATE,LoadingStateView.class);
StateViewStore.registerState(ExceptionStateView.STATE,ExceptionStateView.class);
```
#### 用法
#### 用法一 代码接入
> 一个套路 `wrapper(View)`  这个方法就是将原View 绑定到 StateManagerView中 我们需要修改这个View的状态 只需要调用

```

StateViewChanger mChanger = new StateManagerView.Builder(this).wrapper(findViewById(R.id.tv_hello)).builder();

mChanger.setStateActionListener(new StateActionListener() {
            @Override
            public void onActionListener(@NotNull String state, @NotNull View view, @Nullable StateProperty stateProperty) {
                if (state == ExceptionStateView.STATE) {
                    if (view.getId()==R.id.btn_report) {
                        mObserver.showState(CoreStateView.STATE);
                        startActivity(new Intent(MainActivity.this,SimpleActivity.class));
                    }
                }
            }
        });

mChanger.showState(LoadingStateView.STATE);

```

#### 用法二 xml接入

```xml
<com.zdg.stateviewmanager.StateManagerView
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <!--布局-->
</com.zdg.stateviewmanager.StateManagerView>
```
 操作用法和上面一样 只是接入StateView 不一样  这种侵入性强
 
### UI Activty / Fragment

#### 依赖
```
implementation 'com.zdg.android:basestateui:0.0.1'
```
#### 用法

```
public class DemoStateUIActivity extends StateActivity{
    setStateActionListener(); //  类似mChanger.setStateActionListener
    showState();              //   类似mChanger.showState
    hideState();              //   类似mChanger.hideState
}
public class DemoFragment extends StateFragment{
    setStateActionListener(); //  类似mChanger.setStateActionListener
    showState();              //   类似mChanger.showState
    hideState();              //   类似mChanger.hideState
}
```


![主页面](/img/1550465208880.jpg)

![SimpleActivity](/img/1550465242564.jpg)


![DemoStateUIActivity](/img/1550465271867.jpg)



![ShowState](/img/Video_20190218_010025_173.gif)

![SimpleActivity](/img/Video_20190218_010108_145.gif)


![DemoStateUIActivity](/img/Video_20190218_010123_190.gif)
**[具体方法看Demo]**