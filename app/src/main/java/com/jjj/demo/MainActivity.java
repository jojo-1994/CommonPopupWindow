package com.jjj.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jjj.smartpopupwindow.CommonPopupWindow;

import static com.jjj.smartpopupwindow.CommonPopupWindow.LayoutGravity.ALIGN_LEFT;
import static com.jjj.smartpopupwindow.CommonPopupWindow.LayoutGravity.ALIGN_RIGHT;
import static com.jjj.smartpopupwindow.CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL;
import static com.jjj.smartpopupwindow.CommonPopupWindow.LayoutGravity.CENTER_VERTICAL;
import static com.jjj.smartpopupwindow.CommonPopupWindow.LayoutGravity.TO_ABOVE;
import static com.jjj.smartpopupwindow.CommonPopupWindow.LayoutGravity.TO_BOTTOM;


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
    }

    private void showPopupWindowAtLocation(int gravity) {
        new CommonPopupWindow(MainActivity.this)
                .setContentView(R.layout.popupwindow_view)
                .setLayoutWrapContent()
                .createPopupWindow()
                .showAtLocation(findViewById(android.R.id.content), gravity, 0, 0)
                .showPopupWindow();
    }

    private void showPopupWindowBashOfAnchor(int gravity) {
        final TextView textView = findViewById(R.id.text_view);
        new CommonPopupWindow(MainActivity.this)
                .setContentView(R.layout.popupwindow_view)
                .setLayoutWrapContent()
                .createPopupWindow()
                .initPopupWindow(new CommonPopupWindow.InitPoputWindowCallback() {
                    @Override
                    public void initPopupWindow(@NonNull View view, @NonNull CommonPopupWindow popupWindow) {
                        //  View view = popupWindow.getView(R.id.xx);
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
    }
}
