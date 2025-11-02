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
import com.example.chatbox.ui.theme.ChatBoxTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = ""
)

class MainActivity : ComponentActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().getReference("users")

        setContent {
            ChatBoxTheme {
                val userList = remember { mutableStateListOf<User>() }

                LaunchedEffect(Unit) {
                    database.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            userList.clear()
                            for (userSnapshot in snapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)
                                if (user != null && user.uid != auth.currentUser?.uid) {
                                    userList.add(user)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }

                UserListScreen(
                    users = userList,
                    onSearch = { query ->
                        // Implement search logic if needed
                    },
                    onLogout = {
                        auth.signOut()
                        startActivity(Intent(this, AuthActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(users: List<User>, onSearch: (String) -> Unit, onLogout: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") },
                actions = {
                    IconButton(onClick = onLogout) {
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
                    onSearch(it)
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
        UserListScreen(users = listOf(User(username = "User 1"), User(username = "User 2")), onSearch = {}, onLogout = {})
    }
}