package com.example.easychat.presentation.scene.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easychat.domain.model.Message
import com.example.easychat.domain.use_case.MainActivityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val useCase = MainActivityUseCase {
        onNewMessage(it)
    }
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var haveMoreMessages: Boolean = true

    private val _messages: MutableLiveData<MutableList<Message>> = MutableLiveData()
    val messages: LiveData<MutableList<Message>> get() = _messages

    private val _newMessage: MutableLiveData<Message> = MutableLiveData()
    val newMessage: LiveData<Message> get() = _newMessage

    fun reload() {
        _messages.value?.clear()
        haveMoreMessages = true
        loadMessages()
    }

    fun onScrollOldest() {
        if (haveMoreMessages) {
            loadMessages()
        }
    }

    fun sendMessage(messageText: String) {
        coroutineScope.launch {
            useCase.saveMessage(messageText)
        }
    }

    private fun loadMessages() {
        coroutineScope.launch {
            val result = useCase.loadMessages()
            val list = result.first.toMutableList()
            haveMoreMessages = result.second
            launch(Dispatchers.IO) {
                _messages.postValue(list)
            }
        }
    }

    private fun onNewMessage(message: Message) {
        _newMessage.postValue(message)
    }

}