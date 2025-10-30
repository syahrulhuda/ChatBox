package com.example.chatbox

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Message(val text: String = "", val senderId: String = "", val timestamp: Long = 0)

class ChatViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(chatId: String, text: String) {
        val currentUser = auth.currentUser ?: return
        val message = Message(text, currentUser.uid, System.currentTimeMillis())
        database.getReference("chats").child(chatId).child("messages").push().setValue(message)
    }

    fun loadMessages(chatId: String) {
        val messagesRef = database.getReference("chats").child(chatId).child("messages")
        messagesRef.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val messageList = mutableListOf<Message>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let { messageList.add(it) }
                }
                _messages.value = messageList
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                // Handle error
            }
        })
    }

    fun getChatId(userId1: String, userId2: String): String {
        return if (userId1 < userId2) {
            "$userId1-$userId2"
        } else {
            "$userId2-$userId1"
        }
    }
}
