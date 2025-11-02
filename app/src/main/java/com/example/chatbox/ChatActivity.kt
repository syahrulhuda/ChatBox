package com.example.chatbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatbox.ui.theme.ChatBoxTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class Message(
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0
)

class ChatActivity : ComponentActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipientId = intent.getStringExtra("recipientId") ?: ""
        val recipientUsername = intent.getStringExtra("recipientUsername") ?: ""

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference

        val currentUser = auth.currentUser
        val chatId = getChatId(currentUser?.uid ?: "", recipientId)

        setContent {
            ChatBoxTheme {
                val messages = remember { mutableStateListOf<Message>() }

                LaunchedEffect(chatId) {
                    val chatRef = database.child("chats").child(chatId).child("messages")
                    val messageListener = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            messages.clear()
                            for (messageSnapshot in snapshot.children) {
                                val message = messageSnapshot.getValue(Message::class.java)
                                if (message != null) {
                                    messages.add(message)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    }
                    chatRef.addValueEventListener(messageListener)
                }

                ChatScreen(
                    recipientUsername = recipientUsername,
                    messages = messages,
                    onSendMessage = { text ->
                        sendMessage(chatId, text)
                    }
                )
            }
        }
    }

    private fun sendMessage(chatId: String, text: String) {
        val currentUser = auth.currentUser
        val message = Message(
            senderId = currentUser?.uid ?: "",
            text = text,
            timestamp = System.currentTimeMillis()
        )
        database.child("chats").child(chatId).child("messages").push().setValue(message)
    }

    private fun getChatId(userId1: String, userId2: String): String {
        return if (userId1 > userId2) {
            "$userId1-$userId2"
        } else {
            "$userId2-$userId1"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(recipientUsername: String, messages: List<Message>, onSendMessage: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val currentUser = Firebase.auth.currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipientUsername) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp),
                reverseLayout = true
            ) {
                items(messages.reversed()) { message ->
                    MessageItem(message = message, isSentByCurrentUser = message.senderId == currentUser?.uid)
                }
            }
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Message") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { 
                    onSendMessage(text)
                    text = ""
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, isSentByCurrentUser: Boolean) {
    val alignment = if (isSentByCurrentUser) Alignment.End else Alignment.Start
    val backgroundColor = if (isSentByCurrentUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Text(
            text = message.text,
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(8.dp))
                .padding(8.dp)
        )
    }
}
