package com.example.chatbox

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = ""
)

data class Message(
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0
)

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
    object Success : AuthState()
}
