package com.example.easychat.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var bottomVisiblePosition = 0
    private var positionOffset = 0

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        val linearLayoutManager = layoutManager as LinearLayoutManager
        bottomVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition()
//        TODO firstVisibleItemのoffsetを取得
//        val firstVisibleItem = getChildAt(bottomVisiblePosition)
//        println( firstVisibleItem.y)
        val startView = getChildAt(0)
        positionOffset =
            if (startView == null) 0 else (startView.top - paddingTop)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (canScrollVertically(View.SCROLL_INDICATOR_BOTTOM) && oldh > h) {
            println()
            val linearLayoutManager = layoutManager as LinearLayoutManager
//            TODO keyboardが開く前と同じoffsetにする
            linearLayoutManager.scrollToPositionWithOffset(bottomVisiblePosition,0)
        } else if (computeVerticalScrollRange() - computeVerticalScrollExtent() - computeVerticalScrollOffset() < oldh - h) {
            println()
        } else {
            println()
        }
    }
}