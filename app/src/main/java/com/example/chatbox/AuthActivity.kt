package com.example.chatbox

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatbox.ui.theme.ChatBoxTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.chatbox.User

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = ""
)

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
    object Success : AuthState()
}

class AuthActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            ChatBoxTheme {
                AuthScreen(
                    onLogin = { email, password ->
                        loginUser(email, password)
                    },
                    onRegister = { email, password, username ->
                        registerUser(email, password, username)
                    }
                )
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val uid = firebaseUser?.uid
                    if (uid != null) {
                        val database = FirebaseDatabase.getInstance().getReference("users")
                        val user = User(uid, username, email)
                        database.child(uid).setValue(user)
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}

@Composable
fun AuthScreen(onLogin: (String, String) -> Unit, onRegister: (String, String, String) -> Unit) {
    var isLogin by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var authState by remember { mutableStateOf<AuthState>(AuthState.Unauthenticated) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = if (isLogin) "Login" else "Register", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            if (!isLogin) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = authState != AuthState.Loading
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = authState != AuthState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                enabled = authState != AuthState.Loading
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    authState = AuthState.Loading
                    if (isLogin) {
                        onLogin(email, password)
                    } else {
                        onRegister(email, password, username)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = authState != AuthState.Loading
            ) {
                Text(text = if (isLogin) "Login" else "Register")
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { isLogin = !isLogin }) {
                Text(text = if (isLogin) "Don't have an account? Register" else "Already have an account? Login")
            }
        }
        if (authState == AuthState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
