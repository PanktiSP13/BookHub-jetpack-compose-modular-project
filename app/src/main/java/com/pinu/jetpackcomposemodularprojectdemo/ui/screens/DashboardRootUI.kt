package com.pinu.jetpackcomposemodularprojectdemo.ui.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink

@Preview(showBackground = true)
@Composable
fun DashboardRootUI(navController: NavController = rememberNavController()) {

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

    Scaffold(
        topBar = {
            CommonAppBar(title = "Dashboard",navController = navController)
        },
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .background(Color.White)
        ) {
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
                        painter = painterResource(id = R.drawable.open),
                        contentDescription = "open door",
                        modifier = Modifier
                            .size(150.dp, 250.dp)
                            .scale(scale),
                    )
                    Text(
                        text = "Press me !!", textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Pink,
                            fontWeight = FontWeight.W600,
                            fontSize = 12.sp
                        ), modifier = Modifier.scale(scale)
                    )
                }


            }

        }
    }

}

