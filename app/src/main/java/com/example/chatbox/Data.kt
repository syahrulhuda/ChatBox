package com.example.chatbox

data class User(val username: String = "", val email: String = "")

data class UserItem(val uid: String = "", val username: String = "")

data class Message(val senderId: String = "", val receiverId: String = "", val text: String = "", val timestamp: Long = 0)