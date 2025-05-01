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

        email: String,
        password: String
    ): Resource<User> =
        try {
            val fbUser = dataSource.createUser(
                email = email,
                password = password,
            )
            val user = UserMapper.fromFirebase(fbUser)
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
            val user = UserMapper.fromFirebase(fbUser)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login failed")
        }
}