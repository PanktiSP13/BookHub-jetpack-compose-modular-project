package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.CartItem
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.dialog.PaymentSuccessfulDialog
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.CommonAlertDialog

@Preview(showBackground = true)
@Composable
fun CartRootUI(navController: NavController= rememberNavController()){
    val showAlert = remember { mutableStateOf(false) }
    val showPaymentSuccessDialog = remember { mutableStateOf(false) }

    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.cart),
                canGoBack = true, navController = navController,
                isCartVisible = false, isFavouritesVisible = false)
        },
        bottomBar = {
            Button(
                onClick = {
                    showPaymentSuccessDialog.value = true
                },
                elevation = ButtonDefaults.elevatedButtonElevation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryVariant
                )
            ) {
                Text(
                    text = stringResource(R.string.proceed_to_checkout),
                    color = OnPrimaryColor
                )
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier.padding(contentPadding),
            color = Color.White
        ) {

            LazyColumn {
                items(20){
                    CartItem()
                }
            }
        }
    }

    if (showAlert.value) {
        CommonAlertDialog(
            onNegativeButtonClicked = {
                showAlert.value = false
            },
            onPositiveButtonClicked = {
                showAlert.value = false
            })
    }
    if (showPaymentSuccessDialog.value) {
        PaymentSuccessfulDialog(onDismiss = {
            showPaymentSuccessDialog.value = false
        }, onContinueShoppingClicked = {
            showPaymentSuccessDialog.value = false

            // redirect back to dashboard with removing all stack entries
            navController.navigate(NavigationRoutes.DashboardScreen.route) {
                // Clear the stack up to the root
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }

        })
    }


}
