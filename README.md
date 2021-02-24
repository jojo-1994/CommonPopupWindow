# [PopupWindow管理库](https://github.com/jjjSilence/CommonPopupWindow)

最近更新：
- 2020-07-24，解决setOutsideTouchable无效问题。最终效果：PopupWindow显示时，点击外部视图不可取消PopupWindow的显示。
- 2020-07-23，重构项目，简化CommonPopupWindow的使用方式。

简介：
- 链式调用的方式，满足基础的开发需求；
- 双弹框场景，可自定义window alpha；
- 解决setOutsideTouchable无效问题；

使用步骤：
#### 1.在project的build.gradle文件中添加 
```
maven { url 'https://jitpack.io' }
```
     
#### 2.在app的build.gradle文件中添加：
```
implementation 'com.github.jjjSilence:CommonPopupWindow:1.0.7'
```

#### 3.示例代码:
```
     new CommonPopupWindow.PopupWindowBuilder(MainActivity.this)
                        .setContent(R.layout.popupwindow_view)
                        .setLayoutWrapContent()
                        .setOutsideTouchDismiss(false)
                        .setOnInitCallback(new OnInitCallback() {
                            @Override
                            public void onInit(@NonNull View view, @NonNull final CommonPopupWindow commonPopupWindow) {
                                TextView textView = view.findViewById(R.id.text);
                                textView.setText("点此消失，点击外部不消失");
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        commonPopupWindow.dismiss();
                                    }
                                });
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
                        .create()
                        .showAtLocation(Gravity.CENTER);

```

demo效果图如下：
![enter image description here](https://github.com/jjjSilence/SmartPopupWindow/blob/master/demo.gif)
