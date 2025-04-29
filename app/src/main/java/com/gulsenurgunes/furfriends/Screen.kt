package com.gulsenurgunes.furfriends

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object PageOne : Screen("page_one")

    @Serializable
    data object PageTwo : Screen("page_two")

    @Serializable
    data object SignIn : Screen("sign_in")

    @Serializable
    data object SignUp : Screen("sign_up")

    @Serializable
    data object Forgot : Screen("forgot_password")

    @Serializable
    data object Code : Screen("enter_code")

    @Serializable
    data object EnterNewPassword : Screen("enter_new_password")

    @Serializable
    data object Home : Screen("home")
}