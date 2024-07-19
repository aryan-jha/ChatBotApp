package com.example.chatbot.Domain.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.Domain.Secrets.API_KEY
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class chatViewModel : ViewModel() {

    val messageList by lazy { mutableStateListOf<MessageModel>() }

    val geminiModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = API_KEY
    )

    fun sendMessage(question: String) {

        try {
            viewModelScope.launch {
                val chat = geminiModel.startChat(
                    history = messageList.map{
                        content(role = it.role){
                            text(it.message)
                        }
                    }.toList()
                )

                messageList.add(MessageModel(message = question, role = "user"))
                messageList.add(MessageModel(message = "Typing...", role = "model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(message = response.text.toString(), role = "model"))
            }
        }catch (e:Exception){
            messageList.removeLast()
            messageList.add(MessageModel(message = e.message.toString(), role = "model"))
        }

    }

}