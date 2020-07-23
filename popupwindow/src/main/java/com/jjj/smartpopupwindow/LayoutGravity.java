package com.jjj.smartpopupwindow;

import android.view.View;
import android.widget.PopupWindow;

/**
 * xx
 *
 * @author jiangjiaojiao
 * @since 2020-07-23
 */
public class LayoutGravity {
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

    private boolean isParamFit(int param) {
        return (layoutGravity & param) > 0;
    }

    private int getHorizontalParam() {
        for (int i = 0x1; i <= 0x100; i = i << 2) {
            if (isParamFit(i)) {
                return i;
            }
        }
        return ALIGN_LEFT;
    }

    private int getVerticalParam() {
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

        int offsetx = 0;
        int offsety = 0;
        switch (getHorizontalParam()) {
            case ALIGN_LEFT:
                offsetx = 0;
                break;
            case ALIGN_RIGHT:
                offsetx = anchorWidth - popupWindowWidth;
                break;
            case TO_LEFT:
                offsetx = -popupWindowWidth;
                break;
            case TO_RIGHT:
                offsetx = anchorWidth;
                break;
            case CENTER_HORIZONTAL:
                offsetx = (anchorWidth - popupWindowWidth) / 2;
                break;
            default:
                break;
        }
        switch (getVerticalParam()) {
            case ALIGN_ABOVE:
                offsety = -anchorHeight;
                break;
            case ALIGN_BOTTOM:
                offsety = -popupWindowHeight;
                break;
            case TO_ABOVE:
                offsety = -anchorHeight - popupWindowHeight;
                break;
            case TO_BOTTOM:
                offsety = 0;
                break;
            case CENTER_VERTICAL:
                offsety = (-popupWindowHeight - anchorHeight) / 2;
                break;
            default:
                break;
        }
        return new int[]{offsetx, offsety};
    }
}

