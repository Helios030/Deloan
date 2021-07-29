package com.neutron.deloan.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @ProjectName: NewCustomORunning
 * @Package: com.oplayer.osportplus.view
 * @ClassName: NoScrollViewPager
 * @Description: 禁止滑动
 * @Author: Ben
 * @CreateDate: 2019/4/9 9:58
 */
class NoScrollViewPager : ViewPager {
    private var noScroll = true

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) { // TODO Auto-generated constructor stub
    }

    constructor(context: Context?) : super(context!!) {}

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean { /* return false;//super.onTouchEvent(arg0); */
        return if (noScroll) false else super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) false else super.onInterceptTouchEvent(arg0)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }
}