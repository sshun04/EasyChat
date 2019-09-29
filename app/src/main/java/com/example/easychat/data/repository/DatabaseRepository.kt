package com.example.easychat.data.repository

import com.example.easychat.domain.model.Message
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface DatabaseRepository {
    suspend fun loadFirstPage(limit:Long):QuerySnapshot
    suspend fun loadFollowingPage(startAfter:DocumentSnapshot,limit: Long):QuerySnapshot
    suspend fun saveMessage(message: Message)
    fun onConnected()
    fun detach()
}