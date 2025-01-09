package com.pinu.jetpackcomposemodularprojectdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavArguments
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.BookDetailRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.BookListRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.CartRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.DashboardRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.ProfileRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.JetpackComposeModularProjectDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val sharedViewModel: SharedViewModel by viewModels() // Only for shared data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeModularProjectDemoTheme(darkTheme = false) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.DashboardScreen.route) {

                    composable(route = NavigationRoutes.DashboardScreen.route) {
                        DashboardRootUI(navController, sharedViewModel)
                    }

                    composable(route = NavigationRoutes.BookListScreen.route) {
                        BookListRootUI(navController)
                    }

                    composable(route = NavigationRoutes.BookDetailScreen.route,
                        arguments = listOf(
                            navArgument(NavArguments.BOOK_ID) {
                                type = NavType.IntType
                                defaultValue = 0
                            }
                        )) { backEntry ->

                        val bookId = backEntry.arguments?.getInt(NavArguments.BOOK_ID, 0) ?: 0
                        BookDetailRootUI(navController, bookId, sharedViewModel)
                    }

                    composable(route = NavigationRoutes.CartScreen.route) {
                        CartRootUI(navController, sharedViewModel)
                    }

                    composable(route = NavigationRoutes.ProfileScreen.route) {
                        ProfileRootUI(navController, sharedViewModel)
                    }
                }
            }
        }
    }
}

