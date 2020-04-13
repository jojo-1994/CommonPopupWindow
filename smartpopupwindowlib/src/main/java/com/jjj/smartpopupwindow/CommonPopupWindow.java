package com.jjj.smartpopupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author jjj
 * @date 2018/6/11
 * Custom CommonPopupWindow
 */
public class CommonPopupWindow {
    private final float DEFAULT_BACKGROUND_ALPHA = 0.5f;
    private final float DISMISS_BACKGROUND_ALPHA = 1.0f;

    private Activity mActivity;
    private PopupWindow mPopupWindow;
    private View mContentView;
    private int mPopupWindowWidth;
    private int mPopupWindowHeight;
    private int mGravity;
    private float mBackgroundAlpha = DEFAULT_BACKGROUND_ALPHA;
    private boolean mResetBackgroundAlpha = true;
    private PopupWindowCallback mPopupWindowCallback;

    private int mOffsetx;
    private int mOffsety;
    private int mShowLocation;
    private View mAnchorView;
    private LayoutGravity mLayoutGravity;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SHOW_LOCATION {
        int ShowAtParent = -1;
        int showBashOfAnchor = 1;
    }

    public CommonPopupWindow(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    public CommonPopupWindow setContentView(int layoutResource) {

        mContentView = LayoutInflater.from(mActivity).inflate(layoutResource, null, false);
        return this;
    }

    public CommonPopupWindow setLayoutSize(int width, int height) {
        this.mPopupWindowWidth = width;
        this.mPopupWindowHeight = height;
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

    public CommonPopupWindow createPopupWindow() {
        mPopupWindow = new PopupWindow(mContentView, mPopupWindowWidth, mPopupWindowHeight);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.getContentView().setFocusable(true);
        return this;
    }

    public CommonPopupWindow initPopupWindow(@NonNull InitPoputWindowCallback callback) {
        callback.initPopupWindow(mContentView, this);
        return this;
    }

    public CommonPopupWindow setText(int id, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = mContentView.findViewById(id);
            textView.setText(msg);
            if (textView instanceof EditText) {
                ((EditText) textView).setSelection(msg.length());
            }
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

    public CommonPopupWindow setVisibility(int id, boolean visible) {
        mContentView.findViewById(id).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public CommonPopupWindow setSelected(int id, boolean selected) {
        mContentView.findViewById(id).setSelected(selected);
        return this;
    }

    public CommonPopupWindow setOnClickListener(int viewId, @NonNull View.OnClickListener clickInterface) {
        return setOnClickListener(new int[]{viewId}, clickInterface);
    }

    public CommonPopupWindow setOnClickListener(int[] viewIds, @NonNull View.OnClickListener clickInterface) {
        for (int viewId : viewIds) {
            mContentView.findViewById(viewId).setOnClickListener(clickInterface);
        }
        return this;
    }

    public CommonPopupWindow setOnClickReturnListener(int[] viewIds, @NonNull final OnClickListener clickInterface) {
        for (int viewId : viewIds) {
            mContentView.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickInterface.onClick(v, CommonPopupWindow.this);
                }
            });
        }
        return this;
    }

    public CommonPopupWindow setOnClickReturnListener(int id, @NonNull final OnClickListener clickInterface) {
        mContentView.findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.onClick(v, CommonPopupWindow.this);
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

    public CommonPopupWindow setOutsideTouchable(boolean cancelable) {
        mPopupWindow.setOutsideTouchable(cancelable);
        return this;
    }

    public CommonPopupWindow setResetBackgroundAlpha(boolean resetBackgroundAlpha) {
        mResetBackgroundAlpha = resetBackgroundAlpha;
        return this;
    }

    public CommonPopupWindow setSoftInputMode(int mode) {
        mPopupWindow.setSoftInputMode(mode);
        return this;
    }

    public CommonPopupWindow setBackgroundAlpha(float backgroundAlpha) {
        this.mBackgroundAlpha = backgroundAlpha;
        return this;
    }

    public CommonPopupWindow setPopupWindowCallback(@NonNull PopupWindowCallback callback) {
        mPopupWindowCallback = callback;
        return this;
    }

    public CommonPopupWindow setAnimationStyle(int style) {
        mPopupWindow.setAnimationStyle(style);
        return this;
    }

    public CommonPopupWindow showAtLocation(View anchorView, int gravity, int offsetX, int offSetY) {
        this.mAnchorView = anchorView;
        this.mGravity = gravity;
        this.mOffsetx = offsetX;
        this.mOffsety = offSetY;
        this.mShowLocation = SHOW_LOCATION.ShowAtParent;
        return this;
    }

    public CommonPopupWindow showBashOfAnchor(View anchorView, LayoutGravity layoutGravity, int offsetX, int offSetY) {
        this.mAnchorView = anchorView;
        this.mLayoutGravity = layoutGravity;
        this.mOffsetx = offsetX;
        this.mOffsety = offSetY;
        mShowLocation = SHOW_LOCATION.showBashOfAnchor;
        return this;
    }

    public void showPopupWindow() {
        if (mPopupWindow == null) {
            Toast.makeText(mActivity, "Popup window has not create", Toast.LENGTH_SHORT).show();
            return;
        }

        setWindowAlpha(mBackgroundAlpha);

        switch (mShowLocation) {
            default:
            case SHOW_LOCATION.ShowAtParent:
                mPopupWindow.showAtLocation(mAnchorView, mGravity, mOffsetx, mOffsety);
                break;
            case SHOW_LOCATION.showBashOfAnchor:
                int[] offset = mLayoutGravity.getOffset(mAnchorView, mPopupWindow);
                mPopupWindow.showAsDropDown(mAnchorView, offset[0] + mOffsetx, offset[1] + mOffsety);
                break;
        }

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPopupWindowCallback != null) {
                    mPopupWindowCallback.onDismiss(mContentView, CommonPopupWindow.this);
                }
                if (mResetBackgroundAlpha) {
                    setWindowAlpha(DISMISS_BACKGROUND_ALPHA);
                }
            }
        });

        if (mPopupWindowCallback != null) {
            mPopupWindowCallback.onShow(mContentView, this);
        }
    }

    public void onDismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void setWindowAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (lp.alpha == DISMISS_BACKGROUND_ALPHA) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mActivity.getWindow().setAttributes(lp);
    }


    public static class LayoutGravity {
        private int layoutGravity;
        public static final int ALIGN_LEFT = 0x1;
        public static final int ALIGN_ABOVE = 0x2;
        public static final int ALIGN_RIGHT = 0x4;
        public static final int ALIGN_BOTTOM = 0x8;
        public static final int TO_LEFT = 0x10;
        public static final int TO_ABOVE = 0x20;
        public static final int TO_RIGHT = 0x40;
        public static final int TO_BOTTOM = 0x80;
        public static final int CENTER_HORIZONTAL = 0x100;
        public static final int CENTER_VERTICAL = 0x200;

        public LayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        public int getLayoutGravity() {
            return layoutGravity;
        }

        public void setLayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        public void setHorizontalGravity(int gravity) {
            layoutGravity &= (ALIGN_ABOVE + ALIGN_BOTTOM + TO_ABOVE + TO_BOTTOM + CENTER_VERTICAL);
            layoutGravity |= gravity;
        }

        public void setVerticalGravity(int gravity) {
            layoutGravity &= (ALIGN_LEFT + ALIGN_RIGHT + TO_LEFT + TO_RIGHT + CENTER_HORIZONTAL);
            layoutGravity |= gravity;
        }

        public boolean isParamFit(int param) {
            return (layoutGravity & param) > 0;
        }

        public int getHorizontalParam() {
            for (int i = 0x1; i <= 0x100; i = i << 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return ALIGN_LEFT;
        }

        public int getVerticalParam() {
            for (int i = 0x2; i <= 0x200; i = i << 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return TO_BOTTOM;
        }

        public int[] getOffset(View anchor, PopupWindow popupWindow) {
            int anchorWidth = anchor.getWidth();
            int anchorHeight = anchor.getHeight();

            int popupWindowWidth = popupWindow.getWidth();
            int popupWindowHeight = popupWindow.getHeight();
            if (popupWindowWidth <= 0 || popupWindowHeight <= 0) {
                View view = popupWindow.getContentView();
                int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                view.measure(width, height);
                popupWindowWidth = view.getMeasuredWidth();
                popupWindowHeight = view.getMeasuredHeight();
            }

            int offsetX = 0;
            int offsetY = 0;
            switch (getHorizontalParam()) {
                case ALIGN_LEFT:
                    offsetX = 0;
                    break;
                case ALIGN_RIGHT:
                    offsetX = anchorWidth - popupWindowWidth;
                    break;
                case TO_LEFT:
                    offsetX = -popupWindowWidth;
                    break;
                case TO_RIGHT:
                    offsetX = anchorWidth;
                    break;
                case CENTER_HORIZONTAL:
                    offsetX = (anchorWidth - popupWindowWidth) / 2;
                    break;
                default:
                    break;
            }
            switch (getVerticalParam()) {
                case ALIGN_ABOVE:
                    offsetY = -anchorHeight;
                    break;
                case ALIGN_BOTTOM:
                    offsetY = -popupWindowHeight;
                    break;
                case TO_ABOVE:
                    offsetY = -anchorHeight - popupWindowHeight;
                    break;
                case TO_BOTTOM:
                    offsetY = 0;
                    break;
                case CENTER_VERTICAL:
                    offsetY = (-popupWindowHeight - anchorHeight) / 2;
                    break;
                default:
                    break;
            }
            return new int[]{offsetX, offsetY};
        }
    }

    public interface InitPoputWindowCallback {
        void initPopupWindow(@NonNull View view, @NonNull CommonPopupWindow popupWindow);
    }

    public interface OnClickListener {
        void onClick(View view, CommonPopupWindow popupWindow);
    }

    public interface PopupWindowCallback {
        void onShow(View view, CommonPopupWindow popupWindow);

        void onDismiss(View view, CommonPopupWindow popupWindow);
    }
}
