package com.jjj.popupwindow;

import android.view.View;

/**
 * xx
 *
 * @author jiangjiaojiao
 * @since 2020-07-23
 */
public interface OnDismissListener {
    /**
     * PopupWindow dismiss callback method
     *
     * @param view
     * @param popupWindow
     */
    void onDismiss(View view, CommonPopupWindow popupWindow);
}
