package com.gulsenurgunes.furfriends.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.gulsenurgunes.furfriends.domain.model.User

object UserMapper {
    fun fromFirebase(
        user: FirebaseUser
    ): User {
        return User(
            uid = user.uid,
            email = user.email ?: "",
        )
    }
}