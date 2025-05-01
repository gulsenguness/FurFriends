package com.gulsenurgunes.furfriends.data.datasource

import com.google.firebase.auth.FirebaseUser

interface AuthDataSource {
    suspend fun createUser(name: String, email: String, password: String): FirebaseUser
    suspend fun signIn(email: String, password: String): FirebaseUser
}