package com.gulsenurgunes.furfriends.navigation

sealed class Screen(val route: String) {
    data object PageOne : Screen("page_one")
    data object PageTwo : Screen("page_two")
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object Forgot : Screen("forgot_password")
    data object Code : Screen("enter_code")
    data object EnterNewPassword : Screen("enter_new_password")
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object MyCart : Screen("mycart")
    data object Category : Screen("category")
    data object Profile : Screen("profile")
    data object CategoryGroup : Screen("category_group/{categoryKey}") {
        fun createRoute(categoryKey: String) = "category_group/$categoryKey"
    }
    data object Detail : Screen("detail/{productId}") {
        fun createRoute(productId: Int): String = "detail/$productId"
    }

}

