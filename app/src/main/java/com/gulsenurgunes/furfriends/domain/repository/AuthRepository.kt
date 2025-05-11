package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.data.source.remote.model.User

interface AuthRepository {
    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<User>

    suspend fun signIn(
        email: String,
        password: String
    ): Resource<User>

    suspend fun signInWithGoogle(
        idToken: String
    ): Resource<User>

}