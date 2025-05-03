package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.data.datasource.AuthDataSource
import com.gulsenurgunes.furfriends.data.mapper.UserMapper
import com.gulsenurgunes.furfriends.domain.model.User
import com.gulsenurgunes.furfriends.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<User> =
        try {
            val fbUser = dataSource.createUser(
                name = name,
                email = email,
                password = password,
            )
            val user = UserMapper.map(fbUser, password)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }

    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<User> =
        try {
            val fbUser = dataSource.signIn(
                email = email,
                password = password,
            )
            val user = UserMapper.map(fbUser, password)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login failed")
        }

    override suspend fun signInWithGoogle(
        idToken: String
    ): Resource<User> =
        try {
            val fbUser = dataSource.signInWithGoogle(idToken)
            val user = UserMapper.map(fbUser, password = "google")
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Google sign-in failed")
        }
}

