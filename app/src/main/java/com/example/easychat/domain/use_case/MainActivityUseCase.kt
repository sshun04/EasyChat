package com.example.easychat.domain.use_case

import com.example.easychat.data.repository.DatabaseRepository
import com.example.easychat.data.repository.impl.FireStoreDatabaseRepository
import com.example.easychat.domain.model.Message
import com.google.firebase.firestore.DocumentSnapshot

class MainActivityUseCase(onNewMessage: (Message) -> Unit) {
    private val databaseRepository: DatabaseRepository = FireStoreDatabaseRepository(onNewMessage)
    private var pageLastDocument: DocumentSnapshot? = null
    private var haveMoreMessage: Boolean = true

    suspend fun saveMessage(messageText: String) {
        val message = Message(message = messageText)
        databaseRepository.saveMessage(message)
    }

    suspend fun loadMessages(): Pair<List<Message>, Boolean> {
        val sizeLimit = 30L
        val querySnapshot =
            if (pageLastDocument != null) databaseRepository.loadFollowingPage(pageLastDocument!!,sizeLimit)
            else databaseRepository.loadFirstPage(sizeLimit)

        haveMoreMessage = querySnapshot.count() == sizeLimit.toInt()
        if (haveMoreMessage){
            pageLastDocument = querySnapshot.last()
        }

        val messageList = querySnapshot.map { it.toObject(Message::class.java) }

        return Pair(messageList, haveMoreMessage)
    }
}