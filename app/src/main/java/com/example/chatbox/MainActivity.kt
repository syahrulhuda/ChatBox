package com.example.chatbox

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatbox.ui.theme.ChatBoxTheme
import com.example.chatbox.UserItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatBoxTheme {
                UserListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(mainViewModel: MainViewModel = viewModel()) {
    val users by mainViewModel.users.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") },
                actions = {
                    IconButton(onClick = { 
                        mainViewModel.logout()
                        context.startActivity(Intent(context, AuthActivity::class.java))
                        (context as? ComponentActivity)?.finish()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    mainViewModel.searchUsers(it)
                },
                label = { Text("Search users") },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn {
                items(users) { user ->
                    ListItem(
                        headlineContent = { Text(user.username) },
                        modifier = Modifier.clickable { 
                            val intent = Intent(context, ChatActivity::class.java)
                            intent.putExtra("recipientId", user.uid)
                            intent.putExtra("recipientUsername", user.username)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    ChatBoxTheme {
        UserListScreen()
    }
}