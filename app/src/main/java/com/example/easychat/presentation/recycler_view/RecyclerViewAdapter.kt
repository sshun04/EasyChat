package com.example.easychat.presentation.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easychat.R
import com.example.easychat.presentation.custom_view.MessageCellView

class RecyclerViewAdapter(context: Context) : BasePaingAdapter<RecyclerView.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            layoutInflater.inflate(R.layout.item_message_others, parent, false) as MessageCellView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        holder as ViewHolder
        holder.view.build(message)
    }

    private class ViewHolder(val view: MessageCellView) : RecyclerView.ViewHolder(view)
}