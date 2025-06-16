package com.gulsenurgunes.furfriends.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gulsenurgunes.furfriends.ui.auth.entercode.EnterCode
import com.gulsenurgunes.furfriends.ui.auth.enternewpassword.EnterNewPassword
import com.gulsenurgunes.furfriends.ui.auth.forgot.ForgotScreen
import com.gulsenurgunes.furfriends.ui.auth.signin.SignInScreen
import com.gulsenurgunes.furfriends.ui.auth.signup.SignUpScreen
import com.gulsenurgunes.furfriends.ui.auth.splash.PageOne
import com.gulsenurgunes.furfriends.ui.auth.splash.PageTwo
import com.gulsenurgunes.furfriends.ui.category.CategoryScreen
import com.gulsenurgunes.furfriends.ui.categorygroup.CategoryGroup
import com.gulsenurgunes.furfriends.ui.detail.DetailScreen
import com.gulsenurgunes.furfriends.ui.favorites.FavoritesScreen
import com.gulsenurgunes.furfriends.ui.home.homefirst.HomeScreen
import com.gulsenurgunes.furfriends.ui.mycart.MyCartScreen
import com.gulsenurgunes.furfriends.ui.navigation.BottomNavigationBar
import com.gulsenurgunes.furfriends.ui.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = GraphRoutes.AUTH
) {
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStack?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute in bottomTabs.map { it.route }) {
                BottomNavigationBar(navController, currentRoute!!)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            navigation(
                route = GraphRoutes.AUTH,
                startDestination = Screen.PageOne.route,
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
                        onNavigateHome = {
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
                    HomeScreen(navController)
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen()
                }
                composable(Screen.MyCart.route) {
                    MyCartScreen()
                }
                composable(Screen.Category.route) {
                    CategoryScreen(navController)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
                composable(
                    route = Screen.CategoryGroup.route,
                    arguments = listOf(
                        navArgument("categoryKey") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    CategoryGroup(
                        navController = navController,
                    )
                }
                composable(
                    route = "detail/{productId}",
                    arguments = listOf(navArgument("productId") {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId")
                    if (productId != null) {
                        DetailScreen(navController)
                    }
                }

            }
        }
    }
}