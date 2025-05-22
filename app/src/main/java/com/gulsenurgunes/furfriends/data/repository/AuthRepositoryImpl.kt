package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.mapResource
import com.gulsenurgunes.furfriends.component.toUserPassword
import com.gulsenurgunes.furfriends.data.datasource.AuthDataSource
import com.gulsenurgunes.furfriends.data.mapper.UserMapper
import com.gulsenurgunes.furfriends.data.safeApiCall
import com.gulsenurgunes.furfriends.data.source.remote.model.User
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
        safeApiCall {
            dataSource.createUser(name, email, password)
        }.mapResource { fbUser ->
            fbUser.toUserPassword(password)
        }

    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<User> =
        safeApiCall {
            dataSource.signIn(email, password)
        }.mapResource { fbUser ->
            UserMapper.map(fbUser, password)
        }


    override suspend fun signInWithGoogle(
        idToken: String
    ): Resource<User> =
        safeApiCall {
            dataSource.signInWithGoogle(idToken)
        }.mapResource { fbUser ->
            UserMapper.map(fbUser, password = "google")
        }
}

