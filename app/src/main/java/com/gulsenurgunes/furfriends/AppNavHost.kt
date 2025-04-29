package com.gulsenurgunes.furfriends

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.gulsenurgunes.furfriends.ui.auth.entercode.EnterCode
import com.gulsenurgunes.furfriends.ui.auth.enternewpassword.EnterNewPassword
import com.gulsenurgunes.furfriends.ui.auth.forgot.ForgotScreen
import com.gulsenurgunes.furfriends.ui.auth.signin.SignInScreen
import com.gulsenurgunes.furfriends.ui.auth.signup.SignUpScreen
import com.gulsenurgunes.furfriends.ui.auth.splash.PageOne
import com.gulsenurgunes.furfriends.ui.auth.splash.PageTwo
import com.gulsenurgunes.furfriends.ui.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = GraphRoutes.AUTH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        navigation(
            route = GraphRoutes.AUTH,
            startDestination = Screen.PageOne.route
        ) {
            composable(Screen.PageOne.route) {
                PageOne(onTimeout = {
                    navController.navigate(Screen.PageTwo.route) {
                        popUpTo(Screen.PageOne.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.PageTwo.route) {
                PageTwo(onGetStarted = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.PageTwo.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.SignIn.route) {
                SignInScreen(
                    onForgotPasswordClick = {
                        navController.navigate(Screen.Forgot.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    },
                    onHomeClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(Screen.SignUp.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onSignUpClick = {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    },
                    onSignInClick = {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.Forgot.route) { ForgotScreen() }
            composable(Screen.Code.route) { EnterCode() }
            composable(Screen.EnterNewPassword.route) { EnterNewPassword() }
        }
        navigation(
            route = GraphRoutes.HOME,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
        }
    }
}