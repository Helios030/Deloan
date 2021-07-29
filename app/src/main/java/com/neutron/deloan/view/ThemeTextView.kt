package com.neutron.deloan.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.neutron.deloan.R

class ThemeTextView : AppCompatTextView {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var textClor = R.color.text_color


    @SuppressLint("Recycle")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ThemeTextView)
        textClor = a.getResourceId(R.styleable.ThemeTextView_textColor, textClor)
        val textSize = a.getFloat(R.styleable.ThemeTextView_textSize, 14F)
        this.setTextColor(ContextCompat.getColor(getContext(), textClor))
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX,dp2px(context,textSize))

    }

    fun setThemTextColor(color:Int){
        textClor=color
        this.setTextColor(ContextCompat.getColor(getContext(), textClor))
    }


    fun dp2px(context: Context, dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            context.resources.displayMetrics
        )
    }

}