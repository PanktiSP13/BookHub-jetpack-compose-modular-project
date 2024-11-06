package com.pinu.jetpackcomposemodularprojectdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.dashboard.DashboardRootUI
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.JetpackComposeModularProjectDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JetpackComposeModularProjectDemoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.DashboardScreen.route) {

                    composable(route = NavigationRoutes.DashboardScreen.route){
                        DashboardRootUI()
                    }
                }



            }

        }
    }
}
