package com.neutron.deloan.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OffsetLinearLayoutManager(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : LinearLayoutManager(context, attrs, defStyleAttr, defStyleRes) {

    private val heightMap = HashMap<Int, Int>()

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        val firstVisible = findFirstVisibleItemPosition()
        val lastVisible = findLastVisibleItemPosition()
        if (firstVisible >= 0 && lastVisible >= 0 && lastVisible >= firstVisible) {
            for (i in firstVisible until lastVisible) {
                getChildAt(i)?.let {
                    heightMap[i] = it.height
                }
            }
        }
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        if (childCount == 0) return 0
        return try {
            val firstVisiblePosition = findFirstVisibleItemPosition()
            val viewByPosition = findViewByPosition(firstVisiblePosition)
            var offset = 0
            for (i in 0 until firstVisiblePosition) {
                offset += heightMap[i] ?: 0
            }
            offset -= viewByPosition?.top ?: 0
            offset
        } catch (e: Exception) {
            0
        }
    }
}