package com.example.easychat.presentation.scene.main

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easychat.R
import com.example.easychat.presentation.recycler_view.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_talk_room.view.*

class TalkRoomFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_talk_room,container,false)
        val linearLayoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,true).apply {
            stackFromEnd = true
        }

        val recyclerViewAdapter = RecyclerViewAdapter(requireContext())
        val list = (0..30).map { com.example.easychat.domain.model.Message(message = it.toString()) }
        recyclerViewAdapter.insertNestPage(list)
        view.subRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = linearLayoutManager
        }

        return view
    }
}