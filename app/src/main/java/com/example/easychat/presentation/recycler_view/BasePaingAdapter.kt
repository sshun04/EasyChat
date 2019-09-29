package com.example.easychat.presentation.recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.example.easychat.domain.model.Message

abstract class BasePaingAdapter<VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val messages: MutableList<Message> = mutableListOf()
    private val insertPosition = 0

    override fun getItemCount(): Int = messages.count()

    fun insertNestPage(nextPage: List<Message>) {
        messages.addAll(nextPage)
        notifyDataSetChanged()
    }

    fun insertItem(message: Message) {
        messages.add(insertPosition, message)
        notifyItemInserted(insertPosition)
    }
}