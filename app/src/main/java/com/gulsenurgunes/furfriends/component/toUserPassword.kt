package com.gulsenurgunes.furfriends.component

import com.google.firebase.auth.FirebaseUser
import com.gulsenurgunes.furfriends.data.source.remote.model.User

fun FirebaseUser.toUserPassword(password: String): User =
    User(
        uid = this.uid,
        name = this.displayName.orEmpty(),
        email = this.email.orEmpty(),
        password = password
    )