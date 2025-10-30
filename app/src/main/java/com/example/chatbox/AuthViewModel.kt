package com.example.chatbox

package com.example.chatbox

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String) {
        // TODO: Implement login logic
    }

    fun register(email: String, password: String, username: String) {
        // TODO: Implement register logic
    }
}
