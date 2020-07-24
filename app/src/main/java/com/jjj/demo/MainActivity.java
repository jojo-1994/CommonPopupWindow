package com.jjj.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jjj.popupwindow.CommonPopupWindow;
import com.jjj.popupwindow.LayoutGravity;
import com.jjj.popupwindow.OnDismissListener;
import com.jjj.popupwindow.OnInitCallback;
import com.jjj.popupwindow.OnShowListener;

import static com.jjj.popupwindow.LayoutGravity.ALIGN_LEFT;
import static com.jjj.popupwindow.LayoutGravity.ALIGN_RIGHT;
import static com.jjj.popupwindow.LayoutGravity.CENTER_HORIZONTAL;
import static com.jjj.popupwindow.LayoutGravity.CENTER_VERTICAL;
import static com.jjj.popupwindow.LayoutGravity.TO_ABOVE;
import static com.jjj.popupwindow.LayoutGravity.TO_BOTTOM;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.left_top_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.TOP | Gravity.START);
            }
        });
        findViewById(R.id.top_center_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.TOP);
            }
        });
        findViewById(R.id.right_top_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.TOP | Gravity.END);
            }
        });
        findViewById(R.id.left_bottom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.BOTTOM | Gravity.START);
            }
        });
        findViewById(R.id.bottom_center_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.BOTTOM);
            }
        });
        findViewById(R.id.right_bottom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.BOTTOM | Gravity.END);
            }
        });
        findViewById(R.id.center_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowAtLocation(Gravity.CENTER);
            }
        });
        findViewById(R.id.anchor_left_top_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(ALIGN_LEFT | TO_ABOVE);
            }
        });
        findViewById(R.id.anchor_top_center_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(CENTER_HORIZONTAL | TO_ABOVE);
            }
        });
        findViewById(R.id.anchor_right_top_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(ALIGN_RIGHT | TO_ABOVE);
            }
        });
        findViewById(R.id.anchor_left_bottom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(ALIGN_LEFT | TO_BOTTOM);
            }
        });
        findViewById(R.id.anchor_bottom_center_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(CENTER_HORIZONTAL | TO_BOTTOM);
            }
        });
        findViewById(R.id.anchor_right_bottom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(ALIGN_RIGHT | TO_BOTTOM);
            }
        });
        findViewById(R.id.anchor_center_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBashOfAnchor(CENTER_HORIZONTAL | CENTER_VERTICAL);
            }
        });
        findViewById(R.id.outside_touch_not_dismiss_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .create()
                        .showAtLocation(Gravity.CENTER);
            }
        });
    }

    private void showPopupWindowAtLocation(int gravity) {
        new CommonPopupWindow.PopupWindowBuilder(this)
                .setContent(R.layout.popupwindow_view)
                .setLayoutWrapContent()
                .create()
                .showAtLocation(gravity);
    }

    private void showPopupWindowBashOfAnchor(int gravity) {
        new CommonPopupWindow.PopupWindowBuilder(this)
                .setContent(R.layout.popupwindow_view)
                .setLayoutWrapContent()
                .setWindowAlphaOnShow(0.6f)
                .setWindowAlphaOnDismiss(1.0f)
                .setOnInitCallback(new OnInitCallback() {
                    @Override
                    public void onInit(@NonNull View view, @NonNull CommonPopupWindow commonPopupWindow) {

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
                .showBashOfAnchor(findViewById(R.id.text_view), new LayoutGravity(gravity), 0, 0);
    }
}
