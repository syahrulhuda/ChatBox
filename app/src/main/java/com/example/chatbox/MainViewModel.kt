package com.example.chatbox

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    fun searchUsers(query: String) {
        val currentUser = auth.currentUser ?: return
        val usersRef = database.getReference("users")

        usersRef.orderByChild("username").startAt(query).endAt(query + "\uf8ff")
            .get()
            .addOnSuccessListener { dataSnapshot ->
                val userList = mutableListOf<User>()
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null && snapshot.key != currentUser.uid) {
                        userList.add(user)
                    }
                }
                _users.value = userList
            }
    }

    fun logout() {
        auth.signOut()
    }
}
