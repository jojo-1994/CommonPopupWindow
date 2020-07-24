package com.jjj.popupwindow;

import android.view.View;

/**
 * xx
 *
 * @author jiangjiaojiao
 * @since 2020-07-23
 */
public interface OnShowListener {
    /**
     * PopupWindow show callback method
     *
     * @param view
     * @param popupWindow
     */
    void onShow(View view, CommonPopupWindow popupWindow);
}
