# SmartPopupWindow

如果你的项目需要显示很多popupwindow，那么smartpopupwindow绝对能帮你大忙。
像写activity一样创建popuwindow.
来看看具体的如何使用吧：

#### 1.在project的build.gradle文件中添加 
```
maven { url 'https://jitpack.io' }
```
     
#### 2.在项目中引用
```
implementation 'com.github.jjjSilence:SmartPopupWindow:1.0.1'
```

#### 3.开始创建一个popupwindow
```
new CommonPopupWindow(MainActivity.this)
                .setContentView(R.layout.popupwindow_view)
                .setLayoutWrapContent()
                .createPopupWindow()
                .initPopupWindow(new CommonPopupWindow.InitPoputWindowCallback() {
                    @Override
                    public void initPopupWindow(@NonNull View view, @NonNull CommonPopupWindow popupWindow) {
						// View view = popupWindow.getView(R.id.xx);
                        // ...
                    }
                })
                .setPopupWindowCallback(new CommonPopupWindow.PopupWindowCallback() {
                    @Override
                    public void onShow(View view, CommonPopupWindow popupWindow) {

                    }

                    @Override
                    public void onDismiss(View view, CommonPopupWindow popupWindow) {

                    }
                })
                .showBashOfAnchor(textView, new CommonPopupWindow.LayoutGravity(gravity), 0, 0)
                .showPopupWindow();

```

demo效果图如下：
![enter image description here](https://github.com/jjjSilence/SmartPopupWindow/blob/master/demo.gif)

感谢您的阅读，如有问题，欢迎[在此](https://github.com/jjjSilence/SmartPopupWindow/issues)告知~
