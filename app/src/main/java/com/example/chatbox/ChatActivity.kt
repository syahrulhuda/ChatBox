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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatbox.ui.theme.ChatBoxTheme
import com.google.firebase.auth.FirebaseAuth

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipientId = intent.getStringExtra("recipientId") ?: ""
        val recipientUsername = intent.getStringExtra("recipientUsername") ?: ""
        setContent {
            ChatBoxTheme {
                ChatScreen(recipientId = recipientId, recipientUsername = recipientUsername)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(chatViewModel: ChatViewModel = viewModel(), recipientId: String, recipientUsername: String) {
    val messages by chatViewModel.messages.collectAsState()
    var text by remember { mutableStateOf("") }
    val currentUser = FirebaseAuth.getInstance().currentUser

    val chatId = currentUser?.uid?.let { chatViewModel.getChatId(it, recipientId) } ?: ""
    LaunchedEffect(chatId) {
        chatViewModel.loadMessages(chatId)
    }

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
                    chatViewModel.sendMessage(chatId, text)
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
