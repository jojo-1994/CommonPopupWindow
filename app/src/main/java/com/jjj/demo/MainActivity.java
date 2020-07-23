package com.jjj.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjj.smartpopupwindow.CommonPopupWindow;
import com.jjj.smartpopupwindow.LayoutGravity;
import com.jjj.smartpopupwindow.OnDismissListener;
import com.jjj.smartpopupwindow.OnInitCallback;
import com.jjj.smartpopupwindow.OnShowListener;

import static com.jjj.smartpopupwindow.LayoutGravity.ALIGN_LEFT;
import static com.jjj.smartpopupwindow.LayoutGravity.ALIGN_RIGHT;
import static com.jjj.smartpopupwindow.LayoutGravity.CENTER_HORIZONTAL;
import static com.jjj.smartpopupwindow.LayoutGravity.CENTER_VERTICAL;
import static com.jjj.smartpopupwindow.LayoutGravity.TO_ABOVE;
import static com.jjj.smartpopupwindow.LayoutGravity.TO_BOTTOM;


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
        new CommonPopupWindow(MainActivity.this, R.layout.popupwindow_view)
                .setLayoutSize(100, 200)
                .showAtLocation(findViewById(android.R.id.content), gravity, 0, 0)
                .showPopupWindow();
    }

    private void showPopupWindowBashOfAnchor(int gravity) {
        final TextView textView = findViewById(R.id.text_view);
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
                .showBashOfAnchor(textView, new LayoutGravity(gravity), 0, 0)
                .showPopupWindow();
    }
}
