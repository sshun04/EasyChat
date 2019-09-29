package com.example.easychat.data.repository.impl

import com.example.easychat.data.repository.DatabaseRepository
import com.example.easychat.domain.model.Message
import com.google.firebase.firestore.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*

class FireStoreDatabaseRepository(private val onNewMessage: (Message) -> Unit) :
    DatabaseRepository {
    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        dataBase.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    companion object {
        private const val COLLECTION_PATH = "messages"
    }

    private val query = dataBase.collection(COLLECTION_PATH)
    private lateinit var registration: ListenerRegistration
    override suspend fun saveMessage(message: Message) {
        query
            .document()
            .set(message).await()
    }

    override suspend fun loadFirstPage(limit: Long): QuerySnapshot = runBlocking {
        val querySnapshot = query
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()
        onConnected()
        return@runBlocking querySnapshot
    }

    override suspend fun loadFollowingPage(
        startAfter: DocumentSnapshot,
        limit: Long
    ): QuerySnapshot = runBlocking {
        val querySnapshot = query.orderBy("timestamp", Query.Direction.DESCENDING)
            .startAfter(startAfter)
            .limit(limit)
            .get()
            .await()
        return@runBlocking querySnapshot
    }

    override fun onConnected() {
        registration = query
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .startAfter(Calendar.getInstance().time)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) return@addSnapshotListener
                for (dc in querySnapshot!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> onNewMessage(dc.document.toObject(Message::class.java))
                    }
                }
            }
    }

    override fun detach() {
        registration.remove()
    }


}