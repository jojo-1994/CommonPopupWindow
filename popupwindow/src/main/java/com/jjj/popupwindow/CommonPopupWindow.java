package com.jjj.popupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author jjj
 * @date 2018/6/11
 * Custom CommonPopupWindow
 */
public class CommonPopupWindow implements PopupWindow.OnDismissListener {
    private static final float SHOW_WINDOW_ALPHA = 0.5f;
    private static final float DISMISS_WINDOW_ALPHA = 1.0f;
    private static final int INVALID_VALUE = -1;

    @Nullable
    private PopupWindow mPopupWindow;
    @NonNull
    private PopupWindowBuilder mBuild;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LOCATION_METHOD {
        int SHOW_AT_PARENT = -1;
        int SHOW_BASH_ANCHOR = 1;
    }

    private CommonPopupWindow(@NonNull CommonPopupWindow.PopupWindowBuilder builder) {
        mBuild = builder;
        createPopupWindow();
    }

    private PopupWindow createPopupWindow() {
        if (mBuild.contentView == null) {
            throw new RuntimeException("You do not set content view");
        }
        mPopupWindow = new PopupWindow(mBuild.contentView, mBuild.width, mBuild.height);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(mBuild.outsideTouchDismiss);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.update();
        mPopupWindow.setOnDismissListener(this);

        if (mBuild.softInputMode != INVALID_VALUE) {
            mPopupWindow.setSoftInputMode(mBuild.softInputMode);
        }
        if (mBuild.animationStyle != INVALID_VALUE) {
            mPopupWindow.setAnimationStyle(mBuild.animationStyle);
        }
        if (mBuild.initCallback != null) {
            mBuild.initCallback.onInit(mBuild.contentView, this);
        }
        return mPopupWindow;
    }

    public CommonPopupWindow showAtLocation(int gravity) {
        return showAtLocation(gravity, 0, 0);
    }

    public CommonPopupWindow showAtLocation(int gravity, int offsetx, int offsety) {
        show(mBuild.activity.findViewById(android.R.id.content), LOCATION_METHOD.SHOW_AT_PARENT,
                new LayoutGravity(gravity), offsetx, offsety);
        return this;
    }

    public CommonPopupWindow showBashOfAnchor(View anchorView, @NonNull LayoutGravity layoutGravity) {
        return showBashOfAnchor(anchorView, layoutGravity, 0, 0);
    }

    public CommonPopupWindow showBashOfAnchor(View anchorView, @NonNull LayoutGravity layoutGravity, int offsetx, int offsety) {
        show(anchorView, LOCATION_METHOD.SHOW_BASH_ANCHOR, layoutGravity, offsetx, offsety);
        return this;
    }

    private void show(View anchorView, @LOCATION_METHOD int locationMethod, LayoutGravity layoutGravity, int offsetx, int offsety) {
        if (mPopupWindow == null || mPopupWindow.isShowing()) {
            return;
        }

        setWindowAlpha(mBuild.windowAlphaOnShow);

        switch (locationMethod) {
            default:
            case LOCATION_METHOD.SHOW_AT_PARENT:
                mPopupWindow.showAtLocation(anchorView, layoutGravity.getLayoutGravity(), offsetx, offsety);
                break;
            case LOCATION_METHOD.SHOW_BASH_ANCHOR:
                int[] offset = layoutGravity.getOffset(anchorView, mPopupWindow);
                mPopupWindow.showAsDropDown(anchorView, offset[0] + offsetx, offset[1] + offsety);
                break;
        }

        if (mBuild.showListener != null) {
            mBuild.showListener.onShow(mPopupWindow.getContentView(), this);
        }
    }

    private void setWindowAlpha(float bgAlpha) {
        Window window = mBuild.activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha;
        if (lp.alpha == DISMISS_WINDOW_ALPHA) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        window.setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        if (mBuild.dismissListener != null) {
            assert mPopupWindow != null;
            mBuild.dismissListener.onDismiss(mPopupWindow.getContentView(), CommonPopupWindow.this);
        }
        setWindowAlpha(mBuild.windowAlphaOnDismiss);
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Nullable
    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }


    public static class PopupWindowBuilder {
        @NonNull
        private Activity activity;
        private View contentView;
        private int width;
        private int height;
        private float windowAlphaOnShow = SHOW_WINDOW_ALPHA;
        private float windowAlphaOnDismiss = DISMISS_WINDOW_ALPHA;

        private boolean outsideTouchDismiss;
        private int softInputMode = INVALID_VALUE;
        private int animationStyle = INVALID_VALUE;
        @Nullable
        private OnInitCallback initCallback;
        @Nullable
        private OnShowListener showListener;
        @Nullable
        private OnDismissListener dismissListener;

        public PopupWindowBuilder(@NonNull Activity activity) {
            this.activity = activity;
            setLayoutWrapContent();
        }

        public PopupWindowBuilder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public PopupWindowBuilder setLayoutWrapContent() {
            this.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            this.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            return this;
        }

        public PopupWindowBuilder setLayoutMatchParent() {
            this.width = ViewGroup.LayoutParams.MATCH_PARENT;
            this.height = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public PopupWindowBuilder setContent(int layoutResource) {
            this.contentView = LayoutInflater.from(activity).inflate(layoutResource, null, false);
            return this;
        }

        public PopupWindowBuilder setContent(@NonNull View view) {
            this.contentView = view;
            return this;
        }

        public PopupWindowBuilder setWindowAlphaOnShow(float backgroundAlpha) {
            this.windowAlphaOnShow = backgroundAlpha;
            return this;
        }

        public PopupWindowBuilder setWindowAlphaOnDismiss(float backgroundAlpha) {
            this.windowAlphaOnDismiss = backgroundAlpha;
            return this;
        }

        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchDismiss) {
            this.outsideTouchDismiss = outsideTouchDismiss;
            return this;
        }

        public PopupWindowBuilder setSoftInputMode(int softInputMode) {
            this.softInputMode = softInputMode;
            return this;
        }

        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public PopupWindowBuilder setOnInitCallback(@Nullable OnInitCallback initCallback) {
            this.initCallback = initCallback;
            return this;
        }

        public PopupWindowBuilder setOnShowListener(OnShowListener showListener) {
            this.showListener = showListener;
            return this;
        }

        public PopupWindowBuilder setOnDismissListener(OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public CommonPopupWindow create() {
            return new CommonPopupWindow(this);
        }
    }
}