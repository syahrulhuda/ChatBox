package com.example.chatbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.chatbox.ui.theme.ChatBoxTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatBoxTheme {
                AuthScreen()
            }
        }
    }
}

@Composable
fun AuthScreen() {
    Text(text = "Login/Register Screen")
}
