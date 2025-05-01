package com.gulsenurgunes.furfriends.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.gulsenurgunes.furfriends.domain.model.User

object UserMapper {
    fun fromFirebase(
        user: FirebaseUser,
        password: String
    ): User = User(
        uid      = user.uid,
        name     = user.displayName.orEmpty(),
        email    = user.email.orEmpty(),
        password = password
    )
}