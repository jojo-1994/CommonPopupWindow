package com.jjj.smartpopupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author jjj
 * @date 2018/6/11
 * Custom CommonPopupWindow
 */
public class CommonPopupWindow {
    private final float SHOW_WINDOW_ALPHA = 0.5f;
    private final float DISMISS_WINDOW_ALPHA = 1.0f;

    @NonNull
    private Activity mActivity;

    @NonNull
    private View mContentView;
    @Nullable
    private PopupWindow mPopupWindow;
    private boolean isCreated;
    private int mPopupWindowWidth;
    private int mPopupWindowHeight;

    private int mShowLocation;
    @NonNull
    private View mAnchorView;
    @Nullable
    private LayoutGravity mLayoutGravity;
    private int mGravity;
    private float mWindowAlphaOnShow;
    private float mWindowAlphaOnDismiss;
    private int mOffsetx;
    private int mOffsety;

    @Nullable
    private OnShowListener mOnShowListener;
    @Nullable
    private OnDismissListener mOnDismissListener;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SHOW_LOCATION {
        int SHOW_AT_PARENT = -1;
        int SHOW_BASH_ANCHOR = 1;
    }

    public CommonPopupWindow(@NonNull Activity activity, int layoutResource) {
        this(activity, layoutResource, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public CommonPopupWindow(@NonNull Activity activity, int layoutResource, int width, int height) {
        mActivity = activity;
        mContentView = LayoutInflater.from(mActivity).inflate(layoutResource, null, false);
        setLayoutSize(width, height);
        initData();
    }

    private void initData() {
        mShowLocation = SHOW_LOCATION.SHOW_AT_PARENT;
        mAnchorView = mActivity.findViewById(android.R.id.content);
        mGravity = Gravity.CENTER;
        mWindowAlphaOnShow = SHOW_WINDOW_ALPHA;
        mWindowAlphaOnDismiss = DISMISS_WINDOW_ALPHA;
    }

    public CommonPopupWindow setLayoutSize(int width, int height) {
        mPopupWindowWidth = width;
        mPopupWindowHeight = height;
        return this;
    }

    public CommonPopupWindow setLayoutWrapContent() {
        mPopupWindowWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        mPopupWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        return this;
    }

    public CommonPopupWindow setLayoutMatchParent() {
        mPopupWindowWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        mPopupWindowHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        return this;
    }

    public CommonPopupWindow setOutsideTouchable(boolean cancelable) {
        checkCreatePopupWindow();
        assert mPopupWindow != null;
        mPopupWindow.setOutsideTouchable(false);
        return this;
    }

    public CommonPopupWindow setSoftInputMode(int mode) {
        checkCreatePopupWindow();
        assert mPopupWindow != null;
        mPopupWindow.setSoftInputMode(mode);
        return this;
    }

    public CommonPopupWindow setWindowAlphaOnShow(float backgroundAlpha) {
        this.mWindowAlphaOnShow = backgroundAlpha;
        return this;
    }

    public CommonPopupWindow setWindowAlphaOnDismiss(float backgroundAlpha) {
        mWindowAlphaOnDismiss = backgroundAlpha;
        return this;
    }

    public CommonPopupWindow setAnimationStyle(int style) {
        checkCreatePopupWindow();
        assert mPopupWindow != null;
        mPopupWindow.setAnimationStyle(style);
        return this;
    }

    public CommonPopupWindow initPopupWindow(@NonNull OnInitCallback callback) {
        checkCreatePopupWindow();
        callback.onInit(mContentView, this);
        return this;
    }

    public CommonPopupWindow setOnShowListener(OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;
        return this;
    }

    public CommonPopupWindow setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    public CommonPopupWindow showAtParent(@NonNull View anchorView, int gravity) {
        return showAtLocation(anchorView, gravity, 0, 0);
    }

    public CommonPopupWindow showAtLocation(@NonNull View anchorView, int gravity, int offsetx, int offSety) {
        this.mAnchorView = anchorView;
        this.mGravity = gravity;
        this.mOffsetx = offsetx;
        this.mOffsety = offSety;
        this.mShowLocation = SHOW_LOCATION.SHOW_AT_PARENT;
        return this;
    }

    public CommonPopupWindow showBashOfAnchor(View anchorView, @NonNull LayoutGravity layoutGravity) {
        return showBashOfAnchor(anchorView, layoutGravity, 0, 0);
    }

    public CommonPopupWindow showBashOfAnchor(View anchorView, @NonNull LayoutGravity layoutGravity, int offsetx, int offSety) {
        this.mAnchorView = anchorView;
        this.mLayoutGravity = layoutGravity;
        this.mOffsetx = offsetx;
        this.mOffsety = offSety;
        mShowLocation = SHOW_LOCATION.SHOW_BASH_ANCHOR;
        return this;
    }

    public void showPopupWindow() {
        checkCreatePopupWindow();
        assert mPopupWindow != null;
        if (mPopupWindow.isShowing()) {
            return;
        }

        setWindowAlpha(mWindowAlphaOnShow);
        setOnDismissListener();

        switch (mShowLocation) {
            default:
            case SHOW_LOCATION.SHOW_AT_PARENT:
                mPopupWindow.showAtLocation(mAnchorView, mGravity, mOffsetx, mOffsety);
                break;
            case SHOW_LOCATION.SHOW_BASH_ANCHOR:
                assert mLayoutGravity != null;
                int[] offset = mLayoutGravity.getOffset(mAnchorView, mPopupWindow);
                mPopupWindow.showAsDropDown(mAnchorView, offset[0] + mOffsetx, offset[1] + mOffsety);
                break;
        }

        if (mOnShowListener != null) {
            mOnShowListener.onShow(mContentView, this);
        }
    }

    public void onDismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public CommonPopupWindow setText(int id, String msg) {
        TextView textView = mContentView.findViewById(id);
        textView.setText(msg);
        if (textView instanceof EditText) {
            ((EditText) textView).setSelection(msg.length());
        }
        return this;
    }

    @NonNull
    public String getText(int id) {
        View view = mContentView.findViewById(id);
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        }
        return "";
    }

    @Nullable
    public <T extends View> T getView(int id) {
        return mContentView.findViewById(id);
    }

    public CommonPopupWindow setOnClickListener(int viewId, @NonNull View.OnClickListener onClickListener) {
        return setOnClickListener(new int[]{viewId}, onClickListener);
    }

    public CommonPopupWindow setOnClickListener(int[] viewIds, @NonNull View.OnClickListener onClickListener) {
        for (int viewId : viewIds) {
            mContentView.findViewById(viewId).setOnClickListener(onClickListener);
        }
        return this;
    }

    public CommonPopupWindow setOnClickListener(int[] viewIds, @NonNull final OnClickListener onClickListener) {
        for (int viewId : viewIds) {
            mContentView.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, CommonPopupWindow.this);
                }
            });
        }
        return this;
    }

    public CommonPopupWindow setOnClickListener(int id, @NonNull final OnClickListener onClickListener) {
        mContentView.findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v, CommonPopupWindow.this);
            }
        });
        return this;
    }

    public CommonPopupWindow setCancelView(int... viewIds) {
        for (int viewId : viewIds) {
            mContentView.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDismiss();
                }
            });
        }
        return this;
    }

    private void checkCreatePopupWindow() {
        if (!isCreated) {
            createPopupWindow();
        }
    }

    private void createPopupWindow() {
        mPopupWindow = new PopupWindow(mContentView, mPopupWindowWidth, mPopupWindowHeight);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.getContentView().setFocusable(true);
        isCreated = true;
    }

    private void setWindowAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (lp.alpha == DISMISS_WINDOW_ALPHA) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mActivity.getWindow().setAttributes(lp);
    }

    private void setOnDismissListener() {
        checkCreatePopupWindow();
        assert mPopupWindow != null;
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss(mContentView, CommonPopupWindow.this);
                }
                setWindowAlpha(mWindowAlphaOnDismiss);
            }
        });
    }
}
