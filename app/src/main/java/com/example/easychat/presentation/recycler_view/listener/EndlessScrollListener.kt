package com.example.easychat.presentation.recycler_view.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onLoadMoreListener: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemCount = layoutManager.findLastVisibleItemPosition().plus(1)
            if (lastVisibleItemCount == totalItemCount) {
                onLoadMoreListener()
            }
        }
    }
}