package com.pinu.jetpackcomposemodularprojectdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.domain.entities.viewmodels.CartViewModel
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.books.screens.BookDetailRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.books.screens.BookListRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.CartRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.DashboardRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens.ProfileRootUI
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.JetpackComposeModularProjectDemoTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val booksViewModel : BooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//       not required edge-to-edge
//        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(ContextCompat.getColor(this,R.color.black)))


        setContent {
            JetpackComposeModularProjectDemoTheme(darkTheme = false) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.DashboardScreen.route
                ) {

                    composable(route = NavigationRoutes.DashboardScreen.route) {
                        DashboardRootUI(navController)
                    }

                    composable(route = NavigationRoutes.BookListScreen.route) {
                        BookListRootUI(navController,booksViewModel)
                    }

                    composable(route = NavigationRoutes.BookDetailScreen.route) {
                        BookDetailRootUI(navController,booksViewModel)
                    }

                    composable(route = NavigationRoutes.CartScreen.route) {
                        val cartViewModel : CartViewModel by viewModels()
                        CartRootUI(navController,cartViewModel)
                    }

                    composable(route = NavigationRoutes.ProfileScreen.route) {
                        ProfileRootUI(navController)
                    }


                }


            }

        }
    }
}
