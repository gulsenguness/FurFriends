package com.gulsenurgunes.furfriends.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.gulsenurgunes.furfriends.data.source.remote.model.User
import com.gulsenurgunes.furfriends.component.toUserPassword


object UserMapper {
    fun map(user: FirebaseUser, password: String): User =
        user.toUserPassword(password)
}