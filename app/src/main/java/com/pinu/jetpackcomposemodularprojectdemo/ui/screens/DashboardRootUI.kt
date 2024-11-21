package com.pinu.jetpackcomposemodularprojectdemo.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.domain.entities.viewmodels.DashboardViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.TextPrimary
import com.pinu.jetpackcomposemodularprojectdemo.ui.util.CommonAlertDialog

@Preview(showBackground = true)
@Composable
fun DashboardRootUI(
    navController: NavController = rememberNavController(),
    viewmodel: DashboardViewModel = DashboardViewModel()
) {

    val showExitDialog = remember { mutableStateOf(false) }
    val activity = LocalContext.current as? Activity  // Get the current activity


    // Define an infinite transition
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Create scale and color animations
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // Intercept back press
    BackHandler {
        showExitDialog.value = true
    }

    Scaffold(
        topBar = {
            CommonAppBar(title = "Dashboard", navController = navController)
        },

    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .background(Color.White),
            color = Color.White) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .indication(
                            indication = ripple(bounded = false, radius = 100.dp),
                            interactionSource = remember { MutableInteractionSource() })
                        .clickable {
                            navController.navigate(route = NavigationRoutes.BookListScreen.route)
                        }) {
                    Image(
                        painter = painterResource(id = R.drawable.opendoor),
                        contentDescription = "open door",
                        modifier = Modifier
                            .size(100.dp, 120.dp)
                            .scale(scale),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.press_me),
                        textAlign = TextAlign.Center,
                        style = BookHubTypography.bodyLarge.copy(color = TextPrimary),
                        modifier = Modifier.scale(scale)
                    )
                }


            }

        }
    }

    if (showExitDialog.value) {
        CommonAlertDialog(title = stringResource(R.string.exit_app),
            text = stringResource(R.string.are_you_sure_you_want_to_exit),
            negativeButtonText = stringResource(R.string.no),
            positiveButtonText = stringResource(R.string.yes),
            onNegativeButtonClicked = { showExitDialog.value = false },
            onPositiveButtonClicked = {
                showExitDialog.value = false
                // This allows you to close the activity,
                // effectively closing the app.
                activity?.finish()
            })
    }
}

