package com.example.administrator.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.Utils.ScreenUtils;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class SlidingMenu extends HorizontalScrollView {

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * dp
     */
    private int mMenuRightPadding = 50;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    private int mHalfMenuwidth;

    private boolean once;
    private  boolean isOPen;

    public boolean getisOPen() {
        return isOPen;
    }

    public SlidingMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mScreenWidth = ScreenUtils.getScreenWidth(context);

    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mScreenWidth = ScreenUtils.getScreenWidth(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch(attr)
            {
                case R.styleable.SlidingMenu_rightPadding:
                    //默认50
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50f,
                                    getResources().getDisplayMetrics()
                            ));//默认为10dp
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显示的设置一个宽度
         */
        if(!once)
        {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
            ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
            // dp to px
            mMenuRightPadding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content
                                .getResources().getDisplayMetrics()
            );

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuwidth = mMenuWidth/2;
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed)
        {
            //将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
        {
            // Up时，进行判断，如果显示区域大于菜单一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX > mHalfMenuwidth)
                {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOPen = false;
                }

                else{
                    this.smoothScrollTo(0, 0);
                    isOPen = true;
                }

                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        if(isOPen)
            return;
        this.smoothScrollTo(0, 0);
        isOPen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        if(isOPen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOPen = false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle()
    {
        if(isOPen)
        {
            closeMenu();
        }else
        {
            openMenu();
        }

    }


}
