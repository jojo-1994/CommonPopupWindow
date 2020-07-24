package com.jjj.popupwindow;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * xx
 *
 * @author jiangjiaojiao
 * @since 2020-07-23
 */
public interface OnInitCallback {
    /**
     * PopupWindow init callback method
     *
     * @param view
     * @param commonPopupWindow
     */
    void onInit(@NonNull View view, @NonNull CommonPopupWindow commonPopupWindow);
}