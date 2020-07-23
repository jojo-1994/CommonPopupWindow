# [PopupWindow管理库](https://github.com/jjjSilence/CommonPopupWindow)

最近更新：2020-07-23，重构项目，简化CommonPopupWindow的使用方式。

即将解决：setOutsideTouchable无效问题。最终效果：PopupWindow显示时，点击外部视图不可取消PopupWindow的显示。

简介：
- 链式调用的方式，满足基础的开发需求；
- 双弹框场景，可自定义window alpha；

使用步骤：
#### 1.在project的build.gradle文件中添加 
```
maven { url 'https://jitpack.io' }
```
     
#### 2.在项目中引用
```
implementation 'com.github.jjjSilence:CommonPopupWindow:1.0.5'
```

#### 3.开始创建一个popupwindow
```
new CommonPopupWindow(MainActivity.this, R.layout.popupwindow_view)
                .setLayoutWrapContent()
                .setText(R.id.text, "hello jjj")
                .setWindowAlphaOnShow(0.6f)
                .setWindowAlphaOnDismiss(1.0f)
                .initPopupWindow(new OnInitCallback() {
                    @Override
                    public void onInit(@NonNull View view, @NonNull CommonPopupWindow popupWindow) {
                        //  View view = popupWindow.getView(R.id.xx);
                        // ...
                    }
                })
                .setOnClickListener(R.id.text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "what？", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(View view, CommonPopupWindow popupWindow) {

                    }
                })
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(View view, CommonPopupWindow popupWindow) {

                    }
                })
                .showBashOfAnchor(textView, new LayoutGravity(gravity), 100, 0)
                .showPopupWindow();

```

demo效果图如下：
![enter image description here](https://github.com/jjjSilence/SmartPopupWindow/blob/master/demo.gif)

感谢您的阅读，如有问题，欢迎[在此](https://github.com/jjjSilence/SmartPopupWindow/issues)告知~
